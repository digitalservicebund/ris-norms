import { test, expect } from "@playwright/test"

test.describe("Logout functionality", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/")
  })

  test("Clicking logout should redirect to the login UI and verify user info after login", async ({
    page,
  }) => {
    const logoutLink = page.getByRole("link", { name: "Ausloggen" })
    await expect(logoutLink).toBeVisible()

    await logoutLink.click()
    await expect(
      page.getByRole("heading", { name: "Sign in to your account" }),
    ).toBeVisible()

    await page.goto("/amending-laws")
    await expect(
      page.getByRole("heading", { name: "Sign in to your account" }),
    ).toBeVisible()

    await page
      .getByRole("textbox", { name: "Username or email" })
      .fill("jane.doe")
    await page.getByRole("textbox", { name: "Password" }).fill("test")
    await page.getByRole("button", { name: "Sign In" }).click()

    const logoutLinkAfterLogin = page.getByRole("link", { name: "Ausloggen" })
    await expect(logoutLinkAfterLogin).toBeVisible()

    const usernameDisplay = page.getByText("Jane Doe")
    await expect(usernameDisplay).toBeVisible()
  })
})
