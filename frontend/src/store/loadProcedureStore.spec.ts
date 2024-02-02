import { describe, it, expect, beforeEach, vi } from "vitest"
import { setActivePinia, createPinia } from "pinia"
import { useProceduresStore } from "@/store/loadProcedureStore"
import { getProcedures } from "@/services/proceduresService"
import { Procedure } from "e2e/testData/testData"

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
        printAnnouncementNumber: 123,
        printAnnouncementPage: 4,
        publicationDate: new Date("2023-03-12"),
        fna: "000-1-222",
      },
      {
        eli: "eli2",
        printAnnouncementGazette: "",
        printAnnouncementYear: 2,
        printAnnouncementNumber: 345,
        printAnnouncementPage: 6,
        publicationDate: new Date("2024-01-01"),
        fna: "111-2-333",
      },
    ]
    vi.mocked(getProcedures).mockReturnValue(mockProcedures)
  })

  it("loads procedures correctly", async () => {
    const store = useProceduresStore()
    store.loadProcedures()

    expect(getProcedures).toHaveBeenCalledOnce()
    expect(store.procedures).toEqual(mockProcedures)
  })

  it("finds a procedure by ELI", () => {
    const store = useProceduresStore()
    store.$state.procedures = mockProcedures

    const foundProcedure = store.getProcedureByEli("eli1")

    expect(foundProcedure).toEqual(mockProcedures[0])
  })

  it("returns undefined for a non-existent ELI", () => {
    const store = useProceduresStore()
    store.$state.procedures = mockProcedures

    const foundProcedure = store.getProcedureByEli("eli3")

    expect(foundProcedure).toBeUndefined()
  })
})
