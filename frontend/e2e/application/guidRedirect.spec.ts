import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("GUID redirect", () => {
  test("should redirect guid to eli", async ({ page }) => {
    await page.goto("./53d31e3c-5c46-4e96-8017-b0db064561a1/metadata")

    await expect(
      page.getByRole("heading", {
        name: "(Wachstumschancengesetz)",
        exact: true,
      }),
    ).toBeVisible()

    expect(page.url()).toContain(
      "eli/bund/bgbl-1/2024/108/2024-03-27/1/deu/regelungstext-verkuendung-1/metadata",
    )
  })

  test("should redirect unknown guid to 404", async ({ page }) => {
    await page.goto("./00000000-aaaa-bbbb-cccc-ddddddeeeeee/metadata")

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
