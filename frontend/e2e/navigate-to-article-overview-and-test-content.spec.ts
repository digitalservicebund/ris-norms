import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

for (const amendingLaw of amendingLaws) {
  test(`navigate and verify navigation to article overview for ${amendingLaw.eli}`, async ({
    page,
  }) => {
    // Navigation
    await page.goto("/")
    await page.click(`a[href*="${amendingLaw.eli}"]`)
    await expect(page).toHaveURL(
      `/amendinglaws/${amendingLaw.eli}/article-overview`,
    )
    await expect(
      page.locator('a.router-link-active:has-text("Artikelübersicht")'),
    ).toHaveAttribute("class", expect.stringContaining("bg-blue-200"))

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
    await expect(page).toHaveURL("/amendinglaws")
  })
}
