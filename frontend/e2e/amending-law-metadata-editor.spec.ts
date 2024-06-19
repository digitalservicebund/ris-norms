import { expect, test } from "@playwright/test"

test.describe("navigate to page", () => {
  test("navigate to affected document metadata editor", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents",
    )

    await page.getByRole("link", { name: "Metadaten bearbeiten" }).click()

    // Only expect the URL to be somewhat equal to the following. The reason is that
    // the page redirects to a subpage, so this test might be flaky otherwise depending
    // on how fast the redirect is.
    await expect(page).toHaveURL(
      /.*\/amending-laws\/eli\/bund\/bgbl-1\/2023\/413\/2023-12-29\/1\/deu\/regelungstext-1\/affected-documents\/eli\/bund\/bgbl-1\/1990\/s2954\/2023-12-29\/1\/deu\/regelungstext-1\/edit.*/,
    )
  })

  test("displays affected document title", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    await expect(page.getByText("BGBl. I 2023 Nr. 413")).toBeVisible()

    await expect(
      page.getByText(
        "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
      ),
    ).toBeVisible()
  })

  test("shows an error when the amending law could not be loaded", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/0000/000/0000-00-00/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/",
    )

    await expect(page.getByText("404")).toBeVisible()
  })

  test("shows an error when the time boundaries could not be loaded", async ({
    page,
  }) => {
    await page.route(/timeBoundaries/, (route) => {
      route.abort()
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(
      page.getByText("Die Zeitgrenzen konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })
})

test.describe("sidebar navigation", () => {
  test("shows the elements affected by this amending law", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    const nav = page.getByRole("complementary", { name: "Inhaltsverzeichnis" })

    const links = nav.getByRole("link")
    await expect(links).toHaveText(
      [
        "Rahmen",
        "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      ],
      { useInnerText: true },
    )

    // Go to the other time boundary to check if the result is the same (should always
    // show all affected elements independent from the time boundary).
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    // Wait again ...
    await page.waitForResponse((response) =>
      response.url().includes("/elements?type="),
    )

    // Expect to see the same result again
    await expect(links).toHaveText(
      [
        "Rahmen",
        "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
      ],
      { useInnerText: true },
    )
  })

  test("shows the available time boundaries in the document", async ({
    page,
  }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    const select = page.getByRole("combobox", { name: "Zeitgrenze" })
    const options = select.getByRole("option")

    // First entry selected by default
    await expect(select).toHaveValue("1970-01-01")

    // Time boundaries available as options
    await expect(options).toHaveText(["01.01.1970", "30.12.2023"], {
      useInnerText: true,
    })
  })

  test("restores the selected time boundary from the URL", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await expect(
      page.getByRole("combobox", { name: "Zeitgrenze" }),
    ).toHaveValue("2023-12-30")
  })

  test("shows an error when the elements could not be loaded", async ({
    page,
  }) => {
    await page.route(
      /elements\?type=article&type=conclusions&type=preamble&type=preface&amendedBy=eli/,
      (route) => {
        route.abort()
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(
      page.getByText("Artikel konnten nicht geladen werden."),
    ).toBeVisible()

    await page.unrouteAll()
  })

  test("shows an empty state when no elements are found", async ({ page }) => {
    await page.route(
      /elements\?type=article&type=conclusions&type=preamble&type=preface&amendedBy=eli/,
      async (route) => {
        await route.fulfill({ status: 200, json: {} })
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/1970-01-01",
    )

    await expect(page.getByText("Keine Artikel gefunden.")).toBeVisible()

    await page.unrouteAll()
  })

  test("does not render links when no time boundary is selected", async ({
    page,
  }) => {
    page.route(/timeBoundaries/, async (route) => {
      await route.fulfill({ json: [], status: 200 })
    })

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit",
    )

    await expect(page.getByText("Keine Zeitgrenze ausgewählt.")).toBeVisible()

    await expect(
      page
        .getByRole("complementary", { name: "Inhaltsverzeichnis" })
        .getByRole("link"),
    ).toHaveCount(0)
  })
})
