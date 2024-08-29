import { Locator, Page } from "@playwright/test"
import { RahmenProprietary } from "@/types/proprietary"

export class MetadataEditorRahmenPage {
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

  async gotoTimeBoundary(date: string) {
    await this.page.goto(
      `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/${date}`,
    )
  }

  async saveMetadata() {
    await this.saveButton.click()
    await this.page.waitForResponse(/proprietary/)
  }

  async mockPutResponse(data: RahmenProprietary) {
    await this.page.route(/\/proprietary\/2023-12-30/, async (route) => {
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
