import { test, expect } from "@playwright/test"
import { procedures } from "../e2e/testData/testData"

test("navigate from procedures list to a procedure detail page", async ({
  page,
}) => {
  await page.goto("/")
  await expect(page.locator("text=Vorgänge")).toBeVisible()

  const procedure = procedures[0]

  await page.click(`a[href*="${procedure.eli}"]`)

  await expect(page).toHaveURL(`/procedures/${procedure.eli}/article-overview`)
})
