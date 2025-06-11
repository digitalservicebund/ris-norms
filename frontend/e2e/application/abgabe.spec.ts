import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe("Abgabe view with expressions", { tag: ["@RISDEV-7186"] }, () => {
  test.beforeAll(async ({ authenticatedRequest }) => {
    await uploadAmendingLaw(
      authenticatedRequest,
      "aenderungsgesetz-abgabe.xml",
      frontendTestDataDirectory,
    )
  })

  test("shows expressions in Abgabe view", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2025/9999/2025-06-11/1/deu/regelungstext-1/zielnorm/eli/bund/bgbl-1/1964/1234/abgabe",
    )

    const rows = page.getByRole("row")

    const expectedRows = [
      {
        eli: "eli/bund/bgbl-1/1964/1234/1964-09-21/1/deu",
        date: "21.09.1964 – 31.12.2023",
        status: "Prätext abgegeben",
      },
      {
        eli: "eli/bund/bgbl-1/1964/1234/2024-01-01/1/deu",
        date: "01.01.2024",
        status: "Noch nicht abgegeben",
      },
    ]

    for (const { eli, date, status } of expectedRows) {
      const row = rows.filter({ hasText: eli })
      await expect(row).toContainText(eli)
      await expect(row).toContainText(date)
      await expect(row).toContainText(status)
    }
  })
})
