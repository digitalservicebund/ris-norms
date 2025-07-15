import type { Locator, Page } from "@playwright/test"
import type { RahmenProprietary } from "@/types/proprietary"

export class DatenbankMetadataEditorRahmenPage {
  readonly page: Page

  readonly fnaInput: Locator
  readonly documentTypeDropdown: Locator
  readonly artSnCheckbox: Locator
  readonly artAnCheckbox: Locator
  readonly artUnCheckbox: Locator
  readonly bezeichnungInput: Locator
  readonly staatDropdown: Locator
  readonly organDropdown: Locator
  readonly qualMehrheitCheckbox: Locator
  readonly ressortDropdown: Locator
  readonly organisationsEinheitInput: Locator

  readonly saveButton: Locator

  constructor(page: Page) {
    this.page = page
    this.fnaInput = page.getByRole("textbox", { name: "Sachgebiet" })
    this.documentTypeDropdown = page.getByRole("combobox", {
      name: "Dokumenttyp",
    })
    this.artSnCheckbox = page.getByRole("checkbox", {
      name: "SN - Stammnorm",
    })
    this.artAnCheckbox = page.getByRole("checkbox", {
      name: "ÄN - Änderungsnorm",
    })
    this.artUnCheckbox = page.getByRole("checkbox", {
      name: "ÜN - Übergangsnorm",
    })
    this.bezeichnungInput = page.getByRole("textbox", {
      name: "Bezeichnung gemäß Vorlage",
    })
    this.staatDropdown = page.getByRole("combobox", {
      name: "Staat",
    })
    this.organDropdown = page.getByRole("combobox", {
      name: "beschließendes Organ",
    })
    this.qualMehrheitCheckbox = page.getByRole("checkbox", {
      name: "Beschlussf. qual. Mehrheit",
    })
    this.ressortDropdown = page.getByRole("combobox", {
      name: "Ressort",
    })
    this.organisationsEinheitInput = page.getByRole("textbox", {
      name: "Organisationseinheit",
    })
    this.saveButton = page.getByRole("button", { name: "Speichern" })
  }

  async goto() {
    await this.page.goto(
      "./datenbank/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/metadaten",
    )
  }

  async saveMetadata() {
    await this.saveButton.click()
    await this.page.waitForResponse(/proprietary/)
  }

  async mockPutResponse(data: RahmenProprietary) {
    await this.page.route(/\/proprietary/, async (route) => {
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
}
