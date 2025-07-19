import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { setZeitgrenzen, setZielnormReferences } from "@e2e/pages/verkuendung"
import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "showing the Zielnormen page for a Verkündung",
  { tag: ["@RISDEV-6946"] },
  () => {
    test("opens the page for a Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      )

      await page.getByRole("link", { name: "Zielnormen verknüpfen" }).click()

      await expect(
        page
          .getByRole("navigation", { name: "Navigationspfad" })
          .getByRole("link", { name: "BGBl. I 2017 S. 419" }),
      ).toBeVisible()

      await expect(
        page
          .getByRole("navigation", { name: "Navigationspfad" })
          .getByText("Zielnormen verknüpfen"),
      ).toBeVisible()
    })

    test("shows the details of the Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      const details = page.getByRole("region", { name: "Verkündungsdaten" })

      await expect(
        details.getByRole("figure", { name: "Verkündungsdatum" }),
      ).toHaveText(/15.03.2017$/)
    })

    test("shows an error if the Verkündung can't be loaded", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        async (route) => {
          await route.fulfill({ status: 500, json: {} })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("shows 404 if the Verkündung isn't found", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2099-12-31/1/deu/regelungstext-verkuendung-1/zeitgrenzen",
      )

      await expect(page.getByText("Seite nicht gefunden")).toBeVisible()
    })

    test("initially shows an empty state if no Artikel has been selected yet", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      await expect(
        page.getByText("Bitte wählen Sie einen Artikel und eine Änderung aus."),
      ).toBeVisible()
    })
  },
)

test.describe("Geltungszeiten-Artikel", { tag: ["@RISDEV-6946"] }, () => {
  test("shows the preview of the Geltungszeiten-Artikel", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    const details = page.getByRole("region", { name: "Verkündungsdaten" })

    await expect(
      details.getByRole("heading", { name: /Inkrafttreten/ }),
    ).toBeVisible()
  })

  test("shows an error if the Geltungszeiten-Artikel can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten",
      async (route) => {
        await route.fulfill({ status: 500, json: {} })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zeitgrenzen",
    )

    await expect(
      page
        .getByRole("region", { name: "Verkündungsdaten" })
        .getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })
})

test.describe(
  "table of contents",
  { tag: ["@RISDEV-6946", "@RISDEV-8575"] },
  () => {
    test("shows the table of contents of the Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/zielnormen",
      )

      const toc = page.getByRole("tree", { name: "Inhaltsverzeichnis" })

      await expect(
        toc.getByRole("button", {
          name: "Unbenanntes Element Soldatinnen- und Soldatengleichstellungsgesetz (SGleiG)",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 2 Änderung des Bundesgleichstellungsgesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 3 Änderung des Soldatengesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 4 Änderung der Gleichstellungsbeauftragten-Wahlverordnung Soldatinnen",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 5 Änderung der Soldaten-Haushaltshilfen-Verordnung",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 6 Änderung des Beamtenversorgungsgesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 7 Änderung des Soldatenversorgungsgesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 8 Änderung des Soldatenversorgungsgesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 9 Änderung des Unterhaltssicherungsgesetzes",
        }),
      ).toBeVisible()

      await expect(
        toc.getByRole("button", {
          name: "Artikel 10 Inkrafttreten, Außerkrafttreten",
        }),
      ).toBeVisible()
    })

    test("shows an error if the table contents can't be loaded", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/toc",
        async (route) => {
          await route.fulfill({ status: 500, json: {} })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("shows an empty state if the Verkündung has no content", async ({
      page,
    }) => {
      await page.route(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/toc",
        async (route) => {
          await route.fulfill({ status: 200, json: [] })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
      )

      await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()
    })
  },
)

test.describe("detail", { tag: ["@RISDEV-6946", "@RISDEV-8575"] }, () => {
  test("displays the preview of an Artikel", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page
      .getByRole("tree", { name: "Inhaltsverzeichnis" })
      .getByRole("button", { name: /Artikel 1/ })
      .click()

    await expect(
      page
        .getByRole("complementary", { name: "Änderungsgesetz" })
        .getByText("Änderung des Vereinsgesetzes"),
    ).toBeVisible()
  })

  test("displays a reference to an eingebundene Stammform in an Artikel", async ({
    page,
  }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/zielnormen",
    )

    await page
      .getByRole("tree", { name: "Inhaltsverzeichnis" })
      .getByRole("button", {
        name: /Soldatinnen- und Soldatengleichstellungsgesetz/,
      })
      .click()

    const preview = page.getByRole("complementary", { name: "Änderungsgesetz" })

    await expect(preview.getByText("Unbenanntes Element")).toBeVisible()
    await expect(
      preview.getByText(
        "Soldatinnen- und Soldatengleichstellungsgesetz (SGleiG)",
      ),
    ).toBeVisible()
  })

  test("shows an error if the Artikel contents can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/elements/art-z1",
      async (route) => {
        if ((await route.request().headerValue("Accept")) === "text/html") {
          await route.fulfill({ status: 500, json: {} })
        } else return route.fallback()
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page
      .getByRole("tree", { name: "Inhaltsverzeichnis" })
      .getByRole("button", { name: /Artikel 1/ })
      .click()

    await expect(
      page
        .getByRole("complementary", { name: "Änderungsgesetz" })
        .getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("shows an empty state if the Artikel contents are empty", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1/elements/art-z1",
      async (route) => {
        await route.fulfill({ status: 200, body: "" })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page
      .getByRole("tree", { name: "Inhaltsverzeichnis" })
      .getByRole("button", { name: /Artikel 1/ })
      .click()

    await expect(
      page
        .getByRole("complementary", { name: "Änderungsgesetz" })
        .getByText("Der Artikel hat keinen Inhalt."),
    ).toBeVisible()
  })

  test("navigates back to the table of contents", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page
      .getByRole("tree", { name: "Inhaltsverzeichnis" })
      .getByRole("button", { name: /Artikel 1/ })
      .click()

    await expect(
      page
        .getByRole("complementary", { name: "Änderungsgesetz" })
        .getByText("Änderung des Vereinsgesetzes"),
    ).toBeVisible()

    await page
      .getByRole("button", { name: "Inhaltsverzeichnis anzeigen" })
      .click()

    await expect(
      page.getByRole("tree", { name: "Inhaltsverzeichnis" }),
    ).toBeVisible()
  })
})

test.describe("Artikel editing form", { tag: ["@RISDEV-6946"] }, () => {
  let zeitgrenzenIds: string[] = []

  test.beforeAll(async ({ authenticatedRequest: request }) => {
    // Prepare Zeitgrenzen
    zeitgrenzenIds = await setZeitgrenzen(
      NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      ),
      [
        { id: "", date: "2017-03-16", art: "INKRAFT" },
        { id: "", date: "2025-05-30", art: "INKRAFT" },
        { id: "", date: "2025-06-20", art: "AUSSERKRAFT" },
      ],
      request,
    )
  })

  test.beforeEach(async ({ authenticatedRequest: request }) => {
    setZielnormReferences(
      NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      ),
      null,
      request,
    )
  })

  test("shows an error if the Zeitgrenzen can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zeitgrenzen",
      async (route) => {
        await route.fulfill({ status: 500, json: {} })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("shows an error if the Zielnormen references can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zeitgrenzen",
      async (route) => {
        await route.fulfill({ status: 500, json: {} })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("shows the Zeitgrenzen", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const article = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    await article.click()

    await page.getByRole("combobox", { name: "Geltungszeitregel" }).click()

    await expect(
      page.getByRole("option", { name: "16.03.2017 (Inkrafttreten)" }),
    ).toBeVisible()
    await expect(
      page.getByRole("option", { name: "30.05.2025 (Inkrafttreten)" }),
    ).toBeVisible()
    await expect(
      page.getByRole("option", { name: "20.06.2025 (Außerkrafttreten)" }),
    ).toBeVisible()
  })

  test("allows editing and saving ELI and Geltungszeitregel for one element", async ({
    page,
  }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const article = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    await article.click()

    await page
      .getByRole("textbox", { name: "ELI Zielnormenkomplex" })
      .fill("eli/bund/bgbl-1/2021/123")

    await page.getByRole("combobox", { name: "Geltungszeitregel" }).click()
    await page
      .getByRole("option", { name: "16.03.2017 (Inkrafttreten)" })
      .click()

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Gespeichert!")).toBeVisible()

    await page.reload()

    await page.getByRole("button", { name: /Artikel 1/ }).click()
    await article.click()

    await expect(
      page.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli/bund/bgbl-1/2021/123")

    await expect(
      page.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveText("16.03.2017 (Inkrafttreten)")
  })

  test("allows editing and saving ELI and Geltungszeitregel for multiple elements", async ({
    page,
  }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const outerArticle = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    const innerArticle = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    await outerArticle.click()
    await innerArticle.click({ modifiers: ["ControlOrMeta"] })

    await page
      .getByRole("textbox", { name: "ELI Zielnormenkomplex" })
      .fill("eli/bund/bgbl-1/2021/123")

    await page.getByRole("combobox", { name: "Geltungszeitregel" }).click()
    await page
      .getByRole("option", { name: "16.03.2017 (Inkrafttreten)" })
      .click()

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Gespeichert!")).toBeVisible()

    await page.reload()

    await page.getByRole("button", { name: /Artikel 1/ }).click()
    await innerArticle.click()

    await expect(
      page.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli/bund/bgbl-1/2021/123")

    await expect(
      page.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveText("16.03.2017 (Inkrafttreten)")

    await outerArticle.click()

    await expect(
      page.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli/bund/bgbl-1/2021/123")

    await expect(
      page.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveText("16.03.2017 (Inkrafttreten)")
  })

  test("shows identical data when multiple elements are selected", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await setZielnormReferences(
      NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      ),
      [
        {
          geltungszeit: zeitgrenzenIds[0],
          zielnorm: "eli/bund/bgbl-1/2021/123",
          typ: "Änderungsvorschrift",
          eId: "art-z1_abs-z",
          isNewWork: false,
        },
        {
          geltungszeit: zeitgrenzenIds[0],
          zielnorm: "eli/bund/bgbl-1/2021/123",
          typ: "Änderungsvorschrift",
          eId: "art-z1_abs-z_untergl-n1_listenelem-n1",
          isNewWork: false,
        },
      ],
      request,
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const outerArticle = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    const innerArticle = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    await outerArticle.click()
    await innerArticle.click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli/bund/bgbl-1/2021/123")

    await expect(
      page.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveText("16.03.2017 (Inkrafttreten)")

    await outerArticle.click()

    await expect(
      page.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli/bund/bgbl-1/2021/123")

    await expect(
      page.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveText("16.03.2017 (Inkrafttreten)")
  })

  test("keeps different data when multiple elements are selected", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await request.post(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      {
        data: [
          {
            geltungszeit: zeitgrenzenIds[0],
            zielnorm: "eli/bund/bgbl-1/2021/123",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z",
            isNewWork: false,
          },
          {
            geltungszeit: zeitgrenzenIds[1],
            zielnorm: "eli/bund/bgbl-1/2021/456",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z_untergl-n1_listenelem-n1",
            isNewWork: false,
          },
        ],
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const outerArticle = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    const innerArticle = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    const eli = page.getByRole("textbox", { name: "ELI Zielnormenkomplex" })

    const geltungszeitregel = page.getByRole("combobox", {
      name: "Geltungszeitregel",
    })

    await outerArticle.click()
    await innerArticle.click({ modifiers: ["ControlOrMeta"] })

    await expect(eli).toHaveAttribute("placeholder", "Mehrere")
    await expect(geltungszeitregel).toHaveText("Mehrere ausgewählt")

    await page.getByRole("button", { name: "Speichern" }).click()
    await expect(page.getByText("Gespeichert!")).toBeVisible()
    await page.reload()

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    await outerArticle.click()
    await expect(eli).toHaveValue("eli/bund/bgbl-1/2021/123")
    await expect(geltungszeitregel).toHaveText("16.03.2017 (Inkrafttreten)")

    await innerArticle.click()
    await expect(eli).toHaveValue("eli/bund/bgbl-1/2021/456")
    await expect(geltungszeitregel).toHaveText("30.05.2025 (Inkrafttreten)")
  })

  test("sets different data to the same value when multiple elements are selected", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await request.post(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      {
        data: [
          {
            geltungszeit: "bc2188d7-84f4-41c5-9921-41b95b79241b",
            zielnorm: "eli/bund/bgbl-1/2021/123",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z",
            isNewWork: false,
          },
          {
            geltungszeit: "fe0d79b2-449d-4b08-a826-b2ae5262067a",
            zielnorm: "eli/bund/bgbl-1/2021/456",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z_untergl-n1_listenelem-n1",
            isNewWork: false,
          },
        ],
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const outerArticle = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    const innerArticle = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    const eli = page.getByRole("textbox", { name: "ELI Zielnormenkomplex" })

    const geltungszeitregel = page.getByRole("combobox", {
      name: "Geltungszeitregel",
    })

    await outerArticle.click()
    await innerArticle.click({ modifiers: ["ControlOrMeta"] })

    await expect(eli).toHaveAttribute("placeholder", "Mehrere")
    await eli.fill("eli/bund/bgbl-1/2021/789")

    await expect(geltungszeitregel).toHaveText("Mehrere ausgewählt")
    await geltungszeitregel.click()
    await page
      .getByRole("option", { name: "20.06.2025 (Außerkrafttreten)" })
      .click()

    await page.getByRole("button", { name: "Speichern" }).click()
    await expect(page.getByText("Gespeichert!")).toBeVisible()
    await page.reload()

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    await outerArticle.click()
    await expect(eli).toHaveValue("eli/bund/bgbl-1/2021/789")
    await expect(geltungszeitregel).toHaveText("20.06.2025 (Außerkrafttreten)")

    await innerArticle.click()
    await expect(eli).toHaveValue("eli/bund/bgbl-1/2021/789")
    await expect(geltungszeitregel).toHaveText("20.06.2025 (Außerkrafttreten)")
  })

  test("allows deleting references for a single element", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await request.post(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      {
        data: [
          {
            geltungszeit: "bc2188d7-84f4-41c5-9921-41b95b79241b",
            zielnorm: "eli/bund/bgbl-1/2021/123",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z",
            isNewWork: false,
          },
        ],
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const article = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    await article.click()
    await page.getByRole("button", { name: "Einträge entfernen" }).click()
    await page
      .getByRole("alertdialog")
      .getByRole("button", { name: "Entfernen" })
      .click()

    await expect(page.getByText("Gelöscht")).toBeVisible()
    await expect(
      page.getByText("Bitte wählen Sie einen Artikel und eine Änderung aus."),
    ).toBeVisible()
  })

  test("allows deleting references for multiple elements", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await request.post(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      {
        data: [
          {
            geltungszeit: "bc2188d7-84f4-41c5-9921-41b95b79241b",
            zielnorm: "eli/bund/bgbl-1/2021/123",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z",
          },
          {
            geltungszeit: "fe0d79b2-449d-4b08-a826-b2ae5262067a",
            zielnorm: "eli/bund/bgbl-1/2021/456",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z_untergl-n1_listenelem-n1",
          },
        ],
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const outerArticle = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    const innerArticle = page.getByRole("button", {
      name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
      exact: true,
    })

    await innerArticle.click()
    await outerArticle.click({ modifiers: ["ControlOrMeta"] })
    await page.getByRole("button", { name: "Einträge entfernen" }).click()
    await page
      .getByRole("alertdialog")
      .getByRole("button", { name: "Entfernen" })
      .click()

    await expect(page.getByText("Gelöscht")).toBeVisible()
    await expect(
      page.getByText("Bitte wählen Sie einen Artikel und eine Änderung aus."),
    ).toBeVisible()
  })

  test("shows an error when saving fails", async ({
    page,
    authenticatedRequest: request,
  }) => {
    await request.post(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      {
        data: [
          {
            geltungszeit: zeitgrenzenIds[0],
            zielnorm: "eli/bund/bgbl-1/2021/123",
            typ: "Änderungsvorschrift",
            eId: "art-z1_abs-z",
          },
        ],
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const article = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    await article.click()

    await page
      .getByRole("textbox", { name: "ELI Zielnormenkomplex" })
      .fill("invalid/eli")

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Fehler: Invalide ELI")).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: /Das Vereinsgesetz vom 5\. August 1964/,
      }),
    ).toBeVisible()
  })

  test("shows an error when deleting fails", async ({ page }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnorm-references",
      async (route) => {
        if (route.request().method() === "DELETE") {
          await route.fulfill({ status: 500, json: {} })
        } else await route.continue()
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zielnormen",
    )

    await page.getByRole("button", { name: /Artikel 1/ }).click()

    const article = page.getByRole("button", {
      name: /Das Vereinsgesetz vom 5\. August 1964/,
    })

    await article.click()
    await page.getByRole("button", { name: "Einträge entfernen" }).click()
    await page
      .getByRole("alertdialog")
      .getByRole("button", { name: "Entfernen" })
      .click()

    await expect(
      page.getByText("Fehler: Ein unbekannter Fehler ist aufgetreten"),
    ).toBeVisible()
  })
})
