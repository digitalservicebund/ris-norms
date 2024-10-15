import { expect, test } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/upload-with.force"

test.describe("Loading mod details", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })
  test("Loading of mod details with correct input values", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

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
    await expect(timeBoundariesElement).toHaveValue("2017-03-16")

    const timeBoundaryOptionElements = timeBoundariesElement.getByRole("option")
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
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
    )
    await expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    // Quoted Text First
    const quotedTextFirstElement = modFormSection
      .getByLabel("zu ersetzender Text")
      .getByRole("textbox")
    await expect(quotedTextFirstElement).toBeVisible()
    await expect(quotedTextFirstElement).toContainText(
      "§ 9 Abs. 1 Satz 2, Abs. 2",
    )

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

  test("Display preview of time machine", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })
    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()
    const previewSection = page.getByRole("region", {
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
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })
  test.afterEach(async ({ request }) => {
    await uploadAmendingLaw(request, "Vereinsgesetz_2017_s419_2017-03-15.xml")
  })
  test("editing and saving the quotedTextSecondElement", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await modFormSection
      .getByRole("textbox", { name: "Neuer Text Inhalt" })
      .fill("testing new text")
    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    const previewSection = page.getByRole("region", {
      name: "Vorschau",
    })

    await expect(previewSection.getByText("testing new text")).toBeVisible()
  })

  test("preview of text to be replaced in xml", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })
    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })
    const previewSection = page.getByRole("region", {
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
    await page.keyboard.press("ControlOrMeta+f")
    await previewSection
      .getByRole("textbox", { name: "Find" })
      .fill("testing new text")
    await previewSection.getByRole("button", { name: "next" }).click()
    await expect(
      previewSection.getByText("testing new text").first(),
    ).toBeVisible()
  })

  test("editing and saving the eid mod change", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await modFormSection
      .getByRole("textbox", { name: "zu ersetzende Textstelle" })
      .fill(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
      )

    const saveResponsePromise = page.waitForResponse("**/norms/eli/**")
    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    // Then
    const saveResponse = await (await saveResponsePromise).json()
    expect(saveResponse.amendingNormXml).toContain(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
    )
  })

  test("editing and saving the eid mod change by highlighting", async ({
    page,
  }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await expect(
      modFormSection.getByRole("textbox", { name: "zu ersetzende Textstelle" }),
    ).toHaveValue(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
    )

    const textBoundingBox = await modFormSection
      .getByLabel("Zu ersetzender Text")
      .getByText("entgegen § 9")
      .boundingBox()

    await page.mouse.dblclick(textBoundingBox!.x + 50, textBoundingBox!.y + 5)
    await page.mouse.click(0, 0)

    await expect(
      modFormSection.getByRole("textbox", { name: "zu ersetzende Textstelle" }),
    ).not.toHaveValue(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
    )

    await expect(
      modFormSection.getByRole("textbox", { name: "zu ersetzende Textstelle" }),
    ).toHaveValue(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-8.xml",
    )
  })

  test("selecting and saving the time boundary", async ({ page }) => {
    // use api to create new time boundary
    await page.request.put(
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

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-10-10")

    const saveResponsePromise = page.waitForResponse("**/norms/eli/**")
    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    // Then
    const saveResponse = await (await saveResponsePromise).json()
    expect(saveResponse.amendingNormXml).toContain('date="2023-10-10"')

    // delete test time boundary
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )
    await page.request.put(
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
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })
  test.afterEach(async ({ request }) => {
    await uploadAmendingLaw(
      request,
      "Fiktives Beispielgesetz_1001_11_1001-01-01.xml",
    )
  })
  test("Displaying time boundary", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Beispiel")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "2 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    const timeBoundariesElement = page.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    await expect(timeBoundariesElement).toHaveValue("multiple")

    const timeBoundaryOptionElements = timeBoundariesElement.getByRole("option")
    await expect(timeBoundaryOptionElements).toHaveCount(4)
  })

  test("rendering the preview by default when clicking on a command", async ({
    page,
  }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("3. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    const previewSection = page.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewSection.getByText("Fiktives Beispielgesetz"),
    ).toBeVisible()
  })

  test("selecting and saving the time boundary", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("3. Fall").click()
    await amendingLawSection
      .getByText("4. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Speichern erfolgreich")).toBeVisible()

    await expect(page.getByRole("region", { name: "Vorschau" })).toContainText(
      "4. Fall",
    )
  })

  test("show 'Mehrere' and no preview if time boundaries differ", async ({
    page,
  }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")
    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Speichern erfolgreich")).toBeVisible()

    await amendingLawSection
      .getByText("2. Beispiel")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("multiple")

    await expect(
      page.getByText(
        "Eine Vorschau kann nur für Änderungsbefehle mit der selben Zeitgrenze generiert werden.",
      ),
    ).toBeVisible()
  })
})
