import type { RahmenProprietary } from "@/types/proprietary"
import { expect } from "@playwright/test"
import { test } from "@e2e/utils/testWithAuth"
import { MetadataEditorRahmenPage } from "@e2e/pages/metadataEditorRahmen"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"

test.describe("preview", { tag: ["@RISDEV-6266"] }, () => {
  test("displays the title and preview", async ({ page }) => {
    // Given
    await page.goto(
      "./eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    // Then
    await expect(
      page.getByRole("heading", {
        name: "Bundesverfassungsschutzgesetz\n",
        exact: true,
      }),
    ).toBeVisible()

    await expect(
      preview.getByRole("heading", {
        name: "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
      }),
    ).toBeVisible()
  })

  test("shows an error when the preview could not be loaded", async ({
    page,
  }) => {
    // Given
    await page.route(
      /api\/v1\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\?/,
      async (route) => {
        await route.abort()
      },
    )

    await page.goto(
      "./eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata",
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

test.describe("metadata view", { tag: ["@RISDEV-6266"] }, () => {
  test.afterAll(async ({ authenticatedRequest }) => {
    await uploadAmendingLaw(
      authenticatedRequest,
      "bgbl-1_2023_413/aenderungsgesetz.xml",
    )
  })

  test.beforeEach(async ({ authenticatedRequest }) => {
    const testData: RahmenProprietary = {
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

    await authenticatedRequest.put(
      "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/proprietary",
      { data: testData },
    )
  })

  test("displays metadata for the expression", async ({ page }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)

    // When
    await metadataPage.goto()

    // Then
    await expect.soft(metadataPage.fnaInput).toHaveValue("210-5")
    await expect
      .soft(metadataPage.documentTypeDropdown)
      .toHaveText("Rechtsverordnung")
    await expect.soft(metadataPage.artSnCheckbox).toBeChecked()
    await expect.soft(metadataPage.artAnCheckbox).not.toBeChecked()
    await expect.soft(metadataPage.artUnCheckbox).toBeChecked()
    await expect
      .soft(metadataPage.bezeichnungInput)
      .toHaveValue("Testbezeichnung nach meiner Vorlage")
    await expect
      .soft(metadataPage.staatDropdown)
      .toHaveText("BEO - Berlin (Ost)")
    await expect
      .soft(metadataPage.organDropdown)
      .toHaveText("BMinJ - Bundesministerium der Justiz")
    await expect.soft(metadataPage.qualMehrheitCheckbox).toBeChecked()
    await expect
      .soft(metadataPage.ressortDropdown)
      .toHaveText("BMVg - Bundesministerium der Verteidigung")
    await expect
      .soft(metadataPage.organisationsEinheitInput)
      .toHaveValue("Einheit 1")
  })

  test("saves changes", async ({ page }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)

    await metadataPage.goto()

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

  test("is updated with backend state after saving", async ({ page }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)

    await metadataPage.goto()

    // Given
    await metadataPage.mockPutResponse({
      fna: "fake-backend-state",
      art: "offene-struktur",
      typ: "sonstige-bekanntmachung",
      subtyp: "Technische Norm",
      artDerNorm: "SN,ÄN,ÜN",
      bezeichnungInVorlage: "fake-backend-bezeichnung",
      staat: "SL - Saarland",
      beschliessendesOrgan: "AA - Auswärtiges Amt",
      qualifizierteMehrheit: false,
      ressort: "AA - Auswärtiges Amt",
      organisationsEinheit: "Fake Einheit",
    })

    // When
    await metadataPage.saveMetadata()

    // Then
    await expect.soft(metadataPage.fnaInput).toHaveValue("fake-backend-state")
    await expect
      .soft(metadataPage.documentTypeDropdown)
      .toHaveText("Technische Norm")
    await expect.soft(metadataPage.artSnCheckbox).toBeChecked()
    await expect.soft(metadataPage.artAnCheckbox).toBeChecked()
    await expect.soft(metadataPage.artUnCheckbox).toBeChecked()
    await expect
      .soft(metadataPage.bezeichnungInput)
      .toHaveValue("fake-backend-bezeichnung")
    await expect.soft(metadataPage.staatDropdown).toHaveText("SL - Saarland")
    await expect
      .soft(metadataPage.organDropdown)
      .toHaveText("AA - Auswärtiges Amt")
    await expect.soft(metadataPage.qualMehrheitCheckbox).not.toBeChecked()
    await expect
      .soft(metadataPage.ressortDropdown)
      .toHaveText("AA - Auswärtiges Amt")
    await expect
      .soft(metadataPage.organisationsEinheitInput)
      .toHaveValue("Fake Einheit")
  })

  test.describe("Dokumenttyp", () => {
    test("displays the value as unknown", async ({ page }) => {
      const metadataPage = new MetadataEditorRahmenPage(page)

      // Given
      await page.route(
        /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\/proprietary$/,
        async (route) => {
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
        },
      )
      await metadataPage.goto()

      // Then
      await expect(metadataPage.documentTypeDropdown).toHaveText("Unbekannt")
    })

    test("displays the value as empty", async ({ page }) => {
      const metadataPage = new MetadataEditorRahmenPage(page)

      // Given
      await page.route(
        /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\/proprietary$/,
        async (route) => {
          if (route.request().method() === "GET") {
            const response = await route.fetch()
            const body = await response.json()

            await route.fulfill({
              response,
              body: JSON.stringify({ ...body, art: "", typ: "", subtyp: "" }),
            })
          } else await route.fulfill()
        },
      )
      await metadataPage.goto()

      // Then
      await expect(metadataPage.documentTypeDropdown).toHaveText("")
    })
  })

  test("shows an error if the metadata could not be loaded", async ({
    page,
  }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)

    // Given
    await page.route(
      /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2022-12-19\/1\/deu\/regelungstext-1\/proprietary$/,
      (request) => {
        request.abort()
      },
    )

    await metadataPage.goto()

    const editorRegion = page.getByRole("region", {
      name: "Metadaten dokumentieren",
    })

    // Then
    await expect(
      editorRegion.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()
  })

  test("displays a success message when the data has been saved", async ({
    page,
  }) => {
    const metadataPage = new MetadataEditorRahmenPage(page)
    // Given
    await metadataPage.goto()

    // When
    await metadataPage.saveMetadata()

    // Then
    const toastMessage = page.getByRole("alert")
    await expect(toastMessage).toHaveText("Gespeichert!")
  })
})
