import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { logAccessibilityViolations, useAxeBuilder } from "../utils/a11y"

test.describe(
  "accessibility check for Verkuendungen page",
  { tag: ["@RISDEV-6515"] },
  () => {
    test("should have no detectable accessibility violations", async ({
      page,
    }) => {
      await page.goto("./verkuendungen")
      await expect(
        page.getByRole("button", {
          name: "Verkündung manuell hinzufügen",
          exact: true,
        }),
      ).toBeVisible()

      const accessibilityScanResults = await useAxeBuilder(page).analyze()
      logAccessibilityViolations(accessibilityScanResults.violations)
      expect(accessibilityScanResults.violations).toHaveLength(0)
    })
  },
)
