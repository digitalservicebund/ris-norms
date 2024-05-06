import { test, expect } from "@playwright/test"

test.describe("Temporal Data for an amending law", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
  })

  test("navigate to temporal data page for an amending law", async ({
    page,
  }) => {
    await expect(
      page.locator('[data-testid="temporalDataHeading"]'),
    ).toBeVisible()
  })

  test(`contents of entry into force article html rendering`, async ({
    page,
  }) => {
    await expect(page.getByText("Artikel 3Inkrafttreten")).toBeVisible()
  })

  test(`renders correct number of date inputs for the time boundaries`, async ({
    page,
  }) => {
    const dateInputs = page.locator('[data-testid="date-input-field"]')
    await expect(dateInputs).toHaveCount(1)
    const inputValue = await dateInputs.inputValue()
    await expect(inputValue).toBe("16.03.2017")
  })

  test("can add, edit, delete and save time boundaries", async ({ page }) => {
    // adding new time boundaries
    const newDateInput = page.locator('[data-testid="new-date-input-field"]')
    await newDateInput.fill("01-05-2023")
    await newDateInput.fill("02-06-2023")

    const saveButton = page.locator("text=Speichern")

    await saveButton.click()

    await page.reload()

    let dateInputs = page.locator('[data-testid="date-input-field"]')
    await expect(dateInputs).toHaveCount(3)

    // Editing the time boundaries
    const dateInputToEdit = page
      .locator('[data-testid="date-input-field"]')
      .nth(1)
    await dateInputToEdit.fill("")
    await dateInputToEdit.fill("03.06.2023")
    await saveButton.click()
    await page.reload()

    // Verifying the edited value
    const editedValue = await dateInputs.nth(1).inputValue()
    await expect(editedValue).toBe("03.06.2023")

    // deletion of the time boundaries
    for (let i = 2; i > 0; i--) {
      const deleteButton = page.locator(`[data-testid="delete-button-${i}"]`)
      await deleteButton.click()
      await saveButton.click()
      await page.reload()

      dateInputs = page.locator('[data-testid="date-input-field"]')
      await expect(dateInputs).toHaveCount(i)
    }

    const deleteButton = page.locator(`[data-testid="delete-button-0"]`)
    const isDisabled = await deleteButton.isDisabled()
    expect(isDisabled).toBe(true)
  })
})
