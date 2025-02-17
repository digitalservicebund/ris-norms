import { expect } from "@playwright/test"
import { test } from "@e2e/utils/test-with-auth"

test.describe("navigate to page", () => {
  // Opening the page is tested in expression-metadata-editor-rahmen.spec.ts as that's
  // the page that is effectively being opened. This file only tests behavior that is
  // shared between all metadata editor pages.

  test("shows an error when the expression could not be loaded", async ({
    page,
  }) => {
    await page.goto(
      "/eli/bund/bgbl-1/0000/000/0000-00-00/1/deu/regelungstext-1/metadata",
    )

    await expect(page.getByText("404")).toBeVisible()
  })
})

test.describe("table of contents", () => {
  test("shows the elements affected by this amending law", async ({ page }) => {
    await page.goto(
      "/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/metadata",
    )

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    // Ensure the TOC is loaded before running checks
    await expect(nav.getByRole("link")).not.toHaveCount(0)

    const expectedLinks = [
      "Rahmen",
      "1. Buch",
      "Strukturen und Gliederungsebenen",
      "2. Buch",
      "Beispiele für Strukturen",
      "3. Buch",
      "Beispiele Teil I",
      "4. Buch",
      "Beispielbuch",
      "5. Buch",
      "Übergangsregelungen und Geltungszeiten",
    ]

    // Check that each expected link is visible individually
    for (const linkText of expectedLinks) {
      await expect(nav.getByRole("link", { name: linkText })).toBeVisible()
    }
  })

  test("shows an error when the toc elements could not be loaded", async ({
    page,
  }) => {
    await page.route(/toc$/, (route) => {
      route.abort()
    })

    await page.goto(
      "/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/metadata",
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
      "/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/metadata",
    )

    await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()

    await page.unrouteAll()
  })
})
