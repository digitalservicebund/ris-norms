import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.describe("Affected documents page", () => {
  for (const amendingLaw of amendingLaws) {
    test(`navigate and verify navigation to affected documents for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      // Navigation
      await page.goto(`/amending-laws/${amendingLaw.eli}/affected-documents`)

      // Menu
      const locator = page.locator(`a:has-text("Betroffene Normenkomplexe")`)
      await expect(locator).toHaveClass(/router-link-active/)
      await expect(locator).toHaveClass(/bg-blue-200/)

      // Content
      // eslint-disable-next-line playwright/no-conditional-in-test
      for (const article of amendingLaw.articles ?? []) {
        await expect(
          page.getByText(`Artikel ${article.enumeration}`),
        ).toBeVisible()
        await expect(page.getByText(article.eid)).toBeVisible()
      }

      await expect(page.getByText("Metadaten editieren")).toBeVisible()

      // Back
      await page.getByText("Zurück").click()
      await expect(page).toHaveURL("/amending-laws")
    })
  }
})
