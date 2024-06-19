import { Proprietary } from "@/types/proprietary"
import { Page, expect, test } from "@playwright/test"

test.describe("navigate to page", () => {
  test("navigate to the metadata editor", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents",
    )

    await page.getByRole("link", { name: "Metadaten bearbeiten" }).click()

    // Only expect the URL to be somewhat equal to the following. The reason is that
    // the page redirects to a subpage, so this test might be flaky otherwise depending
    // on how fast the redirect is.
    await expect(page).toHaveURL(
      /.*\/amending-laws\/eli\/bund\/bgbl-1\/2023\/413\/2023-12-29\/1\/deu\/regelungstext-1\/affected-documents\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\/edit.*/,
    )
  })

  test("displays affected document title", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    await expect(page.getByText("BGBl. I 2023 Nr. 413")).toBeVisible()

    await expect(
      page.getByText(
        "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
      ),
    ).toBeVisible()
  })

  test("navigates to the selected time boundary", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )
  })
})

test.describe("preview", () => {
  test("displays the title and preview", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await expect(page.getByRole("heading", { name: "Rahmen" })).toBeVisible()

    const preview = page.getByRole("region", { name: "Vorschau" })
    await expect(
      preview.getByRole("heading", {
        name: "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz Bundesverfassungsschutzgesetz",
      }),
    ).toBeVisible()
  })

  test("shows the preview at different time boundaries", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    await expect(preview).toHaveText(
      /.*am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt.*/,
    )

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    await expect(preview).toHaveText(/.*nach Ablauf von fünf Jahren.*/)
  })

  test("shows an error when the preview could not be loaded", async ({
    page,
  }) => {
    await page.route(
      /norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\?atIsoDate=/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const previewRegion = page.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewRegion.getByText("Die Vorschau konnte nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})

test.describe("XML view", () => {
  test("displays the XML of the target law with metadata", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await page.getByRole("tab", { name: "XML" }).click()

    await expect(
      page
        .getByRole("region", { name: "Metadaten bearbeiten" })
        .getByText(
          'value="eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1"',
        ),
    ).toBeVisible()
  })

  test("updates the XML preview after saving metadata", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    // Updating the FNA as an example for any change happening on the page
    const fnaInput = editorRegion.getByRole("textbox", {
      name: "Sachgebiet",
    })
    await expect(fnaInput).toHaveValue("210-5")
    await fnaInput.fill("1234-56-78")
    await editorRegion
      .getByRole("button", { name: "Metadaten speichern" })
      .click()

    // Check the content of the XML reload call as we currently don't have a
    // good way of checking the actual editor content. This is because
    // CodeMirror uses lazy scrolling and therefore depending on the size of the
    // document the text snippet we're looking for might not actually be rendered.
    const textResponse = await page
      .waitForResponse(
        // XML reload call
        /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\?$/,
      )
      .then((response) => response.text())

    expect(textResponse).toContain("1234-56-78")

    // Reset the data
    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/1970-01-01",
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        data: JSON.stringify({
          art: "regelungstext",
          fna: "210-5",
          subtyp: "Rechtsverordnung",
          typ: "gesetz",
          bezeichnungInVorlage: "Testbezeichnung nach meiner Vorlage",
          artDerNorm: "SN,ÜN",
          normgeber: "BEO - Berlin (Ost)",
          beschliessendesOrgan: "BMinJ - Bundesministerium der Justiz",
          qualifizierteMehrheit: true,
          federfuehrung: "BMVg - Bundesministerium der Verteidigung",
        }),
      },
    )
  })

  test("shows an error when the XML could not be loaded", async ({ page }) => {
    await page.route(
      /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\?$/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await page.getByRole("tab", { name: "XML" }).click()

    await expect(
      page.getByText("Die XML-Ansicht konnte nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})

test.describe("metadata view", () => {
  // TODO: Can this be changed to use the same example data?
  test("displays metadata at different time boundaries", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1/edit/1934-10-16",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await page.waitForResponse((response) =>
      response.url().includes("/proprietary/"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet")).toHaveValue("754-28-2")

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue("")
    await expect(
      editorRegion.getByLabel("Bezeichnung gemäß Vorlage"),
    ).toBeEmpty()
    const SNcheckbox = page.getByRole("checkbox", {
      name: "SN - Stammnorm",
    })
    const ANcheckbox = page.getByRole("checkbox", {
      name: "ÄN - Änderungsnorm",
    })
    const UNcheckbox = page.getByRole("checkbox", {
      name: "ÜN - Übergangsnorm",
    })
    await expect(SNcheckbox).not.toBeChecked()
    await expect(ANcheckbox).not.toBeChecked()
    await expect(UNcheckbox).not.toBeChecked()
    await expect(editorRegion.getByLabel("Normgeber")).toHaveValue("")
    await expect(editorRegion.getByLabel("beschließendes Organ")).toHaveValue(
      "",
    )
    await expect(
      editorRegion.getByLabel("Beschlussf. qual. Mehrheit"),
    ).not.toBeChecked()
    await expect(editorRegion.getByLabel("Federführung")).toHaveValue(
      "BMF - Bundesministerium der Finanzen",
    )

    const dropdown = page.getByRole("combobox", { name: "Zeitgrenze" })
    dropdown.selectOption("2009-10-08")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2009-10-08"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet")).toHaveValue("111-11-1")

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Verwaltungsvorschrift",
    )
    await expect(
      editorRegion.getByLabel("Bezeichnung gemäß Vorlage"),
    ).toHaveValue("Testbezeichnung 1 nach meiner Vorlage")
    await expect(SNcheckbox).toBeChecked()
    await expect(ANcheckbox).not.toBeChecked()
    await expect(UNcheckbox).toBeChecked()
    await expect(editorRegion.getByLabel("Normgeber")).toHaveValue(
      "MV - Land Mecklenburg-Vorpommern",
    )
    await expect(editorRegion.getByLabel("beschließendes Organ")).toHaveValue(
      "BT - Bundestag",
    )
    await expect(
      editorRegion.getByLabel("Beschlussf. qual. Mehrheit"),
    ).toBeChecked()
    await expect(editorRegion.getByLabel("Federführung")).toHaveValue(
      "BMWSB - Bundesministerium für Wohnen, Stadtentwicklung und Bauwesen",
    )

    dropdown.selectOption("2023-01-01")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-01-01"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet")).toHaveValue("222-22-2")
    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )
    await expect(
      editorRegion.getByLabel("Bezeichnung gemäß Vorlage"),
    ).toHaveValue("Testbezeichnung 2 nach meiner Vorlage")
    await expect(SNcheckbox).not.toBeChecked()
    await expect(ANcheckbox).toBeChecked()
    await expect(UNcheckbox).toBeChecked()
    await expect(editorRegion.getByLabel("Normgeber")).toHaveValue(
      "PR - Preußen",
    )
    await expect(editorRegion.getByLabel("beschließendes Organ")).toHaveValue(
      "OFD - Oberfinanzdirektion",
    )
    await expect(
      editorRegion.getByLabel("Beschlussf. qual. Mehrheit"),
    ).not.toBeChecked()
    await expect(editorRegion.getByLabel("Federführung")).toHaveValue(
      "BMDV - Bundesministerium für Digitales und Verkehr",
    )

    dropdown.selectOption("2023-12-24")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-12-24"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet")).toHaveValue("333-33-3")

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )
    await expect(
      editorRegion.getByLabel("Bezeichnung gemäß Vorlage"),
    ).toHaveValue("Testbezeichnung 3 nach meiner Vorlage")
    await expect(SNcheckbox).toBeChecked()
    await expect(ANcheckbox).toBeChecked()
    await expect(UNcheckbox).toBeChecked()
    await expect(editorRegion.getByLabel("Normgeber")).toHaveValue(
      "EA - Euratom",
    )
    await expect(editorRegion.getByLabel("beschließendes Organ")).toHaveValue(
      "BMinI - Bundesministerium des Innern",
    )
    await expect(
      editorRegion.getByLabel("Beschlussf. qual. Mehrheit"),
    ).toBeChecked()
    await expect(editorRegion.getByLabel("Federführung")).toHaveValue(
      "BMG - Bundesministerium für Gesundheit",
    )
  })

  test("displays an error if the metadata could not be loaded", async ({
    page,
  }) => {
    await page.route(/\/proprietary\/2023-12-30$/, (request) => {
      request.abort()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await expect(
      editorRegion.getByText("Die Metadaten konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})

test.describe("displaying, editing, and saving", () => {
  let sharedPage: Page

  async function restoreInitialState() {
    const data: Proprietary = {
      fna: "210-5",
      art: "regelungstext",
      typ: "gesetz",
      subtyp: "Rechtsverordnung",
      bezeichnungInVorlage: "Testbezeichnung nach meiner Vorlage",
      artDerNorm: "SN,ÜN",
      normgeber: "BEO - Berlin (Ost)",
      beschliessendesOrgan: "BMinJ - Bundesministerium der Justiz",
      qualifizierteMehrheit: true,
    }

    await sharedPage.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/2023-12-30",
      { data },
    )
  }

  async function saveMetadata() {
    await sharedPage
      .getByRole("button", { name: "Metadaten speichern" })
      .click()
    await sharedPage.waitForResponse(/proprietary/)
  }

  async function mockPutResponse(data: Proprietary) {
    await sharedPage.route(/\/proprietary\/2023-12-30/, async (route) => {
      if (route.request().method() === "PUT") {
        const response = await route.fetch()
        const body = await response.json()

        await route.fulfill({
          response,
          body: JSON.stringify({ ...body, ...data }),
        })
      } else await route.continue()
    })
  }

  test.beforeAll(async ({ browser }) => {
    sharedPage = await browser.newPage()

    await restoreInitialState()

    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )
  })

  test.afterAll(async () => {
    await restoreInitialState()
  })

  test.describe("FNA", () => {
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("textbox", { name: "Sachgebiet" })

      // Then
      await expect(control).toHaveValue("210-5")
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("textbox", { name: "Sachgebiet" })

      // When
      await control.fill("123-4")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("123-4")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ fna: "fake-backend-state" })
      const control = sharedPage.getByRole("textbox", { name: "Sachgebiet" })
      await expect(control).toHaveValue("123-4")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("fake-backend-state")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Dokumenttyp", () => {
    // TODO: Implement
  })

  test.describe("Art der Norm", () => {
    // TODO: Implement
  })

  test.describe("Bezeichnung gemäß Vorlage", () => {
    // TODO: Implement
  })

  test.describe("Normgeber", () => {
    // TODO: Implement
  })

  test.describe("beschließendes Organ", () => {
    // TODO: Implement
  })

  test.describe("Beschlussfassung qualifizierte Mehrheit", () => {
    // TODO: Implement
  })

  test.describe("Federführung", () => {
    // TODO: Implement
  })
})

test.describe("metadata reading", () => {
  test("displays metadata", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await page.waitForResponse((response) =>
      response.url().includes("/proprietary/"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet")).toHaveValue("210-5")
    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )
    await expect(editorRegion.getByLabel("SN - Stammnorm")).toBeChecked()
    await expect(
      editorRegion.getByLabel("ÄN - Änderungsnorm"),
    ).not.toBeChecked()
    await expect(editorRegion.getByLabel("ÜN - Übergangsnorm")).toBeChecked()

    await expect(editorRegion.getByLabel("Normgeber")).toHaveValue(
      "BEO - Berlin (Ost)",
    )
    await expect(editorRegion.getByLabel("beschließendes Organ")).toHaveValue(
      "BMinJ - Bundesministerium der Justiz",
    )
    await expect(
      editorRegion.getByLabel("Beschlussf. qual. Mehrheit"),
    ).toBeChecked()
  })
})

test.describe("metadata editing", () => {
  test.describe("editing individual fields", () => {
    // Creating a shared page allows us to stay in the same page/context while
    // editing all metadata fields (as a user would) without reloading the page
    // after each edit while still using separate test cases and keeping each
    // individual test small.
    let sharedPage: Page

    test.beforeAll(async ({ browser }) => {
      sharedPage = await browser.newPage()
      await sharedPage.goto(
        "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
      )
    })

    test("can edit the FNA", async () => {
      const fnaTextbox = sharedPage.getByRole("textbox", {
        name: "Sachgebiet",
      })

      await fnaTextbox.fill("123-4")
      await expect(fnaTextbox).toHaveValue("123-4")
    })

    test("can edit the Dokumenttyp", async () => {
      const documentTypeDropdown = sharedPage.getByRole("combobox", {
        name: "Dokumenttyp",
      })
      documentTypeDropdown.selectOption("Anordnung des Bundespräsidenten")

      await expect(documentTypeDropdown).toHaveValue(
        "Anordnung des Bundespräsidenten",
      )
    })

    test("can edit the Bezeichnung gemäß Vorlage", async () => {
      const bezeichnungTextbox = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })

      await bezeichnungTextbox.fill("Testbezeichnung")
      await expect(bezeichnungTextbox).toHaveValue("Testbezeichnung")
    })

    test("can check/uncheck SN - Stammnorm", async () => {
      const SNcheckbox = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      await SNcheckbox.check()
      await expect(SNcheckbox).toBeChecked()
      await SNcheckbox.uncheck()
      await expect(SNcheckbox).not.toBeChecked()
    })

    test("can check/uncheck ÄN - Änderungsnorm", async () => {
      const ANcheckbox = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      await ANcheckbox.check()
      await expect(ANcheckbox).toBeChecked()
      await ANcheckbox.uncheck()
      await expect(ANcheckbox).not.toBeChecked()
    })

    test("can check/uncheck ÜN - Übergangsnorm", async () => {
      const UNcheckbox = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })
      await UNcheckbox.check()
      await expect(UNcheckbox).toBeChecked()
      await UNcheckbox.uncheck()
      await expect(UNcheckbox).not.toBeChecked()
    })

    test("can edit the Normgeber", async () => {
      const normgeberDropdown = sharedPage.getByRole("combobox", {
        name: "Normgeber",
      })
      normgeberDropdown.selectOption("RP - Rheinland-Pfalz")

      await expect(normgeberDropdown).toHaveValue("RP - Rheinland-Pfalz")
    })

    test("can edit the beschließendes Organ", async () => {
      const beschliessendesOrganDropdown = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })
      beschliessendesOrganDropdown.selectOption(
        "BMinAS - Bundesministerium für Arbeit und Soziales",
      )

      await expect(beschliessendesOrganDropdown).toHaveValue(
        "BMinAS - Bundesministerium für Arbeit und Soziales",
      )
    })

    test("can check/uncheck Beschlussf. qual. Mehrheit", async () => {
      const qualMehrheit = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })
      await qualMehrheit.check()
      await expect(qualMehrheit).toBeChecked()
      await qualMehrheit.uncheck()
      await expect(qualMehrheit).not.toBeChecked()
    })

    test("can edit Federführung", async () => {
      const federfuehrungDropdown = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })
      federfuehrungDropdown.selectOption("BKAmt - Bundeskanzleramt")

      await expect(federfuehrungDropdown).toHaveValue(
        "BKAmt - Bundeskanzleramt",
      )
    })
  })

  test("persists changes across page loads after saving successfully", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const saved = page.waitForResponse(
      (response) =>
        response.request().method() === "PUT" &&
        response.request().url().endsWith("/proprietary/2023-12-30"),
    )

    // FNA
    const fnaTextbox = page.getByRole("textbox", {
      name: "Sachgebiet",
    })
    await expect(fnaTextbox).toHaveValue("210-5")
    await fnaTextbox.fill("123-4")

    // Dokumenttyp
    const documentTypeDropdown = page.getByRole("combobox", {
      name: "Dokumenttyp",
    })
    await expect(documentTypeDropdown).toHaveValue("Rechtsverordnung")
    await documentTypeDropdown.selectOption("Berichtigung")

    // ST - Stammnorm
    const SNcheckbox = page.getByRole("checkbox", {
      name: "SN - Stammnorm",
    })
    await expect(SNcheckbox).toBeChecked()
    await SNcheckbox.uncheck()

    // ÄN - Änderungsnorm"
    const ANcheckbox = page.getByRole("checkbox", {
      name: "ÄN - Änderungsnorm",
    })
    await expect(ANcheckbox).toBeChecked()
    await ANcheckbox.check()

    // ÜN - Übergangsnorm
    const UNcheckbox = page.getByRole("checkbox", {
      name: "ÜN - Übergangsnorm",
    })
    await expect(UNcheckbox).not.toBeChecked()
    await UNcheckbox.check()

    // Bezeichnung gemäß Vorlage
    const bezeichnungTextbox = page.getByRole("textbox", {
      name: "Bezeichnung gemäß Vorlage",
    })
    await expect(bezeichnungTextbox).toBeEmpty()
    await bezeichnungTextbox.fill("Testbezeichnung")

    // Normgeber
    const normgeberDropdown = page.getByRole("combobox", {
      name: "Normgeber",
    })
    await expect(normgeberDropdown).toHaveValue("")
    await normgeberDropdown.selectOption("EG - Europäische Gemeinschaft")

    // beschließendes Organ
    const beschliessendesOrganDropdown = page.getByRole("combobox", {
      name: "beschließendes Organ",
    })
    await expect(beschliessendesOrganDropdown).toHaveValue("")
    await beschliessendesOrganDropdown.selectOption("BReg - Bundesregierung")

    // Beschlussf. qual. Mehrheit
    const qualMehrheit = page.getByRole("checkbox", {
      name: "Beschlussf. qual. Mehrheit",
    })
    await expect(qualMehrheit).not.toBeChecked()
    await qualMehrheit.check()

    // Federführung
    const federfuehrungDropdown = page.getByRole("combobox", {
      name: "Federführung",
    })
    await expect(federfuehrungDropdown).toHaveValue("")
    await federfuehrungDropdown.selectOption("BKAmt - Bundeskanzleramt")

    await page.getByRole("button", { name: "Metadaten speichern" }).click()
    await saved

    // Verify that results have been persisted after reloading the page
    await page.reload()
    await expect(fnaTextbox).toHaveValue("123-4")
    await expect(documentTypeDropdown).toHaveValue("Berichtigung")
    await expect(bezeichnungTextbox).toHaveValue("Testbezeichnung")
    await expect(SNcheckbox).toBeChecked()
    await expect(ANcheckbox).toBeChecked()
    await expect(UNcheckbox).toBeChecked()
    await expect(normgeberDropdown).toHaveValue("EG - Europäische Gemeinschaft")
    await expect(beschliessendesOrganDropdown).toHaveValue(
      "BReg - Bundesregierung",
    )
    await expect(qualMehrheit).toBeChecked()
    await expect(federfuehrungDropdown).toHaveValue("BKAmt - Bundeskanzleramt")

    // Reset the data
    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/proprietary/1964-09-21",
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        data: JSON.stringify({
          fna: "210-5",
          art: null,
          typ: null,
          subtyp: null,
          bezeichnungInVorlage: null,
          artDerNorm: null,
          normgeber: null,
          beschliessendesOrgan: null,
          qualifizierteMehrheit: null,
          federfuehrung: null,
        }),
      },
    )
  })
})

test.describe("saving", () => {
  test("updates with metadata from the backend after saving", async ({
    page,
  }) => {
    await page.route(/\/proprietary\/2023-12-30$/, (route) => {
      // Mocking the response instead of getting the real response from the backend
      // to force a state where the value returned from the backend is different from
      // the state in the UI. In real-world use, this should almost never happen,
      // but we still want to test it.
      if (route.request().method() === "PUT")
        route.fulfill({
          status: 200,
          json: {
            fna: "600-1",
            art: "offene-struktur",
            typ: "sonstige-bekanntmachung",
            subtyp: "Geschäftsordnung",
            bezeichnungInVorlage: "Testbezeichnung",
            artDerNorm: "ÄN",
            normgeber: "BesR - Besatzungsrecht",
            beschliessendesOrgan: "BMinG - Bundesministerium für Gesundheit",
            qualifizierteMehrheit: false,
            federfuehrung: "AA - Auswärtiges Amt",
          },
        })
      else route.continue()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // Get metadata controls
    const fnaTextbox = page.getByRole("textbox", { name: "Sachgebiet" })

    const documentTypeDropdown = page.getByRole("combobox", {
      name: "Dokumenttyp",
    })

    const bezeichnungTextbox = page.getByRole("textbox", {
      name: "Bezeichnung gemäß Vorlage",
    })

    const SNcheckbox = page.getByRole("checkbox", {
      name: "SN - Stammnorm",
    })

    const ANcheckbox = page.getByRole("checkbox", {
      name: "ÄN - Änderungsnorm",
    })

    const UNcheckbox = page.getByRole("checkbox", {
      name: "ÜN - Übergangsnorm",
    })

    const normgeberDropdown = page.getByRole("combobox", {
      name: "Normgeber",
    })

    const beschliessendesOrganDropdown = page.getByRole("combobox", {
      name: "beschließendes Organ",
    })

    const qualMehrheit = page.getByRole("checkbox", {
      name: "Beschlussf. qual. Mehrheit",
    })

    // Verify the current state
    await expect(fnaTextbox).toHaveValue("210-5")
    await expect(documentTypeDropdown).toHaveValue("Rechtsverordnung")
    await expect(bezeichnungTextbox).toHaveValue(
      "Testbezeichnung nach meiner Vorlage",
    )
    await expect(SNcheckbox).toBeChecked()
    await expect(ANcheckbox).not.toBeChecked()
    await expect(UNcheckbox).toBeChecked()
    await expect(normgeberDropdown).toHaveValue("BEO - Berlin (Ost)")
    await expect(beschliessendesOrganDropdown).toHaveValue(
      "BMinJ - Bundesministerium der Justiz",
    )
    await expect(qualMehrheit).toBeChecked()

    // Save -> this will be caught and mocked to return changed data
    await page.getByRole("button", { name: "Metadaten speichern" }).click()

    // Verify the state after saving
    await expect(fnaTextbox).toHaveValue("600-1")
    await expect(documentTypeDropdown).toHaveValue("Geschäftsordnung")
    await expect(bezeichnungTextbox).toHaveValue("Testbezeichnung")
    await expect(SNcheckbox).not.toBeChecked()
    await expect(ANcheckbox).toBeChecked()
    await expect(UNcheckbox).not.toBeChecked()
    await expect(normgeberDropdown).toHaveValue("BesR - Besatzungsrecht")
    await expect(beschliessendesOrganDropdown).toHaveValue(
      "BMinG - Bundesministerium für Gesundheit",
    )
    await expect(qualMehrheit).not.toBeChecked()

    await page.unrouteAll()
  })

  test("displays a success message when the data has been saved", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await page.getByRole("button", { name: "Metadaten speichern" }).click()

    await expect(
      page.getByRole("tooltip", { name: "Gespeichert!" }),
    ).toBeVisible()

    await page.unrouteAll()
  })

  test("displays an error if the data could not be saved", async ({ page }) => {
    await page.route(/\/proprietary\/2023-12-30$/, (route) => {
      if (route.request().method() === "PUT") route.abort("failed")
      else route.continue()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await page.getByRole("button", { name: "Metadaten speichern" }).click()

    await expect(
      page.getByRole("tooltip", { name: "Speichern fehlgeschlagen" }),
    ).toBeVisible()

    await page.unrouteAll()
  })
})
