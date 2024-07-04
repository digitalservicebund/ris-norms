import { test, expect } from "@playwright/test"

test(`navigate to article editor using side navigation`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )

  await page.getByRole("link", { name: "Artikelübersicht" }).click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles`,
  )

  await page.getByText("Änderungsbefehl prüfen").first().click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test.describe("Url and selecting works", () => {
  test(`Navigation to base url opens editor without selected mods`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    await expect(
      page.getByText("Wählen sie einen Änderungsbefehl zur Bearbeitung aus."),
    ).toBeVisible()
  })

  test(`Navigation to url with selected mod opens editor with selected mod`, async ({
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

  test(`Selecting mod updates url`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("§ 1 Absatz 1 Satz 1").click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )
  })

  test(`Deselecting mod updates url`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("wie folgt geändert").click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
  })

  test(`Selecting multiple mod updates url`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
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

  test(`Deselecting some mod works`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
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
})

test.describe("Loading amending norm details", () => {
  test(`see amending law number and article title`, async ({ page }) => {
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

  test(`load amending law xml and html render in their respective tabs`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

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
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const textContent = page.getByText(
      "Wählen sie einen Änderungsbefehl zur Bearbeitung aus.",
    )
    await expect(textContent).toBeVisible()
  })

  test(`highlight mods in colors which change when hovered and selected`, async ({
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
  test("Loading of mod details with correct input values", async ({ page }) => {
    page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
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
        name: "Änderungsbefehl bearbeiten",
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
            { date: "2017-03-16", eventRefEid: "meta-1_lebzykl-1_ereignis-2" },
          ]),
        },
      )

      await page.reload()
    })
  })
})

test.describe("Editing multiple mods", () => {
  test("Loading of mod details with correct input values", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
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

    await expect(timeBoundariesElement).toHaveValue("1001-03-01")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(3)
  })

  test(`rendering the preview by default when clicking on a command`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    const previewSection = page.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewSection.getByText("Fiktives Beispielgesetz"),
    ).toBeVisible()
  })

  test(`selecting and saving the time boundary`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(
      page.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()
    await expect(page.getByRole("region", { name: "Vorschau" })).toContainText(
      "2. Fall",
    )

    // reset to original value
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("1.3.1001")

    await page.getByRole("button", { name: "Speichern" }).click()
    await expect(page.getByRole("region", { name: "Vorschau" })).toContainText(
      "2. Beispiel",
    )
  })

  test(`show "Mehrere" and no preview if time boundaries differ`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle Gesetz zur Änderung des Beispielgesetzes",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")
    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(
      page.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()

    await amendingLawSection
      .getByText("2. Fall")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("multiple")

    await expect(
      page.getByText(
        "Eine Vorschau kann nur für Änderungsbefehle mit der selben Zeitgrenze generiert werden.",
      ),
    ).toBeVisible()

    // reset data
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("1.3.1001")
    await page.getByRole("button", { name: "Speichern" }).click()
  })
})
