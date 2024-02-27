import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

for (const amendingLaw of amendingLaws) {
  let expectedHeading = ""

  if (amendingLaw?.printAnnouncementGazette) {
    expectedHeading = `${amendingLaw.printAnnouncementGazette} S. ${amendingLaw.printAnnouncementPage}`
  } else if (amendingLaw?.digitalAnnouncementEdition) {
    expectedHeading = `${amendingLaw.digitalAnnouncementMedium} Nr. ${amendingLaw.digitalAnnouncementEdition}`
  }

  test("navigate and verify navigation to article overview", async ({
    page,
  }) => {
    await page.goto("/")

    await page.click(`a[href*="${amendingLaw.eli}"]`)

    await expect(page.locator(".ds-heading-03-reg")).toHaveText(expectedHeading)

    await expect(
      page.locator('a.router-link-active:has-text("Artikelübersicht")'),
    ).toHaveAttribute("class", expect.stringContaining("bg-blue-200"))

    const checkChangeCommandButton = page.locator(
      'text="Änderungsbefehl prüfen"',
    )
    await expect(checkChangeCommandButton).toBeVisible()

    await expect(page).toHaveURL(
      `/amendinglaws/${amendingLaw.eli}/article-overview`,
    )

    await page.click("text=Zurück")
    await expect(page).toHaveURL("/amendinglaws")
  })

  test("navigate and verify navigation to affected standard", async ({
    page,
  }) => {
    await page.goto("/")

    await page.click(`a[href*="${amendingLaw.eli}"]`)

    await expect(page.locator(".ds-heading-03-reg")).toHaveText(expectedHeading)

    await page.click("text=Betroffene Normenkomplexe")
    await expect(page).toHaveURL(
      `/amendinglaws/${amendingLaw.eli}/affected-standards`,
    )

    const editMetadataButton = page.locator('text="Metadaten editieren"')
    await expect(editMetadataButton).toBeVisible()

    await expect(
      page.locator(
        'a.router-link-active:has-text("Betroffene Normenkomplexe")',
      ),
    ).toHaveAttribute("class", expect.stringContaining("bg-blue-200"))

    await page.click("text=Zurück")
    await expect(page).toHaveURL("/amendinglaws")
  })
}
