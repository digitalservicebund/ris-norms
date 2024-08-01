import { test, expect } from "@playwright/test"

// TODO: (Malte Laukötter, 2024-07-31) add when a link to the page exists
/*test("navigate to amending law overview", async ({ page }) => {
  await page.goto("/amending-laws")
  await page.getByRole("link", { name: "BGBl. I 2023 Nr. 413" }).click()

  await expect(page).toHaveURL(
    "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
  )
})*/

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

  const textBoundingBox = await page
    .getByRole("region", { name: "Textbasierte Metadaten" })
    .getByText(/Zur Unterstützung der Implementierung /)
    .boundingBox()

  await page.mouse.move(textBoundingBox!.x + 30, textBoundingBox!.y)
  await page.mouse.down()
  await page.mouse.move(textBoundingBox!.x + 145, textBoundingBox!.y)
  await page.mouse.up()

  const newRefRegion = page.getByRole("region", { name: "Unterstützung der" })

  await expect(newRefRegion).toBeVisible()

  await newRefRegion
    .getByRole("combobox", { name: "Typ" })
    .selectOption("Zitierung")
  await newRefRegion
    .getByRole("textbox", { name: "ELI mit Zielstelle" })
    .fill("eli/bund/test")

  await page.waitForRequest(/renderings/)

  await page.getByRole("button", { name: "Speichern" }).click()

  await page.reload()

  await page
    .getByRole("button", { name: /Absatz 2 bis Absatz 3 wird ersetzt durch/ })
    .click()

  await page.getByRole("button", { name: "Unterstützung der" }).click()

  await expect(newRefRegion.getByRole("combobox", { name: "Typ" })).toHaveValue(
    "zitierung",
  )
  await expect(
    newRefRegion.getByRole("textbox", { name: "ELI mit Zielstelle" }),
  ).toHaveValue("eli/bund/test")

  await newRefRegion.getByRole("button", { name: "Löschen" }).click()
  await page.waitForRequest(/renderings/)

  await expect(newRefRegion).toBeHidden()

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
    page.getByRole("region", { name: "Unterstützung der" }),
  ).toBeVisible()
  await expect(
    page.getByRole("region", { name: "zentrale Koordinierungsstelle" }),
  ).toBeVisible()

  await ref1Highlight.click()
  await ref1Highlight.getByRole("button", { name: "Löschen" }).click()

  await expect(
    page.getByRole("region", { name: "Unterstützung der" }),
  ).toBeHidden()
  await expect(
    page.getByRole("region", { name: "zentrale Koordinierungsstelle" }),
  ).toBeVisible()
  await expect(ref1Highlight).toBeHidden()
  await expect(ref2Highlight).toBeVisible()
})
