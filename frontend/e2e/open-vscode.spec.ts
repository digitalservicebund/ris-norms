import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.beforeEach(async ({ page }) => {
  // Intercept API calls and respond with your test data
  await page.route("**/api/v1/norms/procedures", (route) =>
    route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(amendingLaws),
    }),
  )
})

test.use({
  ignoreHTTPSErrors: true,
})

// Skipped as the routing directly to an article-overview does not work at the moment.
test.skip("open a new tab with vs code from the article overview", async ({
  page,
  context,
}) => {
  await page.goto(
    `/procedures/${encodeURIComponent("eli/bund/bgbl-1/2017/s419")}/article-overview`,
  )
  const pagePromise = context.waitForEvent("page")
  await page.getByText("Änderungsbefehl prüfen").click()
  const vscodePage = await pagePromise
  await expect(vscodePage).toHaveURL(
    `${process.env.VITE_VSCODE_URL}login?folder=/home/ubuntu/ldml-samples/eli/bund/bgbl-1/2017/s419&to=`,
  )
})
