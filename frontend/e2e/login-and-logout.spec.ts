import { test } from "@e2e/utils/test-with-auth"
import { expect } from "@playwright/test"

test.describe("Login / logout functionality", { tag: ["@RISDEV-5654"] }, () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/")
  })

  test("Clicking logout should redirect to the login UI and verify user info after login", async ({
    page,
  }) => {
    await page.goto("/")
    await page.getByRole("link").click()
    await expect(
      page.getByRole("heading", { name: "Sign in to your account" }),
    ).toBeVisible()

    await page
      .getByRole("textbox", { name: "Username or email" })
      .fill("jane.doe")
    await page.getByRole("textbox", { name: "Password" }).fill("test")
    await page.getByRole("button", { name: "Sign In" }).click()

    await page.goto("/amending-laws")
    const logoutLinkAfterLogin = page.getByRole("link", { name: "Ausloggen" })
    await expect(logoutLinkAfterLogin).toBeVisible()

    const usernameDisplay = page.getByText("Jane Doe")
    await expect(usernameDisplay).toBeVisible()

    await logoutLinkAfterLogin.click()
    await expect(
      page.getByRole("heading", { name: "Sign in to your account" }),
    ).toBeVisible()
  })
})
