import { beforeEach, describe, expect, it, vi } from "vitest"
import { TableOfContentsItem } from "@/types/tableOfContents"
import { ref } from "vue"
import { flushPromises } from "@vue/test-utils"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("useGetNormTableOfContents", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("fetches the table of contents from the API", async () => {
    const fixture: TableOfContentsItem[] = [
      {
        id: "1",
        marker: "§ 1",
        heading: "Article 1",
        type: "article",
        children: [
          {
            id: "1.1",
            marker: "1.",
            heading: "First Point",
            type: "section",
            children: [],
          },
          {
            id: "1.2",
            marker: "2.",
            heading: "Second Point",
            type: "section",
            children: [],
          },
        ],
      },
      {
        id: "2",
        marker: "§ 2",
        heading: "Inkrafttreten",
        type: "article",
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

    const { useGetNormTableOfContents } = await import("./tocService")

    const result = useGetNormTableOfContents(
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

    const { useGetNormTableOfContents } = await import("./tocService")

    const eli = ref(undefined)
    useGetNormTableOfContents(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetNormTableOfContents } = await import("./tocService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
    useGetNormTableOfContents(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})
