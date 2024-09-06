import { ElementProprietary } from "@/types/proprietary"
import { Locator, Page, expect, test } from "@playwright/test"

async function restoreInitialState(page: Page) {
  const dataIn2015: ElementProprietary = {
    artDerNorm: "SN",
  }

  const dataIn2023: ElementProprietary = {
    artDerNorm: "ÄN",
  }

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-erster_para-6/2015-06-01",
    { data: dataIn2015 },
  )

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-erster_para-6/2023-12-30",
    { data: dataIn2023 },
  )
}

test.describe("navigate to page", () => {
  test("shows an error when an element page could not be loaded", async ({
    page,
  }) => {
    // Given
    await page.route(
      /elements\/hauptteil-1_abschnitt-erster_para-6/,
      async (route) => {
        await route.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
    )

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

  test("navigates to the selected time boundary", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/",
    )

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    // When
    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    await nav
      .getByRole("link", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      })
      .click()

    // Then
    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/hauptteil-1_abschnitt-erster_para-6",
    )
  })

  test("navigates between elements", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-2",
    )

    const heading = page.getByRole("heading", { level: 2 })
    await expect(heading).toHaveText("§ 2 Verfassungsschutzbehörden")

    // When
    await page
      .getByRole("link", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      })
      .click()

    // Then
    await expect(heading).toHaveText(
      "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
    )
  })
})

test.describe("preview", () => {
  test("displays the title and preview", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/hauptteil-1_abschnitt-erster_para-6",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    // Then
    await expect(
      page.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
        level: 2,
      }),
    ).toBeVisible()

    await expect(
      preview.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
        level: 3,
      }),
    ).toBeVisible()
  })

  test("shows the preview at different time boundaries", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    await expect(preview).toContainText(
      "am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt",
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
      /elements\/hauptteil-1_abschnitt-erster_para-6\?atIsoDate=/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
    )

    const previewRegion = page.getByRole("region", { name: "Vorschau" })

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
  test("displays the XML of the target law with metadata", async ({ page }) => {
    // Given
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
    )

    await page.getByRole("tab", { name: "XML" }).click()

    // Then
    await expect(
      page
        .getByRole("region", { name: "Metadaten dokumentieren" })
        .getByText(
          'value="eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1"',
        ),
    ).toBeVisible()
  })

  test("updates the XML preview after saving metadata", async ({ page }) => {
    // Given
    await restoreInitialState(page)

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
    )

    // When
    await page.getByRole("radio", { name: "ÜN - Übergangsnorm" }).check()

    await page.getByRole("button", { name: "Speichern" }).click()

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

    // Then
    expect(textResponse).toContain("ÜN</meta:artDerNorm")

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
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2015-06-01/hauptteil-1_abschnitt-erster_para-6",
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
  let sharedPage: Page

  async function saveMetadata() {
    await sharedPage.getByRole("button", { name: "Speichern" }).click()
    await sharedPage.waitForResponse(/proprietary/)
  }

  async function gotoTimeBoundary(date: string) {
    await sharedPage.goto(
      `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/${date}/hauptteil-1_abschnitt-erster_para-6`,
    )
  }

  async function mockPutResponse(data: ElementProprietary) {
    await sharedPage.route(
      /\/proprietary\/hauptteil-1_abschnitt-erster_para-6\/2023-12-30/,
      async (route) => {
        if (route.request().method() === "PUT") {
          const response = await route.fetch()
          const body = await response.json()

          await route.fulfill({
            response,
            body: JSON.stringify({ ...body, ...data }),
          })
        } else await route.continue()
      },
    )
  }

  test.beforeAll(async ({ browser }) => {
    sharedPage = await browser.newPage()
    await restoreInitialState(sharedPage)
    await gotoTimeBoundary("2023-12-30")
  })

  test.afterAll(async () => {
    await restoreInitialState(sharedPage)
  })

  test.describe("Art der Norm", () => {
    let artSnRadio: Locator
    let artAnRadio: Locator
    let artUnRadio: Locator

    test.beforeAll(() => {
      // Given
      artSnRadio = sharedPage.getByRole("radio", {
        name: "SN - Stammnorm",
      })
      artAnRadio = sharedPage.getByRole("radio", {
        name: "ÄN - Änderungsnorm",
      })
      artUnRadio = sharedPage.getByRole("radio", {
        name: "ÜN - Übergangsnorm",
      })
    })

    test("displays at different time boundaries", async () => {
      // When
      await gotoTimeBoundary("2015-06-01")

      // Then
      await expect(artSnRadio).toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).not.toBeChecked()

      // When
      await gotoTimeBoundary("2023-12-30")

      // Then
      await expect(artSnRadio).not.toBeChecked()
      await expect(artAnRadio).toBeChecked()
      await expect(artUnRadio).not.toBeChecked()
    })

    test("saves changes", async () => {
      // When
      await artUnRadio.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(artSnRadio).not.toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ artDerNorm: "SN" })
      await expect(artSnRadio).not.toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(artSnRadio).toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).not.toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })
})
