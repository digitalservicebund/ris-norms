import type { Norm } from "@/types/norm"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("useNormGuidService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: Norm = {
      eli: "fake/eli",
      title: "Example norm",
    }

    const useApiFetch = vi.fn().mockReturnValue({
      json: () => ({
        data: ref(fixture),
        execute: vi.fn(),
      }),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useNormGuidService } = await import("./normGuidService")

    const result = useNormGuidService("e7abd358-32cb-4fc2-8a1a-b033961f3708")
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("calls correct endpoint", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormGuidService } = await import("./normGuidService")

    useNormGuidService("e7abd358-32cb-4fc2-8a1a-b033961f3708")

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/e7abd358-32cb-4fc2-8a1a-b033961f3708",
        expect.any(Object),
      ),
    )
  })

  it("does not load if the GUID has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormGuidService } = await import("./normGuidService")

    const guid = ref(undefined)
    useNormGuidService(guid)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })
})
