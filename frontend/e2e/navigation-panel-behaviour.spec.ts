import { test, expect } from "@playwright/test"

test("navigate and verify navigation panel behavior", async ({ page }) => {
  const expectedELIs = [
    "eli_bund_bgbl-1_1964_s593_regelungstext-1",
    "eli_bund_bgbl-1_1982_s22_regelungstext-1",
  ]

  for (const eli of expectedELIs) {
    await page.goto("/procedures")

    await page.click(`a[href*="${eli}"]`)

    const articleOverviewClass = await page
      .locator('a.router-link-active:has-text("Artikelübersicht")')
      .getAttribute("class")
    expect(articleOverviewClass).toContain("bg-blue-200")

    await page.click("text=Betroffene Normenkomplexe")
    await expect(page).toHaveURL(`/procedures/${eli}/affected-standards`)

    const affectedStandardsClass = await page
      .locator('a.router-link-active:has-text("Betroffene Normenkomplexe")')
      .getAttribute("class")
    const affectedStandardsIsActive =
      affectedStandardsClass?.includes("bg-blue-200")
    expect(affectedStandardsIsActive).toBeTruthy()

    await page.click("text=Artikelübersicht")
    await expect(page).toHaveURL(`/procedures/${eli}/article-overview`)

    await page.click("text=Zurück")
    await expect(page).toHaveURL("/procedures")
  }
})
