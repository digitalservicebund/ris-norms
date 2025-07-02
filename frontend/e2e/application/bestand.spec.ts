import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "Bestand view with list of works",
  { tag: ["@RISDEV-8331"] },
  () => {
    test("shows expressions in Abgabe view", async ({ page }) => {
      await page.goto("./bestand")

      await expect(page.getByRole("heading", { name: "Bestand" })).toBeVisible()
    })
  },
)
