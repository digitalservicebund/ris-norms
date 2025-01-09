import { describe, it, expect, vi, beforeEach } from "vitest"

describe("logoutService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("calls the /logout endpoint with POST method", async () => {
    const useFetchMock = {
      error: { value: null },
      isFetching: { value: false },
    }

    vi.doMock("@/services/apiService", () => ({
      useApiFetch: vi.fn().mockReturnValue(useFetchMock),
    }))

    const { useLogoutService } = await import("@/services/logoutService")

    const result = useLogoutService()

    expect(result.error.value).toBe(null)
    expect(result.isFetching.value).toBe(false)

    const { useApiFetch } = await import("@/services/apiService")
    expect(useApiFetch).toHaveBeenCalledWith("/logout", {
      method: "POST",
    })
  })
})
