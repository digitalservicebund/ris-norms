import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.beforeEach(async ({ page }) => {
  // Intercept API calls and respond with your test data
  await page.route("**/api/v1/amendinglaw", (route) =>
    route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(amendingLaws),
    }),
  )
})

test("navigate from amending laws list to an amending law detail page", async ({
  page,
}) => {
  await page.goto("/")
  await expect(page.locator("text=Vorgänge")).toBeVisible()

  await page
    .getByRole("link")
    .filter({ hasText: "BGBl. I 2017 S. 419" })
    .click()

  await expect(page).toHaveURL(
    `/amendinglaws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/article-overview`,
  )
})
