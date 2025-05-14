import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "open start page and no errors",
  { tag: ["@RISDEV-6811"] },
  () => {
    test("start page does not show errors", async ({
      page,
      appCredentials,
    }) => {
      await page.goto("./verkuendungen")
      await expect(page.getByText("Anmelden bei NeuRIS Staging")).toBeVisible()
      await page
        .getByRole("textbox", { name: "E-Mailadresse" })
        .fill(appCredentials.username)

      await page
        .getByRole("textbox", { name: "Passwort" })
        .fill(appCredentials.password)

      await page.getByRole("button", { name: "Anmelden" }).click()

      await page.waitForURL("/app/verkuendungen")

      await expect(
        page.getByRole("heading", { name: "Verkündungen" }),
      ).toBeVisible()

      await expect(page.getByRole("alert")).toHaveCount(0)
    })
  },
)
