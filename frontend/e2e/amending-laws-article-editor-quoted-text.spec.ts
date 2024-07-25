import { expect, Page, test } from "@playwright/test"
import { ModData } from "@/types/ModType"

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

const BASE_URL =
  "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

test.beforeEach(async () => {
  await sharedPage.goto(BASE_URL)
})

test.describe("Loading mod details", () => {
  test("Loading of mod details with correct input values", async () => {
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

  test("Display preview of time machine", async () => {
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

test.describe("Editing a single mod", () => {
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
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
  })

  test.afterEach(async () => {
    await restoreInitialState()
  })

  test("Displaying time boundary", async () => {
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
