import { ElementProprietary } from "@/types/proprietary"
import { Locator, Page, expect } from "@playwright/test"
import { test } from "@e2e/utils/test-with-auth"

async function restoreInitialState(page: Page) {
  const dataIn2023: ElementProprietary = {
    artDerNorm: "ÄN",
  }

  await page.request.put(
    "/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/proprietary/hauptteil-1_abschnitt-1_art-6",
    { data: dataIn2023 },
  )
}

test.describe("navigate to page", { tag: ["@RISDEV-6266"] }, () => {
  test("shows an error when an element page could not be loaded", async ({
    page,
  }) => {
    // Given
    await page.route(
      /elements\/hauptteil-1_abschnitt-1_art-6/,
      async (route) => {
        await route.abort()
      },
    )

    await page.goto(
      "/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata/element/hauptteil-1_abschnitt-1_art-6",
    )

    // Then
    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })

  test("navigates between elements", async ({ page }) => {
    // Given
    await page.goto(
      "/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata/element/hauptteil-1_abschnitt-1_art-2",
    )

    const heading = page.getByRole("heading", { level: 2 })
    await expect(heading).toHaveText("§ 2 Verfassungsschutzbehörden")

    // When
    await page
      .getByRole("link", {
        name: "Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      })
      .click()

    // Then
    await expect(heading).toHaveText(
      "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
    )
  })
})

test.describe("preview", { tag: ["@RISDEV-6266"] }, () => {
  test("displays the title and preview", async ({ page }) => {
    // Given
    await page.goto(
      "/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata/element/hauptteil-1_abschnitt-1_art-6",
    )

    const preview = page.getByRole("region", { name: "Vorschau" })

    // Then
    await expect(
      page.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",

        level: 2,
      }),
    ).toBeVisible()

    await expect(
      preview.getByRole("heading", {
        name: "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
        level: 3,
      }),
    ).toBeVisible()
  })

  test("shows an error when the preview could not be loaded", async ({
    page,
  }) => {
    // Given
    await page.route(
      /elements\/hauptteil-1_abschnitt-1_art-6/,
      async (request) => {
        await request.abort()
      },
    )

    await page.goto(
      "/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata/element/hauptteil-1_abschnitt-1_art-6",
    )

    // Then
    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    // Cleanup
    await page.unrouteAll()
  })
})

test.describe("metadata view", { tag: ["@RISDEV-6266"] }, () => {
  let sharedPage: Page

  async function saveMetadata() {
    await sharedPage.getByRole("button", { name: "Speichern" }).click()
    await sharedPage.waitForResponse(/proprietary/)
  }

  async function mockPutResponse(data: ElementProprietary) {
    await sharedPage.route(
      /\/proprietary\/hauptteil-1_abschnitt-1_art-6/,
      async (route) => {
        if (route.request().method() === "PUT") {
          const response = await route.fetch()
          const body = await response.json()

          await route.fulfill({
            response,
            body: JSON.stringify({ ...body, ...data }),
          })
        } else await route.continue()
      },
    )
  }

  test.beforeAll(async ({ browser }) => {
    sharedPage = await browser.newPage()
    await sharedPage.goto(
      "/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/metadata/element/hauptteil-1_abschnitt-1_art-6",
    )
    await restoreInitialState(sharedPage)
  })

  test.afterAll(async () => {
    await restoreInitialState(sharedPage)
  })

  test.describe("Art der Norm", () => {
    let artSnRadio: Locator
    let artAnRadio: Locator
    let artUnRadio: Locator

    test.beforeAll(() => {
      // Given
      artSnRadio = sharedPage.getByRole("radio", {
        name: "SN - Stammnorm",
      })
      artAnRadio = sharedPage.getByRole("radio", {
        name: "ÄN - Änderungsnorm",
      })
      artUnRadio = sharedPage.getByRole("radio", {
        name: "ÜN - Übergangsnorm",
      })
    })

    test("saves changes", async () => {
      // When
      await artUnRadio.check()
      await saveMetadata()
      await sharedPage.reload()

      // Then
      await expect(artSnRadio).not.toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).toBeChecked()
    })

    test("is updated with backend state after saving", async () => {
      // Given
      await mockPutResponse({ artDerNorm: "SN" })
      await expect(artSnRadio).not.toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).toBeChecked()

      // When
      await saveMetadata()

      // Then
      await expect(artSnRadio).toBeChecked()
      await expect(artAnRadio).not.toBeChecked()
      await expect(artUnRadio).not.toBeChecked()

      // Cleanup
      await sharedPage.unrouteAll()
    })
  })
})
