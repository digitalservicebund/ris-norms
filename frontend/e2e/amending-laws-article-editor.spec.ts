import { test, expect } from "@playwright/test"
import { amendingLawXml } from "@e2e/testData/amendingLawXml"

test(`navigate to article editor`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles",
  )
  await page.getByText("Änderungsbefehl prüfen").click()

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

  test(`see article number, target law title and amending law title`, async ({
    page,
  }) => {
    await expect(
      page.getByRole("heading", { level: 1, name: "Artikel 1" }),
    ).toBeVisible()

    await expect(
      page.locator('[data-testid="targetLawHeading"]'),
    ).toContainText("Gesetz zur Regelungs des öffenltichen Vereinsrechts")

    await expect(
      page.locator('[data-testid="amendingLawHeading"]'),
    ).toContainText("Änderung des Vereinsgesetzes")
  })

  test(`contents of target law in both xml and html tabs`, async ({ page }) => {
    await expect(
      page.getByText(
        "Gesetz zur Regelungs des öffenltichen Vereinsrechts ( Vereinsgesetz)",
      ),
    ).toBeVisible()

    const targetLawSection = page.locator(
      '[aria-labelledby="originalArticleTitle"]',
    )
    const xmlTabButton = targetLawSection.locator('button[aria-label="xml"]')
    await xmlTabButton.click()

    await expect(
      page.getByText(
        '<akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">',
      ),
    ).toBeVisible()
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
        '<akn:meta eId="meta-1" GUID="7e5837c8-b967-45be-924b-c95956c4aa94">',
      ),
    ).toBeVisible()
  })

  test(`update law with new content`, async ({ page }) => {
    try {
      const saveButton = page.getByRole("button", { name: "Speichern" })
      await expect(saveButton).toBeDisabled()
      const amendingLawSection = page.locator(
        '[aria-labelledby="changeCommandsEditor"]',
      )
      const xmlTabButton = amendingLawSection.locator(
        'button[aria-label="xml"]',
      )
      await xmlTabButton.click()

      const editor = amendingLawSection.locator(".cm-editor .cm-content")
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

  test("preview is rendered", async ({ page }) => {
    const changedArticlePreivewSection = page.locator(
      '[aria-labelledby="changedArticlePreivew"]',
    )
    await page.getByRole("button", { name: "Vorschau generieren" }).click()
    const textContentArea = changedArticlePreivewSection.locator(
      "text=Gesetz zur Regelungs des öffenltichen Vereinsrechts ( Vereinsgesetz)",
    )
    await expect(textContentArea).toBeVisible()

    const xmlTabButton = changedArticlePreivewSection.locator(
      'button[aria-label="xml"]',
    )
    await xmlTabButton.click()

    await expect(
      page.getByLabel("Vorschau").getByText(
        // meta tag of the target law, but in another format (eId and GUID are reordered) than the original target law
        '<akn:meta GUID="82a65581-0ea7-4525-9190-35ff86c977af" eId="meta-1">',
      ),
    ).toBeVisible()
  })
})
