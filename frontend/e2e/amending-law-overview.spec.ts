import { test, expect } from "@playwright/test"

test("navigate to amending law overview", async ({ page }) => {
  await page.goto("/amending-laws")
  await page.getByRole("link", { name: "BGBl. I 2023 Nr. 413" }).click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )
})

test("see page title", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  await expect(
    page.getByRole("heading", { level: 1, name: "Verkündung", exact: true }),
  ).toBeVisible()
})

test("see rendered law text", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  const article = page.getByRole("article")
  await expect(article).toBeVisible()
  await expect(article).toContainText("Vom 29.12.2023")
})

test("should display a loading error message when the API call fails", async ({
  page,
}) => {
  await page.route(
    "**/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1?",
    async (route, request) => {
      if (request.headers()["accept"] === "text/html") {
        await route.abort()
      } else {
        await route.continue()
      }
    },
  )

  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  await expect(
    page.getByText("Ein unbekannter Fehler ist aufgetreten."),
  ).toBeVisible()

  await expect(
    page.getByRole("button", {
      name: "Trace-ID in die Zwischenablage kopieren",
    }),
  ).toBeVisible()

  await page.unrouteAll()
})

test("should redirect to the 404 page when the amending law JSON does not exist", async ({
  page,
}) => {
  await page.route(
    "**/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1?",
    async (route, request) => {
      if (request.headers()["accept"] === "application/json") {
        await route.fulfill({
          status: 404,
        })
      } else {
        await route.continue()
      }
    },
  )

  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  await expect(
    page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
  ).toBeVisible()
})

test("should redirect to the 404 page when the amending law HTML does not exist", async ({
  page,
}) => {
  await page.route(
    "**/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1?",
    async (route, request) => {
      if (request.headers()["accept"] === "text/html") {
        await route.fulfill({
          status: 404,
        })
      } else {
        await route.continue()
      }
    },
  )

  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  await expect(
    page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
  ).toBeVisible()
})
