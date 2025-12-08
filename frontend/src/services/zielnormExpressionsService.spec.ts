import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"
import type { ZielnormPreviewSchema } from "@/types/zielnormPreview"
import type { z } from "zod"

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
    const fixture: z.input<typeof ZielnormPreviewSchema>[] = [
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
            isOrphan: false,
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

    const { useGetZielnormPreview } =
      await import("./zielnormExpressionsService")

    const result = useGetZielnormPreview(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    expect(result.data.value).toEqual([
      {
        title: "Beispielnorm",
        shortTitle: "Beispielnorm",
        normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2025/1"),
        expressions: [],
      },
      {
        title: "Beispielnorm 2",
        shortTitle: "Beispielnorm 2",
        normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2025/2"),
        expressions: [
          {
            createdBy: "diese Verkündung",
            isCreated: true,
            isGegenstandslos: false,
            isOrphan: false,
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
            ),
          },
        ],
      },
    ])

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormPreview } =
      await import("./zielnormExpressionsService")

    const eli = ref(undefined)
    useGetZielnormPreview(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormPreview } =
      await import("./zielnormExpressionsService")

    const eli = ref(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    useGetZielnormPreview(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})

describe("useCreateZielnormExpressions", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: z.input<typeof ZielnormPreviewSchema> = {
      title: "Beispielnorm 2",
      shortTitle: "Beispielnorm 2",
      normWorkEli: "eli/bund/bgbl-1/2025/2",
      expressions: [
        {
          createdBy: "diese Verkündung",
          isCreated: true,
          isGegenstandslos: false,
          isOrphan: false,
          normExpressionEli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        },
      ],
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      post: vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useCreateZielnormExpressions } =
      await import("./zielnormExpressionsService")

    const { data } = useCreateZielnormExpressions(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
      NormWorkEli.fromString("eli/bund/bgbl-1/2025/1"),
    )

    expect(data.value).toEqual({
      title: "Beispielnorm 2",
      shortTitle: "Beispielnorm 2",
      normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2025/2"),
      expressions: [
        {
          createdBy: "diese Verkündung",
          isCreated: true,
          isGegenstandslos: false,
          isOrphan: false,
          normExpressionEli: NormExpressionEli.fromString(
            "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
          ),
        },
      ],
    })

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the expression ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useCreateZielnormExpressions } =
      await import("./zielnormExpressionsService")

    const expressionEli = ref(undefined)
    const zielnormWorkEli = ref(
      NormWorkEli.fromString("eli/bund/bgbl-1/2025/1"),
    )
    useCreateZielnormExpressions(expressionEli, zielnormWorkEli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not load if the zielnormWork ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useCreateZielnormExpressions } =
      await import("./zielnormExpressionsService")

    const expressionEli = ref(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    const zielnormWorkEli = ref(undefined)
    useCreateZielnormExpressions(expressionEli, zielnormWorkEli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })
})
