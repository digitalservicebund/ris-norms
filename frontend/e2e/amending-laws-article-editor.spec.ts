import { test, expect, Page } from "@playwright/test"
import { ModData } from "@/types/ModType"

test("navigate to article editor using side navigation", async ({ page }) => {
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

test.describe("Url and selecting works", () => {
  test("Navigation to base url opens editor without selected mods", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    await expect(
      page.getByText("Wählen Sie einen Änderungsbefehl zur Bearbeitung aus."),
    ).toBeVisible()
  })

  test("Navigation to url with selected mod opens editor with selected mod", async ({
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
})

test.describe("Loading amending norm details", () => {
  test("see amending law number and article title", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    await expect(
      page.getByRole("navigation").getByText("BGBl. I 2017 S. 419"),
    ).toBeVisible()

    await expect(
      page.getByRole("navigation").getByText("Änderung des Vereinsgesetzes"),
    ).toBeVisible()
  })

  test("load amending law xml and html render in their respective tabs", async ({
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
  })

  test("loading empty mod state when no command is selected", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const textContent = page.getByText(
      "Wählen Sie einen Änderungsbefehl zur Bearbeitung aus.",
    )
    await expect(textContent).toBeVisible()
  })

  test("highlight mods in colors which change when hovered and selected", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: /^Änderungsbefehle/,
    })

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 1 Satz 1/),
    ).toHaveCSS("background-color", "rgb(208, 223, 240)")

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/),
    ).toHaveCSS("background-color", "rgb(208, 223, 240)")

    await amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/).hover()

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/),
    ).toHaveCSS("background-color", "rgb(166, 188, 221)")

    await amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/).click()

    await expect(
      amendingLawSection.getByText(/^In § 1 Absatz 2 Satz 1/),
    ).toHaveCSS("background-color", "rgb(121, 153, 200)")
  })
})

test.describe("Editing a single mod", () => {
  async function restoreInitialState(page: Page) {
    const originalModState: ModData = {
      refersTo:
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
      timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-1",
      destinationHref:
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
      newContent: "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3",
    }

    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/mods/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1",
      { data: originalModState },
    )
  }

  let sharedPage: Page
  test.beforeAll(async ({ browser }) => {
    sharedPage = await browser.newPage()
    await restoreInitialState(sharedPage)
  })

  test.afterAll(async () => {
    await restoreInitialState(sharedPage)
  })
  test("Loading of mod details with correct input values", async () => {
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    await expect(
      sharedPage.getByRole("heading", {
        level: 3,
        name: "Änderungsbefehl bearbeiten",
      }),
    ).toBeVisible()

    const modFormSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })
    await expect(modFormSection).toBeVisible()

    // Textual Mode Type
    const textualModeTypeElement = modFormSection.getByRole("textbox", {
      name: "Änderungstyp",
    })
    await expect(textualModeTypeElement).toBeVisible()
    await expect(textualModeTypeElement).toHaveValue("Ersetzen")
    await expect(textualModeTypeElement).toHaveAttribute("readonly", "")

    // Time Boundaries
    const timeBoundariesElement = modFormSection.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    await expect(timeBoundariesElement).toBeVisible()
    await expect(timeBoundariesElement).toHaveValue("2017-03-16")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(2)

    // // Destination Href Eli
    const destinationHrefEliElement = modFormSection.getByRole("textbox", {
      name: "ELI Zielgesetz",
    })
    await expect(destinationHrefEliElement).toBeVisible()
    await expect(destinationHrefEliElement).toHaveValue(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1",
    )
    await expect(destinationHrefEliElement).toHaveAttribute("readonly", "")

    // Destination Href Eid
    const destinationHrefEidElement = modFormSection.getByRole("textbox", {
      name: "zu ersetzende Textstelle",
    })
    await expect(destinationHrefEidElement).toBeVisible()
    await expect(destinationHrefEidElement).toHaveValue(
      "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
    )
    await expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    // Quoted Text First
    const quotedTextFirstElement = modFormSection.getByRole("textbox", {
      name: "zu ersetzender Text",
    })
    await expect(quotedTextFirstElement).toBeVisible()
    await expect(quotedTextFirstElement).toHaveValue(
      "§ 9 Abs. 1 Satz 2, Abs. 2",
    )
    await expect(quotedTextFirstElement).toHaveAttribute("readonly", "")

    // Quoted Text Second
    const quotedTextSecondElement = modFormSection.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })
    await expect(quotedTextSecondElement).toBeVisible()
    await expect(quotedTextSecondElement).toHaveValue(
      "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3",
    )
    await expect(quotedTextSecondElement).not.toHaveAttribute("readonly")
  })

  test.describe("Generating preview", () => {
    const BASE_URL =
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

    test.beforeEach(async () => {
      await sharedPage.goto(BASE_URL)
    })

    test("rendering the preview by default when clicking on a command", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })
      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()
      const previewSection = sharedPage.getByRole("region", {
        name: "Vorschau",
      })
      await expect(
        previewSection.getByText(
          "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        ),
      ).toBeVisible()
    })
  })

  test.describe("Update mod details", () => {
    const BASE_URL =
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

    test.beforeEach(async () => {
      await sharedPage.goto(BASE_URL)
    })

    test("editing and saving the quotedTextSecondElement", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })

      await modFormSection
        .getByRole("textbox", { name: "Neuer Text Inhalt" })
        .fill("testing new text")
      await modFormSection.getByRole("button", { name: "Speichern" }).click()

      const previewSection = sharedPage.getByRole("region", {
        name: "Vorschau",
      })

      await expect(previewSection.getByText("testing new text")).toBeVisible()
    })

    test("preview of text to be replaced in xml", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })
      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })
      const previewSection = sharedPage.getByRole("region", {
        name: "Vorschau",
      })

      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

      await modFormSection
        .getByRole("textbox", { name: "Neuer Text Inhalt" })
        .fill("testing new text")
      await modFormSection.getByRole("button", { name: "Speichern" }).click()

      await previewSection.getByRole("tab", { name: "xml" }).click()

      await previewSection
        .getByText('<?xml version="1.0" encoding="UTF-8"?>')
        .click()
      await sharedPage.keyboard.press("ControlOrMeta+f")
      await previewSection
        .getByRole("textbox", { name: "Find" })
        .fill("testing new text")
      await previewSection.getByRole("button", { name: "next" }).click()
      await expect(
        previewSection.getByText("testing new text").first(),
      ).toBeVisible()
    })

    test("editing and saving the eid mod change", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })

      await modFormSection
        .getByRole("textbox", { name: "zu ersetzende Textstelle" })
        .fill(
          "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
        )
      await modFormSection.getByRole("button", { name: "Speichern" }).click()
      await amendingLawSection.getByRole("tab", { name: "xml" }).click()
      await expect(
        amendingLawSection.getByText(
          "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
        ),
      ).toBeVisible()
    })

    test("selecting and saving the time boundary", async () => {
      // use api to create new time boundary
      await sharedPage.request.put(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries",
        {
          data: [
            {
              date: "2017-03-16",
              eventRefEid: "meta-1_lebzykl-1_ereignis-2",
              temporalGroupEid: "meta-1_geltzeiten-1_geltungszeitgr-1",
            },
            { date: "2023-10-10", eventRefEid: "" },
          ],
        },
      )

      await sharedPage.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
      )

      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })

      await sharedPage
        .getByRole("combobox", { name: "Zeitgrenze" })
        .selectOption("2023-10-10")

      await modFormSection.getByRole("button", { name: "Speichern" }).click()
      await amendingLawSection.getByRole("tab", { name: "xml" }).click()
      await expect(
        amendingLawSection.getByText('date="2023-10-10"'),
      ).toBeVisible()

      // delete test time boundary
      await sharedPage.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
      )
      await sharedPage.request.put(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries",
        {
          data: [
            {
              date: "2017-03-16",
              eventRefEid: "meta-1_lebzykl-1_ereignis-2",
              temporalGroupEid: "meta-1_geltzeiten-1_geltungszeitgr-1",
            },
          ],
        },
      )
    })
  })
})

test.describe("Editing multiple mods", () => {
  let sharedPage: Page

  async function restoreInitialState() {
    const originalModsState = {
      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1":
        { timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-1" },
      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1":
        { timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-1" },
    }

    await sharedPage.request.patch(
      "api/v1/norms/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/mods",
      { data: originalModsState },
    )
  }

  test.beforeEach(async ({ browser }) => {
    sharedPage = await browser.newPage()
    await restoreInitialState()
  })

  test.afterEach(async () => {
    await restoreInitialState()
  })

  test("Loading of mod details with correct input values", async () => {
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      sharedPage.getByRole("heading", {
        level: 3,
        name: "2 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    const timeBoundariesElement = sharedPage.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    await expect(timeBoundariesElement).toHaveValue("1001-03-01")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(3)
  })

  test("rendering the preview by default when clicking on a command", async () => {
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    const previewSection = sharedPage.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewSection.getByText("Fiktives Beispielgesetz"),
    ).toBeVisible()
  })

  test("selecting and saving the time boundary", async () => {
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await sharedPage
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")

    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()
    await expect(
      sharedPage.getByRole("region", { name: "Vorschau" }),
    ).toContainText("2. Fall")
  })

  test("show 'Mehrere' and no preview if time boundaries differ", async () => {
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()

    await sharedPage
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")
    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()

    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      sharedPage.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("multiple")

    await expect(
      sharedPage.getByText(
        "Eine Vorschau kann nur für Änderungsbefehle mit der selben Zeitgrenze generiert werden.",
      ),
    ).toBeVisible()
  })
})

test.describe("Quoted Structure", () => {
  test("Navigation to url with selected mod opens editor with selected mod", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_%C3%A4ndbefehl-1",
    )

    await expect(page.getByTestId("replacingElement")).toHaveText(
      "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
    )
  })

  test.describe("Load mod details", () => {
    test("Loading of mod details into form", async ({ page }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
      )

      const amendingLawSection = page.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

      await expect(
        page.getByRole("heading", {
          level: 3,
          name: "Änderungsbefehl bearbeiten",
        }),
      ).toBeVisible()

      const modFormSection = page.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })
      await expect(modFormSection).toBeVisible()

      // Textual Mode Type
      const textualModeTypeElement = modFormSection.getByRole("textbox", {
        name: "Änderungstyp",
      })
      await expect(textualModeTypeElement).toBeVisible()
      await expect(textualModeTypeElement).toHaveValue("Ersetzen")
      await expect(textualModeTypeElement).toHaveAttribute("readonly", "")

      // Time Boundaries
      const timeBoundariesElement = modFormSection.getByRole("combobox", {
        name: "Zeitgrenze",
      })
      await expect(timeBoundariesElement).toBeVisible()
      await expect(timeBoundariesElement).toHaveValue("1002-01-11")

      const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
      await expect(timeBoundaryOptionElements).toHaveCount(3)

      // // Destination Href Eli
      const destinationHrefEliElement = modFormSection.getByRole("textbox", {
        name: "ELI Zielgesetz",
      })
      await expect(destinationHrefEliElement).toBeVisible()
      await expect(destinationHrefEliElement).toHaveValue(
        "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1",
      )
      await expect(destinationHrefEliElement).toHaveAttribute("readonly", "")

      const elementToBeReplacedField = page.getByTestId("elementToBeReplaced")

      const selectedElementLocator = elementToBeReplacedField.getByRole(
        "button",
        { name: "long title p Fiktives" },
      )

      await expect(selectedElementLocator).toBeInViewport()

      await expect(selectedElementLocator).toHaveClass(/selected/)

      await expect(page.getByTestId("replacingElement")).toHaveText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
      )
    })

    test("Display preview of time machine", async ({ page }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
      )
      const amendingLawSection = page.getByRole("region", {
        name: "Änderungsbefehle",
      })
      const previewSection = page.getByRole("region", {
        name: "Vorschau",
      })

      await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

      await expect(
        previewSection.getByText(
          "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten (Strukturänderungsgesetz)",
        ),
      ).toBeHidden()

      await expect(
        previewSection.getByText(
          "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
        ),
      ).toBeVisible()

      await previewSection.getByRole("tab", { name: "xml" }).click()

      // Do a search
      await previewSection
        .getByText('<?xml version="1.0" encoding="UTF-8"?>')
        .click()
      await page.keyboard.press("ControlOrMeta+f")
      await previewSection
        .getByRole("textbox", { name: "Find" })
        .fill("mit Änderungsbefehlen")
      await previewSection.getByRole("button", { name: "next" }).click()
      await expect(
        previewSection.getByText("mit Änderungsbefehlen").first(),
      ).toBeVisible()
    })
  })

  test.describe("Update mod details", () => {
    async function restoreInitialState(page: Page) {
      const originalModState: ModData = {
        refersTo: "aenderungsbefehl-ersetzen",
        timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-2",
        destinationHref:
          "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml",
        newContent: "NOT USED",
      }

      await page.request.put(
        "/api/v1/norms/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/mods/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_%C3%A4ndbefehl-1",
        { data: originalModState },
      )
    }

    let sharedPage: Page
    test.beforeAll(async ({ browser }) => {
      sharedPage = await browser.newPage()
      await restoreInitialState(sharedPage)
    })

    test.afterAll(async () => {
      await restoreInitialState(sharedPage)
    })
    const BASE_URL =
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit"

    test.beforeEach(async () => {
      await sharedPage.goto(BASE_URL)
    })

    test("selecting and saving the time boundary", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })

      await sharedPage
        .getByRole("combobox", { name: "Zeitgrenze" })
        .selectOption("1002-01-10")

      await modFormSection.getByRole("button", { name: "Speichern" }).click()
      await amendingLawSection.getByRole("tab", { name: "xml" }).click()

      await expect(
        sharedPage.getByRole("combobox", { name: "Zeitgrenze" }),
      ).toHaveValue("1002-01-10")
    })

    test("select another target node and see preview", async () => {
      const amendingLawSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehle",
      })
      const modFormSection = sharedPage.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })
      const previewSection = sharedPage.getByRole("region", {
        name: "Vorschau",
      })

      await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

      const elementToBeReplacedField = sharedPage.getByTestId(
        "elementToBeReplaced",
      )

      await elementToBeReplacedField
        .getByRole("button", {
          name: "headingGesetz zur Einbindung eines Regelungstextes in Stammformheading",
          exact: true,
        })
        .click()

      await modFormSection.getByRole("button", { name: "Speichern" }).click()

      await expect(
        previewSection.getByText(
          "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten (Strukturänderungsgesetz)",
        ),
      ).toBeVisible()

      await expect(
        previewSection.getByText(
          "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
        ),
      ).toBeVisible()

      await previewSection.getByRole("tab", { name: "xml" }).click()

      // Do a search
      await previewSection
        .getByText('<?xml version="1.0" encoding="UTF-8"?>')
        .click()
      await sharedPage.keyboard.press("ControlOrMeta+f")
      await previewSection
        .getByRole("textbox", { name: "Find" })
        .fill("<akn:longTitle")
      await previewSection.getByRole("button", { name: "next" }).click()
      await expect(
        previewSection.getByText("<akn:longTitle").first(),
      ).toBeVisible()
    })
  })
})
