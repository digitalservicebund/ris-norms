import { amendingLaws } from "@e2e/testData/testData"
import { test } from "@e2e/utils/test-with-auth"
import { expect } from "@playwright/test"

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
          element.getByRole("link", { name: "Metadaten dokumentieren" }),
        ).toBeVisible()
      }

      // Back
      await page.getByRole("link", { name: "Zurück" }).click()
      await expect(page).toHaveURL("/amending-laws")
    })
  }

  test("shows an error if the affected documents could not be loaded", async ({
    page,
  }) => {
    await page.route(/articles/, (route) => {
      route.abort()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    await page.unrouteAll()
  })
})
