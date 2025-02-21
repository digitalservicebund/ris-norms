import { amendingLaws, getExpectedHeading } from "@e2e/testData/testData"
import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("Info header", () => {
  for (const amendingLaw of amendingLaws) {
    test(`navigate and verify header for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      await page.goto(`/amending-laws/${amendingLaw.eli}`)

      await expect(
        page.getByText(getExpectedHeading(amendingLaw)),
      ).toBeVisible()
      await expect(
        page.getByText(amendingLaw.title ?? "").first(),
      ).toBeVisible()
    })
  }
})
