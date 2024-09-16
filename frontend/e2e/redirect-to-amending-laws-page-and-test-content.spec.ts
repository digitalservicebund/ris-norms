import { test, expect } from "@playwright/test"

test.describe("Redirect and start page content", () => {
  test(`redirect to amending laws page and test content for eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1`, async ({
    page,
  }) => {
    await page.goto("/")
    await expect(page).toHaveURL("/amending-laws")

    const link = page.getByRole("link", { name: "BGBl. I 2017 S. 419" })
    await expect(link).toBeVisible()
    await expect(link.getByText("15.03.2017")).toBeVisible()
  })

  test(`redirect to amending laws page and test content for eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1`, async ({
    page,
  }) => {
    await page.goto("/")
    await expect(page).toHaveURL("/amending-laws")

    const link = page.getByRole("link", { name: "BGBl. I 2023 Nr. 413" })
    await expect(link).toBeVisible()
    await expect(link.getByText("29.12.2023")).toBeVisible()
  })

  test("should display a loading error message when the API call fails", async ({
    page,
  }) => {
    await page.route("**/api/v1/announcements", (route) => {
      route.fulfill({
        status: 500,
        body: JSON.stringify({ message: "Internal Server Error" }),
      })
    })

    await page.goto("/")

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()
  })
})
