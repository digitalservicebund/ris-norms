import { test, expect } from "@playwright/test"
import { targetLawXml } from "@e2e/testData/targetLawXml"

test(`navigate to affected document metadata editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents",
  )
  await page.getByText("Metadaten bearbeiten").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/edit`,
  )
})

test(`see affected document title and xml`, async ({ page }) => {
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
})

test(`load preview`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1/edit`,
  )

  await page.getByRole("button", { name: "Vorschau generieren" }).click()

  // part of the rendered html of the preview
  const targetLawPreview = page
    .getByRole("region", { name: "Vorschau" })
    .getByText("§ 9Kennzeichenverbot")
  await expect(targetLawPreview).toBeVisible()
})

test(`update law with new content`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/edit`,
  )

  try {
    const saveButton = page.getByRole("button", { name: "Speichern" })
    await expect(saveButton).toBeDisabled()

    const editor = page
      .getByRole("region", { name: "Geändertes Gesetz" })
      .getByRole("textbox")
    await expect(editor).toBeVisible()

    // eslint-disable-next-line playwright/no-conditional-in-test -- we need to know if we are running on macos (which uses the darwin nodejs build) to use the correct key for selecting everything in the editor
    await editor.press(
      `${process.platform === "darwin" ? "Meta" : "Control"}+a`,
    )
    await editor.press("Backspace")
    await editor.fill("<xml></xml>")
    await expect(saveButton).toBeEnabled()

    await saveButton.click()
    await expect(saveButton).toBeDisabled()

    // Validate the xml is saved
    const response = await page.request.get(
      `/api/v1/target-laws/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1`,
      {
        headers: {
          Accept: "application/xml",
        },
      },
    )
    expect(await response.text()).toBe("<xml></xml>")
  } finally {
    // Reset the xml
    await page.request.put(
      `/api/v1/target-laws/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1`,
      {
        headers: {
          "Content-Type": "application/xml",
          Accept: "application/xml",
        },
        data: targetLawXml,
      },
    )
  }
})
