import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("navigate to page", { tag: ["@RISDEV-6266"] }, () => {
  test("display the preview when an outline element is selected", async ({
    page,
  }) => {
    await page.goto(
      "./datenbank/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/metadaten/outline/hauptteil-n1_abschnitt-n1",
    )

    await expect(
      page.getByRole("region", { name: "Vorschau" }).getByRole("heading", {
        level: 2,
        name: "Erster Abschnitt Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden",
      }),
    ).toBeVisible()
  })

  test("display the warning that no metadata is currently implemented at outline level when an outline element is selected", async ({
    page,
  }) => {
    await page.goto(
      "./datenbank/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/metadaten",
    )

    await page
      .getByRole("button", {
        name: "Erster Abschnitt Zusammenarbeit, Aufgaben der Verfassungsschutzbehörden",
      })
      .click()

    await expect(
      page.getByText(
        "Aktuell sind keine Metadaten auf Gliederungsebene implementiert.",
      ),
    ).toBeVisible()
  })
})
