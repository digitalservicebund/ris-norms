import { test, expect } from "@playwright/test"

test(`navigate to article editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
  )
  await page.getByText("Änderungsbefehl prüfen").first().click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test.describe("article editor", () => {
  const BASE_URL =
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit"

  test.beforeEach(async ({ page }) => {
    await page.goto(BASE_URL)
  })

  test(`see article number and amending law title`, async ({ page }) => {
    await expect(
      page.getByRole("heading", { level: 1, name: "Artikel 1" }),
    ).toBeVisible()

    await expect(
      page.locator('[data-testid="amendingLawHeading"]'),
    ).toContainText("Änderung des Vereinsgesetzes")
  })

  test(`contents of amending law in both xml and html tabs`, async ({
    page,
  }) => {
    const amendingLawSection = page.locator(
      '[aria-labelledby="changeCommandsEditor"]',
    )
    const textContent = amendingLawSection.locator(
      "text=Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    )
    await expect(textContent).toBeVisible()

    const xmlTabButton = amendingLawSection.locator('button[aria-label="xml"]')
    await xmlTabButton.click()

    await expect(
      page.getByText(
        '<akn:meta GUID="7e5837c8-b967-45be-924b-c95956c4aa94" eId="meta-1">',
      ),
    ).toBeVisible()
  })
})
