import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
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

describe("useGetNormReleaseStatus", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture = {
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: "eli/bund/bgbl-1/1964/s593",
      expressions: [
        {
          normExpressionEli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          isGegenstandslos: false,
          currentStatus: "NOT_RELEASED",
        },
      ],
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useGetNormReleaseStatus } = await import("./normReleaseService")

    const result = useGetNormReleaseStatus(
      NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
    )

    expect(result.data.value).toEqual({
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
      expressions: [
        {
          normExpressionEli: NormExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          ),
          isGegenstandslos: false,
          currentStatus: "NOT_RELEASED",
        },
      ],
    })

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetNormReleaseStatus } = await import("./normReleaseService")

    const eli = ref(undefined)
    useGetNormReleaseStatus(eli)
    await flushPromises()

    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        JSON.stringify({
          title: "Dummy",
          shortTitle: "Dummy",
          normWorkEli: "eli/bund/bgbl-1/2021/1",
          expressions: [],
        }),
      ),
    )

    const { useGetNormReleaseStatus } = await import("./normReleaseService")

    const eli = ref(NormWorkEli.fromString("eli/bund/bgbl-1/2021/1"))
    useGetNormReleaseStatus(eli, { immediate: true, refetch: true })

    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormWorkEli.fromString("eli/bund/bgbl-1/2021/2")
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})

describe("usePostNormRelease", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("posts a praetext release request", async () => {
    const fixture = {
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: "eli/bund/bgbl-1/1964/s593",
      expressions: [
        {
          normExpressionEli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          isGegenstandslos: false,
          currentStatus: "PRAETEXT_RELEASED",
        },
      ],
    }

    const postMock = vi.fn().mockReturnValue({
      execute: vi.fn().mockResolvedValue(fixture),
    })

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture), post: postMock }),
      post: postMock,
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { usePostNormRelease } = await import("./normReleaseService")

    const service = usePostNormRelease(
      ref(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593")),
    )

    await service.execute("praetext")
    expect(postMock).toHaveBeenCalledWith({ releaseType: "praetext" })
    expect(service.data.value).toEqual({
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
      expressions: [
        {
          normExpressionEli: NormExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          ),
          isGegenstandslos: false,
          currentStatus: "PRAETEXT_RELEASED",
        },
      ],
    })

    vi.doUnmock("@/services/apiService")
  })
  it("posts a volldokumentation release request", async () => {
    const fixture = {
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: "eli/bund/bgbl-1/1964/s593",
      expressions: [
        {
          normExpressionEli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          isGegenstandslos: false,
          currentStatus: "VOLLDOKUMENTATION_RELEASED",
        },
      ],
    }

    const postMock = vi.fn().mockReturnValue({
      execute: vi.fn().mockResolvedValue(fixture),
    })

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture), post: postMock }),
      post: postMock,
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { usePostNormRelease } = await import("./normReleaseService")

    const service = usePostNormRelease(
      ref(NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593")),
    )

    await service.execute("volldokumentation")

    expect(service.data.value).toEqual({
      title: "Beispielnorm",
      shortTitle: "Beispielnorm",
      normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/1964/s593"),
      expressions: [
        {
          normExpressionEli: NormExpressionEli.fromString(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu",
          ),
          isGegenstandslos: false,
          currentStatus: "VOLLDOKUMENTATION_RELEASED",
        },
      ],
    })

    vi.doUnmock("@/services/apiService")
  })
})
