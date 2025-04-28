import { test } from "@e2e/utils/testWithAuth"
import { expect } from "playwright/test"

test.describe(
  "showing the Zielnormen page for a Verkündung",
  { tag: ["@RISDEV-6946"] },
  () => {
    test("opens the page for a Verkündung", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
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
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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

    test("initially shows an empty state if no Artikel has been selected yet", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
      )

      await expect(
        page.getByText("Bitte wählen Sie einen Änderungsbefehl aus."),
      ).toBeVisible()
    })
  },
)

test.describe("Geltungszeiten-Artikel", { tag: ["@RISDEV-6946"] }, () => {
  test("shows the preview of the Geltungszeiten-Artikel", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
      async (route) => {
        await route.fulfill({ status: 500, json: {} })
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
})

test.describe("table of contents", { tag: ["@RISDEV-6946"] }, () => {
  test("shows the table of contents of the Verkündung", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
    )

    const toc = page.getByRole("tree", { name: "Inhaltsverzeichnis" })

    await expect(toc).toHaveText(
      "Artikel 1\nÄnderung des Vereinsgesetzes\nArtikel 3\nInkrafttreten",
      { useInnerText: true },
    )
  })

  test("shows an error if the table contents can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/toc",
      async (route) => {
        await route.fulfill({ status: 500, json: {} })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("shows an empty state if the Verkündung has no content", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/toc",
      async (route) => {
        await route.fulfill({ status: 200, json: [] })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
    )

    await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()
  })
})

test.describe("Artikel detail", { tag: ["@RISDEV-6946"] }, () => {
  test("displays the preview of an Artikel", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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

  test("shows an error if the Artikel contents can't be loaded", async ({
    page,
  }) => {
    await page.route(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1",
      async (route) => {
        if ((await route.request().headerValue("Accept")) === "text/html") {
          await route.fulfill({ status: 500, json: {} })
        } else return route.fallback()
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/elements/hauptteil-1_art-1",
      async (route) => {
        await route.fulfill({ status: 200, body: "" })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnormen",
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
