import { describe, it, expect, beforeEach, vi } from "vitest"
import { setActivePinia, createPinia } from "pinia"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import {
  AmendingLaw,
  getAmendingLawByEli,
} from "@/services/amendingLawsService"

vi.mock("@/services/amendingLawsService", () => ({
  getAmendingLawByEli: vi.fn(),
}))

describe("useAmendingLawsStore", () => {
  const eli = "eli/example/2023/1"
  let mockAmendingLaw: AmendingLaw

  beforeEach(() => {
    setActivePinia(createPinia())

    mockAmendingLaw = {
      eli: eli,
      printAnnouncementGazette: "example",
      digitalAnnouncementMedium: undefined,
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
    }

    vi.mocked(getAmendingLawByEli).mockResolvedValue(mockAmendingLaw)
  })

  it("loads amending laws correctly", async () => {
    const store = useAmendingLawsStore()
    await store.loadAmendingLawByEli(eli)

    expect(getAmendingLawByEli).toHaveBeenCalledOnce()
    expect(store.loadedAmendingLaw).toEqual(mockAmendingLaw)
  })
})
