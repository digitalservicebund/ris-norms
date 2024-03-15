import { test, expect } from "@playwright/test"

test(`navigate to affected document metadata editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents",
  )
  await page.getByText("Metadaten bearbeiten").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/edit`,
  )
})

test(`see affected document title and xmls`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/edit`,
  )

  await expect(
    page.getByRole("heading", {
      level: 1,
      name: "Gesetz zur Regelungs des öffenltichen Vereinsrechts (ZF0)",
    }),
  ).toBeVisible()

  // part of the XML of the target law (Gesetz zur Regelungs des öffenltichen Vereinsrechts)
  const targetLawEditor = page
    .getByRole("region", { name: "Geändertes Gesetz" })
    .getByText(
      '<akn:meta eId="meta-1" GUID="849a7fcc-fa01-4b64-92d9-4843520d5db1">',
    )
  await expect(targetLawEditor).toBeVisible()

  // part of the XML of the preview (Änderung des Vereinsgesetzes)
  const targetLawPreview = page
    .getByRole("region", { name: "Vorschau" })
    .getByText(
      '<akn:meta eId="meta-1" GUID="849a7fcc-fa01-4b64-92d9-4843520d5db1">',
    )
  await expect(targetLawPreview).toBeVisible()
})
