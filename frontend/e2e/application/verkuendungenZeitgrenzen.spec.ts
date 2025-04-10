import { test } from "@e2e/utils/testWithAuth"
import { expect } from "playwright/test"

test.describe(
  "showing the Zeitgrenzen page for a Verkündung",
  { tag: ["@RISDEV-4007"] },
  () => {
    test.beforeEach(async ({ page }) => {
      // Temporarily mock the respone while we're waiting for the backend implementation
      // TODO: Remove once backend is ready
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
        async (route) => {
          await route.fulfill({
            status: 200,
            json: [
              { id: "1", date: "2025-04-08", art: "INKRAFT" },
              { id: "2", date: "2025-04-10", art: "INKRAFT" },
              { id: "3", date: "2025-04-20", art: "AUSSERKRAFT" },
            ],
          })
        },
      )
    })

    test("opens the page for a Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await page
        .getByRole("link", { name: "Geltungszeitregeln anlegen" })
        .click()

      await expect(
        page.getByRole("heading", { name: "Geltungszeitregeln anlegen" }),
      ).toBeVisible()

      await expect(
        page
          .getByRole("navigation", { name: "Navigationspfad" })
          .getByRole("link", { name: "BGBl. I 2017 S. 419" }),
      ).toBeVisible()
    })

    test("shows the details of the Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const details = page.getByRole("region", { name: "Verkündungsdaten" })

      await expect(
        details.getByRole("figure", { name: "Verkündungsdatum" }),
      ).toHaveText(/15.03.2017$/)
    })

    test("shows the preview of the Geltungszeiten-Artikel", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const details = page.getByRole("region", { name: "Verkündungsdaten" })

      await expect(
        details.getByRole("heading", { name: /Inkrafttreten/ }),
      ).toBeVisible()
    })

    test("lists the Zeitgrenzen of the Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const editor = page.getByRole("region", {
        name: "Geltungszeitregeln anlegen",
      })

      await page.waitForResponse(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const loadedItems = await editor.getByRole("listitem").all()

      expect(loadedItems).toHaveLength(3)

      // Item 1
      await expect(
        loadedItems[0].getByRole("checkbox", { name: "unbestimmt" }),
      ).not.toBeChecked()

      await expect(
        loadedItems[0].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("08.04.2025")

      await expect(
        loadedItems[0].getByRole("combobox", { name: "Inkrafttreten" }),
      ).toBeVisible()

      // Item 2
      await expect(
        loadedItems[1].getByRole("checkbox", { name: "unbestimmt" }),
      ).not.toBeChecked()

      await expect(
        loadedItems[1].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("10.04.2025")

      await expect(
        loadedItems[1].getByRole("combobox", { name: "Inkrafttreten" }),
      ).toBeVisible()

      // Item 3
      await expect(
        loadedItems[2].getByRole("checkbox", { name: "unbestimmt" }),
      ).not.toBeChecked()

      await expect(
        loadedItems[2].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("20.04.2025")

      await expect(
        loadedItems[2].getByRole("combobox", { name: "Außerkrafttreten" }),
      ).toBeVisible()
    })

    test("shows an empty state if there are no Zeitgrenzen", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
        async (route) => {
          await route.fulfill({
            status: 200,
            json: [],
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      await expect(
        page.getByText("Es wurden noch keine Geltungszeiten angelegt."),
      ).toBeVisible()
    })

    test("shows an error if the Verkündung can't be loaded", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        async (route) => {
          await route.fulfill({
            status: 500,
            json: {},
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("shows an error if the Zeitgrenzen can't be loaded", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
        async (route) => {
          await route.fulfill({
            status: 500,
            json: {},
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("shows 404 if the Verkündung isn't found", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2099-12-31/1/deu/regelungstext-1/zeitgrenzen",
      )

      await expect(page.getByText("Seite nicht gefunden")).toBeVisible()
    })

    test("shows an error if the Preview can't be loaded", async ({ page }) => {
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        async (route) => {
          await route.fulfill({
            status: 500,
            json: {},
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      await expect(
        page
          .getByRole("region", { name: "Verkündungsdaten" })
          .getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("saves new Zeitgrenzen successfully", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const editor = page.getByRole("region", {
        name: "Geltungszeitregeln anlegen",
      })

      // Delete first Zeitgrenze
      await editor
        .getByRole("button", {
          name: "Zeitgrenze vom 08.04.2025 entfernen",
        })
        .click()

      // Change 2nd Zeitgrenze
      const zeitgrenzen = editor.getByRole("listitem")
      await expect(zeitgrenzen.first()).toBeVisible()
      await zeitgrenzen
        .first()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("10.04.2025")

      await zeitgrenzen.first().getByRole("combobox").click()
      await page.getByRole("option", { name: "Außerkrafttreten" }).click()

      // Add a new Zeitgrenze
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()

      await zeitgrenzen.last().getByRole("combobox").click()
      await zeitgrenzen
        .last()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("30.05.2025")
      await page.getByRole("option", { name: "Außerkrafttreten" }).click()

      // Save -> Success toast
      await page.getByRole("button", { name: "Speichern" }).click()
      await expect(page.getByText("Gespeichert!")).toBeVisible()

      // Reload -> Data still there
      // TODO: Add test
    })

    test.skip("shows an error message when saving fails", async ({}) => {
      // TODO: Go to page, create a Zeitgrenze with an empty date, save -> error toast
      expect(true).toBe(true)
    })

    test.skip("updates the data on the page with the new data from the backend", ({}) => {
      // TODO: Test when implemented
      expect(true).toBe(true)
    })
  },
)
