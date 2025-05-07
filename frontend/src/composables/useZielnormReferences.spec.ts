import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { ErrorResponse } from "@/types/errorResponse"
import type { ZielnormReference } from "@/types/zielnormReference"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"
import { INDETERMINATE_VALUE } from "./useZielnormReferences"

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

  it("should return a Zielnorm reference for multiple eIds with different Geltungszeiten", async () => {
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

    expect(refs).toEqual({
      geltungszeit: INDETERMINATE_VALUE,
      zielnorm: "eli/bund/bgbl-1/2021/123",
    })
  })

  it("should return a Zielnorm reference for multiple eIds with different Zielnormen", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Aufhebung",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2021/456",
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

    expect(refs).toEqual({
      geltungszeit: "gz-1",
      zielnorm: INDETERMINATE_VALUE,
    })
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

    expect(refs).toEqual({
      geltungszeit: INDETERMINATE_VALUE,
      zielnorm: INDETERMINATE_VALUE,
    })
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

    const { isLoadingZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isLoadingZielnormReferences.value).toBeTruthy()
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

    const { loadZielnormReferencesError: loadError } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(loadError.value).toBeTruthy()
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

    let payload = undefined
    let didCallExecute = false

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>({ type: "/errors/example" }),
      }),
      usePostZielnormReferences: (data: () => ZielnormReference[]) => ({
        execute: vi.fn().mockImplementation(() => {
          payload = data()
          didCallExecute = true
        }),
        data: ref([]),
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

    const { updateZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    await updateZielnormReferences(
      fixtures[0],
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
    )

    expect(didCallExecute).toBeTruthy()
    expect(payload).toEqual(fixtures)
  })

  it("keeps the original data for indeterminate Zielnormen when updating data", async () => {
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
        zielnorm: "eli/bund/bgbl-1/2021/456",
      },
    ]

    let payload = undefined
    let didCallExecute = false

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: (data: () => ZielnormReference[]) => ({
        execute: vi.fn().mockImplementation(() => {
          payload = data()
          didCallExecute = true
        }),
        data: ref([]),
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

    const { updateZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    await updateZielnormReferences(
      { geltungszeit: "gz-2", zielnorm: INDETERMINATE_VALUE },
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
    )

    expect(didCallExecute).toBeTruthy()
    expect(payload).toEqual([
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2021/456",
      },
    ])
  })

  it("keeps the original data for indeterminate Geltungszeiten when updating data", async () => {
    const fixtures: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2021/123",
      },
    ]

    let payload = undefined
    let didCallExecute = false

    vi.doMock("@/services/zielnormReferenceService", () => ({
      useGetZielnormReferences: () => ({
        data: ref(fixtures),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
      usePostZielnormReferences: (data: () => ZielnormReference[]) => ({
        execute: vi.fn().mockImplementation(() => {
          payload = data()
          didCallExecute = true
        }),
        data: ref([]),
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

    const { updateZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    await updateZielnormReferences(
      {
        geltungszeit: INDETERMINATE_VALUE,
        zielnorm: "eli/bund/bgbl-1/2021/456",
      },
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
    )

    expect(didCallExecute).toBeTruthy()
    expect(payload).toEqual([
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/456",
      },
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2021/456",
      },
    ])
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

    const { isUpdatingZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isUpdatingZielnormReferences.value).toBeTruthy()
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

    const { updateZielnormReferencesError: updateError } =
      useZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
        ),
      )

    expect(updateError.value).toBeTruthy()
  })

  it("sends eIds to delete to the API and updates the local state", async () => {
    let payload = undefined
    let didCallExecute = false

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
      useDeleteZielnormReferences: (data: () => ZielnormReference[]) => ({
        execute: vi.fn().mockImplementation(() => {
          payload = data()
          didCallExecute = true
        }),
        data: ref([]),
        isFetching: ref(false),
        error: ref<ErrorResponse | null>(null),
      }),
    }))

    const { useZielnormReferences } = await import("./useZielnormReferences")

    const { deleteZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    await deleteZielnormReferences(
      "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
    )

    expect(didCallExecute).toBeTruthy()
    expect(payload).toEqual(["hauptteil-1_art-1_abs-1_untergl-1_listenelem-1"])
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

    const { isDeletingZielnormReferences } = useZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )

    expect(isDeletingZielnormReferences.value).toBeTruthy()
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

    const { deleteZielnormReferencesError: deleteError } =
      useZielnormReferences(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
        ),
      )

    expect(deleteError.value).toBeTruthy()
  })
})
