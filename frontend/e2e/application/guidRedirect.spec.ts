import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("GUID redirect", () => {
  test("should redirect guid to eli", async ({ page }) => {
    await page.goto("./843fc77f-5886-488e-aa13-f3ad8198ad17/metadata")

    await expect(
      page.getByRole("heading", {
        name: "Gesetz zur Regelung des öffentlichen Vereinsrechts\n",
      }),
    ).toBeVisible()

    expect(page.url()).toContain(
      "eli/bund/bgbl-1/2017/s593/2017-03-15/1/deu/regelungstext-verkuendung-1/metadata",
    )
  })

  test("should redirect unknown guid to 404", async ({ page }) => {
    await page.goto("./00000000-aaaa-bbbb-cccc-ddddddeeeeee/metadata")

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
