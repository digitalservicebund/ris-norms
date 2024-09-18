import { expect, test } from "@playwright/test"
import { normWithModsNotInSeedXml } from "./testData/normWithModsNotInSeedXml"
import { normWithModsSchematronInvalidXml } from "./testData/normWithModsSchematronInvalidXml"
import { normWithModsXml } from "./testData/normWithModsXml"
import { normWithModsXsdInvalidXml } from "./testData/normWithModsXsdInvalidXml"

test("navigates to the upload page", async ({ page }) => {
  await page.goto("/amending-laws")

  await page
    .getByRole("button", { name: "Verkündung manuell hinzufügen" })
    .click()

  await expect(page.getByRole("heading", { name: "Upload" })).toBeVisible()
})

test("uploads a new norm successfully and redirects to the new norm", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(normWithModsNotInSeedXml),
      mimeType: "text/xml",
      name: "amendingLaw",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await page.waitForURL(
    "/amending-laws/eli/bund/bgbl-1/2017/s4711/2017-03-15/1/deu/regelungstext-1",
  )

  await expect(
    page.getByText("Verkündung erfolgreich hochgeladen"),
  ).toBeVisible()
})

test("shows a confirmation dialog if the uploaded norm already exists", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(normWithModsXml),
      mimeType: "text/xml",
      name: "amendingLaw",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await expect(page.getByText("Verkündung existiert bereits")).toBeVisible()
})

test("Closes the confirmation dialog for a forced upload when user chooses not to overwrite", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(normWithModsXml),
      mimeType: "text/xml",
      name: "amendingLaw",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await expect(page.getByText("Verkündung existiert bereits")).toBeVisible()
  await page.getByRole("button", { name: "Abbrechen" }).click()
  await expect(
    page.getByText("Verkündung erfolgreich hochgeladen"),
  ).toBeHidden()
})

test("shows an error if the uploaded norm is not an XML file", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from("Text file"),
      mimeType: "text/plain",
      name: "amendingLaw",
    },
  ])

  await expect(page.getByText("amendingLaw ist keine XML-Datei.")).toBeVisible()
})

test("shows an error if the uploaded xml is not a LDML.de file", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  const simpleXml = `
    <root>
      <child>Sample content</child>
    </root>
  `

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(simpleXml),
      mimeType: "text/xml",
      name: "test.xml",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await expect(
    page.getByText('Die XML-Datei "test.xml" ist keine LDML.de-Datei.'),
  ).toBeVisible()
})

test("shows validation errors if the uploaded norm is not an xsd-valid LDML document", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(normWithModsXsdInvalidXml),
      mimeType: "text/xml",
      name: "amendingLaw",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await expect(page.getByText("Invalide LDML.de-Datei")).toBeVisible()

  await expect(page.getByLabel("Fehlerliste")).toContainText(
    /\/errors\/ldml-de-not-valid\//,
  )
})

test("shows validation errors if the uploaded norm is not a schematron-valid LDML document", async ({
  page,
}) => {
  await page.goto("/amending-laws/upload")

  await page.locator("input[type=file]").setInputFiles([
    {
      buffer: Buffer.from(normWithModsSchematronInvalidXml),
      mimeType: "text/xml",
      name: "amendingLaw",
    },
  ])

  await page.getByRole("button", { name: "Hochladen" }).click()

  await expect(page.getByText("Invalide LDML.de-Datei")).toBeVisible()

  await expect(page.getByLabel("Fehlerliste")).toContainText(
    /\/errors\/ldml-de-not-schematron-valid\//,
  )
})
