import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("navigation", () => {
  test("navigate to article editor using side navigation", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )

    await page.getByRole("link", { name: "Artikelübersicht" }).click()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
    )

    await page.getByText("Änderungsbefehl prüfen").first().click()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })

  test("navigate to base url opens editor without selected mods", async ({
    page,
  }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    await expect(
      page.getByText("Wählen Sie einen Änderungsbefehl zur Bearbeitung aus."),
    ).toBeVisible()

    await expect(
      page.getByRole("navigation").getByText("BGBl. I 2017 S. 419"),
    ).toBeVisible()

    await expect(
      page.getByRole("navigation").getByText("Änderung des Vereinsgesetzes"),
    ).toBeVisible()
  })

  test("navigate to url with selected mod opens editor with selected mod", async ({
    page,
  }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    await expect(
      page.getByRole("combobox", {
        name: "Zeitgrenze",
      }),
    ).toHaveText("01.02.1001")
  })
})

test.describe("mod selection and URL behaviour", () => {
  test("selecting mod updates url", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 1 Absatz 1 Satz 1").click()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )
  })

  test("deselecting mod updates url", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("wie folgt geändert").click()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })

  test("selecting multiple mod updates url", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection
      .getByText("§ 1 Absatz 2 Satz 1")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "2 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })

  test("deselecting some mod works", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection
      .getByText("§ 1 Absatz 2 Satz 1")
      .click({ modifiers: ["ControlOrMeta"] })
    await amendingLawSection
      .getByText("§ 1 Absatz 1 Satz 1")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "Änderungsbefehl bearbeiten",
      }),
    ).toBeVisible()

    await expect(page).toHaveURL(
      "/app/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
    )
  })

  test("selecting a range of mods using Shift + click works", async ({
    page,
  }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 1 Absatz 2 Satz 1").click()
    await amendingLawSection
      .getByText("§ 1 Absatz 5 Satz 1")
      .click({ modifiers: ["Shift"] })

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "4 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()
  })

  test("selecting all mods using Ctrl+A works", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection
      .getByText("Beispielgesetz vom 1. Januar 1001")
      .click()

    await page.keyboard.press("ControlOrMeta+a")

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "10 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()
  })

  test("apply 'selected' class when element is clicked", async ({ page }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: /^Änderungsbefehle/,
    })

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 1 Satz 1/),
    ).not.toHaveClass(/selected/)

    await amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/).click()

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/),
    ).toHaveClass(/selected/)
  })
})

test.describe("XML and HTML tabs", () => {
  test("load amending law xml and html in their respective tabs", async ({
    page,
  }) => {
    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await expect(
      amendingLawSection.getByRole("tab", { name: "xml" }),
    ).toBeVisible()
    await amendingLawSection.getByRole("tab", { name: "xml" }).click()

    await expect(
      page.getByText('value="eli/bund/bgbl-1/2017/s419/regelungstext-1"'),
    ).toBeVisible()

    await expect(
      amendingLawSection.getByRole("tab", { name: "Text" }),
    ).toBeVisible()
    await amendingLawSection.getByRole("tab", { name: "Text" }).click()

    await expect(
      page.getByText("Artikel 1Änderung des Vereinsgesetzes"),
    ).toBeVisible()
  })

  test("handle 404 error for XML in tabs", async ({ page }) => {
    await page.route(
      "**/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?",
      async (route, request) => {
        if (
          request.method() === "GET" &&
          request.headers()["accept"] === "application/xml"
        ) {
          await route.fulfill({
            status: 404,
          })
        } else {
          await route.continue()
        }
      },
    )

    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
