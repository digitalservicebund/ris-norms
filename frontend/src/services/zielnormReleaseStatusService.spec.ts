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

describe("useGetZielnormReleaseStatus", () => {
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

    const { useGetZielnormReleaseStatus } = await import(
      "./zielnormReleaseStatusService"
    )

    const result = useGetZielnormReleaseStatus(
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

    const { useGetZielnormReleaseStatus } = await import(
      "./zielnormReleaseStatusService"
    )

    const eli = ref(undefined)
    useGetZielnormReleaseStatus(eli)
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

    const { useGetZielnormReleaseStatus } = await import(
      "./zielnormReleaseStatusService"
    )

    const eli = ref(NormWorkEli.fromString("eli/bund/bgbl-1/2021/1"))
    useGetZielnormReleaseStatus(eli, { immediate: true, refetch: true })

    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormWorkEli.fromString("eli/bund/bgbl-1/2021/2")
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})
