import { test, expect } from "@playwright/test"

test.describe("basic example test", () => {
  test("basic test", async ({ page }) => {
    await page.goto("/")
    await expect(page.locator("text=DigitalService")).toBeVisible()
  })
})
