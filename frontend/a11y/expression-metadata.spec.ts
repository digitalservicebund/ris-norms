import { test, expect } from "@playwright/test"
import { useAxeBuilder, logAccessibilityViolations } from "./a11y-utils"

test.describe("Accessibility check for Announcements Page", () => {
  test("Should have no detectable accessibility violations", async ({
    page,
  }) => {
    await page.goto("/amending-laws")
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
})
