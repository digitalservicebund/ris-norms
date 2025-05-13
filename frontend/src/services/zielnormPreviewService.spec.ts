import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { ZielnormPreview } from "@/types/zielnormPreview"
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

describe("useGetZielnormPreview", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: ZielnormPreview[] = [
      {
        title: "Beispielnorm",
        shortTitle: "Beispielnorm",
        normWorkEli: "eli/bund/bgbl-1/2025/1",
        expressions: [],
      },
      {
        title: "Beispielnorm 2",
        shortTitle: "Beispielnorm 2",
        normWorkEli: "eli/bund/bgbl-1/2025/2",
        expressions: [
          {
            createdBy: "diese Verkündung",
            isCreated: true,
            isGegenstandslos: false,
            normExpressionEli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
          },
        ],
      },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useGetZielnormPreview } = await import("./zielnormPreviewService")

    const result = useGetZielnormPreview(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    expect(result.data.value).toStrictEqual(fixture)

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormPreview } = await import("./zielnormPreviewService")

    const eli = ref(undefined)
    useGetZielnormPreview(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormPreview } = await import("./zielnormPreviewService")

    const eli = ref(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    useGetZielnormPreview(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})
