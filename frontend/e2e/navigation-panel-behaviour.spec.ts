import { test, expect } from "@playwright/test"
import { procedures } from "../e2e/testData/testData"

test("navigate and verify navigation panel behavior", async ({ page }) => {
  for (const procedure of procedures) {
    await page.goto("/")

    await page.click(`a[href*="${procedure.eli}"]`)

    const expectedHeading = `${procedure.printAnnouncementGazette} ${procedure.printAnnouncementYear} Nr. ${procedure.printAnnouncementNumber}`
    await expect(page.locator(".ds-heading-03-reg")).toHaveText(expectedHeading)

    const expectedFna = `${procedure.fna}`
    await expect(page.locator(`text=${expectedFna}`)).toBeVisible()

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
    await expect(page).toHaveURL(
      `/procedures/${procedure.eli}/affected-standards`,
    )

    const editMetadataButton = page.locator('text="Metadaten editieren"')
    await expect(editMetadataButton).toBeVisible()

    const affectedStandardsMenuClass = await page
      .locator('a.router-link-active:has-text("Betroffene Normenkomplexe")')
      .getAttribute("class")
    const affectedStandardsIsActive =
      affectedStandardsMenuClass?.includes("bg-blue-200")
    expect(affectedStandardsIsActive).toBeTruthy()

    await page.click("text=Artikelübersicht")
    await expect(page).toHaveURL(
      `/procedures/${procedure.eli}/article-overview`,
    )

    await page.click("text=Zurück")
    await expect(page).toHaveURL("/procedures")
  }
})
