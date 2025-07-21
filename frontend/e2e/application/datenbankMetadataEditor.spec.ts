import { expect } from "@playwright/test"
import { test } from "@e2e/utils/testWithAuth"

test.describe("navigate to page", { tag: ["@RISDEV-6266"] }, () => {
  // Opening the page is tested in expression-metadata-editor-rahmen.spec.ts as that's
  // the page that is effectively being opened. This file only tests behavior that is
  // shared between all metadata editor pages.

  test("shows an error when the expression could not be loaded", async ({
    page,
  }) => {
    await page.goto(
      "./datenbank/eli/bund/bgbl-1/0000/000/0000-00-00/1/deu/metadaten",
    )

    await expect(page.getByText("404")).toBeVisible()
  })
})

test.describe("table of contents", { tag: ["@RISDEV-6266"] }, () => {
  test("shows the elements affected by this expression", async ({ page }) => {
    await page.goto(
      "./datenbank/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/metadaten",
    )

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    await expect(nav.getByRole("link", { name: "Rahmen" })).toBeVisible()
    await expect(
      nav.getByRole("button", {
        name: "1. Buch Strukturen und Gliederungsebenen",
      }),
    ).toBeVisible()
    await expect(
      nav.getByRole("button", { name: "2. Buch Beispiele für Strukturen" }),
    ).toBeVisible()
    await expect(
      nav.getByRole("button", { name: "3. Buch Beispiele Teil I" }),
    ).toBeVisible()
    await expect(
      nav.getByRole("button", { name: "4. Buch Beispielbuch" }),
    ).toBeVisible()
    await expect(
      nav.getByRole("button", {
        name: "5. Buch Übergangsregelungen und Geltungszeiten",
      }),
    ).toBeVisible()
  })

  test("shows an error when the toc elements could not be loaded", async ({
    page,
  }) => {
    await page.route(/toc$/, (route) => {
      route.abort()
    })

    await page.goto(
      "./datenbank/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/metadaten",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await page.unrouteAll()
  })

  test("shows an empty state when no elements are found", async ({ page }) => {
    await page.route(/toc$/, async (route) => {
      await route.fulfill({ status: 200, json: [] })
    })

    await page.goto(
      "./datenbank/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/metadaten",
    )

    await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()

    await page.unrouteAll()
  })
})
