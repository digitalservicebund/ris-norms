import { test, expect } from "@playwright/test"

test.describe("navigate to page", () => {
  test("display the correct title when an outline element is selected", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/edit",
    )

    await page.getByRole("link", { name: "Beispielkapitel" }).click()

    await expect(
      page.getByText(
        "Aktuell sind keine Metadaten auf Gliederungsebene implementiert.",
      ),
    ).toBeVisible()
  })

  test("display the preview when an outline element is selected", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/edit/1002-01-11/outline/hauptteil-1_buch-4_kapitel-1",
    )

    await expect(
      page.getByRole("heading", { level: 2, name: "Beispielkapitel" }),
    ).toBeVisible()
  })
})
