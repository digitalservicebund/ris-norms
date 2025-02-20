import { AxeBuilder } from "@axe-core/playwright"
import { Page } from "@playwright/test"
import { Result as AxeViolation } from "axe-core"

export const useAxeBuilder = (page: Page) =>
  new AxeBuilder({ page }).exclude(".vue-devtools__panel")

export function logAccessibilityViolations(violations: AxeViolation[]): void {
  if (violations.length > 0) {
    console.log("\n🔴 Accessibility violations found:")
    console.table(
      violations.map(({ id, impact, help, helpUrl }) => ({
        Rule: id,
        Impact: impact,
        Description: help,
        More_Info: helpUrl,
      })),
    )
  }
}
