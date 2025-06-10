import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { setZeitgrenzen, setZielnormReferences } from "@e2e/pages/verkuendung"
import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "showing the Zeitgrenzen page for a Verkündung",
  { tag: ["@RISDEV-4007"] },
  () => {
    test.beforeAll(async ({ authenticatedRequest: request }) => {
      await setZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
        null,
        request,
      )
    })

    test.beforeEach(async ({ authenticatedRequest: request }) => {
      // Set initial Zeitgrenzen data
      await setZeitgrenzen(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        ),
        [{ id: "gz-1", date: "2017-03-16", art: "INKRAFT" }],
        request,
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

      expect(loadedItems).toHaveLength(1)
      await expect(
        loadedItems[0].getByRole("checkbox", { name: "unbestimmt" }),
      ).not.toBeChecked()

      await expect(
        loadedItems[0].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("16.03.2017")

      await expect(
        loadedItems[0].getByRole("combobox", { name: "Inkrafttreten" }),
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

    test("shows an error if the preview of the Geltungszeiten can't be loaded", async ({
      page,
    }) => {
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
          name: "Zeitgrenze vom 16.03.2017 entfernen",
        })
        .click()

      // Add a new Zeitgrenze
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()

      const zeitgrenzen = editor.getByRole("listitem")
      await zeitgrenzen.last().getByRole("combobox").click()
      await zeitgrenzen
        .last()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("20.05.2025")
      await page.getByRole("option", { name: "Außerkrafttreten" }).click()

      // Add another new Zeitgrenze
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()

      await zeitgrenzen.last().getByRole("combobox").click()
      await zeitgrenzen
        .last()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("10.05.2025")
      await page.getByRole("option", { name: "Inkrafttreten" }).click()

      const resposne = page.waitForResponse(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      // Save -> Success toast
      await page.getByRole("button", { name: "Speichern" }).click()
      await resposne
      await expect(page.getByText("Gespeichert!")).toBeVisible()
      await page.reload()
      const editorAfterReload = page.getByRole("region", {
        name: "Geltungszeitregeln anlegen",
      })
      await expect(editorAfterReload).toBeVisible()
      const reloadedItems = await editorAfterReload.getByRole("listitem").all()
      expect(reloadedItems).toHaveLength(2)

      await expect(
        reloadedItems[0].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("10.05.2025")
      await expect(reloadedItems[0].getByRole("combobox")).toContainText(
        "Inkrafttreten",
      )

      await expect(
        reloadedItems[1].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("20.05.2025")
      await expect(reloadedItems[1].getByRole("combobox")).toContainText(
        "Außerkrafttreten",
      )
    })

    test("shows an error message when saving fails", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const editor = page.getByRole("region", {
        name: "Geltungszeitregeln anlegen",
      })

      // Add a new Zeitgrenze with empty date (invalid state)
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()

      await page.getByRole("button", { name: "Speichern" }).click()
      await expect(page.getByText("Fehler: Eingabefehler")).toBeVisible()

      const loadedItems = await editor.getByRole("listitem").all()
      expect(loadedItems).toHaveLength(2)
    })

    test("updates the data on the page with the new data from the backend", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zeitgrenzen",
      )

      const editor = page.getByRole("region", {
        name: "Geltungszeitregeln anlegen",
      })

      // Add a new Zeitgrenze
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()

      const zeitgrenzen = editor.getByRole("listitem")
      await zeitgrenzen.last().getByRole("combobox").click()
      await zeitgrenzen
        .last()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("20.06.2025")
      await page.getByRole("option", { name: "Außerkrafttreten" }).click()

      // Add another new Zeitgrenze
      await editor
        .getByRole("button", { name: "Geltungszeit hinzufügen" })
        .click()
      await zeitgrenzen.last().getByRole("combobox").click()
      await zeitgrenzen
        .last()
        .getByRole("textbox", { name: "Geltungszeit" })
        .fill("30.05.2025")
      await page.getByRole("option", { name: "Inkrafttreten" }).click()

      await page.getByRole("button", { name: "Speichern" }).click()
      await expect(page.getByText("Gespeichert!")).toBeVisible()

      const items = await editor.getByRole("listitem").all()
      expect(items).toHaveLength(3)

      await expect(
        items[0].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("16.03.2017")
      await expect(items[0].getByRole("combobox")).toContainText(
        "Inkrafttreten",
      )

      await expect(
        items[1].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("30.05.2025")
      await expect(items[1].getByRole("combobox")).toContainText(
        "Inkrafttreten",
      )

      await expect(
        items[2].getByRole("textbox", { name: "Geltungszeit" }),
      ).toHaveValue("20.06.2025")
      await expect(items[2].getByRole("combobox")).toContainText(
        "Außerkrafttreten",
      )
    })
  },
)
