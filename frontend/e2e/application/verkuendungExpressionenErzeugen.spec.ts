import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { setZeitgrenzen, setZielnormReferences } from "@e2e/pages/verkuendung"
import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "shows the preview of expressions that will be created",
  { tag: ["@RISDEV-7180"] },
  () => {
    test.beforeAll(async ({ authenticatedRequest: request }) => {
      await setZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
        null,
        request,
      )

      // Prepare Zeitgrenzen
      const zeitgrenzenIds = await setZeitgrenzen(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        ),
        [
          { id: "", date: "2017-03-16", art: "INKRAFT" },
          { id: "", date: "2025-05-30", art: "INKRAFT" },
          { id: "", date: "2025-06-20", art: "AUSSERKRAFT" },
        ],
        request,
      )

      await setZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
        [
          {
            geltungszeit: zeitgrenzenIds[0],
            zielnorm: "eli/bund/bgbl-1/1964/s593",
            typ: "Änderungsvorschrift",
            eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
          },
        ],
        request,
      )
    })

    test("opens the page and shows the data", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
      )

      await page
        .getByRole("button", {
          name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
        })
        .click()

      const row = page.getByRole("row")

      await expect(
        row.getByRole("cell", {
          name: "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
        }),
      ).toBeVisible()

      await expect(
        row.getByRole("cell", {
          name: "16.03.2017",
        }),
      ).toBeVisible()

      await expect(
        row.getByRole("cell", {
          name: "diese Verkündung",
        }),
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
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("shows an error if the preview of expressions can't be loaded", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview",
        async (route) => {
          await route.fulfill({
            status: 500,
            json: {},
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })
  },
)
