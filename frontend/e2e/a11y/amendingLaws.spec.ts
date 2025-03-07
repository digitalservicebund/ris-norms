import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { logAccessibilityViolations, useAxeBuilder } from "../utils/a11y"

test.describe(
  "Accessibility check for Announcements Page",
  { tag: ["@RISDEV-6515"] },
  () => {
    test("Should have no detectable accessibility violations", async ({
      page,
    }) => {
      await page.goto("./amending-laws")
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
