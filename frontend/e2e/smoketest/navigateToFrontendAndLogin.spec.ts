import { test } from "@e2e/utils/testWithAuth"
import { expect } from "playwright/test"

test.describe(
  "navigate to frontend and log in",
  { tag: ["@RISDEV-6811"] },
  () => {
    test("frontend is served", async ({ page }) => {
      const waitForFrontend = page.waitForResponse("/")

      await page.goto("/")

      const frontend = await waitForFrontend

      expect(frontend.ok()).toBe(true)
    })

    test("user can log in and is redirected to subpage in the app", async ({
      page,
      appCredentials,
    }) => {
      await page.goto("/amending-laws/upload")
      await expect(page.getByText("Anmelden bei NeuRIS Staging")).toBeVisible()

      await page
        .getByRole("textbox", { name: "E-Mailadresse" })
        .fill(appCredentials.username)

      await page
        .getByRole("textbox", { name: "Passwort" })
        .fill(appCredentials.password)

      await page.getByRole("button", { name: "Anmelden" }).click()

      await page.waitForURL("/amending-laws/upload")
      await expect(page.getByRole("heading", { name: "Upload" })).toBeVisible()
    })
  },
)
