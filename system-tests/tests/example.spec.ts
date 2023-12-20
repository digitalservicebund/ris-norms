import { test, expect } from "@playwright/test";

test("it shows the intial view of the VSCode UI", async ({ page }) => {
  await page.goto("/");

  const welcomeLabel = page.getByLabel("Welcome, preview").getByText("Welcome");

  await expect(welcomeLabel).toBeVisible();
});
