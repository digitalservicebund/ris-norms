import { test, expect } from "@playwright/test"

test.describe("Navigate to temporal data page and test its rendered content", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )
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
})

test.describe("management of Temporal Data for an amending law", () => {
  test(`renders correct number of date inputs for the time boundaries`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )
    const saveButton = page.locator("text=Speichern")
    let dateInputs = page.locator('[data-testid="date-input-field"]')

    await expect(dateInputs).toHaveCount(1)
    const inputValue = await dateInputs.inputValue()
    await expect(inputValue).toBe("16.03.2017")

    //   add new time boundaries
    const newDateInput = page.locator('[data-testid="new-date-input-field"]')
    await newDateInput.fill("01-05-2023")
    await newDateInput.fill("02-06-2023")
    await saveButton.click()

    await page.waitForResponse(
      (response) =>
        response.url().includes("timeBoundaries") && response.status() === 200,
    )

    const addTimeBoundariesResponse = await page.request.get(
      `/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries`,
      {
        headers: {
          Accept: "application/json",
        },
      },
    )

    const addedTimeBoundaries = await addTimeBoundariesResponse.json()

    expect(addedTimeBoundaries).toEqual([
      { date: "2017-03-16", eventRefEid: "meta-1_lebzykl-1_ereignis-2" },
      { date: "2023-05-01", eventRefEid: "meta-1_lebzykl-1_ereignis-3" },
      { date: "2023-06-02", eventRefEid: "meta-1_lebzykl-1_ereignis-4" },
    ])

    //edit time boundaries

    await expect(dateInputs).toHaveCount(3)
    const dateInputToEdit = page
      .locator('[data-testid="date-input-field"]')
      .nth(1)
    await dateInputToEdit.fill("")
    await dateInputToEdit.fill("03.06.2023")
    await saveButton.click()

    await page.waitForResponse(
      (response) =>
        response.url().includes("timeBoundaries") && response.status() === 200,
    )

    const editedTimeboundariesResponse = await page.request.get(
      `/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries`,
      {
        headers: {
          Accept: "application/json",
        },
      },
    )
    const editedTimeboundaries = await editedTimeboundariesResponse.json()

    expect(editedTimeboundaries).toEqual([
      { date: "2017-03-16", eventRefEid: "meta-1_lebzykl-1_ereignis-2" },
      { date: "2023-06-03", eventRefEid: "meta-1_lebzykl-1_ereignis-3" },
      { date: "2023-06-02", eventRefEid: "meta-1_lebzykl-1_ereignis-4" },
    ])

    const editedValue = await dateInputs.nth(1).inputValue()
    await expect(editedValue).toBe("03.06.2023")

    //   delete time boundaries

    for (let i = 2; i > 0; i--) {
      const deleteButton = page.locator(`[data-testid="delete-button-${i}"]`)
      await deleteButton.click()
      await saveButton.click()
      await page.reload()
      dateInputs = page.locator('[data-testid="date-input-field"]')
      await expect(dateInputs).toHaveCount(i)
    }

    const deletedTimeBoundariesResponse = await page.request.get(
      `/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries`,
      {
        headers: {
          Accept: "application/json",
        },
      },
    )
    const timeboundaries = await deletedTimeBoundariesResponse.json()

    expect(timeboundaries).toEqual([
      { date: "2017-03-16", eventRefEid: "meta-1_lebzykl-1_ereignis-2" },
    ])

    const deleteButton = page.locator(`[data-testid="delete-button-0"]`)
    const isDisabled = await deleteButton.isDisabled()
    expect(isDisabled).toBe(true)

    await page.request.put(
      `/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/timeBoundaries`,
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
  })
})
