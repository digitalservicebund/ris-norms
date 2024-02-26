import { describe, it, expect, vi } from "vitest"
import {
  AmendingLaw,
  getAmendingLawByEli,
  getAmendingLaws,
} from "./amendingLawsService"

vi.mock("./amendingLawsService", () => ({
  getAmendingLaws: vi.fn(),
  getAmendingLawByEli: vi.fn(),
}))

describe("Service consumer tests", () => {
  it("tests another function or component using getAmendingLaws with mock data", async () => {
    const mockedAmendingLawsArray: AmendingLaw[] = [
      {
        eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        printAnnouncementGazette: "bgbl-1",
        publicationDate: "2017-03-15",
        printAnnouncementPage: "419",
        digitalAnnouncementEdition: undefined,
      },
      {
        eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        printAnnouncementGazette: "bgbl-1",
        publicationDate: "2023-12-29",
        printAnnouncementPage: "413",
        digitalAnnouncementEdition: undefined,
      },
    ]

    vi.mocked(getAmendingLaws).mockResolvedValue(mockedAmendingLawsArray)
    const result = await getAmendingLaws()
    expect(result).toBe(mockedAmendingLawsArray)

    expect(getAmendingLaws).toHaveBeenCalled()
  })

  it("tests another function or component using getAmendingLawByEli with mock data", async () => {
    const eli = "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1"
    const mockedAmendingLaw = {
      eli: eli,
      printAnnouncementGazette: "bgbl-1",
      printAnnouncementMedium: undefined,
      publicationDate: "2017-03-15",
      printAnnouncementPage: "419",
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
