import { describe, it, expect, vi } from "vitest"
import { getAmendingLawByEli, getAmendingLaws } from "./amendingLawsService"

vi.mock("./amendingLawsService", () => ({
  getAmendingLaws: vi.fn(),
  getAmendingLawByEli: vi.fn(),
}))

describe("Service consumer tests", () => {
  it("tests another function or component using getAmendingLaws with mock data", async () => {
    const mockedAmendingLawsArray = [
      {
        eli: "eli/example/2023/1",
        printAnnouncementGazette: "example",
        printAnnouncementMedium: undefined,
        publicationDate: "2023-01-01",
        printAnnouncementPage: "1",
        digitalAnnouncementEdition: undefined,
      },
      {
        eli: "eli/example2/2024/2",
        printAnnouncementGazette: "example2",
        printAnnouncementMedium: undefined,
        publicationDate: "2024-01-01",
        printAnnouncementPage: "2",
        digitalAnnouncementEdition: undefined,
      },
    ]

    vi.mocked(getAmendingLaws).mockResolvedValue(mockedAmendingLawsArray)
    const result = await getAmendingLaws()
    expect(result).toBe(mockedAmendingLawsArray)

    expect(getAmendingLaws).toHaveBeenCalled()
  })

  it("tests another function or component using getAmendingLawByEli with mock data", async () => {
    const eli = "eli/example/2023/1"
    const mockedAmendingLaw = {
      eli: eli,
      printAnnouncementGazette: "example",
      printAnnouncementMedium: undefined,
      publicationDate: "2023-01-01",
      printAnnouncementPage: "1",
      digitalAnnouncementEdition: undefined,
      articles: [
        {
          eli: "article eli 1",
          title: "article eli 1",
          enumeration: "1",
        },
        {
          eli: "article eli 2",
          title: "article eli 2",
          enumeration: "2",
        },
      ],
    }

    vi.mocked(getAmendingLawByEli).mockResolvedValue(mockedAmendingLaw)
    const result = await getAmendingLawByEli(eli)
    expect(result).toBe(mockedAmendingLaw)

    expect(getAmendingLawByEli).toHaveBeenCalled()
  })
})
