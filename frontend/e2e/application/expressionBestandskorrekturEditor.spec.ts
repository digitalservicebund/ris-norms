import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe("Bestandskorrektur editor", { tag: ["@RISDEV-8359"] }, () => {
  test.describe("loading and saving", () => {
    test.beforeAll(async ({ authenticatedRequest }) => {
      await uploadAmendingLaw(
        authenticatedRequest,
        "aenderungsgesetz-with-amended-norm-expressions",
        frontendTestDataDirectory,
      )
      await uploadAmendingLaw(
        authenticatedRequest,
        "vereinsgesetz-2222-02-02-gegenstandlos",
        frontendTestDataDirectory,
      )
    })

    test("loads TOC", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/textbearbeitung",
      )

      const nav = page.getByRole("complementary", { name: "Inhaltsübersicht" })
      await expect(nav.getByRole("button")).not.toHaveCount(0)

      const expectedButtons = ["§ 20", "Artikel 34 Inkrafttreten"]

      for (const label of expectedButtons) {
        await expect(nav.getByRole("button", { name: label })).toBeVisible()
      }
    })

    test("loads editor", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/textbearbeitung",
      )

      const editor = page.getByRole("textbox")
      await expect(editor).toBeVisible()

      await expect(
        page.getByText(
          '<akn:meta GUID="e4e9224c-a2ff-46af-b390-eef666ee6706" eId="meta-n1">',
        ),
      ).toBeVisible()
    })

    test("loads TOC and clicking node scrolls to element", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/textbearbeitung",
      )

      const toc = page.getByRole("complementary", { name: "Inhaltsübersicht" })
      const firstTocItem = toc.getByRole("button", { name: "§ 20" })
      await firstTocItem.click()

      await expect(
        page.getByText('eId="art-z20"', { exact: false }),
      ).toBeVisible()
    })

    test("edit text and verifies on reload", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/textbearbeitung",
      )

      const editor = page.getByRole("textbox")
      await editor.focus()
      await editor.press("ArrowDown")
      await editor.press("End")
      await editor.pressSequentially(" TESTING")

      await page.getByRole("button", { name: "Speichern" }).click()
      await expect(page.getByRole("alert")).toHaveText("Gespeichert!")

      await page.reload()
      const updatedEditor = page.getByRole("textbox")
      await expect(updatedEditor).toContainText("TESTING")
    })

    test("xml editing locked when norm is gegenstandslos", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1964/s593/2222-02-02/1/deu/textbearbeitung",
      )

      const message = page.getByText(
        "Diese Expression ist gegenstandslos und deshalb nicht bearbeitbar.",
      )
      await expect(message).toBeVisible()

      const save = page.getByText("Speichern")
      await expect(save).toBeVisible()

      const editor = page.getByRole("textbox")
      await expect(editor).toBeHidden()
    })
  })

  test.describe("404 redirects", () => {
    const expressionUrl =
      "./datenbank/eli/bund/bgbl-1/1964/s593/2222-02-02/1/deu/textbearbeitung"

    test("shows an error if the Xml can't be loaded", async ({ page }) => {
      await page.route("**/api/v1/norms/**", async (route) => {
        await route.fulfill({ status: 500, json: {} })
      })

      await page.goto(expressionUrl)
      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("redirects to 404 when XML returns 404", async ({ page }) => {
      await page.route("**/api/v1/norms/**", (route) =>
        route.fulfill({
          status: 404,
          body: JSON.stringify({
            type: "/errors/not-found",
            title: "Not found",
            status: 404,
          }),
        }),
      )

      await page.goto(expressionUrl)

      await expect(page.getByText("404 - Seite nicht gefunden")).toBeVisible()
    })

    test("redirects to 404 when TOC returns 404", async ({ page }) => {
      await page.route("**/api/v1/norms/**/toc", (route) =>
        route.fulfill({
          status: 404,
          body: JSON.stringify({
            title: "Not found",
            status: 404,
          }),
        }),
      )

      await page.goto(expressionUrl)
      await expect(page.getByText("404 - Seite nicht gefunden")).toBeVisible()
    })
  })
})
