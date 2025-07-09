import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { logAccessibilityViolations, useAxeBuilder } from "../utils/a11y"

test.describe(
  "accessibility check for Verkuendung Zeitgrenzen page",
  { tag: ["@RISDEV-6946"] },
  () => {
    test("should have no detectable accessibility violations", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      await expect(
        page.getByRole("complementary", { name: "Änderungsgesetz" }),
      ).toBeVisible()

      const accessibilityScanResults = await useAxeBuilder(page)
        // Disable empty-heading rule since the empty headings are generated
        // from backend content and cannot be modified at the frontend level.
        // Also disable ARIA issues from the PrimeVue Splitter component and
        // heading-order issues from backend content
        .disableRules([
          "empty-heading",
          "aria-allowed-attr",
          "aria-required-attr",
          "heading-order",
          "color-contrast",
        ])
        .analyze()
      logAccessibilityViolations(accessibilityScanResults.violations)
      expect(accessibilityScanResults.violations).toHaveLength(0)
    })
  },
)
