import { test, expect } from "@playwright/test"

test.describe("Navigation", () => {
  test("Navigate to article editor using side navigation", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )

    await page.getByRole("link", { name: "Artikelübersicht" }).click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
    )

    await page.getByText("Änderungsbefehl prüfen").first().click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })

  test("Navigate to base url opens editor without selected mods", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
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

  test("Navigate to url with selected mod opens editor with selected mod", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    await expect(
      page.getByRole("textbox", {
        name: "Neuer Text Inhalt",
      }),
    ).toHaveValue("1. Beispiel")
  })
})

test.describe("Mod selection and url behaviour", () => {
  test("Selecting mod updates url", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 1 Absatz 1 Satz 1").click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )
  })

  test("Deselecting mod updates url", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("wie folgt geändert").click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
  })

  test("Selecting multiple mod updates url", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
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
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
  })

  test("Deselecting some mod works", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
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
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
    )
  })

  test("Selecting a range of mods using Shift + click works", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
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

  test("Selecting all mods using Ctrl+A works", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
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
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
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
  test("Load amending law xml and html in their respective tabs", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await expect(
      amendingLawSection.getByRole("tab", { name: "xml" }),
    ).toBeVisible()
    await amendingLawSection.getByRole("tab", { name: "xml" }).click()

    await expect(
      page.getByText(
        '<akn:meta GUID="7e5837c8-b967-45be-924b-c95956c4aa94" eId="meta-1">',
      ),
    ).toBeVisible()

    await expect(
      amendingLawSection.getByRole("tab", { name: "Text" }),
    ).toBeVisible()
    await amendingLawSection.getByRole("tab", { name: "Text" }).click()

    await expect(
      page.getByText("Artikel 1Änderung des Vereinsgesetzes"),
    ).toBeVisible()
  })
})
