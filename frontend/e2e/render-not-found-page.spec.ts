import { test, expect } from "@playwright/test"

test.describe("404 Page", () => {
  test(`should display 404 page when trying to access a non existing url`, async ({
    page,
  }) => {
    await page.goto("/amending-laws/non-existing-route")

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })

  test(`should display 404 page when API response is 404`, async ({ page }) => {
    await page.route("/api/v1/announcements", (route) => {
      route.fulfill({
        status: 404,
        body: "Not Found",
      })
    })

    await page.goto("/amending-laws")

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})