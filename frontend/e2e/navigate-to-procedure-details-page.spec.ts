import { test, expect } from "@playwright/test"
import { procedures } from "@e2e/testData/testData"

test.beforeEach(async ({ page }) => {
  // Intercept API calls and respond with your test data
  await page.route("**/api/v1/norms/procedures", (route) =>
    route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(procedures),
    }),
  )
})

test.skip("navigate from procedures list to a procedure detail page", async ({
  page,
}) => {
  await page.goto("/")
  await expect(page.locator("text=Vorgänge")).toBeVisible()

  const procedure = procedures[0]
  const encodedEli = encodeURIComponent(procedure.eli)

  await page.click(`a[href*="${encodedEli}"]`)

  await expect(page).toHaveURL(`/procedures/${encodedEli}/article-overview`)
})
