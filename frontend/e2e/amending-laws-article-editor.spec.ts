import { test, expect } from "@playwright/test"
import { amendingLawXml } from "@e2e/testData/amendingLawXml"

test(`navigate to article editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )
  await page.getByText("Änderungsbefehl prüfen").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test(`see law title, article number and xmls`, async ({ page }) => {
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

test(`update law with new content`, async ({ page }) => {
  await page.goto(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )

  try {
    const saveButton = page.getByRole("button", { name: "Speichern" })
    await expect(saveButton).toBeDisabled()

    const editor = page.getByRole("textbox").nth(2)
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
      `/api/v1/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1`,
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
      `/api/v1/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1`,
      {
        headers: {
          "Content-Type": "application/xml",
          Accept: "application/xml",
        },
        data: amendingLawXml,
      },
    )
  }
})
