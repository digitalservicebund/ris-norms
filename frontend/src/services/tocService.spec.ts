import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { DokumentManifestationEli } from "@/lib/eli/DokumentManifestationEli"
import type { TocItem } from "@/types/toc"
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

describe("useGetNormToc", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("fetches the table of contents from the API", async () => {
    const fixture: TocItem[] = [
      {
        id: "1",
        marker: "§ 1",
        heading: "Article 1",
        type: "article",
        eingebundeneStammformEli: undefined,
        children: [
          {
            id: "1.1",
            marker: "1.",
            heading: "First Point",
            type: "section",
            eingebundeneStammformEli: undefined,
            children: [],
          },
          {
            id: "1.2",
            marker: "2.",
            heading: "Second Point",
            type: "section",
            eingebundeneStammformEli: undefined,
            children: [],
          },
        ],
      },
      {
        id: "2",
        marker: "§ 2",
        heading: "Inkrafttreten",
        type: "article",
        eingebundeneStammformEli: DokumentManifestationEli.fromString(
          "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml",
        ),
        children: [],
      },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({
        data: ref(fixture),
      }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useGetNormToc } = await import("./tocService")

    const result = useGetNormToc(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    expect(result.data.value).toEqual(fixture)

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetNormToc } = await import("./tocService")

    const eli = ref(undefined)
    useGetNormToc(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetNormToc } = await import("./tocService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useGetNormToc(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})
