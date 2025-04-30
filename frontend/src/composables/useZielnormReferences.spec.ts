import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { ErrorResponse } from "@/types/errorResponse"
import type { ZielnormReference } from "@/types/zielnormReference"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("useZielnormReferences", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("loads Zielnorm references", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(zielnormReferences.value).toEqual(fixtures)
  })

  it("should return the existing Zielnorm reference for a single eId", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferencesForEid } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    const refs = zielnormReferencesForEid(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
    )

    expect(refs).toEqual({
      geltungszeit: "gz-1",
      zielnorm: "eli/bund/bgbl-1/2021/123",
    })
  })

  it("should return a new, empty Zielnorm reference for a single eId that doesn't exist", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferencesForEid } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    const refs = zielnormReferencesForEid("fake-eid")

    expect(refs).toEqual({ geltungszeit: "", zielnorm: "" })
  })

  it("should return a Zielnorm reference for multiple eIds with identical data", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-3",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferencesForEid } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    const refs = zielnormReferencesForEid(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-3",
    )

    expect(refs).toEqual({
      geltungszeit: "gz-1",
      zielnorm: "eli/bund/bgbl-1/2021/123",
    })
  })

  it("should return a Zielnorm reference for multiple eIds with different data", async () => {
    const fixtures: ZielnormReference[] = [
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

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferencesForEid } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    const refs = zielnormReferencesForEid(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
    )

    expect(refs).toEqual({ geltungszeit: "", zielnorm: "" })
  })

  it("should return a new, empty Zielnorm reference for multiple eIds that don't exist", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { zielnormReferencesForEid } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    const refs = zielnormReferencesForEid(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
    )

    expect(refs).toEqual({ geltungszeit: "", zielnorm: "" })
  })

  it("sets the fetching state while loading Zielnorm references", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(true),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { isFetching } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isFetching.value).toBeTruthy()
  })

  it("returns errors when loading Zielnorm references", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { error } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(error.value).toBeTruthy()
  })

  it("sends updated data to the API and updates the local state", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    const updateData = ref<ZielnormReference[]>([])
    const execute = vi.fn().mockImplementation(() => {
      updateData.value = fixtures
    })

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
      usePostZielnormReferences: () => ({
        execute,
        data: updateData,
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { updateZielnormReferences, zielnormReferences } =
      useZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
        ),
      )

    await updateZielnormReferences(
      fixtures[0],
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
    )

    expect(execute).toHaveBeenCalled()
    expect(zielnormReferences.value).toEqual(fixtures)
  })

  it("sets the loading state while updating Zielnormen data", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(true),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { isFetching } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isFetching.value).toBeTruthy()
  })

  it("returns errors when updating Zielnorm references", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { error } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(error.value).toBeTruthy()
  })

  it("sends eIds to delete to the API and updates the local state", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    const updateData = ref<ZielnormReference[]>(fixtures)
    const execute = vi.fn().mockImplementation(() => {
      updateData.value = fixtures
    })

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: execute,
        data: updateData,
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { deleteZielnormReferences, zielnormReferences } =
      useZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
        ),
      )

    await deleteZielnormReferences(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
    )

    expect(execute).toHaveBeenCalled()
    expect(zielnormReferences.value).toHaveLength(0)
  })

  it("sets the loading state while deleting Zielnormen data", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(true),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { isFetching } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isFetching.value).toBeTruthy()
  })

  it("returns errors when deleting Zielnorm references", async () => {
    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      useDeleteZielnormReferences: () => ({
        execute: vi.fn(),
        data: ref(null),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { error } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(error.value).toBeTruthy()
  })
})
