import { test, expect } from "@playwright/test"

test(`navigate to article editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )
  await page.getByText("Änderungsbefehl prüfen").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test(`see law title, articel number and xmls`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )

  await expect(
    page.getByRole("heading", { level: 1, name: "Artikel 1" }),
  ).toBeVisible()
  await expect(
    page.getByRole("heading", {
      level: 3,
      name: "Gesetz zur Regelungs des öffenltichen Vereinsrechts",
    }),
  ).toBeVisible()
  await expect(
    page.getByRole("heading", {
      level: 3,
      name: "Änderung des Vereinsgesetzes",
    }),
  ).toBeVisible()

  // part of the XML of the target law (Gesetz zur Regelungs des öffenltichen Vereinsrechts)
  await expect(
    page.getByText(
      '<akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">',
    ),
  ).toBeVisible()

  // part of the XML of the amending law (Änderung des Vereinsgesetzes)
  await expect(
    page.getByText(
      '<akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">',
    ),
  ).toBeVisible()
})
