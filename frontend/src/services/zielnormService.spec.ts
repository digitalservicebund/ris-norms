import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import type { ZielnormReference } from "@/types/zielnorm"
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

describe("useGetZielnormen", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("fetches the table of contents from the API", async () => {
    const fixture: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Aufhebung",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2019/789",
      },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useGetZielnormen } = await import("./zielnormService")

    const result = useGetZielnormen(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
    expect(result.data.value).toEqual(fixture)

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormen } = await import("./zielnormService")

    const eli = ref(undefined)
    useGetZielnormen(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormen } = await import("./zielnormService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
    useGetZielnormen(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})
