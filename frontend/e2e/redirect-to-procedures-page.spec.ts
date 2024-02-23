import { test, expect } from "@playwright/test"

test("display start page and redirect to procedures page", async ({ page }) => {
  await page.goto("/")
  await expect(page).toHaveURL("/amendinglaws")
})
