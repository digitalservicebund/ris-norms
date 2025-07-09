import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe("Textkonsolidierung editor", { tag: ["@RISDEV-6833"] }, () => {
  test.describe("loading and saving", () => {
    test.beforeAll(async ({ authenticatedRequest }) => {
      await uploadAmendingLaw(
        authenticatedRequest,
        "aenderungsgesetz-with-amended-norm-expressions",
        frontendTestDataDirectory,
      )
    })

    test("navigates from expression list", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu",
      )

      const zielnormenSection = page.getByRole("region", {
        name: "Zielnormen",
      })

      await zielnormenSection
        .getByRole("button", {
          name: /Vereinsgesetz/,
        })
        .click()

      await zielnormenSection
        .getByRole("button", {
          name: "Textkonsolidierung",
        })
        .click()

      const eliText = "eli/bund/bgbl-1/1964/321/2017-03-16/1/deu"

      const textkonRegion = zielnormenSection.getByRole("region", {
        name: "Textkonsolidierung",
      })

      await textkonRegion.getByRole("link", { name: eliText }).click()

      await expect(page).toHaveURL(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
      )
    })

    test("loads TOC", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
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
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
      )

      const editor = page.getByRole("textbox")
      await expect(editor).toBeVisible()

      await expect(
        page.getByText(
          '<akn:meta GUID="e4e9224c-a2ff-46af-b390-eef666ee6706" eId="meta-n1">',
        ),
      ).toBeVisible()
    })

    test("loads explorer", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
      )

      const explorer = page.getByRole("complementary", {
        name: "Änderungsgesetz",
      })
      await expect(explorer).toBeVisible()
    })

    test("loads TOC and clicking node scrolls to element", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
      )

      const toc = page.getByRole("complementary", { name: "Inhaltsübersicht" })
      const firstTocItem = toc.getByRole("button", { name: "§ 20" })
      await firstTocItem.click()

      await expect(
        page.getByText('eId="art-z20"', { exact: false }),
      ).toBeVisible()
    })

    test("clicking explorer does NOT allow editing", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
      )

      const explorer = page.getByRole("complementary", {
        name: "Änderungsgesetz",
      })

      await explorer
        .getByRole("tree", { name: "Inhaltsverzeichnis" })
        .getByRole("button", { name: /Artikel 1/ })
        .click()

      await expect(
        explorer.getByText("In § 20 Absatz 1 Satz 2 wird"),
      ).toBeVisible()

      await expect(
        explorer.getByRole("button", {
          name: "2. In § 20 Absatz 1 Satz 2 wird die Angabe § 9 Abs. 1 Satz 2, Abs. 2 durch die Wörter § 9 Absatz 1 Satz 2, Absatz 2 oder 3 ersetzt.",
          exact: true,
        }),
      ).not.toHaveClass("selected")
    })

    test("edit text and verifies on reload", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1",
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
  })

  test.describe("404 redirects", () => {
    const expressionUrl =
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-verkuendung-1"

    test("redirects to 404 when verkuendung returns 404", async ({ page }) => {
      await page.route("**/api/v1/verkuendungen/**", (route) =>
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

    test("shows an error if the Verkündung can't be loaded", async ({
      page,
    }) => {
      await page.route("**/api/v1/verkuendungen/**", async (route) => {
        await route.fulfill({ status: 500, json: {} })
      })

      await page.goto(expressionUrl)
      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

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

  test.describe("navigating between expressions", () => {
    test("navigates to next expression version via arrow and updates content", async ({
      page,
    }) => {
      const initialUrl =
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1"
      const expectedNextUrl =
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-05-01/1/deu/regelungstext-verkuendung-1"

      await page.goto(initialUrl)
      const nav = page.getByRole("complementary", {
        name: "Inhaltsübersicht",
      })

      const nextArrow = nav.getByRole("link", {
        name: /Nächste Version/,
      })

      await nextArrow.click()

      await expect(page).toHaveURL(expectedNextUrl)
      await expect(
        page
          .getByLabel("Zeitpunkt der Gültigkeit dieser Fassung")
          .getByText("01.05.2017"),
      ).toBeVisible()

      await expect(nextArrow).toBeDisabled()
    })

    test("navigates to previous expression version via arrow and updates content", async ({
      page,
    }) => {
      const initialUrl =
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-05-01/1/deu/regelungstext-verkuendung-1"
      const expectedNextUrl =
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu/textkonsolidierung/eli/bund/bgbl-1/1964/321/2017-03-16/1/deu/regelungstext-verkuendung-1"
      await page.goto(initialUrl)
      const nav = page.getByRole("complementary", {
        name: "Inhaltsübersicht",
      })

      const previousArrow = nav.getByRole("link", {
        name: /Vorherige Version/,
      })

      await previousArrow.click()

      await expect(page).toHaveURL(expectedNextUrl)
      await expect(
        page
          .getByLabel("Zeitpunkt der Gültigkeit dieser Fassung")
          .getByText("16.03.2017"),
      ).toBeVisible()

      await expect(previousArrow).toBeDisabled()
    })
  })
})
