import { test, expect } from "@playwright/test"

test("navigate to temporal data page for an amending law", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
  )
  await expect(
    page.locator('[data-testid="temporalDataHeading"]'),
  ).toBeVisible()
})

test.describe("Temporal Data for an amending law", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
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

  test("can add and save multiple new time boundaries", async ({ page }) => {
    const newDateInput = page.locator('[data-testid="new-date-input-field"]')
    await newDateInput.fill("01-05-2023")
    await newDateInput.fill("02-06-2023")
    await newDateInput.fill("03-07-2023")

    const saveButton = page.locator("text=Speichern")
    await saveButton.click()
    await page.reload()

    const dateInputs = page.locator('[data-testid="date-input-field"]')
    await expect(dateInputs).toHaveCount(4)
  })

  test("can delete a date input and verify the remaining inputs", async ({
    page,
  }) => {
    let dateInputs = page.locator('[data-testid="date-input-field"]')
    const saveButton = page.locator("text=Speichern")

    await expect(dateInputs).toHaveCount(4)

    for (let i = 3; i > 0; i--) {
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
