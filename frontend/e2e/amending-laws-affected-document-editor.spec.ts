import { test, expect } from "@playwright/test"

test(`navigate to affected document metadata editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents",
  )
  await page.getByText("Metadaten bearbeiten").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/edit`,
  )
})

test(`see affected document titla and xmls`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/edit`,
  )

  await expect(
    page.getByRole("heading", {
      level: 1,
      name: "Gesetz zur Regelungs des öffenltichen Vereinsrechts",
    }),
  ).toBeVisible()

  // part of the XML of the target law (Gesetz zur Regelungs des öffenltichen Vereinsrechts)
  const targetLawEditor = page
    .getByRole("region", { name: "Geändertes Gesetz" })
    .getByText(
      '<akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">',
    )
  await expect(targetLawEditor).toBeVisible()

  // part of the XML of the preview (Änderung des Vereinsgesetzes)
  const targetLawPreview = page
    .getByRole("region", { name: "Vorschau" })
    .getByText(
      '<akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">',
    )
  await expect(targetLawPreview).toBeVisible()
})
