import { test, expect } from "@playwright/test"

test("display start page and redirect to amending laws page", async ({
  page,
}) => {
  await page.goto("/")
  await expect(page).toHaveURL("/amending-laws")
})
