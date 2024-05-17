import { test, expect } from "@playwright/test"

test(`navigate to article editor using side navigation`, async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
  )

  await page.getByText("Artikelübersicht").click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles`,
  )

  await page.getByText("Änderungsbefehl prüfen").first().click()

  await expect(page).toHaveURL(
    `/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit`,
  )
})

test.describe("Loading amending law and mod details", () => {
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

  test(`loading contents of amending law in both xml and html tabs`, async ({
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

  test(`loading empty mod state when no command is selected`, async ({
    page,
  }) => {
    const textContent = page.locator(
      "text=Wählen sie einen Änderungsbefehl zur Bearbeitung aus.",
    )
    await expect(textContent).toBeVisible()
  })

  test(`loading of mod details with correct input values`, async ({ page }) => {
    const amendingLawSection = page.locator(
      '[aria-labelledby="changeCommandsEditor"]',
    )
    const targetElement = amendingLawSection.locator(
      "text=§ 20 Absatz 1 Satz 2",
    )

    await targetElement.click()

    const formTitle = page.locator("text=Änderungsbefehle bearbeiten\n")
    await expect(formTitle).toBeVisible()

    const formElement = page.locator('[id="risModForm"]')
    await expect(formElement).toBeVisible()

    // Textual Mode Type
    const textualModeTypeElement = formElement.locator("#textualModeType")
    await expect(textualModeTypeElement).toBeVisible()
    await expect(textualModeTypeElement).toHaveValue("Ersetzen")
    await expect(textualModeTypeElement).toHaveAttribute("readonly", "")

    // Time Boundaries
    const timeBoundariesElement = formElement.locator("#timeBoundaries")
    await expect(timeBoundariesElement).toBeVisible()
    await expect(timeBoundariesElement).toHaveValue("2017-03-16")

    const timeBoundaryOptionElements = timeBoundariesElement.locator("option")
    await expect(timeBoundaryOptionElements).toHaveCount(2)

    // Destination Href Eli
    const destinationHrefEliElement = formElement.locator("#destinationHrefEli")
    await expect(destinationHrefEliElement).toBeVisible()
    await expect(destinationHrefEliElement).toHaveValue(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1",
    )
    await expect(destinationHrefEliElement).toHaveAttribute("readonly", "")

    // Destination Href Eid
    const destinationHrefEidElement = formElement.locator("#destinationHrefEid")
    await expect(destinationHrefEidElement).toBeVisible()
    await expect(destinationHrefEidElement).toHaveValue(
      "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-0.xml",
    )
    await expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    // Quoted Text First
    const quotedTextFirstElement = formElement.locator("#quotedTextFirst")
    await expect(quotedTextFirstElement).toBeVisible()
    await expect(quotedTextFirstElement).toHaveValue(
      "§ 9 Abs. 1 Satz 2, Abs. 2",
    )
    await expect(quotedTextFirstElement).toHaveAttribute("readonly", "")

    // Quoted Text Second
    const quotedTextSecondElement = formElement.locator("#quotedTextSecond")
    await expect(quotedTextSecondElement).toBeVisible()
    await expect(quotedTextSecondElement).toHaveValue(
      "§ 9 Absatz 1 Satz 2, Absatz 2 oder 3",
    )
    await expect(quotedTextSecondElement).not.toHaveAttribute("readonly")
  })

  test(`rendering the preview on clicking Vorschau`, async ({ page }) => {
    const amendingLawSection = page.locator(
      '[aria-labelledby="changeCommandsEditor"]',
    )
    const targetElement = amendingLawSection.locator(
      "text=§ 20 Absatz 1 Satz 2",
    )

    await targetElement.click()

    const formElement = page.locator('[id="risModForm"]')
    const vorschauButton = formElement.locator("text=Vorschau")
    await expect(vorschauButton).toBeVisible()
    await vorschauButton.click()

    const previewSection = page.locator(
      '[aria-labelledby="changedArticlePreivew"]',
    )

    const textContent = previewSection.locator(
      "text=Gesetz zur Regelung des öffentlichen Vereinsrechts",
    )
    await expect(textContent).toBeVisible()
  })
})
