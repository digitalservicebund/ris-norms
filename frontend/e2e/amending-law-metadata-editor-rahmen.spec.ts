import { Proprietary } from "@/types/proprietary"
import { Locator, Page, expect, test } from "@playwright/test"

async function restoreInitialState(page: Page) {
  const dataIn1970: Proprietary = {
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
  }

  const dataIn2023: Proprietary = {
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
  test("displays the XML of the target law", async ({ page }) => {
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
    await restoreInitialState(page)

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
    await restoreInitialState(page)
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

  async function saveMetadata() {
    await sharedPage
      .getByRole("button", { name: "Metadaten speichern" })
      .click()
    await sharedPage.waitForResponse(/proprietary/)
  }

  async function gotoTimeBoundary(date: string) {
    await sharedPage.goto(
      `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/${date}`,
    )
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
    await restoreInitialState(sharedPage)
    await gotoTimeBoundary("2023-12-30")
  })

  test.afterAll(async () => {
    await restoreInitialState(sharedPage)
  })

  test.describe("FNA", () => {
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("textbox", { name: "Sachgebiet" })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue("210-5")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue("310-5")
    })

    test("saves changes", async () => {
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
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("combobox", { name: "Dokumenttyp" })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue("Rechtsverordnung")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue("Gesetz im formellen Sinne")
    })

    test("saves changes", async () => {
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

      // Then
      await expect(control).toHaveValue("")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Art der Norm", () => {
    let controlSn: Locator
    let controlAn: Locator
    let controlUn: Locator

    test.beforeAll(() => {
      // Given
      controlSn = sharedPage.getByRole("checkbox", {
        name: "SN - Stammnorm",
      })
      controlAn = sharedPage.getByRole("checkbox", {
        name: "ÄN - Änderungsnorm",
      })
      controlUn = sharedPage.getByRole("checkbox", {
        name: "ÜN - Übergangsnorm",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(controlSn).toBeChecked()
      await expect(controlAn).not.toBeChecked()
      await expect(controlUn).toBeChecked()

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(controlSn).not.toBeChecked()
      await expect(controlAn).toBeChecked()
      await expect(controlUn).not.toBeChecked()
    })

    test("saves changes", async () => {
      // When
      await controlSn.check()
      await controlAn.uncheck()
      await controlUn.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(controlSn).toBeChecked()
      await expect(controlAn).not.toBeChecked()
      await expect(controlUn).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ artDerNorm: "SN,ÄN,ÜN" })
      await expect(controlSn).toBeChecked()
      await expect(controlAn).not.toBeChecked()
      await expect(controlUn).toBeChecked()

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
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("textbox", {
        name: "Bezeichnung gemäß Vorlage",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue("Testbezeichnung nach meiner Vorlage")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue("Neue Testbezeichnung ab 2023")
    })

    test("saves changes", async () => {
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
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("combobox", { name: "Normgeber" })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue("BEO - Berlin (Ost)")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue("HA - Hamburg")
    })

    test("saves changes", async () => {
      // When
      await control.selectOption("BE - Berlin")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue("BE - Berlin")
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ normgeber: "SL - Saarland" })
      await expect(control).toHaveValue("BE - Berlin")

      // When
      await saveMetadata()

      // Then
      await expect(control).toHaveValue("SL - Saarland")

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("beschließendes Organ", () => {
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("combobox", {
        name: "beschließendes Organ",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue("BMinJ - Bundesministerium der Justiz")

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue("BMinI - Bundesministerium des Innern")
    })

    test("saves changes", async () => {
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
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("checkbox", {
        name: "Beschlussf. qual. Mehrheit",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toBeChecked()

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).not.toBeChecked()
    })

    test("saves changes", async () => {
      // When
      await control.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ qualifizierteMehrheit: false })
      await expect(control).toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(control).not.toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })

  test.describe("Federführung", () => {
    let control: Locator

    test.beforeAll(() => {
      control = sharedPage.getByRole("combobox", {
        name: "Federführung",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("1970-01-01")

      // Then
      await expect(control).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(control).toHaveValue(
        "BMI - Bundesministerium des Innern und für Heimat",
      )
    })

    test("saves changes", async () => {
      // When
      await control.selectOption("BMVg - Bundesministerium der Verteidigung")
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(control).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ federfuehrung: "AA - Auswärtiges Amt" })
      await expect(control).toHaveValue(
        "BMVg - Bundesministerium der Verteidigung",
      )

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
