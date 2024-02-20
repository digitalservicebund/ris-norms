import { describe, it, expect, beforeEach, vi } from "vitest"
import { setActivePinia, createPinia } from "pinia"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { getAmendingLaws, AmendingLaw } from "@/services/amendingLawsService"

vi.mock("@/services/amendingLawsService", () => ({
  getAmendingLaws: vi.fn(),
}))

describe("useAmendingLawsStore", () => {
  let mockProcedures: AmendingLaw[]

  beforeEach(() => {
    setActivePinia(createPinia())

    mockProcedures = [
      {
        eli: "eli/example/2023/1",
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
        ],
      },
      {
        eli: "eli/example2/2024/2",
        printAnnouncementGazette: "example2",
        printAnnouncementMedium: undefined,
        publicationDate: "2024-01-01",
        printAnnouncementPage: "2",
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

    vi.mocked(getAmendingLaws).mockResolvedValue(mockProcedures)
  })

  it("loads amending laws correctly", async () => {
    const store = useAmendingLawsStore()
    await store.loadAmendingLaws()

    expect(getAmendingLaws).toHaveBeenCalledOnce()
    expect(store.amendingLaws).toEqual(mockProcedures)
  })
})
