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

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "__unknown_document_type__",
    )
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
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })

      // Then
      await expect(control).toHaveValue("Rechtsverordnung")
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })

      // When
      await control.selectOption("Satzung")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("Satzung")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({
        art: "offene-struktur",
        typ: "sonstige-bekanntmachung",
        subtyp: "Technische Norm",
      })
      const control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })
      await expect(control).toHaveValue("Satzung")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("Technische Norm")

      // Cleanup
      await sharedPage.unrouteAll()
    })

    test("displays the value as unknown", async () => {
      // Given
      await sharedPage.route(/\/proprietary\/2023-12-30/, async (route) => {
        if (route.request().method() === "GET") {
          const response = await route.fetch()
          const body = await response.json()

          await route.fulfill({
            response,
            body: JSON.stringify({
              ...body,
              art: "dummy",
              typ: "dummy",
              subtyp: "",
            }),
          })
        } else await route.fulfill()
      })
      await sharedPage.reload()
      const control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })

      // Then
      await expect(control).toHaveValue("__unknown_document_type__")

      // Cleanup
      await sharedPage.unrouteAll()
    })

    test("displays the value as empty", async () => {
      // Given
      await sharedPage.route(/\/proprietary\/2023-12-30/, async (route) => {
        if (route.request().method() === "GET") {
          const response = await route.fetch()
          const body = await response.json()

          await route.fulfill({
            response,
            body: JSON.stringify({ ...body, art: "", typ: "", subtyp: "" }),
          })
        } else await route.fulfill()
      })
      await sharedPage.reload()
      const control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })

      // Then
      await expect(control).toHaveValue("")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Art der Norm", () => {
    test("is loaded", async () => {
      // Given
      const controlSn = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      const controlAn = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      const controlUn = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })

      // Then
      await expect(controlSn).toBeChecked()
      await expect(controlAn).not.toBeChecked()
      await expect(controlUn).toBeChecked()
    })

    test("saves changes", async () => {
      // Given
      const controlSn = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      const controlAn = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      const controlUn = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })

      // When
      await controlSn.uncheck()
      await controlAn.check()
      await controlUn.uncheck()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(controlSn).not.toBeChecked()
      await expect(controlAn).toBeChecked()
      await expect(controlUn).not.toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ artDerNorm: "SN,ÄN,ÜN" })
      const controlSn = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      const controlAn = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      const controlUn = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })
      await expect(controlSn).not.toBeChecked()
      await expect(controlAn).toBeChecked()
      await expect(controlUn).not.toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(controlSn).toBeChecked()
      await expect(controlAn).toBeChecked()
      await expect(controlUn).toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Bezeichnung gemäß Vorlage", () => {
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })

      // Then
      await expect(control).toHaveValue("Testbezeichnung nach meiner Vorlage")
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })

      // When
      await control.fill("Andere Bezeichnung")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("Andere Bezeichnung")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({
        bezeichnungInVorlage: "fake-backend-bezeichnung",
      })
      const control = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })
      await expect(control).toHaveValue("Andere Bezeichnung")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("fake-backend-bezeichnung")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Normgeber", () => {
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", { name: "Normgeber" })

      // Then
      await expect(control).toHaveValue("BEO - Berlin (Ost)")
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", { name: "Normgeber" })

      // When
      await control.selectOption("HA - Hamburg")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("HA - Hamburg")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ normgeber: "SL - Saarland" })
      const control = sharedPage.getByRole("combobox", { name: "Normgeber" })
      await expect(control).toHaveValue("HA - Hamburg")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("SL - Saarland")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("beschließendes Organ", () => {
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })

      // Then
      await expect(control).toHaveValue("BMinJ - Bundesministerium der Justiz")
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })

      // When
      await control.selectOption("BT - Bundestag")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("BT - Bundestag")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ beschliessendesOrgan: "AA - Auswärtiges Amt" })
      const control = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })
      await expect(control).toHaveValue("BT - Bundestag")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("AA - Auswärtiges Amt")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Beschlussfassung qualifizierte Mehrheit", () => {
    test("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })

      // Then
      await expect(control).toBeChecked()
    })

    test("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })

      // When
      await control.uncheck()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).not.toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ qualifizierteMehrheit: true })
      const control = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })
      await expect(control).not.toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(control).toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Federführung", () => {
    // Skipped until implemented in the backend
    test.skip("is loaded", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })

      // Then
      await expect(control).toHaveValue("BMJ - Bundesministerium der Justiz")
    })

    // Skipped until implemented in the backend
    test.skip("saves changes", async () => {
      // Given
      const control = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })

      // When
      await control.selectOption("BKAmt - Bundeskanzleramt")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("BKAmt - Bundeskanzleramt")
    })

    // Skipped until implemented in the backend
    test.skip("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ federfuehrung: "AA - Auswärtiges Amt" })
      const control = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })
      await expect(control).toHaveValue("BKAmt - Bundeskanzleramt")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("AA - Auswärtiges Amt")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test("shows an error if the metadata could not be loaded", async () => {
    await sharedPage.route(/\/proprietary\/2023-12-30$/, (request) => {
      request.abort()
    })

    await sharedPage.reload()

    const editorRegion = sharedPage.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await expect(
      editorRegion.getByText("Die Metadaten konnten nicht geladen werden."),
    ).toBeVisible()

    await sharedPage.unrouteAll()
  })

  test("displays a success message when the data has been saved", async () => {
    await sharedPage.reload()

    await sharedPage
      .getByRole("button", { name: "Metadaten speichern" })
      .click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Gespeichert!" }),
    ).toBeVisible()

    await sharedPage.unrouteAll()
  })

  test("displays an error if the data could not be saved", async () => {
    await sharedPage.route(/\/proprietary\/2023-12-30$/, (route) => {
      if (route.request().method() === "PUT") route.abort("failed")
      else route.continue()
    })

    await sharedPage.reload()

    await sharedPage
      .getByRole("button", { name: "Metadaten speichern" })
      .click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern fehlgeschlagen" }),
    ).toBeVisible()

    await sharedPage.unrouteAll()
  })
})
