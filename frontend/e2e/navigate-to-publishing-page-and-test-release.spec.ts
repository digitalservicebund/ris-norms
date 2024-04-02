import { test, expect, Page } from "@playwright/test"
import { readFileSync } from "fs"

test.describe("Publishing flow for an amending law", () => {
  async function verifyPublicationTime(
    page: Page,
    expectedDate: string,
    expectedTime: string,
  ): Promise<void> {
    const timeElement = page.locator("time")
    await expect(timeElement).toHaveText(
      `${expectedDate} um ${expectedTime} Uhr. Die aktuelle Version kann hier eingesehen werden: `,
    )
  }

  async function downloadAndVerifyFile(
    page: Page,
    linkSelector: string,
    expectedDownloadAttribute: string,
    expectedContent: string,
  ): Promise<void> {
    const [download] = await Promise.all([
      page.waitForEvent("download"),
      page.click(linkSelector),
    ])

    expect(await download.suggestedFilename()).toBe(expectedDownloadAttribute)

    const path = await download.path()
    const fileContent = readFileSync(path, { encoding: "utf8" })
    expect(fileContent).toContain(expectedContent)
  }

  test("navigate to publishing page for an amending law", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing",
    )

    await expect(
      page.locator('text="Das Gesetz wurde noch nicht veröffentlicht."'),
    ).toBeVisible()

    // Trigger the publication of the amending law
    await page.getByRole("button", { name: "Jetzt abgeben" }).click()

    const currentTimestamp = new Date()
    const expectedDateString = currentTimestamp.toLocaleDateString("de-DE", {
      dateStyle: "medium",
    })
    const expectedTimeString = currentTimestamp.toLocaleTimeString("de-DE", {
      hour: "2-digit",
      minute: "2-digit",
    })

    // Verify publication time
    await verifyPublicationTime(page, expectedDateString, expectedTimeString)

    // Verify Links
    const amendingLawEli =
      "eli_bund_bgbl-1_2017_s419_2017-03-15_1_deu_regelungstext-1.xml"
    const targetLawEli =
      "eli_bund_bgbl-1_1964_s593_2017-03-15_1_deu_regelungstext-1.xml"

    const amendingLawLink = page.locator(
      `a:has-text("${amendingLawEli.replace(/_/g, "/")}")`,
    )
    await expect(amendingLawLink).toHaveAttribute("download", amendingLawEli)

    const targetLawLink = page.locator(
      `a:has-text("${targetLawEli.replace(/_/g, "/")}")`,
    )
    await expect(targetLawLink).toHaveAttribute("download", targetLawEli)

    // Download and verify files
    const publishedAmendingLawExpectedContent =
      'value="eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"'
    const publishedTargetLawExpectedContent =
      'value="eli/bund/bgbl-1/1964/s593/regelungstext-1"'

    await downloadAndVerifyFile(
      page,
      `a[download='${amendingLawEli}']`,
      amendingLawEli,
      publishedAmendingLawExpectedContent,
    )
    await downloadAndVerifyFile(
      page,
      `a[download='${targetLawEli}']`,
      targetLawEli,
      publishedTargetLawExpectedContent,
    )

    // Check new published time after republishing
    await page.getByRole("button", { name: "Jetzt abgeben" }).click()

    const newTimestamp = new Date()
    const newExpectedDateString = newTimestamp.toLocaleDateString("de-DE", {
      dateStyle: "medium",
    })
    const newExpectedTimeString = newTimestamp.toLocaleTimeString("de-DE", {
      hour: "2-digit",
      minute: "2-digit",
    })

    await page.reload()
    await verifyPublicationTime(
      page,
      newExpectedDateString,
      newExpectedTimeString,
    )
  })
})
