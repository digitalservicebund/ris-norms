import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.describe("Articles page", () => {
  test(`navigate to articles overview`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
    await page.getByRole("link", { name: "Artikelübersicht" }).click()

    await expect(page).toHaveURL(
      `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles`,
    )
    await expect(
      page.getByRole("heading", { name: "Enthaltene Artikel" }),
    ).toBeVisible()
  })

  for (const amendingLaw of amendingLaws) {
    test(`navigate and verify navigation to articles page for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      // Navigation
      await page.goto(`/amending-laws/${amendingLaw.eli}/articles`)

      // Menu
      const locator = page.getByRole("link", { name: "Artikelübersicht" })
      await expect(locator).toHaveClass(/router-link-active/)
      await expect(locator).toHaveClass(/bg-blue-200/)

      // Content
      // eslint-disable-next-line playwright/no-conditional-in-test
      for (const article of amendingLaw.articles ?? []) {
        await expect(page.getByText(article.enumeration)).toBeVisible()
        await expect(
          page.getByText(article.title, {
            exact: true,
          }),
        ).toBeVisible()
      }

      const checkChangeCommandButton = page
        .getByText("Änderungsbefehl prüfen")
        .first()
      await expect(checkChangeCommandButton).toBeVisible()

      // Back
      await page.getByRole("link", { name: "Zurück" }).click()
      await expect(page).toHaveURL("/amending-laws")
    })
  }
})

test.describe("Articles page error handling", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles"

  test("redirects to NotFound page when useArticles returns 404", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
      (route) =>
        route.fulfill({
          status: 404,
        }),
    )
    await page.goto(BASE_URL)

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })

  test("displays error callout when useArticles returns a non-404 error", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
      (route) =>
        route.fulfill({
          status: 403,
          body: JSON.stringify({ message: "Internal Server Error" }),
        }),
    )

    await page.goto(BASE_URL)

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
