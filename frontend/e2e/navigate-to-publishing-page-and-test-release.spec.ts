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
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing",
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
          name: `eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      await expect(
        page.getByRole("link", {
          name: `eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
      const manifestationOfPreviouslyPublishedExpression =
        await page.request.get(
          `/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        )
      expect(
        await manifestationOfPreviouslyPublishedExpression.text(),
      ).toContain(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1.xml",
      )
      expect(
        await manifestationOfPreviouslyPublishedExpression.text(),
      ).toContain(
        "Abs. 1 Satz 2, Abs. 2 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet",
      )

      const expressionAtFirstTimeBoundary = await page.request.get(
        `/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
      )
      expect(await expressionAtFirstTimeBoundary.text()).toContain(
        "Absatz 2 oder 3 Kennzeichen eines verbotenen Vereins oder einer Ersatzorganisation verwendet",
      )
    })

    // TODO: (Malte Laukötter, 2024-10-30) check that further editing doesn't change published norms
    // TODO: (Malte Laukötter, 2024-10-30) check that further editing & then publishing again does change published norms (at same date)

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
