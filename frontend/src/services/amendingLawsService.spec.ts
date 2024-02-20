import { describe, it, expect, vi } from "vitest"
import { getAmendingLaws } from "./amendingLawsService"

vi.mock("./amendingLawsService", () => ({
  getAmendingLaws: vi.fn(),
}))

describe("Service consumer tests", () => {
  it("tests another function or component using getAmendingLaws with mock data", async () => {
    const mockedProceduresArray = [
      {
        eli: "eli/example/2023/1",
        printAnnouncementGazette: "example",
        printAnnouncementMedium: undefined,
        publicationDate: "2023-01-01",
        printAnnouncementPage: 1,
        digitalAnnouncementEdition: undefined,
        articles: [
          {
            eli: "article eli 1",
            title: "article eli 1",
            enumeration: "1",
          },
        ],
      },
      {
        eli: "eli/example2/2024/2",
        printAnnouncementGazette: "example2",
        printAnnouncementMedium: undefined,
        publicationDate: "2024-01-01",
        printAnnouncementPage: 2,
        digitalAnnouncementEdition: undefined,
        articles: [
          {
            eli: "article eli 2",
            title: "article title 2",
            enumeration: "2",
          },
        ],
      },
    ]

    vi.mocked(getAmendingLaws).mockResolvedValue(mockedProceduresArray)
    const result = await getAmendingLaws()
    expect(result).toBe(mockedProceduresArray)

    expect(getAmendingLaws).toHaveBeenCalled()
  })
})
