import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe("Abgabe view with expressions", { tag: ["@RISDEV-7186"] }, () => {
  test.beforeAll(async ({ authenticatedRequest }) => {
    await uploadAmendingLaw(
      authenticatedRequest,
      "aenderungsgesetz-abgabe",
      frontendTestDataDirectory,
    )
  })

  test("shows expressions in Abgabe view", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu/zielnorm/eli/bund/bgbl-1/1964/1234/abgabe",
    )

    const rows = page.getByRole("row")

    const expectedRows = [
      {
        eli: "eli/bund/bgbl-1/1964/1234/1964-09-21/1/deu",
        date: "21.09.1964 – 31.12.2023",
        status: "Noch nicht abgegeben",
      },
      {
        eli: "eli/bund/bgbl-1/1964/1234/2024-01-01/1/deu",
        date: "01.01.2024",
        status: "Noch nicht abgegeben",
      },
    ]

    for (const { eli, date, status } of expectedRows) {
      const row = rows.filter({ hasText: eli })
      await expect(row).toContainText(eli)
      await expect(row).toContainText(date)
      await expect(row).toContainText(status)
    }
  })

  test("shows empty state when no expressions exist", async ({ page }) => {
    await page.route(
      "**/api/v1/norms/eli/bund/bgbl-1/1964/0000/releases",
      async (route) => {
        await route.fulfill({
          status: 200,
          contentType: "application/json",
          body: JSON.stringify({
            title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
            shortTitle: "Vereinsgesetz",
            normWorkEli: "eli/bund/bgbl-1/1964/0000",
            expressions: [],
          }),
        })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu/zielnorm/eli/bund/bgbl-1/1964/0000/abgabe",
    )

    await expect(
      page.getByText(
        "Es sind keine unveröffentlichten Expressionen für diese Norm vorhanden.",
      ),
    ).toBeVisible()
  })

  test("publishes Prätext and shows success toast", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu/zielnorm/eli/bund/bgbl-1/1964/1234/abgabe",
    )

    await page.getByRole("button", { name: "Prätexte abgeben" }).click()

    const dialog = page.getByRole("alertdialog")
    await dialog.getByRole("button", { name: "Abgeben" }).click()

    await expect(page.getByText("Abgabe erfolgreich")).toBeVisible()

    const rows = page.getByRole("row", { name: /eli\/bund/ })
    const expectedStatus = "Prätext abgegeben"

    const rowCount = await rows.count()
    for (let i = 0; i < rowCount; i++) {
      await expect(rows.nth(i)).toContainText(expectedStatus)
    }
  })

  test("publishes Volldokumentationen and shows success toast", async ({
    page,
  }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu/zielnorm/eli/bund/bgbl-1/1964/1234/abgabe",
    )

    await page
      .getByRole("button", { name: "Volldokumentationen abgeben" })
      .click()

    const dialog = page.getByRole("alertdialog")
    await dialog.getByRole("button", { name: "Abgeben" }).click()

    await expect(page.getByText("Abgabe erfolgreich")).toBeVisible()

    const rows = page.getByRole("row", { name: /eli\/bund/ })
    const expectedStatus = "Volldokumentation abgegeben"

    const rowCount = await rows.count()
    for (let i = 0; i < rowCount; i++) {
      const statusChip = rows.nth(i).getByText(expectedStatus, { exact: true })
      await expect(statusChip).toBeVisible()
    }
  })
})
