import { test, expect } from "@playwright/test"

test(`navigate to publishing`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )
  await page.getByRole("link", { name: "Abgabe" }).click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing`,
  )
})

test(`see page title`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing`,
  )

  await expect(
    page.getByRole("heading", { level: 1, name: "Abgabe" }),
  ).toBeVisible()
})
