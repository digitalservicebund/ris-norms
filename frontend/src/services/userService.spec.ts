import { describe, it, expect, vi, beforeEach } from "vitest"

describe("userService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGetUserName()", () => {
    it("provides the user data from the API", async () => {
      const mockData = {
        name: "John Doe",
      }

      const useFetchMock = {
        data: { value: mockData },
        error: { value: null },
        isFetching: { value: false },
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue({
          json: vi.fn().mockReturnValue(useFetchMock),
        }),
      }))

      const { useGetUserName } = await import("@/services/userService")

      const result = useGetUserName()

      const data = result.data.value!
      expect(data.name).toBe("John Doe")

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith("/me")
    })
  })
})
