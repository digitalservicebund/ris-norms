import { test } from "@e2e/utils/testWithAuth"
import { expect, Page } from "@playwright/test"

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
          name: `eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        }),
      ).toBeVisible()
    })

    test.skip("editing of a published announcement doesn't change the published norms contents", async ({
      page,
      authenticatedRequest: request,
    }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing",
      )

      // We'll use a metadatum to check if something changed; first verify it's not there
      const expressionAtFirstTimeBoundaryBefore = await page.request
        .get(
          `/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        )
        .then((response) => response.text())
      expect(expressionAtFirstTimeBoundaryBefore).not.toContain(
        "</ris:beschliessendesOrgan>",
      )

      // Submit metadata change (API)
      await request.put(
        "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary/2017-03-16",
        { data: { beschliessendesOrgan: "AA - Auswärtiges Amt" } },
      )

      // Fetch same document again and ensure nothing has changed
      const expressionAtFirstTimeBoundaryAfter = await page.request
        .get(
          `/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        )
        .then((response) => response.text())
      expect(expressionAtFirstTimeBoundaryAfter).toEqual(
        expressionAtFirstTimeBoundaryBefore,
      )
    })

    test.skip("editing of a published announcement and re-publishing updates the published norms", async ({
      page,
      authenticatedRequest: request,
    }) => {
      await page.goto(
        "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/publishing",
      )

      // We'll use a metadatum to check if something changed; first verify it's not there
      const expressionAtFirstTimeBoundaryBefore = await request
        .get(
          `/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        )
        .then((response) => response.text())
      expect(expressionAtFirstTimeBoundaryBefore).not.toContain(
        "</ris:beschliessendesOrgan>",
      )

      // Submit metadata change (API)
      await request.put(
        "/api/v1/norms/eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/proprietary",
        { data: { beschliessendesOrgan: "AA - Auswärtiges Amt" } },
      )

      // Publish again
      await page.getByRole("button", { name: "Jetzt abgeben" }).click()

      const currentTimestamp = new Date()
      const expectedDateString = currentTimestamp.toLocaleDateString("de-DE", {
        dateStyle: "medium",
      })
      const expectedTimeString = currentTimestamp.toLocaleTimeString("de-DE", {
        hour: "2-digit",
        minute: "2-digit",
      })

      await verifyPublicationTime(page, expectedDateString, expectedTimeString)

      // Fetch same document again and ensure nothing has changed
      const expressionAtFirstTimeBoundaryAfter = await request
        .get(
          `/api/v1/norms/eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu/${new Date().toISOString().substring(0, 10)}/regelungstext-1.xml`,
        )
        .then((response) => response.text())
      expect(expressionAtFirstTimeBoundaryAfter).toContain(
        "AA - Auswärtiges Amt</ris:beschliessendesOrgan>",
      )
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
