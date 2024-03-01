import { test, expect } from "@playwright/test"
import { amendingLaws, getExpectedHeading } from "@e2e/testData/testData"

for (const amendingLaw of amendingLaws) {
  test(`navigate and verify header for ${amendingLaw.eli}`, async ({
    page,
  }) => {
    await page.goto(`/amending-laws/${amendingLaw.eli}`)

    await expect(page.locator(".ds-heading-03-reg")).toHaveText(
      getExpectedHeading(amendingLaw),
    )

    // TODO
  })
}
