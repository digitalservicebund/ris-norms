import { describe, it, expect, beforeEach, vi } from "vitest"
import { setActivePinia, createPinia } from "pinia"
import { useProceduresStore } from "@/store/loadProcedureStore"
import { getProcedures, Procedure } from "@/services/proceduresService"

vi.mock("@/services/proceduresService", () => ({
  getProcedures: vi.fn(),
}))

describe("useProceduresStore", () => {
  let mockProcedures: Procedure[]

  beforeEach(() => {
    setActivePinia(createPinia())

    mockProcedures = [
      {
        eli: "eli1",
        printAnnouncementGazette: "",
        printAnnouncementYear: 1,
        printAnnouncementPage: 4,
      },
      {
        eli: "eli2",
        printAnnouncementGazette: "",
        printAnnouncementYear: 2,
        printAnnouncementPage: 6,
      },
    ]

    vi.mocked(getProcedures).mockResolvedValue(mockProcedures)
  })

  it("loads procedures correctly", async () => {
    const store = useProceduresStore()
    await store.loadProcedures()

    expect(getProcedures).toHaveBeenCalledOnce()
    expect(store.procedures).toEqual(mockProcedures)
  })
})
