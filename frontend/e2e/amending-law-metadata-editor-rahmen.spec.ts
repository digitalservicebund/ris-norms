import { RahmenProprietary } from "@/types/proprietary"
import { expect, test } from "@playwright/test"
import { MetadataEditorRahmenPage } from "@e2e/pages/MetadataEditorRahmenPage"
import { uploadAmendingLaw } from "@e2e/utils/upload-with.force"

test.describe("navigate to page", () => {
  test("navigate to the metadata editor", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents",
    )

    // When
    await page.getByRole("link", { name: "Metadaten dokumentieren" }).click()

    // Then
    // Only expect the URL to be somewhat equal to the following. The reason is that
    // the page redirects to a subpage, so this test might be flaky otherwise depending
    // on how fast the redirect is.
    await expect(page).toHaveURL(
      /.*\/amending-laws\/eli\/bund\/bgbl-1\/2023\/413\/2023-12-29\/1\/deu\/regelungstext-1\/affected-documents\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\/edit.*/,
    )
  })

  test("displays affected document short title", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit",
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
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit",
    )

    // When
    await page.getByRole("combobox", { name: "Zeitgrenze" }).click()

    await page
      .getByRole("option", {
        name: "30.12.2023",
      })
      .click()

    // Then
    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )
  })
})

test.describe("preview", () => {
  test("displays the title and preview", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
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
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })
    const timeBoundarySelectSection = page.getByRole("complementary", {
      name: "Inhaltsverzeichnis",
    })

    // Then
    await expect(preview).toContainText("nach Ablauf von fünf Jahren")

    // When
    await timeBoundarySelectSection
      .getByRole("combobox", { name: "Zeitgrenze" })
      .click()

    await page
      .getByRole("option", {
        name: "01.06.2024",
      })
      .click()

    // Then
    await expect(preview).toContainText("nach Ablauf von fünf Jahren")
  })

  test("shows an error when the preview could not be loaded", async ({
    page,
  }) => {
    // Given
    await page.route(
      /norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\?atIsoDate=/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const previewRegion = page.getByRole("region", {
      name: "Vorschau",
    })

    // Then
    await expect(
      previewRegion.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })
})

test.describe("XML view", () => {
  test("displays the XML of the target law", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // When
    await page.getByRole("tab", { name: "XML" }).click()

    // Then
    await expect(
      page
        .getByRole("region", { name: "Metadaten dokumentieren" })
        .getByText(
          'value="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"',
        )
        .first(),
    ).toBeVisible()
  })

  test("updates the XML preview after saving metadata", async ({
    page,
    request,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten dokumentieren",
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
        /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\?$/,
      )
      .then((response) => response.text())

    expect(textResponse).toContain("1234-56-78")

    // Cleanup
    await uploadAmendingLaw(request, "bgbl-1_2023_413/aenderungsgesetz.xml")
  })

  test("shows an error when the XML could not be loaded", async ({ page }) => {
    // Given
    await page.route(
      /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\?$/,
      async (request) => {
        const accept = await request.request().headerValue("Accept")
        if (accept === "application/xml") await request.abort()
        else await request.continue()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // When
    await page.getByRole("tab", { name: "XML" }).click()

    // Then
    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })
})

test.describe("metadata view", () => {
  test.afterAll(async ({ request }) => {
    await uploadAmendingLaw(request, "bgbl-1_2023_413/aenderungsgesetz.xml")
  })

  test.beforeEach(async ({ page }) => {
    const dataIn2023: RahmenProprietary = {
      fna: "210-5",
      art: "regelungstext",
      typ: "gesetz",
      subtyp: "Rechtsverordnung",
      bezeichnungInVorlage: "Testbezeichnung nach meiner Vorlage",
      artDerNorm: "SN,ÜN",
      staat: "BEO - Berlin (Ost)",
      beschliessendesOrgan: "BMinJ - Bundesministerium der Justiz",
      qualifizierteMehrheit: true,
      ressort: "BMVg - Bundesministerium der Verteidigung",
      organisationsEinheit: "Einheit 1",
    }

    const dataIn2024: RahmenProprietary = {
      fna: "310-5",
      art: "regelungstext",
      typ: "gesetz",
      subtyp: "Gesetz im formellen Sinne",
      bezeichnungInVorlage: "Neue Testbezeichnung ab 2023",
      artDerNorm: "ÄN",
      staat: "HA - Hamburg",
      beschliessendesOrgan: "BMinI - Bundesministerium des Innern",
      qualifizierteMehrheit: false,
      ressort: "BMI - Bundesministerium des Innern und für Heimat",
      organisationsEinheit: "Einheit 2",
    }

    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/proprietary/2023-12-30",
      { data: dataIn2023 },
    )

    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/proprietary/2024-06-01",
      { data: dataIn2024 },
    )
  })

  test("saves changes", async ({ page }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)
    await metadataPage.gotoTimeBoundary("2023-12-30")

    // When
    await metadataPage.fnaInput.fill("123-4")
    await metadataPage.documentTypeDropdown.click()
    await page
      .getByRole("option", {
        name: "Satzung",
      })
      .click()
    await metadataPage.artSnCheckbox.check()
    await metadataPage.artAnCheckbox.uncheck()
    await metadataPage.artUnCheckbox.check()
    await metadataPage.bezeichnungInput.fill("Andere Bezeichnung")
    await metadataPage.staatDropdown.click()
    await page
      .getByRole("option", {
        name: "BE - Berlin",
      })
      .click()
    await metadataPage.organDropdown.click()
    await page
      .getByRole("option", {
        name: "BT - Bundestag",
      })
      .click()
    await metadataPage.qualMehrheitCheckbox.check()
    await metadataPage.ressortDropdown.click()
    await page
      .getByRole("option", {
        name: "BMVg - Bundesministerium der Verteidigung",
      })
      .click()
    await metadataPage.organisationsEinheitInput.fill("Einheit 3")

    await metadataPage.saveMetadata()
    await page.reload()

    // Then
    await expect.soft(metadataPage.fnaInput).toHaveValue("123-4")
    await expect.soft(metadataPage.documentTypeDropdown).toHaveText("Satzung")
    await expect.soft(metadataPage.artSnCheckbox).toBeChecked()
    await expect.soft(metadataPage.artAnCheckbox).not.toBeChecked()
    await expect.soft(metadataPage.artUnCheckbox).toBeChecked()
    await expect
      .soft(metadataPage.bezeichnungInput)
      .toHaveValue("Andere Bezeichnung")
    await expect.soft(metadataPage.staatDropdown).toHaveText("BE - Berlin")
    await expect.soft(metadataPage.organDropdown).toHaveText("BT - Bundestag")
    await expect.soft(metadataPage.qualMehrheitCheckbox).toBeChecked()
    await expect
      .soft(metadataPage.ressortDropdown)
      .toHaveText("BMVg - Bundesministerium der Verteidigung")
    await expect
      .soft(metadataPage.organisationsEinheitInput)
      .toHaveValue("Einheit 3")
  })

  test("displays a success message when the data has been saved", async ({
    page,
  }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)
    // Given
    await metadataPage.gotoTimeBoundary("2023-12-30")

    // When
    await metadataPage.saveMetadata()

    // Then
    const toastMessage = page.getByRole("alert")
    await expect(toastMessage).toHaveText("Gespeichert!")
  })
})
