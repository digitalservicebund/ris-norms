import { test, expect, Page } from "@playwright/test"

test.describe(
  "Publishing flow for an announcement",
  {
    tag: ["@RISDEV-4708"],
  },
  () => {
    test("navigate to publishing page for an unpublished announcement", async ({
      page,
    }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing",
      )

      await expect(
        page.getByText("Das Gesetz wurde noch nicht veröffentlicht."),
      ).toBeVisible()

      // Trigger the publication of the amending law
      await expect(
        page.getByRole("button", { name: "Jetzt abgeben" }),
      ).toBeVisible()
    })

    test("publishing a norm", async ({ page }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/publishing",
      )

      await page.getByRole("button", { name: "Jetzt abgeben" }).click()

      const currentTimestamp = new Date()
      const expectedDateString = currentTimestamp.toLocaleDateString("de-DE", {
        dateStyle: "medium",
      })
      const expectedTimeString = currentTimestamp.toLocaleTimeString("de-DE", {
        hour: "2-digit",
        minute: "2-digit",
      })

      // Verify publication time
      await verifyPublicationTime(page, expectedDateString, expectedTimeString)

      // Verify Links
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/1990/s2954/2024-06-01/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/1990/s2954/2023-12-30/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()

      // TODO: (Malte Laukötter, 2024-10-30) check that further editing doesn't change published norms
      // TODO: (Malte Laukötter, 2024-10-30) check that further editing & then publishing again does change published norms (at same date)
      // TODO: (Malte Laukötter, 2024-10-30) check published file content?
    })

    async function verifyPublicationTime(
      page: Page,
      expectedDate: string,
      expectedTime: string,
    ): Promise<void> {
      const timeElement = page.getByRole("time")
      await expect(timeElement).toHaveText(
        `${expectedDate} um ${expectedTime} Uhr. Die aktuelle Version kann hier eingesehen werden: `,
      )
    }
  },
)
