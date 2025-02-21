import { test } from "@e2e/utils/testWithAuth"
import { expect } from "playwright/test"

test("frontend is served", async ({ page }) => {
  await page.goto("/")
  await expect(page.getByText("Die Anwendung wird geladen...")).toBeVisible()
})
