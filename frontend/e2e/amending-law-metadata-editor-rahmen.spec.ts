import { RahmenProprietary } from "@/types/proprietary"
import { Locator, Page, expect, test } from "@playwright/test"

async function restoreInitialState(page: Page) {
  const dataIn1970: RahmenProprietary = {
    fna: "210-5",
    art: "regelungstext",
    typ: "gesetz",
    subtyp: "Rechtsverordnung",
    bezeichnungInVorlage: "Testbezeichnung nach meiner Vorlage",
    artDerNorm: "SN,ÜN",
    normgeber: "BEO - Berlin (Ost)",
    beschliessendesOrgan: "BMinJ - Bundesministerium der Justiz",
    qualifizierteMehrheit: true,
    federfuehrung: "BMVg - Bundesministerium der Verteidigung",
    organisationsEinheit: "Einheit 1",
  }

  const dataIn2023: RahmenProprietary = {
    fna: "310-5",
    art: "regelungstext",
    typ: "gesetz",
    subtyp: "Gesetz im formellen Sinne",
    bezeichnungInVorlage: "Neue Testbezeichnung ab 2023",
    artDerNorm: "ÄN",
    normgeber: "HA - Hamburg",
    beschliessendesOrgan: "BMinI - Bundesministerium des Innern",
    qualifizierteMehrheit: false,
    federfuehrung: "BMI - Bundesministerium des Innern und für Heimat",
    organisationsEinheit: "Einheit 2",
  }

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/1970-01-01",
    { data: dataIn1970 },
  )

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/2023-12-30",
    { data: dataIn2023 },
  )
}

test.describe("navigate to page", () => {
  test("navigate to the metadata editor", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents",
    )

    // When
    await page.getByRole("link", { name: "Metadaten bearbeiten" }).click()

    // Then
    // Only expect the URL to be somewhat equal to the following. The reason is that
    // the page redirects to a subpage, so this test might be flaky otherwise depending
    // on how fast the redirect is.
    await expect(page).toHaveURL(
      /.*\/amending-laws\/eli\/bund\/bgbl-1\/2023\/413\/2023-12-29\/1\/deu\/regelungstext-1\/affected-documents\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\/edit.*/,
    )
  })

  test("displays affected document short title", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    const header = page.getByRole("navigation")

    // Then
    await expect(header.getByText("BGBl. I 2023 Nr. 413")).toBeVisible()

    await expect(
      header.getByText("Bundesverfassungsschutzgesetz"),
    ).toBeVisible()
  })

  test("navigates to the selected time boundary", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    // When
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    // Then
    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )
  })
})

test.describe("preview", () => {
  test("displays the title and preview", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    // Then
    await expect(page.getByRole("heading", { name: "Rahmen" })).toBeVisible()

    await expect(
      preview.getByRole("heading", {
        name: "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz Bundesverfassungsschutzgesetz",
      }),
    ).toBeVisible()
  })

  test("shows the preview at different time boundaries", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    // Then
    await expect(preview).toHaveText(
      /.*am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt.*/,
    )

    // When
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    // Then
    await expect(preview).toHaveText(/.*nach Ablauf von fünf Jahren.*/)
  })

  test("shows an error when the preview could not be loaded", async ({
    page,
  }) => {
    // Given
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

    // Then
    await expect(
      previewRegion.getByText("Die Vorschau konnte nicht geladen werden."),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })
})

test.describe("XML view", () => {
  test("displays the XML of the target law", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    // When
    await page.getByRole("tab", { name: "XML" }).click()

    // Then
    await expect(
      page
        .getByRole("region", { name: "Metadaten bearbeiten" })
        .getByText(
          'value="eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1"',
        ),
    ).toBeVisible()
  })

  test("updates the XML preview after saving metadata", async ({ page }) => {
    // Given
    await restoreInitialState(page)

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    const fnaInput = editorRegion.getByRole("textbox", {
      name: "Sachgebiet",
    })

    // When
    // Updating the FNA as an example for any change happening on the page
    await expect(fnaInput).toHaveValue("210-5")
    await fnaInput.fill("1234-56-78")
    await page.getByRole("button", { name: "Speichern" }).click()

    // Then
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

    // Cleanup
    await restoreInitialState(page)
  })

  test("shows an error when the XML could not be loaded", async ({ page }) => {
    // Given
    await page.route(
      /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\?$/,
      async (request) => {
        const accept = await request.request().headerValue("Accept")
        if (accept === "application/xml") await request.abort()
        else await request.continue()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    // When
    await page.getByRole("tab", { name: "XML" }).click()

    // Then
    await expect(
      page.getByText("Die XML-Ansicht konnte nicht geladen werden."),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })
})

test.describe("metadata view", () => {
  let sharedPage: Page

  async function saveMetadata() {
    await sharedPage.getByRole("button", { name: "Speichern" }).click()
    await sharedPage.waitForResponse(/proprietary/)
  }

  async function gotoTimeBoundary(date: string) {
    await sharedPage.goto(
      `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/${date}`,
    )
  }

  async function mockPutResponse(data: RahmenProprietary) {
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
    await restoreInitialState(sharedPage)
    await gotoTimeBoundary("2023-12-30")
  })

  test.afterAll(async () => {
    await restoreInitialState(sharedPage)
  })

  test.describe("FNA", () => {
    let fnaInput: Locator

    test.beforeAll(() => {
      fnaInput = sharedPage.getByRole("textbox", { name: "Sachgebiet" })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(fnaInput).toHaveValue("210-5")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(fnaInput).toHaveValue("310-5")
    })

    test("saves changes", async () => {
      // When
      await fnaInput.fill("123-4")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(fnaInput).toHaveValue("123-4")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ fna: "fake-backend-state" })
      await expect(fnaInput).toHaveValue("123-4")

      // When
      await saveMetadata()

      // Then
      await expect(fnaInput).toHaveValue("fake-backend-state")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Dokumenttyp", () => {
    let documentTypeDropdown: Locator

    test.beforeAll(() => {
      documentTypeDropdown = sharedPage.getByRole("combobox", {
        name: "Dokumenttyp",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(documentTypeDropdown).toHaveValue("Rechtsverordnung")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(documentTypeDropdown).toHaveValue(
        "Gesetz im formellen Sinne",
      )
    })

    test("saves changes", async () => {
      // When
      await documentTypeDropdown.selectOption("Satzung")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(documentTypeDropdown).toHaveValue("Satzung")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({
        art: "offene-struktur",
        typ: "sonstige-bekanntmachung",
        subtyp: "Technische Norm",
      })
      await expect(documentTypeDropdown).toHaveValue("Satzung")

      // When
      await saveMetadata()

      // Then
      await expect(documentTypeDropdown).toHaveValue("Technische Norm")

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

      // Then
      await expect(documentTypeDropdown).toHaveValue(
        "__unknown_document_type__",
      )

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

      // Then
      await expect(documentTypeDropdown).toHaveValue("")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Art der Norm", () => {
    let artSnCheckbox: Locator
    let artAnCheckbox: Locator
    let artUnCheckbox: Locator

    test.beforeAll(() => {
      // Given
      artSnCheckbox = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      artAnCheckbox = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      artUnCheckbox = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(artSnCheckbox).toBeChecked()
      await expect(artAnCheckbox).not.toBeChecked()
      await expect(artUnCheckbox).toBeChecked()

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(artSnCheckbox).not.toBeChecked()
      await expect(artAnCheckbox).toBeChecked()
      await expect(artUnCheckbox).not.toBeChecked()
    })

    test("saves changes", async () => {
      // When
      await artSnCheckbox.check()
      await artAnCheckbox.uncheck()
      await artUnCheckbox.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(artSnCheckbox).toBeChecked()
      await expect(artAnCheckbox).not.toBeChecked()
      await expect(artUnCheckbox).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ artDerNorm: "SN,ÄN,ÜN" })
      await expect(artSnCheckbox).toBeChecked()
      await expect(artAnCheckbox).not.toBeChecked()
      await expect(artUnCheckbox).toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(artSnCheckbox).toBeChecked()
      await expect(artAnCheckbox).toBeChecked()
      await expect(artUnCheckbox).toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Bezeichnung gemäß Vorlage", () => {
    let bezeichnungInput: Locator

    test.beforeAll(() => {
      bezeichnungInput = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(bezeichnungInput).toHaveValue(
        "Testbezeichnung nach meiner Vorlage",
      )

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(bezeichnungInput).toHaveValue("Neue Testbezeichnung ab 2023")
    })

    test("saves changes", async () => {
      // When
      await bezeichnungInput.fill("Andere Bezeichnung")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(bezeichnungInput).toHaveValue("Andere Bezeichnung")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({
        bezeichnungInVorlage: "fake-backend-bezeichnung",
      })
      await expect(bezeichnungInput).toHaveValue("Andere Bezeichnung")

      // When
      await saveMetadata()

      // Then
      await expect(bezeichnungInput).toHaveValue("fake-backend-bezeichnung")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Normgeber", () => {
    let normgeberDropdown: Locator

    test.beforeAll(() => {
      normgeberDropdown = sharedPage.getByRole("combobox", {
        name: "Normgeber",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(normgeberDropdown).toHaveValue("BEO - Berlin (Ost)")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(normgeberDropdown).toHaveValue("HA - Hamburg")
    })

    test("saves changes", async () => {
      // When
      await normgeberDropdown.selectOption("BE - Berlin")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(normgeberDropdown).toHaveValue("BE - Berlin")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ normgeber: "SL - Saarland" })
      await expect(normgeberDropdown).toHaveValue("BE - Berlin")

      // When
      await saveMetadata()

      // Then
      await expect(normgeberDropdown).toHaveValue("SL - Saarland")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("beschließendes Organ", () => {
    let organDropdown: Locator

    test.beforeAll(() => {
      organDropdown = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(organDropdown).toHaveValue(
        "BMinJ - Bundesministerium der Justiz",
      )

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(organDropdown).toHaveValue(
        "BMinI - Bundesministerium des Innern",
      )
    })

    test("saves changes", async () => {
      // When
      await organDropdown.selectOption("BT - Bundestag")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(organDropdown).toHaveValue("BT - Bundestag")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ beschliessendesOrgan: "AA - Auswärtiges Amt" })
      await expect(organDropdown).toHaveValue("BT - Bundestag")

      // When
      await saveMetadata()

      // Then
      await expect(organDropdown).toHaveValue("AA - Auswärtiges Amt")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Beschlussfassung qualifizierte Mehrheit", () => {
    let qualMehrheitCheckbox: Locator

    test.beforeAll(() => {
      qualMehrheitCheckbox = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(qualMehrheitCheckbox).toBeChecked()

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(qualMehrheitCheckbox).not.toBeChecked()
    })

    test("saves changes", async () => {
      // When
      await qualMehrheitCheckbox.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(qualMehrheitCheckbox).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ qualifizierteMehrheit: false })
      await expect(qualMehrheitCheckbox).toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(qualMehrheitCheckbox).not.toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Federführung", () => {
    let federfuehrungDropdown: Locator

    test.beforeAll(() => {
      federfuehrungDropdown = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(federfuehrungDropdown).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(federfuehrungDropdown).toHaveValue(
        "BMI - Bundesministerium des Innern und für Heimat",
      )
    })

    test("saves changes", async () => {
      // When
      await federfuehrungDropdown.selectOption(
        "BMVg - Bundesministerium der Verteidigung",
      )
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(federfuehrungDropdown).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ federfuehrung: "AA - Auswärtiges Amt" })
      await expect(federfuehrungDropdown).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )

      // When
      await saveMetadata()

      // Then
      await expect(federfuehrungDropdown).toHaveValue("AA - Auswärtiges Amt")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Organisationseinheit", () => {
    let organisationsEinheitInput: Locator

    test.beforeAll(() => {
      organisationsEinheitInput = sharedPage.getByRole("textbox", {
        name: "Organisationseinheit",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(organisationsEinheitInput).toHaveValue("Einheit 1")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(organisationsEinheitInput).toHaveValue("Einheit 2")
    })

    test("saves changes", async () => {
      // When
      await organisationsEinheitInput.fill("Einheit 3")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(organisationsEinheitInput).toHaveValue("Einheit 3")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ organisationsEinheit: "Fake Einheit" })
      await expect(organisationsEinheitInput).toHaveValue("Einheit 3")

      // When
      await saveMetadata()

      // Then
      await expect(organisationsEinheitInput).toHaveValue("Fake Einheit")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test("shows an error if the metadata could not be loaded", async () => {
    // Given
    await sharedPage.route(/\/proprietary\/2023-12-30$/, (request) => {
      request.abort()
    })

    await sharedPage.reload()

    const editorRegion = sharedPage.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    // Then
    await expect(
      editorRegion.getByText("Die Metadaten konnten nicht geladen werden."),
    ).toBeVisible()

    // Cleanup
    await sharedPage.unrouteAll()
  })

  test("displays a success message when the data has been saved", async () => {
    // Given
    await sharedPage.reload()

    // When
    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    // Then
    await expect(
      sharedPage.getByRole("tooltip", { name: "Gespeichert!" }),
    ).toBeVisible()

    // Cleanup
    await sharedPage.unrouteAll()
  })

  test("displays an error if the data could not be saved", async () => {
    // Given
    await sharedPage.route(/\/proprietary\/2023-12-30$/, (route) => {
      if (route.request().method() === "PUT") route.abort("failed")
      else route.continue()
    })

    await sharedPage.reload()

    // When
    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    // Then
    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern fehlgeschlagen" }),
    ).toBeVisible()

    await sharedPage.unrouteAll()
  })
})
