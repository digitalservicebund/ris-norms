import { test, expect } from "@playwright/test"

test.describe.skip("Outline Elements Navigation", () => {
  test("should display the correct title and preview when an outline element is selected", async ({
    page,
  }) => {
    await page.route(
      "**/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/elements*",
      async (route) => {
        const mockResponse = [
          { title: "Book 1", eid: "book-1", type: "book" },
          { title: "Chapter 1", eid: "chapter-1", type: "chapter" },
          { title: "Part 1", eid: "part-1", type: "part" },
          { title: "Subtitle 1", eid: "subtitle-1", type: "subtitle" },
          {
            title: "Main Title",
            eid: "hauptteil-1_abschnitt-erster_para-2",
            type: "title",
          },
          {
            title: "§ 2 Verfassungsschutzbehörden",
            eid: "hauptteil-1_abschnitt-erster_para-2",
            type: "article",
          },
          {
            title:
              "§ 6 Gegenseitige Unterrichtung der Verfassungsschutzbehörden",
            eid: "hauptteil-1_abschnitt-erster_para-6",
            type: "article",
          },
        ]
        await route.fulfill({
          status: 200,
          contentType: "application/json",
          body: JSON.stringify(mockResponse),
        })
      },
    )

    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/affected-documents/eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1/edit/2023-12-30",
    )

    await page.getByRole("link", { name: "Main Title" }).click()

    await expect(
      page.getByRole("heading", { level: 2, name: "Main Title" }),
    ).toBeVisible()

    await expect(
      page.getByText(
        "Aktuell sind keine Metadaten auf Gliederungsebene implementiert",
      ),
    ).toBeVisible()
  })
})
