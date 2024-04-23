import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.describe("Articles page", () => {
  test(`navigate to articles overview`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
    await page.getByText("Artikelübersicht").click()

    await expect(page).toHaveURL(
      `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles`,
    )
    await expect(
      page.getByRole("heading", { name: "Enthaltene Artikel" }),
    ).toBeVisible()
  })

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

      const checkChangeCommandButton = page
        .getByText("Änderungsbefehl prüfen")
        .first()
      await expect(checkChangeCommandButton).toBeVisible()

      // Back
      await page.getByText("Zurück").click()
      await expect(page).toHaveURL("/amending-laws")
    })
  }
})
