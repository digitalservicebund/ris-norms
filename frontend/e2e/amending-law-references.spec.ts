import { test, expect } from "@playwright/test"

test("navigate to amending law references page without selected mods", async ({
  page,
}) => {
  await page.goto("/amending-laws")
  await page.getByRole("link", { name: "BGBl. I 1002 Nr. 2" }).click()
  await page.getByRole("link", { name: "Betroffene Normenkomplexe" }).click()
  await page.getByRole("link", { name: "Inhaltliche Auszeichnungen" }).click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  await expect(
    page.getByText(
      "Wählen Sie links einen Änderungsbefehl zur Dokumentation von textbasierten Metadaten aus.",
    ),
  ).toBeVisible()
})

test("selects a mod but not references present so it shows the empty state for the ref table", async ({
  page,
}) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
  )

  await expect(
    page.getByText(
      "Für die ausgewählte Textpassage sind noch keine Verweise dokumentiert. Markieren Sie links Text, um neue Verweise hinzuzufügen.",
    ),
  ).toBeVisible()
})

test("see breadcrumb", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  await expect(
    page.getByRole("banner").getByText("Textbasierte Metadaten"),
  ).toBeVisible()
})

test("see rendered law text", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  const article = page.getByRole("article")
  await expect(article).toBeVisible()
  await expect(article).toContainText("vom 1. Januar 1002")
})

test("should be able to select a mod, add a new ref and edit it's refersTo and href and delete it using the delete icon in the table", async ({
  page,
}) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
  )

  const textBoundingBox = await page
    .getByRole("region", { name: "Textbasierte Metadaten" })
    .getByText(/Zur Unterstützung der Implementierung /)
    .boundingBox()

  await page.mouse.move(textBoundingBox!.x + 30, textBoundingBox!.y)
  await page.mouse.down()
  await page.mouse.move(textBoundingBox!.x + 145, textBoundingBox!.y)
  await page.mouse.up()

  const newRefRegion = page.getByRole("region", {
    name: "Unterstützung der",
    exact: true,
  })
  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-1_inhalt-1_text-1_ref-1",
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
    .fill("eli/bund/test")

  await page.waitForRequest(/renderings/)

  const saveResponse = page.waitForResponse(
    "/api/v1/norms/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1?",
  )
  await page.getByRole("button", { name: "Speichern" }).click()
  await saveResponse

  await page.reload()

  await expect(newRefRegion.getByRole("combobox", { name: "Typ" })).toHaveText(
    "Zitierung",
  )
  await expect(
    newRefRegion.getByRole("textbox", { name: "ELI mit Zielstelle" }),
  ).toHaveValue("eli/bund/test")

  await newRefRegion.getByRole("button", { name: "Löschen" }).click()
  await page.waitForRequest(/renderings/)

  await expect(newRefRegion).toBeHidden()
  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-5_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
  )

  await page.getByRole("button", { name: "Speichern" }).click()
})

test("should be able to select a mod, add two new ref's and delete one using the delete icon in the preview", async ({
  page,
}) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/2/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
  )

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  const textBoundingBox = await page
    .getByRole("region", { name: "Textbasierte Metadaten" })
    .getByText(/Zur Unterstützung der Implementierung /)
    .boundingBox()

  await page.mouse.move(textBoundingBox!.x + 30, textBoundingBox!.y)
  await page.mouse.down()
  await page.mouse.move(textBoundingBox!.x + 145, textBoundingBox!.y)
  await page.mouse.up()

  const ref1Highlight = page.getByRole("button", {
    name: "Unterstützung der",
    exact: true,
  })
  await expect(ref1Highlight).toBeVisible()

  await page.mouse.move(textBoundingBox!.x + 30, textBoundingBox!.y + 25)
  await page.mouse.down()
  await page.mouse.move(textBoundingBox!.x + 220, textBoundingBox!.y + 25)
  await page.mouse.up()

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

test.describe("Amending Law References Page Error Handling", () => {
  test("Redirect to 404 if XML not found", async ({ page }) => {
    await page.route(
      "**/norms/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1?",
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
      "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/references",
    )

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
