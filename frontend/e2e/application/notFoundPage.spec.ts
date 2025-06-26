import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.describe("404 Page", () => {
  test("should display 404 page when trying to access a non existing url", async ({
    page,
  }) => {
    await page.goto("./verkuendungen/non-existing-route")

    await expect(
      page.getByRole("heading", { name: /404 - Seite nicht gefunden/ }),
    ).toBeVisible()
  })
})
