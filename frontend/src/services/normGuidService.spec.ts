import type { NormSchema } from "@/types/norm"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"
import type { z } from "zod"

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
    const fixture: z.input<typeof NormSchema> = {
      eli: "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
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

    const result = await useNormGuidService(
      "e7abd358-32cb-4fc2-8a1a-b033961f3708",
    )
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("calls correct endpoint", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormGuidService } = await import("./normGuidService")

    await useNormGuidService("e7abd358-32cb-4fc2-8a1a-b033961f3708")

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
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
    await useNormGuidService(guid)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })
})
