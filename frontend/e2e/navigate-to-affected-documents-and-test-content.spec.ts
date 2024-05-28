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
  }
})
