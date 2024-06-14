import { Page, expect, test } from "@playwright/test"

test.describe("navigate to page", () => {
  test("navigate to affected document metadata editor", async ({ page }) => {
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

  test("shows an error when the amending law could not be loaded", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/0000/000/0000-00-00/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/",
    )

    await expect(page.getByText("404")).toBeVisible()
  })

  test("shows an error when the time boundaries could not be loaded", async ({
    page,
  }) => {
    await page.route(/timeBoundaries/, (route) => {
      route.abort()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(
      page.getByText("Die Zeitgrenzen konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })

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
})

test.describe("sidebar navigation", () => {
  test("shows the elements affected by this amending law", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    const links = nav.getByRole("link")
    await expect(links).toHaveText(
      [
        "Rahmen",
        "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      ],
      { useInnerText: true },
    )

    // Go to the other time boundary to check if the result is the same (should always
    // show all affected elements independent from the time boundary).
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // Wait again ...
    await page.waitForResponse((response) =>
      response.url().includes("/elements?type="),
    )

    // Expect to see the same result again
    await expect(links).toHaveText(
      [
        "Rahmen",
        "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      ],
      { useInnerText: true },
    )
  })

  test("shows the available time boundaries in the document", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    const select = page.getByRole("combobox", { name: "Zeitgrenze" })
    const options = select.getByRole("option")

    // First entry selected by default
    await expect(select).toHaveValue("1970-01-01")

    // Time boundaries available as options
    await expect(options).toHaveText(["01.01.1970", "30.12.2023"], {
      useInnerText: true,
    })
  })

  test("navigates to the selected time boundary of the whole document", async ({
    page,
  }) => {
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

  test("restores the selected time boundary from the URL", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await expect(
      page.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("2023-12-30")
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

  test("shows an error when the elements could not be loaded", async ({
    page,
  }) => {
    await page.route(
      /elements\?type=article&type=conclusions&type=preamble&type=preface&amendedBy=eli/,
      (route) => {
        route.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(
      page.getByText("Artikel konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })

  test("shows an empty state when no elements are found", async ({ page }) => {
    await page.route(
      /elements\?type=article&type=conclusions&type=preamble&type=preface&amendedBy=eli/,
      async (route) => {
        await route.fulfill({ status: 200, json: {} })
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()

    await page.unrouteAll()
  })

  test("does not render links when no time boundary is selected", async ({
    page,
  }) => {
    page.route(/timeBoundaries/, async (route) => {
      await route.fulfill({ json: [], status: 200 })
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    await expect(page.getByText("Keine Zeitgrenze ausgewählt.")).toBeVisible()

    await expect(
      page
        .getByRole("complementary", { name: "Inhaltsverzeichnis" })
        .getByRole("link"),
    ).toHaveCount(0)
  })
})

test.describe("preview", () => {
  test("displays the title and preview of the whole document", async ({
    page,
  }) => {
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

  test("shows the preview of the whole document at different time boundaries", async ({
    page,
  }) => {
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

  test("displays the title and preview of an element", async ({ page }) => {
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

  test("shows the preview of an element at different time boundaries", async ({
    page,
  }) => {
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

  test("shows an error when the preview could not be loaded for the whole document", async ({
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

  test("shows an error when the preview could not be loaded for an element", async ({
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

test.describe("XML preview", () => {
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
      name: "Sachgebiet FNA-Nummer",
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
        }),
      },
    )
  })

  test("shows an error when the XML could not be loaded for the whole document", async ({
    page,
  }) => {
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

  test("shows an error when the XML could not be loaded for an element", async ({
    page,
  }) => {
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

test.describe("metadata reading", () => {
  test("displays metadata on the frame", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await page.waitForResponse((response) =>
      response.url().includes("/proprietary/"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "210-5",
    )
    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )
  })

  test("displays metadata on the frame at different time boundaries", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/2009/s3366/2024-03-27/1/deu/regelungstext-1/edit/1934-10-16",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await page.waitForResponse((response) =>
      response.url().includes("/proprietary/"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "754-28-2",
    )

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue("")

    const dropdown = page.getByRole("combobox", { name: "Zeitgrenze" })
    dropdown.selectOption("2009-10-08")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2009-10-08"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "111-11-1",
    )

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Verwaltungsvorschrift",
    )

    dropdown.selectOption("2023-01-01")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-01-01"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "222-22-2",
    )
    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )

    dropdown.selectOption("2023-12-24")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-12-24"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "333-33-3",
    )

    await expect(editorRegion.getByLabel("Dokumenttyp")).toHaveValue(
      "Rechtsverordnung",
    )
  })

  test("displays an error if the data could not be loaded for the whole document", async ({
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
        name: "Sachgebiet FNA-Nummer",
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
  })

  test("persists changes across page loads after saving successfully", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/edit/1964-09-21",
    )

    const saved = page.waitForResponse(
      (response) =>
        response.request().method() === "PUT" &&
        response.request().url().endsWith("/proprietary/1964-09-21"),
    )

    // FNA
    const fnaTextbox = page.getByRole("textbox", {
      name: "Sachgebiet FNA-Nummer",
    })
    await expect(fnaTextbox).toHaveValue("754-28-1")
    await fnaTextbox.fill("123-4")

    // Dokumenttyp
    const documentTypeDropdown = page.getByRole("combobox", {
      name: "Dokumenttyp",
    })
    await expect(documentTypeDropdown).toHaveValue("")
    await documentTypeDropdown.selectOption("Berichtigung")

    await page.getByRole("button", { name: "Metadaten speichern" }).click()
    await saved

    // Verify that results have been persisted after reloading the page
    await page.reload()
    await expect(fnaTextbox).toHaveValue("123-4")
    await expect(documentTypeDropdown).toHaveValue("Berichtigung")

    // Reset the data
    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/proprietary/1964-09-21",
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        data: JSON.stringify({
          fna: "754-28-1",
          art: null,
          typ: null,
          subtyp: null,
        }),
      },
    )
  })

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
          },
        })
      else route.continue()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // FNA
    const fnaTextbox = page.getByRole("textbox", {
      name: "Sachgebiet FNA-Nummer",
    })
    await expect(fnaTextbox).toHaveValue("210-5")

    // Dokumenttyp
    const documentTypeDropdown = page.getByRole("combobox", {
      name: "Dokumenttyp",
    })
    await expect(documentTypeDropdown).toHaveValue("Rechtsverordnung")

    await page.getByRole("button", { name: "Metadaten speichern" }).click()

    await expect(fnaTextbox).toHaveValue("600-1")
    await expect(documentTypeDropdown).toHaveValue("Geschäftsordnung")

    await page.unrouteAll()
  })

  test("displays an error if the data could not be saved for the whole document", async ({
    page,
  }) => {
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
