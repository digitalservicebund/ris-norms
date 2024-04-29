import { test, expect } from "@playwright/test"
import { amendingLaws } from "@e2e/testData/testData"

test.describe("Affected documents page", () => {
  for (const amendingLaw of amendingLaws) {
    test(`navigate and verify navigation to affected documents for ${amendingLaw.eli}`, async ({
      page,
    }) => {
      // Navigation
      await page.goto(`/amending-laws/${amendingLaw.eli}/affected-documents`)

      // Menu
      const link = page.getByRole("link", { name: "Betroffene Normenkomplexe" })
      await expect(link).toHaveClass(/router-link-active/)

      // Content
      for (const targetLaw of amendingLaw.targetLaws) {
        const element = page
          .getByRole("listitem")
          .filter({ hasText: targetLaw.title })

        await expect(element).toBeVisible()
        await expect(element.getByText(targetLaw.fna!)).toBeVisible()
        await expect(element.getByText(targetLaw.shortTitle!)).toBeVisible()
        await expect(element.getByText(targetLaw.eli)).toBeVisible()

        // Metadata button
        await expect(
          element.getByRole("link", { name: "Metadaten bearbeiten" }),
        ).toBeVisible()
      }

      // Back
      await page.getByText("Zurück").click()
      await expect(page).toHaveURL("/amending-laws")
    })

    // skipped as this view is currently getting reimplemented
    test.skip(`navigate from affected document ${amendingLaw.eli} to the corresponding editor`, async ({
      page,
    }) => {
      // Navigation
      await page.goto(`/amending-laws/${amendingLaw.eli}/affected-documents`)

      for (const targetLaw of amendingLaw.targetLaws) {
        const link = page
          .getByRole("listitem")
          .filter({ hasText: targetLaw.title })
          .getByRole("link", { name: "Metadaten bearbeiten" })

        await link.click()
        await expect(page).toHaveURL(
          `/amending-laws/${amendingLaw.eli}/affected-documents/${targetLaw.zf0Eli}/edit`,
        )

        await page.goBack()
      }
    })
  }
})
