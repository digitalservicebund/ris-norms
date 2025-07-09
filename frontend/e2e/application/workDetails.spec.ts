import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "WorkDetail view with list of expressions and their details",
  { tag: ["@RISDEV-8332"] },
  () => {
    test("navigates from Datenbank to WorkDetail view", async ({ page }) => {
      await page.goto("./datenbank")

      await expect(
        page.getByRole("heading", { name: "Datenbank" }),
      ).toBeVisible()

      await page.getByRole("link", { name: "eli/bund/bgbl-1/1000/s1" }).click()

      await expect(page).toHaveURL("/app/datenbank/eli/bund/bgbl-1/1000/s1")

      await expect(
        page.getByRole("banner").getByText("Mock Formatting Test"),
      ).toBeVisible()
    })

    test("shows expressions list with correct items", async ({ page }) => {
      await page.goto("./datenbank/eli/bund/bgbl-1/1000/s1")

      await expect(
        page.getByRole("heading", { name: "Expressionen" }),
      ).toBeVisible()

      const expressionsSection = page.getByRole("tree", {
        name: "Expressionen",
      })
      const expressionItems = expressionsSection.getByRole("treeitem")
      await expect(expressionItems).toHaveCount(1)

      await expect(page.getByText("01.01.1000")).toBeVisible()

      await expect(page.getByRole("link", { name: "01.01.1000" })).toBeVisible()
    })

    test("navigates to expression detail view when clicking on expression", async ({
      page,
    }) => {
      await page.goto("./datenbank/eli/bund/bgbl-1/1000/s1")

      await page.getByRole("link", { name: "01.01.1000" }).click()

      await expect(page).toHaveURL(
        "/app/datenbank/eli/bund/bgbl-1/1000/s1/expression/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1",
      )

      await expect(
        page.getByRole("banner").getByText("01.01.1000"),
      ).toBeVisible()
    })

    test("shows table of contents with correct items", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1000/s1/expression/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1",
      )

      await expect(
        page.getByRole("heading", { name: "Inhaltsübersicht" }),
      ).toBeVisible()

      const tableOfContentsSection = page.getByRole("tree", {
        name: "Inhaltsübersicht",
      })
      const tableOfContentsItems = tableOfContentsSection.getByRole("treeitem")
      await expect(tableOfContentsItems).toHaveCount(4)

      const expectedTableOfContentsItems = [
        "Basic HTML Elements",
        "Lists",
        "Tables",
        "Modifications",
      ]

      for (const item of expectedTableOfContentsItems) {
        await expect(tableOfContentsSection.getByText(item)).toBeVisible()
      }
    })

    test("shows preview section with correct content", async ({ page }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1000/s1/expression/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1",
      )

      await expect(
        page.getByRole("heading", { name: "Vorschau" }),
      ).toBeVisible()

      await expect(
        page.getByRole("heading", { name: "Mock Formatting Test" }),
      ).toBeVisible()
    })

    test("navigates to metadata view when clicking Metadaten button", async ({
      page,
    }) => {
      await page.goto(
        "./datenbank/eli/bund/bgbl-1/1000/s1/expression/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1",
      )

      await expect(
        page.getByRole("button", { name: "Metadaten" }),
      ).toBeVisible()

      await page.getByRole("button", { name: "Metadaten" }).click()

      await expect(page).toHaveURL(
        "/app/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1/metadata",
      )

      await expect(page.getByRole("heading", { name: "Rahmen" })).toBeVisible()
    })
  },
)
