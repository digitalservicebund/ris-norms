import { getNormByEli } from "@/services/normService"
import { useAmendingLawStore } from "@/store/amendingLawStore"
import { Norm } from "@/types/norm"
import { createPinia, setActivePinia } from "pinia"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"

vi.mock("@/services/normService", () => ({
  getNormByEli: vi.fn(),
}))

describe("useAmendingLawsStore", () => {
  const eli = "eli/example/2023/1"
  let mockAmendingLaw: Norm

  beforeEach(() => {
    setActivePinia(createPinia())

    mockAmendingLaw = {
      eli: eli,
      printAnnouncementGazette: "example",
      digitalAnnouncementMedium: undefined,
      frbrDateVerkuendung: "2023-01-01",
      printAnnouncementPage: "1",
      digitalAnnouncementEdition: undefined,
    }

    vi.mocked(getNormByEli).mockResolvedValue(mockAmendingLaw)
  })

  it("loads amending laws correctly", async () => {
    const store = useAmendingLawStore()

    store.loadAmendingLawByEli(eli)
    await nextTick()

    expect(store.loading).toBe(true)
    expect(getNormByEli).toHaveBeenCalledOnce()

    await vi.waitUntil(() => !store.loading)

    expect(store.loading).toBe(false)
    expect(store.loadedAmendingLaw).toEqual(mockAmendingLaw)
  })
})
