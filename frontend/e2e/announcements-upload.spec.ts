/* eslint-disable playwright/no-raw-locators */
import { samplesDirectory } from "@e2e/utils/samples-directory"
import { test } from "@e2e/utils/test-with-auth"
import { uploadAmendingLaw } from "@e2e/utils/upload-with-force"
import { expect } from "@playwright/test"
import fs from "fs"
import path from "node:path"

test(
  "navigates to the upload page",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
    await page.goto("/amending-laws")

    await page
      .getByRole("button", { name: "Verkündung manuell hinzufügen" })
      .click()

    await expect(page.getByRole("heading", { name: "Upload" })).toBeVisible()
  },
)

test(
  "uploads a new norm successfully and redirects to the new norm",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_5_upload_01/aenderungsgesetz.xml",
          ),
        ),
        mimeType: "text/xml",
        name: "amendingLaw",
      },
    ])

    await page.getByRole("button", { name: "Hochladen" }).click()

    await page.waitForURL(
      "/amending-laws/eli/bund/bgbl-1/1000/5/1000-01-05/1/deu/regelungstext-1",
    )

    await expect(
      page.getByText("Verkündung erfolgreich hochgeladen"),
    ).toBeVisible()
  },
)

test(
  "shows a confirmation dialog if the uploaded norm already exists",
  { tag: ["@RISDEV-4775"] },
  async ({ page, authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1000_6_upload_02/aenderungsgesetz-1.xml",
    )

    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_6_upload_02/aenderungsgesetz-1.xml",
          ),
        ),
        mimeType: "text/xml",
        name: "amendingLaw",
      },
    ])

    await page.getByRole("button", { name: "Hochladen" }).click()

    await expect(page.getByText("Verkündung existiert bereits")).toBeVisible()
  },
)

test(
  "confirming to overwrite an existing norm overwrites it",
  { tag: ["@RISDEV-4775"] },
  async ({ page, authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1000_6_upload_02/aenderungsgesetz-1.xml",
    )

    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_6_upload_02/aenderungsgesetz-2.xml",
          ),
        ),
        mimeType: "text/xml",
        name: "amendingLaw",
      },
    ])

    await page.getByRole("button", { name: "Hochladen" }).click()

    await expect(page.getByText("Verkündung existiert bereits")).toBeVisible()

    await page.getByRole("button", { name: "Überschreiben" }).click()

    await expect(
      page.getByText("Verkündung erfolgreich hochgeladen"),
    ).toBeVisible()

    await expect(page.getByText("Upload Test 2/2")).toBeVisible()
  },
)

test(
  "closes the confirmation dialog for a forced upload when user chooses not to overwrite",
  { tag: ["@RISDEV-4775"] },
  async ({ page, authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1000_6_upload_02/aenderungsgesetz-1.xml",
    )

    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_6_upload_02/aenderungsgesetz-2.xml",
          ),
        ),
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
  },
)

test(
  "shows an error if the uploaded norm is not an XML file",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: Buffer.from("Text file"),
        mimeType: "text/plain",
        name: "amendingLaw",
      },
    ])

    await expect(
      page.getByText("amendingLaw ist keine XML-Datei."),
    ).toBeVisible()
  },
)

test(
  "shows an error if the uploaded xml is not a LDML.de file",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
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
  },
)

test(
  "shows validation errors if the uploaded norm is not an XSD-valid LDML document",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_3_invalid_xsd_01/regelungstext-1.xml",
          ),
        ),
        mimeType: "text/xml",
        name: "amendingLaw",
      },
    ])

    await page.getByRole("button", { name: "Hochladen" }).click()

    await expect(page.getByText("Invalide LDML.de-Datei")).toBeVisible()

    await expect(page.getByLabel("Fehlerliste")).toContainText(
      /\/errors\/ldml-de-not-valid\//,
    )
  },
)

test(
  "shows validation errors if the uploaded norm is not a Schematron-valid LDML document",
  { tag: ["@RISDEV-4771"] },
  async ({ page }) => {
    await page.goto("/amending-laws/upload")

    await page.locator("input[type=file]").setInputFiles([
      {
        buffer: fs.readFileSync(
          path.resolve(
            samplesDirectory,
            "bgbl-1_1000_4_invalid_schematron_01/regelungstext-1.xml",
          ),
        ),
        mimeType: "text/xml",
        name: "amendingLaw",
      },
    ])

    await page.getByRole("button", { name: "Hochladen" }).click()

    await expect(page.getByText("Invalide LDML.de-Datei")).toBeVisible()

    await expect(page.getByLabel("Fehlerliste")).toContainText(
      /\/errors\/ldml-de-not-schematron-valid\//,
    )
  },
)
