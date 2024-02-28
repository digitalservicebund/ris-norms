import { test, expect } from "@playwright/test"

test(`navigate to article editor`, async ({ page }) => {
  await page.goto(
    "/amendinglaws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )
  await page.getByText("Änderungsbefehl prüfen").click()

  await expect(page).toHaveURL(
    `/amendinglaws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1/edit`,
  )

  await expect(page.getByRole("heading", { name: "Artikel 1" })).toBeVisible()
})
