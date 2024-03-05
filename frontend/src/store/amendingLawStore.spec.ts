import { getAmendingLawByEli } from "@/services/amendingLawsService"
import { useAmendingLawStore } from "@/store/amendingLawStore"
import { AmendingLaw } from "@/types/domain"
import { createPinia, setActivePinia } from "pinia"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"

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
          eid: "article eid 1",
          title: "article eli 1",
          enumeration: "1",
          affectedDocumentEli: "affected document eli 1",
        },
      ],
    }

    vi.mocked(getAmendingLawByEli).mockResolvedValue(mockAmendingLaw)
  })

  it("loads amending laws correctly", async () => {
    const store = useAmendingLawStore()

    store.loadAmendingLawByEli(eli)
    await nextTick()

    expect(store.loading).toBe(true)
    expect(getAmendingLawByEli).toHaveBeenCalledOnce()

    await vi.waitUntil(() => !store.loading)

    expect(store.loading).toBe(false)
    expect(store.loadedAmendingLaw).toEqual(mockAmendingLaw)
  })
})
