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
      await page.goto(
        "./announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await expect(
        page
          .getByRole("heading", {
            name: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
          })
          .first(),
      ).toBeVisible()

      const announcementSection = page.getByRole("region", {
        name: "Bekanntmachungsdetails",
      })

      await expect(
        announcementSection.getByText("Veröffentlichungsdatum"),
      ).toBeVisible()
      await expect(announcementSection.getByText("15.03.2017")).toBeVisible()

      await expect(
        announcementSection.getByText("Ausfertigungsdatum"),
      ).toBeVisible()
      await expect(announcementSection.getByText("01.01.1900")).toBeVisible()

      await expect(announcementSection.getByText("FNA")).toBeVisible()
      await expect(
        announcementSection.getByText("nicht-vorhanden"),
      ).toBeVisible()
    })

    test("should redirect to 404 page when announcement returns 404", async ({
      page,
    }) => {
      await page.route(
        "**/api/v1/announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        (route) => route.fulfill({ status: 404 }),
      )

      await page.goto(
        "./announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
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
        "./announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await expect(
        page.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeVisible()
    })

    test("user can navigate to temporal data page", async ({ page }) => {
      await page.goto(
        "./announcements/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      await page.getByText("Geltungszeitregeln anlegen").click()
      await expect(page).toHaveURL(/.*\/temporal-data/)
    })
  },
)
