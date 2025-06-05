import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "Abgabe view with mocked expressions",
  { tag: ["@RISDEV-7186"] },
  () => {
    test("shows expressions in Abgabe view", async ({ page }) => {
      await page.route(
        "**/api/v1/eli/bund/bgbl-1/1964/s593/expressions/release",
        async (route) => {
          await route.fulfill({
            status: 200,
            contentType: "application/json",
            body: JSON.stringify({
              title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
              shortTitle: "Vereinsgesetz",
              normWorkEli: "eli/bund/bgbl-1/1964/s593",
              expressions: [
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
                  isGegenstandslos: false,
                  currentStatus: "NOT_RELEASED",
                },
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/2024-10-20/1/deu",
                  isGegenstandslos: false,
                  currentStatus: "NOT_RELEASED",
                },
                {
                  normExpressionEli:
                    "eli/bund/bgbl-1/1964/s593/2025-11-22/1/deu",
                  isGegenstandslos: false,
                  currentStatus: "NOT_RELEASED",
                },
              ],
            }),
          })
        },
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/zielnorm/eli/bund/bgbl-1/1964/s593/abgabe",
      )

      const rows = page.getByRole("row")

      const expectedRows = [
        {
          eli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          date: "05.08.1964 – 19.10.2024",
          status: "Noch nicht abgegeben",
        },
        {
          eli: "eli/bund/bgbl-1/1964/s593/2024-10-20/1/deu",
          date: "20.10.2024 – 21.11.2025",
          status: "Noch nicht abgegeben",
        },
        {
          eli: "eli/bund/bgbl-1/1964/s593/2025-11-22/1/deu",
          date: "22.11.2025",
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
  },
)
