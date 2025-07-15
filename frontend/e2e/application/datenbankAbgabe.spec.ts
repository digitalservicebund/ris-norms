import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe(
  "Abgabe view from within the database",
  { tag: ["@RISDEV-7186"] },
  () => {
    test.beforeAll(async ({ authenticatedRequest }) => {
      await uploadAmendingLaw(
        authenticatedRequest,
        "aenderungsgesetz-abgabe",
        frontendTestDataDirectory,
      )
    })

    test("shows expressions in Abgabe view", async ({ page }) => {
      await page.goto("./datenbank/eli/bund/bgbl-1/2025/9999/abgabe")

      await expect(
        page.getByRole("cell", {
          name: "eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu",
        }),
      ).toBeVisible()
      await expect(page.getByRole("cell", { name: "11.06.2025" })).toBeVisible()
    })

    test("shows empty state when no expressions exist", async ({ page }) => {
      await page.route(
        "**/api/v1/norms/eli/bund/bgbl-1/2025/9999/releases",
        async (route) => {
          await route.fulfill({
            status: 200,
            contentType: "application/json",
            body: JSON.stringify({
              title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
              shortTitle: "Vereinsgesetz",
              normWorkEli: "eli/bund/bgbl-1/2025/9999",
              expressions: [],
            }),
          })
        },
      )

      await page.goto("./datenbank/eli/bund/bgbl-1/2025/9999/abgabe")

      await expect(
        page.getByText(
          "Es sind keine unveröffentlichten Expressionen für diese Norm vorhanden.",
        ),
      ).toBeVisible()
    })

    test("publishes and shows success toast", async ({ page }) => {
      await page.goto("./datenbank/eli/bund/bgbl-1/2025/9999/abgabe")

      await page.getByRole("button", { name: "Abgeben" }).click()

      const dialog = page.getByRole("alertdialog")
      await dialog.getByRole("button", { name: "Abgeben" }).click()

      await expect(page.getByText("Abgabe erfolgreich")).toBeVisible()
    })
  },
)
