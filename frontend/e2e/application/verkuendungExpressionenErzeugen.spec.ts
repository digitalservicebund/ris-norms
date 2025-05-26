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

test.describe("creates affected expressions", { tag: ["@RISDEV-7181"] }, () => {
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

  test("creates expressions for the first time", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
    )

    await page
      .getByRole("button", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
      })
      .click()

    const row = page.getByRole("row")

    // Assert that the expression has not been created yet. Note that this will
    // only work with a clean DB since creating expressions can not easily be
    // reversed.
    await expect(row.getByText("Expression erzeugt")).toBeHidden()

    await page.getByRole("button", { name: "Expressionen erzeugen" }).click()

    await expect(
      page.getByText(
        "Sind Sie sicher, dass Sie die Expressionen erzeugen möchten?",
      ),
    ).toBeVisible()

    await page.getByRole("button", { name: "Erzeugen", exact: true }).click()

    await expect(
      page.getByText("Expressionen erfolgreich erzeugt"),
    ).toBeVisible()

    await expect(
      row.getByText("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
    ).toBeVisible()

    await expect(row.getByText("Expression erzeugt")).toBeVisible()
  })

  test("shows a warning if the local data has become outdated before creating expressions", async ({
    page,
  }) => {
    // TODO: Remove once the API has been implemented
    await page.route(
      "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview",
      async (route) => {
        await route.fulfill({
          status: 200,
          json: [
            {
              normWorkEli: "eli/bund/bgbl-1/1964/s593",
              title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
              shortTitle: "Vereinsgesetz",
              expressions: [
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
                  isGegenstandslos: false,
                  isCreated: false,
                  createdBy: "diese Verkündung",
                },
              ],
            },
          ],
        })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
    )

    await page
      .getByRole("button", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
      })
      .click()

    const row = page.getByRole("row")

    // Assert that the expression has not been created yet. Note that this will
    // only work with a clean DB since creating expressions can not easily be
    // reversed.
    await expect(row.getByText("Expression erzeugt")).toBeHidden()

    // TODO: Remove once the API has been implemented
    await page.route(
      "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview",
      async (route) => {
        await route.fulfill({
          status: 200,
          json: [
            {
              normWorkEli: "eli/bund/bgbl-1/1964/s593",
              title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
              shortTitle: "Vereinsgesetz",
              expressions: [
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
                  isGegenstandslos: false,
                  isCreated: true,
                  createdBy: "diese Verkündung",
                },
              ],
            },
          ],
        })
      },
    )

    await page.getByRole("button", { name: "Expressionen erzeugen" }).click()

    await expect(page.getByText("Die Daten haben sich geändert")).toBeVisible()
  })

  test("shows an error if the local data couldn't be refreshed before creating expressions", async ({
    page,
  }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
    )

    await page
      .getByRole("button", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
      })
      .click()

    await page.route(
      "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview",
      async (route) => {
        await route.fulfill({
          status: 500,
          json: [],
        })
      },
    )

    await page.getByRole("button", { name: "Expressionen erzeugen" }).click()

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("shows an error if creating expressions fails", async ({ page }) => {
    await page.route(
      "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/eli/bund/bgbl-1/1964/s593/expressions/create",
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

    await page
      .getByRole("button", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
      })
      .click()

    await page.getByRole("button", { name: "Expressionen erzeugen" }).click()

    await expect(
      page.getByText(
        "Sind Sie sicher, dass Sie die Expressionen erzeugen möchten?",
      ),
    ).toBeVisible()

    await page.getByRole("button", { name: "Erzeugen", exact: true }).click()

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("re-creates expressions", async ({ page }) => {
    // TODO: Remove once the API has been implemented
    await page.route(
      "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen/expressions/preview",
      async (route) => {
        await route.fulfill({
          status: 200,
          json: [
            {
              normWorkEli: "eli/bund/bgbl-1/1964/s593",
              title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
              shortTitle: "Vereinsgesetz",
              expressions: [
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu",
                  isGegenstandslos: false,
                  isCreated: true,
                  createdBy: "diese Verkündung",
                },
              ],
            },
          ],
        })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/expressionen-erzeugen",
    )

    await page
      .getByRole("button", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts (Vereinsgesetz)",
      })
      .click()

    const row = page.getByRole("row")

    // Assert that the expression has already been created
    await expect(row.getByText("Expression erzeugt")).toBeVisible()

    await page.getByRole("button", { name: "Expressionen erzeugen" }).click()

    await expect(
      page.getByText(
        "Sind Sie sicher, dass Sie die Expressionen erneut erzeugen und damit bereits erzeugte Expressionen überschrieben möchten?",
      ),
    ).toBeVisible()

    await page
      .getByRole("button", { name: "Erneut erzeugen", exact: true })
      .click()

    await expect(
      page.getByText("Expressionen erfolgreich erzeugt"),
    ).toBeVisible()

    await expect(
      row.getByText("eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu"),
    ).toBeVisible()

    await expect(row.getByText("Expression erzeugt")).toBeVisible()
  })
})
