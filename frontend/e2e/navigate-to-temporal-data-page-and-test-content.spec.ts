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
    const dateInput = page.locator('[data-testid="date-input-field"]')
    await expect(dateInput).toHaveCount(1)
    const inputValue = await dateInput.inputValue()
    await expect(inputValue).toBe("16.03.2017")
  })
})
