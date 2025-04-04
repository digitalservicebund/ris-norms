import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe(
  "Announcement details view",
  {
    tag: ["@RISDEV-6942"],
  },
  () => {
    test("navigate to announcement details page and should display details correctly", async ({
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
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await expect(
        page
          .getByRole("heading", {
            name: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
          })
          .first(),
      ).toBeVisible()

      const announcementSection = page.getByRole("region", {
        name: "Verkündungs-Details",
      })

      await expect(
        announcementSection.getByText("Veröffentlichungsdatum"),
      ).toBeVisible()
      await expect(announcementSection.getByText("15.03.2017")).toBeVisible()

      await expect(
        announcementSection.getByText("Ausfertigungsdatum"),
      ).toBeVisible()
      await expect(announcementSection.getByText("01.01.1900")).toBeVisible()
      await expect(
        announcementSection.getByText("Datenlieferungsdatum"),
      ).toBeVisible()
      await expect(
        announcementSection.getByText("19.03.2025, 23:52"),
      ).toBeVisible()
      await expect(announcementSection.getByText("FNA")).toBeVisible()
      await expect(
        announcementSection.getByText("nicht-vorhanden"),
      ).toBeVisible()
    })

    test("should redirect to 404 page when announcement returns 404", async ({
      page,
    }) => {
      await page.route(
        "**/api/v1/verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        (route) => route.fulfill({ status: 404 }),
      )

      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      // Check if redirected to 404 page
      await expect(page.getByText("404 - Seite nicht gefunden")).toBeVisible()
    })

    test("should display error when loading amending law HTML fails", async ({
      page,
    }) => {
      await page.route(
        "**/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?",
        (route) => route.fulfill({ status: 500, body: "Server error" }),
      )

      // Navigate to the announcement
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("user can navigate to temporal data page", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await page.getByText("Geltungszeitregeln anlegen").click()
      await expect(page).toHaveURL(/.*\/temporal-data/)
    })

    test("should show fallback if no Zielnormen available", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )

      const zielnormenSection = page.getByRole("region", { name: "Zielnormen" })
      await expect(zielnormenSection).toBeVisible()
      await expect(
        zielnormenSection.getByText("Es sind noch keine Zielnormen vorhanden"),
      ).toBeVisible()
    })

    test("should show Zielnormen", async ({ page }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      const zielnormenSection = page.getByRole("region", { name: "Zielnormen" })
      await expect(zielnormenSection).toBeVisible()
      await expect(
        zielnormenSection.getByText(
          "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        ),
      ).toBeVisible()
      await expect(zielnormenSection.getByText("FNA 754-28-1")).toBeVisible()
    })

    test("should expand the Zielnorm and Textkonsolidierung to show the expression with ELI and date", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      const zielnormenSection = page.getByRole("region", { name: "Zielnormen" })

      const zielnormButton = zielnormenSection.getByRole("button", {
        name: /Gesetz zur Regelung des öffentlichen Vereinsrechts/,
      })

      await zielnormButton.click()

      const textkonsolidierungButton = zielnormenSection.getByRole("button", {
        name: "Textkonsolidierung",
      })
      await expect(textkonsolidierungButton).toBeVisible()
      await textkonsolidierungButton.click()

      await expect(
        zielnormenSection.getByText(
          "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu",
        ),
      ).toBeVisible()
      await expect(zielnormenSection.getByText("15.03.2017")).toBeVisible()
    })
  },
)
