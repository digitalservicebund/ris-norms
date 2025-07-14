import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "Datenbank view with list of works",
  { tag: ["@RISDEV-8331"] },
  () => {
    test("shows Datenbank heading", async ({ page }) => {
      await page.goto("./datenbank")

      await expect(
        page.getByRole("heading", { name: "Datenbank" }),
      ).toBeVisible()
    })

    test.describe("URL-based pagination", () => {
      test.beforeEach(async ({ page }) => {
        await page.route("**/norms?**", async (route) => {
          const content = Array.from({ length: 150 }, (_, i) => ({
            eli: `eli/bund/bgbl-1/2024/${i + 1}`,
            title: `Test Norm ${i + 1}`,
          }))

          await route.fulfill({
            status: 200,
            contentType: "application/json",
            body: JSON.stringify({
              content,
              page: {
                size: 100,
                number: 0,
                totalElements: content.length,
                totalPages: 2,
              },
            }),
          })
        })
      })

      test("updates URL when navigating pages", async ({ page }) => {
        await page.goto("./datenbank")
        await expect(
          page.getByRole("heading", { name: "Datenbank" }),
        ).toBeVisible()
        expect(page.url()).toContain("page=1")
        await page.getByRole("button", { name: "Weiter" }).click()
        expect(page.url()).toContain("page=2")
        await page.getByRole("button", { name: "Zurück" }).click()
        expect(page.url()).toContain("page=1")
      })

      test("loads correct page from URL parameter", async ({ page }) => {
        await page.goto("./datenbank?page=2")
        await expect(page.getByText("Seite 2 von 2")).toBeVisible()
        expect(page.url()).toContain("page=2")
      })
    })

    test("shows table with returned data from API", async ({ page }) => {
      await page.route("**/norms?**", async (route) => {
        const content = Array.from({ length: 10 }, (_, i) => ({
          eli: `eli/bund/bgbl-1/2024/${i + 1}`,
          title: `Test Norm ${i + 1}`,
        }))

        await route.fulfill({
          status: 200,
          contentType: "application/json",
          body: JSON.stringify({
            content,
            page: {
              size: 100,
              number: 0,
              totalElements: content.length,
              totalPages: 1,
            },
          }),
        })
      })

      await page.goto("./datenbank")

      await expect(page.getByText("Seite 1 von 1")).toBeVisible()

      const rows = page.getByRole("row")
      const rowCount = await rows.count()
      expect(rowCount).toBe(11)
    })

    test("shows paginated table and paginator works", async ({ page }) => {
      await page.route("**/norms?**", async (route) => {
        const url = new URL(route.request().url())
        const pageParam = parseInt(url.searchParams.get("page") ?? "0", 10)
        const sizeParam = parseInt(url.searchParams.get("size") ?? "100", 10)

        const content = Array.from({ length: 120 }, (_, i) => ({
          eli: `eli/bund/bgbl-1/2024/${i + 1}`,
          title: `Test Norm ${i + 1}`,
        }))

        const start = pageParam * sizeParam
        const end = start + sizeParam
        const pageContent = content.slice(start, end)

        await route.fulfill({
          status: 200,
          contentType: "application/json",
          body: JSON.stringify({
            content: pageContent,
            page: {
              size: sizeParam,
              number: pageParam,
              totalElements: content.length,
              totalPages: Math.ceil(content.length / sizeParam),
            },
          }),
        })
      })

      await page.goto("./datenbank")

      await expect(page.getByText("Seite 1 von 2")).toBeVisible()

      const rows = page.getByRole("row")
      const rowCount = await rows.count()
      expect(rowCount).toBe(101)

      const nextButton = page.getByRole("button", { name: "Weiter" })
      await expect(nextButton).toBeVisible()

      await nextButton.click()

      await expect(page.getByText("Seite 2 von 2")).toBeVisible()
      const prevButton = page.getByRole("button", { name: "Zurück" })
      await expect(prevButton).toBeVisible()
      await expect(prevButton).toBeEnabled()

      const rowsPage2 = page.getByRole("row")
      const rowCount2 = await rowsPage2.count()
      expect(rowCount2).toBe(21)
    })
  },
)
