import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { ZielnormReference } from "@/types/zielnormReference"
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

describe("useZielnormReferencesService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
        isNewWork: false,
      },
      {
        typ: "Aufhebung",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2019/789",
        isNewWork: false,
      },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useZielnormReferencesService } =
      await import("./zielnormReferenceService")

    const result = useZielnormReferencesService(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    expect(result.data.value).toEqual(fixture)

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useZielnormReferencesService } =
      await import("./zielnormReferenceService")

    const eli = ref(undefined)
    useZielnormReferencesService(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useZielnormReferencesService } =
      await import("./zielnormReferenceService")

    const eli = ref(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    useZielnormReferencesService(eli, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})

describe("useGetZielnormReferences", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: ZielnormReference[] = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
        isNewWork: false,
      },
      {
        typ: "Aufhebung",
        geltungszeit: "gz-2",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2",
        zielnorm: "eli/bund/bgbl-1/2019/789",
        isNewWork: false,
      },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      json: vi.fn().mockReturnValue({ data: ref(fixture) }),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useGetZielnormReferences } =
      await import("./zielnormReferenceService")

    const result = useGetZielnormReferences(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    expect(result.data.value).toEqual(fixture)

    vi.doUnmock("@/services/apiService")
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("[]"))

    const { useGetZielnormReferences } =
      await import("./zielnormReferenceService")

    const eli = ref(
      NormExpressionEli.fromString("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu"),
    )
    useGetZielnormReferences(eli)
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })
})

describe("usePostZielnormReferences", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("sends the data to the API on changes", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { usePostZielnormReferences } =
      await import("./zielnormReferenceService")

    const data = ref<ZielnormReference[]>([])
    const { execute } = usePostZielnormReferences(
      data,
      NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )

    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()

    data.value = [
      {
        typ: "Änderungsvorschrift",
        geltungszeit: "gz-1",
        eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
        zielnorm: "eli/bund/bgbl-1/2021/123",
        isNewWork: false,
      },
    ]
    execute()

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/zielnorm-references",
        expect.objectContaining({
          headers: expect.objectContaining({ Accept: "application/json" }),
          method: "POST",
          body: JSON.stringify([
            {
              typ: "Änderungsvorschrift",
              geltungszeit: "gz-1",
              eId: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1",
              zielnorm: "eli/bund/bgbl-1/2021/123",
              isNewWork: false,
            },
          ]),
        }),
      ),
    )
  })
})

describe("useDeleteZielnormReferences", async () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("deletes Zielnormen references", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDeleteZielnormReferences } =
      await import("./zielnormReferenceService")

    const data = ref<string[]>([])
    const { execute } = useDeleteZielnormReferences(
      data,
      NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )

    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()

    data.value = ["eid-1", "eid-2"]
    execute()

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/zielnorm-references",
        expect.objectContaining({
          headers: expect.objectContaining({ Accept: "application/json" }),
          method: "DELETE",
          body: JSON.stringify(["eid-1", "eid-2"]),
        }),
      ),
    )
  })
})
