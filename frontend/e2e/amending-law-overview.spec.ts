import { test, expect } from "@playwright/test"

test(`navigate to amending law overview`, async ({ page }) => {
  await page.goto("/amending-laws")
  await page.getByRole("link", { name: "BGBl. I 2023 Nr. 413" }).click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1`,
  )
})

test(`see page title`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  await expect(
    page.getByRole("heading", { level: 1, name: "Verkündung", exact: true }),
  ).toBeVisible()
})

test(`see rendered law text`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )

  const article = page.getByRole("article")
  await expect(article).toBeVisible()
  await expect(article).toContainText("Vom 29.12.2023")
})
