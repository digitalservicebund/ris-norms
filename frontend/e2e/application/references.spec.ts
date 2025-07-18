import { selectText } from "@e2e/utils/selectText"
import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test("see breadcrumb", async ({ page }) => {
  await page.goto(
    "./amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1/references",
  )

  await expect(
    page.getByRole("banner").getByText("Textbasierte Metadaten"),
  ).toBeVisible()
})

test("should be able to add a new ref and edit it's refersTo and href and delete it using the delete icon in the table", async ({
  page,
}) => {
  await page.goto(
    "./amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1/references",
  )

  const container = page
    .getByRole("region", { name: "Textbasierte Metadaten" })
    .getByRole("textbox")

  const textElement = container.getByText(/Nach Ablauf der/)

  await container.focus()
  await selectText(textElement, "Übergangsphase")
  await container.blur()

  const newRefRegion = page.getByRole("region", {
    name: "Übergangsphase",
    exact: true,
  })
  await expect(page).toHaveURL(
    "/app/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1/references/art-z%253e1_abs-z_untergl-n1_listenelem-n5_untergl-n1_listenelem-n1_inhalt-n1_text-n1_ändbefehl-n1_quotstruct-n1_abs-z3_inhalt-n1_text-n1_ref-1",
  )

  const combobox = newRefRegion.getByRole("combobox", { name: "Typ" })

  await combobox.click()

  await page
    .getByRole("option", {
      name: "Zitierung",
    })
    .click()

  await newRefRegion
    .getByRole("textbox", { name: "ELI mit Zielstelle" })
    .fill(
      "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-verkuendung-1/hauptteil-1_buch-2_kapitel-1_art-2.xml",
    )

  await page.waitForRequest(/renderings/)

  const saveResponse = page.waitForResponse(
    "/api/v1/norms/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1?",
  )
  await page.getByRole("button", { name: "Speichern" }).click()
  await saveResponse

  await page.reload()

  await expect(newRefRegion.getByRole("combobox", { name: "Typ" })).toHaveText(
    "Zitierung",
  )
  await expect(
    newRefRegion.getByRole("textbox", { name: "ELI mit Zielstelle" }),
  ).toHaveValue(
    "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-verkuendung-1/hauptteil-1_buch-2_kapitel-1_art-2.xml",
  )

  await newRefRegion.getByRole("button", { name: "Löschen" }).click()
  await page.waitForRequest(/renderings/)

  await expect(newRefRegion).toBeHidden()
  await expect(page).toHaveURL(
    "/app/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1/references",
  )

  await page.getByRole("button", { name: "Speichern" }).click()
})

test("should be able to add two new ref's and delete one using the delete icon in the preview", async ({
  page,
}) => {
  await page.goto(
    "./amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-verkuendung-1/references",
  )

  const container = page
    .getByRole("region", { name: "Textbasierte Metadaten" })
    .getByRole("textbox")

  const textElement = container.getByText(/Zur Unterstützung der/)

  await container.focus()
  await selectText(textElement, "Unterstützung der")
  await container.blur()

  const ref1Highlight = page.getByRole("button", {
    name: "Unterstützung der",
    exact: true,
  })
  await expect(ref1Highlight).toBeVisible()

  await container.focus()
  await selectText(textElement, "zentrale Koordinierungsstelle", 2)
  await container.blur()

  const ref2Highlight = page.getByRole("button", {
    name: "zentrale Koordinierungsstelle",
    exact: true,
  })
  await expect(ref2Highlight).toBeVisible()

  await expect(
    page.getByRole("region", { name: "Unterstützung der", exact: true }),
  ).toBeVisible()
  await expect(
    page.getByRole("region", {
      name: "zentrale Koordinierungsstelle",
      exact: true,
    }),
  ).toBeVisible()

  await ref1Highlight.click()
  await ref1Highlight.getByRole("button", { name: "Löschen" }).click()

  await expect(
    page.getByRole("region", { name: "Unterstützung der", exact: true }),
  ).toBeHidden()
  await expect(
    page.getByRole("region", {
      name: "zentrale Koordinierungsstelle",
      exact: true,
    }),
  ).toBeVisible()
  await expect(ref1Highlight).toBeHidden()
  await expect(ref2Highlight).toBeVisible()
})

test.describe("references page error handling", () => {
  test("redirect to 404 if XML not found", async ({ page }) => {
    await page.route(
      "**/norms/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-verkuendung-1?",
      async (route, request) => {
        if (
          request.method() === "GET" &&
          request.headers()["accept"] === "application/xml"
        ) {
          await route.fulfill({
            status: 404,
          })
        } else {
          await route.continue()
        }
      },
    )

    await page.goto(
      "./amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-verkuendung-1/references",
    )

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
