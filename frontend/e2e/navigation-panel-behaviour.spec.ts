import { test, expect } from "@playwright/test"
import { amendingLaws } from "../e2e/testData/testData"

test.skip("navigate and verify navigation panel behavior", async ({ page }) => {
  for (const amendingLaw of amendingLaws) {
    await page.goto("/")

    const encodedEli = encodeURIComponent(amendingLaw.eli)

    await page.click(`a[href*="${encodedEli}"]`)

    const expectedHeading = `${amendingLaw.printAnnouncementGazette} ${amendingLaw.publicationDate} Nr. ${amendingLaw.printAnnouncementPage}`
    await expect(page.locator(".ds-heading-03-reg")).toHaveText(expectedHeading)

    const articleOverviewMenuClass = await page
      .locator('a.router-link-active:has-text("Artikelübersicht")')
      .getAttribute("class")
    expect(articleOverviewMenuClass).toContain("bg-blue-200")

    const checkChangeCommandButton = page.locator(
      'text="Änderungsbefehl prüfen"',
    )
    await expect(checkChangeCommandButton).toBeVisible()

    const checkTimeLimitsButton = page.locator('text="Zeitgrenzen prüfen"')
    await expect(checkTimeLimitsButton).toBeVisible()

    await page.click("text=Betroffene Normenkomplexe")
    await expect(page).toHaveURL(`/procedures/${encodedEli}/affected-standards`)

    const editMetadataButton = page.locator('text="Metadaten editieren"')
    await expect(editMetadataButton).toBeVisible()

    const affectedStandardsMenuClass = await page
      .locator('a.router-link-active:has-text("Betroffene Normenkomplexe")')
      .getAttribute("class")
    const affectedStandardsIsActive =
      affectedStandardsMenuClass?.includes("bg-blue-200")
    expect(affectedStandardsIsActive).toBeTruthy()

    await page.click("text=Artikelübersicht")
    await expect(page).toHaveURL(`/procedures/${encodedEli}/article-overview`)

    await page.click("text=Zurück")
    await expect(page).toHaveURL("/procedures")
  }
})
