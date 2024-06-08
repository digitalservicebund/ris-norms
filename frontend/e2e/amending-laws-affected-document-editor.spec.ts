import { Page, expect, test } from "@playwright/test"
import {
  verfassungsschutzgesetz as currentXml,
  changedVerfassungsschutzgesetz as newXml,
} from "./testData/verfassungsschutzgesetz"

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
})

test.describe("XML editing", () => {
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

  test("edits and saves the XML of the target law", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const editorRegion = page.getByRole("region", {
      name: "Metadaten bearbeiten",
    })

    await editorRegion.getByRole("tab", { name: "XML" }).click()

    const editor = editorRegion.getByRole("textbox")

    await expect(
      editorRegion.getByText(
        'value="eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1"',
      ),
    ).toBeVisible()

    try {
      const saveButton = page.getByRole("button", { name: "Speichern" })
      await expect(saveButton).toBeDisabled()

      // eslint-disable-next-line playwright/no-conditional-in-test -- we need to
      // know if we are running on macos (which uses the darwin nodejs build) to
      // use the correct key for selecting everything in the editor
      await editor.press(
        `${process.platform === "darwin" ? "Meta" : "Control"}+a`,
      )

      await editor.press("Backspace")
      await editor.fill(newXml)

      await expect(saveButton).toBeEnabled()
      await saveButton.click()
      await expect(saveButton).toBeDisabled()

      // Validate the XML is saved
      const response = await page.request.get(
        "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        { headers: { Accept: "application/xml" } },
      )

      expect(await response.text()).toBe(newXml)
    } finally {
      // Reset the XML
      await page.request.put(
        "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        {
          headers: {
            "Content-Type": "application/xml",
            Accept: "application/xml",
          },
          data: currentXml,
        },
      )
    }
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

    const dropdown = page.getByRole("combobox", { name: "Zeitgrenze" })
    dropdown.selectOption("2009-10-08")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2009-10-08"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "111-11-1",
    )

    dropdown.selectOption("2023-01-01")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-01-01"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "222-22-2",
    )

    dropdown.selectOption("2023-12-24")
    await page.waitForResponse((response) =>
      response.url().endsWith("/proprietary/2023-12-24"),
    )

    await expect(editorRegion.getByLabel("Sachgebiet FNA-Nummer")).toHaveValue(
      "333-33-3",
    )
  })

  test("displays an error if the data could not be loaded", async ({
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
      editorRegion.getByText("Die Daten konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})

test.describe("metadata editing", () => {
  test.describe("editing individual fields", () => {
    // Creating a shared page allows us to stay in the same page/context while
    // editing all metadata fields (as a user would) without relading the page
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

    const fnaTextbox = page.getByRole("textbox", {
      name: "Sachgebiet FNA-Nummer",
    })
    await expect(fnaTextbox).toHaveValue("754-28-1")
    await fnaTextbox.fill("123-4")
    await page.getByRole("button", { name: "Metadaten speichern" }).click()
    await saved

    await page.reload()
    await expect(fnaTextbox).toHaveValue("123-4")
  })

  test("updates with metadata from the backend after saving", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await page.route(/\/proprietary\/2023-12-30$/, (route) => {
      // Mocking the response instead of getting the real response from the backend
      // to force a state where the value returned from the backend is different from
      // the state in the UI. In real-world use, this should almost never happen,
      // but we still want to test it.
      if (route.request().method() === "PUT")
        route.fulfill({ status: 200, json: { fna: { value: "600-1" } } })
      else route.continue()
    })

    const fnaTextbox = page.getByRole("textbox", {
      name: "Sachgebiet FNA-Nummer",
    })
    await expect(fnaTextbox).toHaveValue("210-5")
    await page.getByRole("button", { name: "Metadaten speichern" }).click()
    await expect(fnaTextbox).toHaveValue("600-1")

    await page.unroute(/\/proprietary\/2023-12-30$/)
  })

  test("displays an error if the data could not be saved", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await page.route(/\/proprietary\/2023-12-30$/, (route) => {
      if (route.request().method() === "PUT") route.abort("failed")
      else route.continue()
    })

    await page.getByRole("button", { name: "Metadaten speichern" }).click()

    await expect(
      page.getByRole("tooltip", { name: "Speichern fehlgeschlagen" }),
    ).toBeVisible()

    await page.unroute(/\/proprietary\/2023-12-30$/)
  })
})
