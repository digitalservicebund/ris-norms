import { test, expect } from "@playwright/test"

test("navigate from procedures list to a procedure detail page", async ({
  page,
}) => {
  await page.goto("/")

  await expect(page.locator("text=Vorgänge")).toBeVisible()

  const procedureCards = page.locator('a:has-text("BGBl")')
  const cardCount = await procedureCards.count()

  const expectedELIs = [
    "eli_bund_bgbl-1_1964_s593_regelungstext-1",
    "eli_bund_bgbl-1_1982_s22_regelungstext-1",
  ]

  for (let i = 0; i < cardCount; i++) {
    await page.goto("/procedures")

    const procedureCard = procedureCards.nth(i)
    await procedureCard.click()

    await expect(page).toHaveURL(
      `/procedures/${expectedELIs[i]}/article-overview`,
    )
  }
})
