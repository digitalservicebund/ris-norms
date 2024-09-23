import { test, expect } from "@playwright/test"

test("navigate to amending law references page without selected mods", async ({
  page,
}) => {
  await page.goto("/amending-laws")
  await page.getByRole("link", { name: "BGBl. I 1002 Nr. 10" }).click()
  await page.getByRole("link", { name: "Betroffene Normenkomplexe" }).click()
  await page.getByRole("link", { name: "Inhaltliche Auszeichnungen" }).click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
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
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
  )

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1",
  )

  await expect(
    page.getByText(
      "Für die ausgewählte Textpassage sind noch keine Verweise dokumentiert. Markieren Sie links Text, um neue Verweise hinzuzufügen.",
    ),
  ).toBeVisible()
})

test("see breadcrumb", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
  )

  await expect(
    page.getByRole("banner").getByText("Textbasierte Metadaten"),
  ).toBeVisible()
})

test("see rendered law text", async ({ page }) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
  )

  const article = page.getByRole("article")
  await expect(article).toBeVisible()
  await expect(article).toContainText("vom 1. Januar 1002")
})

test("should be able to select a mod, add a new ref and edit it's refersTo and href and delete it using the delete icon in the table", async ({
  page,
}) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
  )

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1",
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

  await expect(newRefRegion).toBeVisible()
  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_inhalt-1_text-1_ref-1",
  )

  await newRefRegion
    .getByRole("combobox", { name: "Typ" })
    .selectOption("Zitierung")
  await newRefRegion
    .getByRole("textbox", { name: "ELI mit Zielstelle" })
    .fill("eli/bund/test")

  await page.waitForRequest(/renderings/)

  await page.getByRole("button", { name: "Speichern" }).click()

  await page.reload()

  await expect(newRefRegion.getByRole("combobox", { name: "Typ" })).toHaveValue(
    "zitierung",
  )
  await expect(
    newRefRegion.getByRole("textbox", { name: "ELI mit Zielstelle" }),
  ).toHaveValue("eli/bund/test")

  await newRefRegion.getByRole("button", { name: "Löschen" }).click()
  await page.waitForRequest(/renderings/)

  await expect(newRefRegion).toBeHidden()
  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references/hauptteil-1_art-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1",
  )

  await page.getByRole("button", { name: "Speichern" }).click()
})

test("should be able to select a mod, add two new ref's and delete one using the delete icon in the preview", async ({
  page,
}) => {
  await page.goto(
    "/amending-laws/eli/bund/bgbl-1/1002/10/1002-01-10/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1002/1/1002-01-10/1/deu/regelungstext-1/references",
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
