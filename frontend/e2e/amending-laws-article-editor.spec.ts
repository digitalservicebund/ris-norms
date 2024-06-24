import { test, expect } from "@playwright/test"

test(`navigate to article editor using side navigation`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )

  await page.getByText("Artikelübersicht").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles`,
  )

  await page.getByText("Änderungsbefehl prüfen").first().click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test.describe("Loading amending law and mod details", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
  })

  test(`see article number and amending law title`, async ({ page }) => {
    await expect(
      page.getByRole("heading", { level: 1, name: "Artikel 1" }),
    ).toBeVisible()

    await expect(
      page.getByRole("heading", { level: 2, name: "Änderungsbefehle prüfen" }),
    ).toBeVisible()
  })

  test(`load amending law xml and html render in their respective tabs`, async ({
    page,
  }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    })

    await expect(
      amendingLawSection.getByText(
        "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
      ),
    ).toBeVisible()

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

  test(`loading empty mod state when no command is selected`, async ({
    page,
  }) => {
    const textContent = page.getByText(
      "Wählen sie einen Änderungsbefehl zur Bearbeitung aus.",
    )
    await expect(textContent).toBeVisible()
  })

  test(`loading of mod details with correct input values`, async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehle bearbeiten",
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
})

test.describe("Generating preview", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
  })

  test(`rendering the preview by default when clicking on a command`, async ({
    page,
  }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
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

test.describe("Update mod details", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
  })

  test(`editing and saving the quotedTextSecondElement`, async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehle bearbeiten",
    })

    await modFormSection
      .getByRole("textbox", { name: "Neuer Text Inhalt" })
      .fill("testing new text")
    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    const previewSection = page.getByRole("region", {
      name: "Vorschau",
    })

    await expect(previewSection.getByText("testing new text")).toBeVisible()

    // reset to original value
    await modFormSection
      .getByRole("textbox", { name: "Neuer Text Inhalt" })
      .fill("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3")
    await modFormSection.getByRole("button", { name: "Speichern" }).click()
  })

  test(`editing and saving the eid mod change`, async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehle bearbeiten",
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

    // reset to original value
    await modFormSection
      .getByRole("textbox", { name: "zu ersetzende Textstelle" })
      .fill(
        "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
      )
    await modFormSection.getByRole("button", { name: "Speichern" }).click()
  })

  test(`selecting and saving the time boundary`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )

    await page
      .getByRole("textbox", { name: "Zeitgrenze hinzufügen" })
      .fill("10.10.2023")
    await page.getByRole("button", { name: "Speichern" }).click()

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    })

    await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehle bearbeiten",
    })

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-10-10")

    await modFormSection.getByRole("button", { name: "Speichern" }).click()
    await amendingLawSection.getByRole("tab", { name: "xml" }).click()
    await expect(
      amendingLawSection.getByText('date="2023-10-10"'),
    ).toBeVisible()

    // reset to original value
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2017-03-16")

    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )

    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries",
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        data: JSON.stringify([
          {
            date: "2017-03-16",
            eventRefEid: "meta-1_lebzykl-1_ereignis-2",
            temporalGroupEid: "meta-1_geltzeiten-1_geltungszeitgr-1",
          },
        ]),
      },
    )

    await page.reload()
  })
})
