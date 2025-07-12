import { test } from "@e2e/utils/testWithAuth"
import { expect, type Page } from "@playwright/test"

test.describe(
  "WorkDetail view with list of expressions and their details",
  { tag: ["@RISDEV-8332"] },
  () => {
    const testData = {
      workEli: "eli/bund/bgbl-1/1001/1",
      expressionEli: "eli/bund/bgbl-1/1001/1/1001-01-01/1/deu",
      expressionDate: "01.01.1001",
      workTitle: "Fiktives Beispielgesetz",
      tocItems: ["Anwendungsbereich von Beispielen", "Inkrafttreten"],
      selectedTocItem: "Inkrafttreten",
      selectedTocEid: "art-z2",
    }
    const helpers = {
      async navigateToWorkDetail(page: Page) {
        await page.goto("./datenbank")
        await page.getByRole("link", { name: testData.workEli }).click()
      },

      async navigateToExpressionDetail(page: Page) {
        await page.goto(`./datenbank/${testData.workEli}`)
        await page.getByRole("link", { name: testData.expressionDate }).click()
      },

      getExpressionTreeItem(page: Page) {
        const expressionsSection = page.getByRole("tree", {
          name: "Expressionen",
        })
        return expressionsSection.getByRole("treeitem", {
          name: testData.expressionDate,
        })
      },

      getTocSection(page: Page) {
        return page.getByRole("tree", { name: "Inhaltsübersicht" })
      },

      async selectTocItem(
        page: Page,
        tocSection: ReturnType<typeof page.getByRole>,
        itemName: string = testData.selectedTocItem,
      ) {
        const tocItem = tocSection
          .getByRole("treeitem")
          .filter({ hasText: itemName })
          .first()
        await tocItem.click()
        return tocItem
      },

      getPreviewSection(page: Page) {
        return page.getByRole("region", { name: "Vorschau" })
      },
    }

    test.describe("WorkDetail view", () => {
      test("navigates from Datenbank to WorkDetail view", async ({ page }) => {
        await helpers.navigateToWorkDetail(page)
        await expect(page).toHaveURL(`/app/datenbank/${testData.workEli}`)
        await expect(
          page.getByRole("banner").getByText(testData.workTitle),
        ).toBeVisible()
      })

      test("shows expressions list with correct items", async ({ page }) => {
        await page.goto(`./datenbank/${testData.workEli}`)

        await expect(
          page.getByRole("heading", { name: "Expressionen" }),
        ).toBeVisible()
        const expressionsSection = page.getByRole("tree", {
          name: "Expressionen",
        })
        const expressionItems = expressionsSection.getByRole("treeitem")
        await expect(expressionItems).toHaveCount(1)
        await expect(
          page.getByRole("link", { name: testData.expressionDate }),
        ).toBeVisible()
      })
    })

    test.describe("WorkExpressionDetail view", () => {
      test("navigates to expression detail view when clicking on expression", async ({
        page,
      }) => {
        await helpers.navigateToExpressionDetail(page)

        await expect(page).toHaveURL(`/app/datenbank/${testData.expressionEli}`)
        await expect(
          page.getByRole("banner").getByText(testData.expressionDate),
        ).toBeVisible()

        const selectedExpression = helpers.getExpressionTreeItem(page)
        await expect(selectedExpression).toHaveAttribute("aria-checked", "true")

        await page.reload()
        await expect(selectedExpression).toHaveAttribute("aria-checked", "true")
      })

      test("shows table of contents with correct items", async ({ page }) => {
        await page.goto(`./datenbank/${testData.expressionEli}`)

        await expect(
          page.getByRole("heading", { name: "Inhaltsübersicht" }),
        ).toBeVisible()
        const tocSection = helpers.getTocSection(page)
        const tocItems = tocSection.getByRole("treeitem")
        await expect(tocItems).toHaveCount(testData.tocItems.length)

        for (const item of testData.tocItems) {
          await expect(tocSection.getByText(item)).toBeVisible()
        }
      })

      test("shows preview section with correct content", async ({ page }) => {
        await page.goto(`./datenbank/${testData.expressionEli}`)

        const previewSection = helpers.getPreviewSection(page)
        await expect(previewSection).toBeVisible()
        await expect(
          previewSection.getByRole("heading", { name: "Vorschau" }),
        ).toBeVisible()
        await expect(
          previewSection.getByRole("heading", { name: testData.tocItems[0] }),
        ).toBeVisible()
      })

      test("selects TOC item and scrolls to corresponding section", async ({
        page,
      }) => {
        await page.goto(`./datenbank/${testData.expressionEli}`)

        const tocSection = helpers.getTocSection(page)
        const tocItem = await helpers.selectTocItem(page, tocSection)

        await expect(tocItem).toHaveAttribute("aria-checked", "true")
        expect(page.url()).toContain(testData.selectedTocEid)

        const previewSection = helpers.getPreviewSection(page)
        await expect(
          previewSection.getByRole("heading", {
            name: testData.selectedTocItem,
          }),
        ).toBeInViewport()
      })

      test("maintains TOC selection and scroll position after page refresh", async ({
        page,
      }) => {
        await page.goto(`./datenbank/${testData.expressionEli}`)

        const tocSection = helpers.getTocSection(page)
        const tocItem = await helpers.selectTocItem(page, tocSection)

        await expect(tocItem).toHaveAttribute("aria-checked", "true")
        expect(page.url()).toContain(testData.selectedTocEid)

        const previewSection = helpers.getPreviewSection(page)
        await expect(
          previewSection.getByRole("heading", {
            name: testData.selectedTocItem,
          }),
        ).toBeInViewport()

        await page.reload()

        await expect(tocItem).toHaveAttribute("aria-checked", "true")
        expect(page.url()).toContain(testData.selectedTocEid)
        await expect(
          previewSection.getByRole("heading", {
            name: testData.selectedTocItem,
          }),
        ).toBeInViewport()
      })

      test("navigates to metadata view when clicking Metadaten button", async ({
        page,
      }) => {
        await page.goto("./datenbank/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu")

        await expect(
          page.getByRole("button", { name: "Metadaten" }),
        ).toBeVisible()
        await page.getByRole("button", { name: "Metadaten" }).click()

        await expect(page).toHaveURL(
          "/app/eli/bund/bgbl-1/1000/s1/1000-01-01/1/deu/regelungstext-verkuendung-1/metadata",
        )
        await expect(
          page.getByRole("heading", { name: "Rahmen" }),
        ).toBeVisible()
      })
    })
  },
)
