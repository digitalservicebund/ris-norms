import { test } from "@e2e/utils/test-with-auth"
import { uploadAmendingLaw } from "@e2e/utils/upload-with-force"
import { expect } from "@playwright/test"

test.describe("Editing a single mod", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })
  test.afterEach(async ({ authenticatedRequest: request }) => {
    await uploadAmendingLaw(request, "bgbl-1_2017_s419/aenderungsgesetz.xml")
  })

  test(
    "editing and saving the eid mod change by highlighting",
    { tag: ["@RISDEV-4553"] },
    async ({ page }) => {
      const amendingLawSection = page.getByRole("region", {
        name: "Änderungsbefehle",
      })

      await amendingLawSection.getByText("§ 20 Absatz 1 Satz 2").click()

      const modFormSection = page.getByRole("region", {
        name: "Änderungsbefehl bearbeiten",
      })

      await expect(
        modFormSection.getByRole("textbox", {
          name: "zu ersetzende Textstelle",
        }),
      ).toHaveValue(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
      )

      const textBoundingBox = await modFormSection
        .getByLabel("Zu ersetzender Text")
        .getByText("entgegen § 9")
        .boundingBox()

      await page.mouse.dblclick(textBoundingBox!.x + 50, textBoundingBox!.y + 5)
      await page.mouse.click(0, 0)

      await expect(
        modFormSection.getByRole("textbox", {
          name: "zu ersetzende Textstelle",
        }),
      ).not.toHaveValue(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml",
      )

      await expect(
        modFormSection.getByRole("textbox", {
          name: "zu ersetzende Textstelle",
        }),
      ).toHaveValue(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/0-8.xml",
      )
    },
  )
})

test.describe("Editing multiple mods", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/1001/2/1001-02-01/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit",
    )
  })
  test.afterEach(async ({ authenticatedRequest: request }) => {
    await uploadAmendingLaw(
      request,
      "bgbl-1_1001_2_mods_01/aenderungsgesetz.xml",
    )
  })
  test("Displaying time boundary", async ({ page }) => {
    const amendingLawSection = page.getByRole("region", {
      name: "Änderungsbefehle",
    })

    await amendingLawSection.getByText("1. Fall").click()
    await amendingLawSection
      .getByText("2. Beispiel")
      .click({ modifiers: ["ControlOrMeta"] })

    await expect(
      page.getByRole("heading", {
        level: 3,
        name: "2 Änderungsbefehle bearbeiten",
      }),
    ).toBeVisible()

    const timeBoundariesElement = page.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    await expect(timeBoundariesElement).toHaveText("Mehrere")

    await timeBoundariesElement.click()

    const timeBoundaryOptionElements = page.getByRole("option")
    await expect(timeBoundaryOptionElements).toHaveCount(4)
  })
})
