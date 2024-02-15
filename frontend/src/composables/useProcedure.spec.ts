import { describe, it, expect, vi, beforeEach } from "vitest"
import { useProcedure } from "./useProcedure"
import { setActivePinia, createPinia } from "pinia"
import { ref } from "vue"
import * as proceduresService from "@/services/proceduresService"

vi.mock("@/services/proceduresService", () => ({
  getProcedureByEli: vi.fn(),
}))

describe("useProcedure", () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it("updates procedure on successful fetch", async () => {
    const eli = "test-eli"
    const mockProcedure = {
      eli,
      printAnnouncementGazette: "Test Procedure",
      printAnnouncementYear: 2023,
      printAnnouncementNumber: 1,
      printAnnouncementPage: 100,
      publicationDate: new Date("2023-01-01"),
      fna: "123-456",
    }
    const mockGetProcedureByEli = vi.mocked(proceduresService.getProcedureByEli)
    mockGetProcedureByEli.mockResolvedValue(mockProcedure)

    const { procedure } = useProcedure(eli)

    await vi.waitFor(() => expect(procedure.value).toBeDefined())

    expect(procedure.value).toEqual(mockProcedure)
  })

  it("handles error from getProcedureByEli", async () => {
    const errorMessage = "Fetch error"
    vi.mocked(proceduresService.getProcedureByEli).mockRejectedValue(
      new Error(errorMessage),
    )

    const eli = "test-eli"
    const { procedure } = useProcedure(ref(eli))

    await vi.waitFor(() => expect(procedure.value).toBeUndefined())
  })
})
