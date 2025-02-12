import { test } from "@e2e/utils/test-with-auth"
import { uploadAmendingLaw } from "@e2e/utils/upload-with-force"
import { expect, Page } from "@playwright/test"

let sharedPage: Page
test.beforeAll(async ({ browser }) => {
  sharedPage = await browser.newPage()
})

test.beforeEach(async () => {
  await sharedPage.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
  )
})

test.describe("Load mod details", () => {
  test("Loading of mod details into form", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    await expect(
      sharedPage.getByRole("heading", {
        level: 3,
        name: "Änderungsbefehl bearbeiten",
      }),
    ).toBeVisible()

    const modFormSection = sharedPage.getByRole("region", {
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
    await expect(timeBoundariesElement).toHaveText("11.01.1002")

    await timeBoundariesElement.click()

    const timeBoundaryOptionElements = sharedPage.getByRole("option")
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

    const elementToBeReplacedField = sharedPage.getByTestId(
      "elementToBeReplaced",
    )

    const selectedElementLocator = elementToBeReplacedField.getByRole(
      "button",
      { name: "long title", exact: true },
    )

    await expect(selectedElementLocator).toBeInViewport()

    await expect(selectedElementLocator).toHaveClass(/selected/)

    await expect(sharedPage.getByTestId("replacingElement")).toHaveText(
      "Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen (Strukturänderungsgesetz)",
    )
  })
})

test.describe("Editing a single mod", () => {
  test.afterEach(async ({ authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz.xml",
    )
  })
  test("selecting and saving the time boundary", async () => {
    const amendingLawSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("Fiktives Beispielgesetz").click()

    const modFormSection = sharedPage.getByRole("region", {
      name: "Änderungsbefehl bearbeiten",
    })

    await sharedPage
      .getByRole("combobox", {
        name: "Zeitgrenze",
      })
      .click()

    await sharedPage
      .getByRole("option", {
        name: "10.01.1002",
      })
      .click()

    await modFormSection.getByRole("button", { name: "Speichern" }).click()
    await amendingLawSection.getByRole("tab", { name: "xml" }).click()

    await expect(
      sharedPage.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveText("10.01.1002")
  })
})

test.describe("Editing multiple mods", () => {
  test.afterEach(async ({ authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz.xml",
    )
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

    await expect(timeBoundariesElement).toHaveText("11.1.1002")
    await timeBoundariesElement.click()
    const timeBoundaryOptionElements = sharedPage.getByRole("option")
    await expect(timeBoundaryOptionElements).toHaveCount(4)
  })
})
