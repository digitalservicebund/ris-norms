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

  const amendingLaw = amendingLaws[0]
  const encodedEli = encodeURIComponent(amendingLaw.eli)

  await page.click(`a[href*="${encodedEli}"]`)

  await expect(page).toHaveURL(`/amendinglaws/${encodedEli}/article-overview`)
})
