import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.describe("Articles page", () => {
  for (const amendingLaw of amendingLaws) {
    test(`navigate and verify navigation to articles page for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      // Navigation
      await page.goto(`/amending-laws/${amendingLaw.eli}/articles`)

      // Menu
      const locator = page.locator(`a:has-text("Artikelübersicht")`)
      await expect(locator).toHaveClass(/router-link-active/)
      await expect(locator).toHaveClass(/bg-blue-200/)

      // Content
      // eslint-disable-next-line playwright/no-conditional-in-test
      for (const article of amendingLaw.articles ?? []) {
        await expect(
          page.getByText(`Artikel ${article.enumeration}`),
        ).toBeVisible()
        await expect(
          page.getByText(article.title, {
            exact: true,
          }),
        ).toBeVisible()
      }

      const checkChangeCommandButton = page.locator(
        'text="Änderungsbefehl prüfen"',
      )
      await expect(checkChangeCommandButton).toBeVisible()

      // Back
      await page.click("text=Zurück")
      await expect(page).toHaveURL("/amending-laws")
    })
  }
})
