import { test } from "@e2e/utils/test-with-auth"
import { expect } from "@playwright/test"

test("shows an error message when the environment can't be loaded", async ({
  page,
}) => {
  await page.route("**/environment", (route) => route.abort())
  await page.goto("/")
  const errorContainer = page.getByRole("alert")
  await expect(errorContainer).toBeVisible()

  await expect(
    page.getByText(
      "Die Anwendung konnte nicht gestartet werden. Bitte versuchen Sie es erneut oder kontaktieren Sie den Support, falls das Problem weiterhin besteht.",
    ),
  ).toBeVisible()
})
