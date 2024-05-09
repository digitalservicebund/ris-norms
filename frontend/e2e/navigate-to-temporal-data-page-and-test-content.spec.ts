import { test, expect } from "@playwright/test"

test.describe("Navigate to temporal data page", () => {
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

test.describe("management of Temporal Data for an amending law", () => {
  test(`adding, editing, deleting and saving time boundaries`, async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/temporal-data",
    )

    // check contents of entry into force article html rendering
    await expect(page.getByText("Artikel 3Inkrafttreten")).toBeVisible()

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

    await page.reload()

    await expect(dateInputs).toHaveCount(3)
    //edit time boundaries

    await expect(dateInputs).toHaveCount(3)
    const dateInputToEdit = page
      .locator('[data-testid="date-input-field"]')
      .nth(1)
    await dateInputToEdit.fill("03.06.2023")
    await saveButton.click()
    await page.reload()

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
