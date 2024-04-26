import { test, expect } from "@playwright/test"
import { amendingLaws, getExpectedHeading } from "@e2e/testData/testData"

test.describe("Redirect and start page content", () => {
  for (const amendingLaw of amendingLaws) {
    test(`redirect to amending laws page and test content for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      await page.goto("/")
      await expect(page).toHaveURL("/amending-laws")

      await expect(
        page.getByText(getExpectedHeading(amendingLaw)),
      ).toBeVisible()
      await expect(
        page.getByText(convertToGermanDate(amendingLaw.frbrDateVerkuendung!)),
      ).toBeVisible()
    })
  }
})

function convertToGermanDate(isoDate: string): string {
  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }
  return new Date(isoDate).toLocaleDateString("de-DE", options)
}
