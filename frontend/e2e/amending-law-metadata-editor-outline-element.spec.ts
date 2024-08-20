import { test, expect } from "@playwright/test"

test.describe("navigate to page", () => {
  test("display the correct title when an outline element is selected", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/",
    )

    await page
      .getByRole("link", {
        name: "Erster Abschnitt Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden",
      })
      .click()

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
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30/outline/hauptteil-1_abschnitt-erster",
    )

    await expect(
      page.getByRole("region", { name: "Vorschau" }).getByRole("heading", {
        level: 2,
        name: "Erster Abschnitt Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden",
      }),
    ).toBeVisible()
  })
})
