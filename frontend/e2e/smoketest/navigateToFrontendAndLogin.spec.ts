import { test } from "@e2e/utils/testWithAuth"
import { expect } from "playwright/test"

test.describe(
  "navigate to frontend and log in",
  { tag: ["@RISDEV-6811"] },
  () => {
    test("redirects to frontend from root", async ({ page }) => {
      const waitForFrontend = page.waitForResponse("/app/")

      await page.goto("/")

      const frontend = await waitForFrontend

      expect(frontend.ok()).toBe(true)
    })

    test("frontend is served from /app/", async ({ page }) => {
      const waitForFrontend = page.waitForResponse("/app/")

      await page.goto("./")

      const frontend = await waitForFrontend

      expect(frontend.ok()).toBe(true)
    })

    test("user can log in and is redirected to subpage in the app", async ({
      page,
      appCredentials,
    }) => {
      await page.goto("./amending-laws/upload")
      await expect(page.getByText("Anmelden bei NeuRIS Staging")).toBeVisible()

      await page
        .getByRole("textbox", { name: "E-Mailadresse" })
        .fill(appCredentials.username)

      await page
        .getByRole("textbox", { name: "Passwort" })
        .fill(appCredentials.password)

      await page.getByRole("button", { name: "Anmelden" }).click()

      await page.waitForURL("/app/amending-laws/upload")
      await expect(page.getByRole("heading", { name: "Upload" })).toBeVisible()
    })
  },
)
