import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

for (const amendingLaw of amendingLaws) {
  test(`navigate and verify navigation to affected standard for ${amendingLaw.eli}`, async ({
    page,
  }) => {
    // Navigation
    await page.goto("/")
    await page.click(`a[href*="${amendingLaw.eli}"]`)
    await page.click("text=Betroffene Normenkomplexe")
    await expect(page).toHaveURL(
      `/amendinglaws/${amendingLaw.eli}/affected-standards`,
    )
    await expect(
      page.locator(
        'a.router-link-active:has-text("Betroffene Normenkomplexe")',
      ),
    ).toHaveAttribute("class", expect.stringContaining("bg-blue-200"))

    // Content
    for (const article of amendingLaw.articles) {
      await expect(
        page.getByText(`Artikel ${article.enumeration}`),
      ).toBeVisible()
      await expect(page.getByText(article.eli)).toBeVisible()
    }
    const editMetadataButton = page.locator('text="Metadaten editieren"')
    await expect(editMetadataButton).toBeVisible()

    // Back
    await page.click("text=Zurück")
    await expect(page).toHaveURL("/amendinglaws")
  })
}
