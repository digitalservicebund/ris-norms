import { Page, expect, test } from "@playwright/test"

test.describe("navigate to temporal data page", () => {
  test("navigate to temporal data page for an amending law using side navigation", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )

    await page.getByText("Zeitgrenzen anlegen").click()

    await expect(page).toHaveURL(
      `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data`,
    )

    await expect(
      page.getByRole("heading", { level: 1, name: "Zeitgrenzen anlegen" }),
    ).toBeVisible()
  })
})

test.describe("manage temporal data for an amending law", () => {
  async function setupInitialData(page: Page) {
    await page.request.put(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries",
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        data: JSON.stringify([
          { date: "2017-03-16", eventRefEid: "meta-1_lebzykl-1_ereignis-2" },
        ]),
      },
    )
  }

  test.describe("adding, editing, deleting and saving time boundaries", () => {
    let sharedPage: Page

    test.beforeAll(async ({ browser }) => {
      sharedPage = await browser.newPage()
      await setupInitialData(sharedPage)
      await sharedPage.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
      )
    })

    test.afterAll(async () => {
      await setupInitialData(sharedPage)
    })

    test("renders the HTML preview", async () => {
      await expect(sharedPage.getByText("Artikel 3Inkrafttreten")).toBeVisible()

      const dateInputs = sharedPage.locator('[data-testid="date-input-field"]')

      await expect(dateInputs).toHaveCount(1)
      await expect(dateInputs).toHaveValue("16.03.2017")
    })

    test("adds new time boundaries", async () => {
      const saveButton = sharedPage.locator("text=Speichern")
      const dateInputs = sharedPage.locator('[data-testid="date-input-field"]')

      // add new time boundaries
      const newDateInput = sharedPage.locator(
        '[data-testid="new-date-input-field"]',
      )
      await newDateInput.fill("01.05.2023")
      await newDateInput.fill("02.06.2023")

      await saveButton.click()
      await sharedPage.reload()

      await expect(dateInputs).toHaveCount(3)

      // validate each date input contains the correct data
      await expect(dateInputs.nth(0)).toHaveValue("16.03.2017")
      await expect(dateInputs.nth(1)).toHaveValue("01.05.2023")
      await expect(dateInputs.nth(2)).toHaveValue("02.06.2023")
    })

    test("edits time boundaries", async () => {
      const saveButton = sharedPage.locator("text=Speichern")
      const dateInputs = sharedPage.locator('[data-testid="date-input-field"]')

      const dateInputToEdit = sharedPage
        .locator('[data-testid="date-input-field"]')
        .nth(1)
      await dateInputToEdit.fill("03.06.2023")

      await saveButton.click()
      await sharedPage.reload()

      await expect(dateInputs.nth(1)).toHaveValue("03.06.2023")
    })

    test("deletes time boundaries", async () => {
      const saveButton = sharedPage.locator("text=Speichern")
      const dateInputs = sharedPage.locator('[data-testid="date-input-field"]')

      // delete time boundaries
      for (let i = 2; i > 0; i--) {
        const deleteButton = sharedPage.locator(
          `[data-testid="delete-button-${i}"]`,
        )
        await deleteButton.click()

        await saveButton.click()
        await sharedPage.reload()

        await expect(dateInputs).toHaveCount(i)
      }

      const deleteButton = sharedPage.locator(`[data-testid="delete-button-0"]`)

      await expect(deleteButton).toBeDisabled()
      await expect(dateInputs).toHaveValue("16.03.2017")
    })
  })

  test(`at most 100 time boundaries can be added`, async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )
    await setupInitialData(page)

    // check contents of entry into force article html rendering
    await expect(page.getByText("Artikel 3Inkrafttreten")).toBeVisible()

    await expect(
      page.getByRole("textbox", { name: "Zeitgrenze 1" }),
    ).toBeVisible()

    // add new time boundaries 2 to 100
    for (let i = 2; i <= 100; i++) {
      await page
        .getByRole("textbox", { name: "Zeitgrenze hinzufügen" })
        .fill("01.05.2023")
    }

    await expect(page.getByRole("textbox")).toHaveCount(100)

    await expect(
      page.getByRole("textbox", { name: "Zeitgrenze 100" }),
    ).toBeVisible()

    await expect(
      page.getByRole("textbox", { name: "Zeitgrenze hinzufügen" }),
    ).toBeHidden()
  })
})
