import { expect, test } from "@playwright/test"

test.describe("navigate to page", () => {
  test("shows an error when an element page could not be loaded", async ({
    page,
  }) => {
    await page.route(
      /elements\/hauptteil-1_abschnitt-erster_para-6/,
      async (route) => {
        await route.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
    )

    await expect(
      page.getByText("Das Element konnte nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })

  test("navigates to the selected time boundary of an element", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/",
    )

    await page
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("2023-12-30")

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    await nav
      .getByRole("link", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      })
      .click()

    await expect(page).toHaveURL(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/hauptteil-1_abschnitt-erster_para-6",
    )
  })

  test("navigates between elements", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1/edit/1934-10-16/hauptteil-1_para-2",
    )

    const heading = page.getByRole("heading", { level: 2 })
    await expect(heading).toHaveText("§1")

    await page.getByRole("link", { name: "§3a" }).click()
    await expect(heading).toHaveText("§3a")
  })
})

test.describe("preview", () => {
  test("displays the title and preview", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/hauptteil-1_abschnitt-erster_para-6",
    )

    await expect(
      page.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
        level: 2,
      }),
    ).toBeVisible()

    const preview = page.getByRole("region", {
      name: "Vorschau",
    })
    await expect(
      preview.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
        level: 3,
      }),
    ).toBeVisible()
  })

  test("shows the preview at different time boundaries", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
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
      /elements\/hauptteil-1_abschnitt-erster_para-6\?atIsoDate=/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
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
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
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

  // Skipped until this is implemented on the page and the test has been adapted.
  test.skip("updates the XML preview after saving metadata", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    // TODO: Make some changes to metadata

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

    // TODO: Verify the changes are included
    expect(textResponse).toContain("1234-56-78")

    // TODO: Reset the data
  })

  test("shows an error when the XML could not be loaded", async ({ page }) => {
    await page.route(
      /\/norms\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\?$/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01/hauptteil-1_abschnitt-erster_para-6",
    )

    await page.getByRole("tab", { name: "XML" }).click()

    await expect(
      page.getByText("Die XML-Ansicht konnte nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})
