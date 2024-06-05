import { Proprietary } from "@/types/proprietary"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("useProprietary", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("forwards the data from the service", async () => {
    const mockData = ref<Proprietary>({ fna: { value: "4711" } })
    const mockFetchError = ref(undefined)
    const mockIsFetching = ref(false)

    const useGetProprietary = vi.fn().mockReturnValue({
      data: mockData,
      error: mockFetchError,
      isFetching: mockIsFetching,
    })
    vi.doMock("@/services/proprietaryService", () => ({ useGetProprietary }))

    const { useProprietary } = await import("./useProprietary")

    const { data, fetchError, isFetching } = useProprietary("fake/eli", {
      atDate: "2024-06-05",
    })

    expect(useGetProprietary).toHaveBeenCalledWith("fake/eli", {
      atDate: "2024-06-05",
    })

    expect(data).toBe(mockData)
    expect(fetchError).toBe(mockFetchError)
    expect(isFetching).toBe(mockIsFetching)
  })
})
