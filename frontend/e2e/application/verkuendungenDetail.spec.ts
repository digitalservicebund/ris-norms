import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"
import { uploadAmendingLaw } from "@e2e/utils/uploadWithForce"
import { frontendTestDataDirectory } from "@e2e/utils/dataDirectories"

test.describe("navigate and test content", { tag: ["@RISDEV-6942"] }, () => {
  test("navigate to Verkuendungen detail page and should display details correctly", async ({
    page,
  }) => {
    await page.route(
      "**/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      async (route) => {
        const response = await route.fetch()
        const body = await response.json()
        body.importedAt = "2025-03-19T22:52:00Z"
        await route.fulfill({
          status: response.status(),
          body: JSON.stringify(body),
        })
      },
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
    )

    await expect(
      page
        .getByRole("heading", {
          name: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
        })
        .first(),
    ).toBeVisible()

    const verkuendungSection = page.getByRole("region", {
      name: "Verkündungs-Details",
    })

    await expect(
      verkuendungSection.getByText("Veröffentlichungsdatum"),
    ).toBeVisible()
    await expect(verkuendungSection.getByText("15.03.2017")).toBeVisible()

    await expect(
      verkuendungSection.getByText("Ausfertigungsdatum"),
    ).toBeVisible()
    await expect(verkuendungSection.getByText("01.01.1900")).toBeVisible()
    await expect(
      verkuendungSection.getByText("Datenlieferungsdatum"),
    ).toBeVisible()
    await expect(
      verkuendungSection.getByText("19.03.2025, 23:52"),
    ).toBeVisible()
    await expect(verkuendungSection.getByText("FNA")).toBeVisible()
    await expect(verkuendungSection.getByText("nicht-vorhanden")).toBeVisible()
  })

  test("should redirect to 404 page when verkuendung returns 404", async ({
    page,
  }) => {
    await page.route(
      "**/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
      (route) =>
        route.fulfill({
          status: 404,
          body: JSON.stringify({
            type: "/errors/not-found",
            status: 404,
            title: "Not found",
          }),
        }),
    )

    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
    )

    // Check if redirected to 404 page
    await expect(page.getByText("404 - Seite nicht gefunden")).toBeVisible()
  })

  test("should display error when loading amending law HTML fails", async ({
    page,
  }) => {
    await page.route(
      "**/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu?",
      (route) =>
        route.fulfill({
          status: 500,
          body: JSON.stringify({
            type: "/errors/internal-server-error",
            status: 500,
            title: "Internal Server Error",
            detail: "Server error",
          }),
        }),
    )

    // Navigate to the verkuendung
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
    )

    await expect(
      page.getByText("Ein unerwarteter Fehler ist aufgetreten"),
    ).toBeVisible()
  })

  test("user can navigate to temporal data page", async ({ page }) => {
    await page.goto(
      "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
    )

    await page.getByText("Geltungszeitregeln anlegen").click()
    await expect(page).toHaveURL(
      "/app/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/zeitgrenzen",
    )
  })
})

test.describe("shows Zielnormen", { tag: ["@RISDEV-6941"] }, () => {
  test("should show fallback if no Zielnormen available", async ({ page }) => {
    await page.goto("./verkuendungen/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu")

    const zielnormenSection = page.getByRole("region", {
      name: "Zielnormen",
    })
    await expect(zielnormenSection).toBeVisible()
    await expect(
      zielnormenSection.getByText("Es sind noch keine Zielnormen vorhanden"),
    ).toBeVisible()
  })

  test.describe("Zielnormen with content", () => {
    test.beforeAll(async ({ authenticatedRequest }) => {
      await uploadAmendingLaw(
        authenticatedRequest,
        "aenderungsgesetz-with-amended-norm-expressions",
        frontendTestDataDirectory,
      )
    })

    test("should show Zielnormen", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu",
      )

      const zielnormenSection = page.getByRole("region", {
        name: "Zielnormen",
      })
      await expect(zielnormenSection).toBeVisible()
      await expect(zielnormenSection.getByText(/Vereinsgesetz/)).toBeVisible()
      await expect(zielnormenSection.getByText("FNA 754-28-1")).toBeVisible()
    })

    test("should expand the Zielnorm and Textkonsolidierung to show the expression with ELI and date", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu",
      )

      const zielnormenSection = page.getByRole("region", {
        name: "Zielnormen",
      })

      const zielnormButton = zielnormenSection.getByRole("button", {
        name: /Vereinsgesetz/,
      })

      await zielnormButton.click()

      const textkonsolidierungButton = zielnormenSection.getByRole("button", {
        name: "Textkonsolidierung",
      })
      await expect(textkonsolidierungButton).toBeVisible()
      await textkonsolidierungButton.click()

      const textkonTable = zielnormenSection
        .getByRole("region", { name: "Textkonsolidierung" })
        .getByRole("link", {
          name: "eli/bund/bgbl-1/1964/321/2017-03-16/1/deu",
        })

      await expect(textkonTable).toBeVisible()
    })

    test("should expand the Zielnorm and Metadaten to show the expression with ELI and date", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/123/2017-03-15/1/deu",
      )

      const zielnormenSection = page.getByRole("region", {
        name: "Zielnormen",
      })

      const zielnormButton = zielnormenSection.getByRole("button", {
        name: /Vereinsgesetz/,
      })

      await zielnormButton.click()

      const metadatenButton = zielnormenSection.getByRole("button", {
        name: "Metadaten",
      })
      await expect(metadatenButton).toBeVisible()
      await metadatenButton.click()

      const metadataenTable = zielnormenSection
        .getByRole("region", { name: "Metadaten" })
        .getByRole("link", {
          name: "eli/bund/bgbl-1/1964/321/2017-03-16/1/deu",
        })

      await expect(metadataenTable).toBeVisible()
    })
  })
})
