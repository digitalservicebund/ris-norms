import { expect, Page, test } from "@playwright/test"
import { ModData } from "@/types/ModType"

async function restoreInitialState(page: Page) {
  const originalModState: ModData = {
    refersTo: "aenderungsbefehl-ersetzen",
    timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-2",
    destinationHref:
      "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml",
    newContent: "NOT USED",
  }

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/mods/hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_%C3%A4ndbefehl-1",
    { data: originalModState },
  )
}

let sharedPage: Page
test.beforeAll(async ({ browser }) => {
  sharedPage = await browser.newPage()
  await restoreInitialState(sharedPage)
})

test.afterAll(async () => {
  await restoreInitialState(sharedPage)
})
const BASE_URL =
  "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit"

test.beforeEach(async () => {
  await sharedPage.goto(BASE_URL)
})

test.describe("Load mod details", () => {
  test("Loading of mod details into form", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )

    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "Änderungsbefehl bearbeiten",
      }),
    ).toBeVisible()

    const modFormSection = page.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })
    await expect(modFormSection).toBeVisible()

    // Textual Mode Type
    const textualModeTypeElement = modFormSection.getByRole("textbox", {
      name: "Änderungstyp",
    })
    await expect(textualModeTypeElement).toBeVisible()
    await expect(textualModeTypeElement).toHaveValue("Ersetzen")
    await expect(textualModeTypeElement).toHaveAttribute("readonly", "")

    // Time Boundaries
    const timeBoundariesElement = modFormSection.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    await expect(timeBoundariesElement).toBeVisible()
    await expect(timeBoundariesElement).toHaveValue("1002-01-11")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(3)

    // // Destination Href Eli
    const destinationHrefEliElement = modFormSection.getByRole("textbox", {
      name: "ELI Zielgesetz",
    })
    await expect(destinationHrefEliElement).toBeVisible()
    await expect(destinationHrefEliElement).toHaveValue(
      "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1",
    )
    await expect(destinationHrefEliElement).toHaveAttribute("readonly", "")

    const elementToBeReplacedField = page.getByTestId("elementToBeReplaced")

    const selectedElementLocator = elementToBeReplacedField.getByRole(
      "button",
      { name: "long title p Fiktives" },
    )

    await expect(selectedElementLocator).toBeInViewport()

    await expect(selectedElementLocator).toHaveClass(/selected/)

    await expect(page.getByTestId("replacingElement")).toHaveText(
      "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
    )
  })

  test("Display preview of time machine", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })
    const previewSection = page.getByRole("region", {
      name: "Vorschau",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten (Strukturänderungsgesetz)",
      ),
    ).toBeHidden()

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
      ),
    ).toBeVisible()

    await previewSection.getByRole("tab", { name: "xml" }).click()

    // Do a search
    await previewSection
      .getByText('<?xml version="1.0" encoding="UTF-8"?>')
      .click()
    await page.keyboard.press("ControlOrMeta+f")
    await previewSection
      .getByRole("textbox", { name: "Find" })
      .fill("mit Änderungsbefehlen")
    await previewSection.getByRole("button", { name: "next" }).click()
    await expect(
      previewSection.getByText("mit Änderungsbefehlen").first(),
    ).toBeVisible()
  })
})

test.describe("Editing a single mod", () => {
  test("selecting and saving the time boundary", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    const modFormSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await sharedPage
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("1002-01-10")

    await modFormSection.getByRole("button", { name: "Speichern" }).click()
    await amendingLawSection.getByRole("tab", { name: "xml" }).click()

    await expect(
      sharedPage.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("1002-01-10")
  })

  test("select another target node and see preview", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })
    const modFormSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })
    const previewSection = sharedPage.getByRole("region", {
      name: "Vorschau",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    const elementToBeReplacedField = sharedPage.getByTestId(
      "elementToBeReplaced",
    )

    await elementToBeReplacedField
      .getByRole("button", {
        name: "headingGesetz zur Einbindung eines Regelungstextes in Stammformheading",
        exact: true,
      })
      .click()

    await modFormSection.getByRole("button", { name: "Speichern" }).click()

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten (Strukturänderungsgesetz)",
      ),
    ).toBeVisible()

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
      ),
    ).toBeVisible()

    await previewSection.getByRole("tab", { name: "xml" }).click()

    // Do a search
    await previewSection
      .getByText('<?xml version="1.0" encoding="UTF-8"?>')
      .click()
    await sharedPage.keyboard.press("ControlOrMeta+f")
    await previewSection
      .getByRole("textbox", { name: "Find" })
      .fill("<akn:longTitle")
    await previewSection.getByRole("button", { name: "next" }).click()
    await expect(
      previewSection.getByText("<akn:longTitle").first(),
    ).toBeVisible()
  })
})

test.describe("Editing multiple mods", () => {
  let sharedPage: Page

  async function restoreInitialState() {
    const originalModsState = {
      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1":
        { timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-2" },
      "hauptteil-1_para-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1":
        { timeBoundaryEid: "meta-1_geltzeiten-1_geltungszeitgr-2" },
    }
    await sharedPage.request.patch(
      "api/v1/norms/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/mods",
      { data: originalModsState },
    )
  }

  test.beforeEach(async ({ browser }) => {
    sharedPage = await browser.newPage()
    await restoreInitialState()
    await sharedPage.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_para-1/edit",
    )
  })

  test.afterEach(async () => {
    await restoreInitialState()
  })

  test("Displaying time boundary", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()
    await amendingLawSection
      .getByText("Aufgrund der Spezifikation")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      sharedPage.getByRole("heading", {
        level: 3,
        name: "2 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    const timeBoundariesElement = sharedPage.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    await expect(timeBoundariesElement).toHaveValue("1002-01-11")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(4)
  })

  test("rendering the preview by default when clicking on a command", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()
    await amendingLawSection
      .getByText("Aufgrund der Spezifikation")
      .click({ modifiers: ["ControlOrMeta"] })

    const previewSection = sharedPage.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
      ),
    ).toBeVisible()
  })

  test("selecting and saving the time boundary", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()
    await amendingLawSection
      .getByText("Aufgrund der Spezifikation")
      .click({ modifiers: ["ControlOrMeta"] })

    await sharedPage
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")

    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()

    const previewSection = sharedPage.getByRole("region", {
      name: "Vorschau",
    })

    await expect(
      previewSection.getByText(
        "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten (Strukturänderungsgesetz)",
      ),
    ).toBeVisible()
  })

  test("show 'Mehrere' and no preview if time boundaries differ", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    await sharedPage
      .getByRole("combobox", { name: "Zeitgrenze" })
      .selectOption("Keine Angabe")
    await sharedPage.getByRole("button", { name: "Speichern" }).click()

    await expect(
      sharedPage.getByRole("tooltip", { name: "Speichern erfolgreich" }),
    ).toBeVisible()

    await amendingLawSection
      .getByText("Aufgrund der Spezifikation")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      sharedPage.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("multiple")

    await expect(
      sharedPage.getByText(
        "Eine Vorschau kann nur für Änderungsbefehle mit der selben Zeitgrenze generiert werden.",
      ),
    ).toBeVisible()
  })
})
