import { test } from "@playwright/test"
import { chromium, Browser, Page } from "playwright"
import { injectAxe, checkA11y } from "axe-playwright"

let browser: Browser
let page: Page

test.describe("basic example a11y test", () => {
  test.beforeAll(async () => {
    browser = await chromium.launch()
    page = await browser.newPage()
    await page.goto("/")
    await injectAxe(page)
  })

  // eslint-disable-next-line playwright/expect-expect
  test("simple accessibility run", async () => {
    await checkA11y(page)
  })

  // eslint-disable-next-line playwright/expect-expect
  test("check a11y for the whole page and axe run options", async () => {
    await checkA11y(page, undefined, {
      axeOptions: {
        runOnly: {
          type: "tag",
          values: ["wcag2a"],
        },
      },
    })
  })

  test.afterAll(async () => {
    await browser.close()
  })
})
