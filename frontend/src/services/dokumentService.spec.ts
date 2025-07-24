import type { Norm } from "@/types/norm"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("useDokumentService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: Norm = {
      eli: DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
      title: "Example norm",
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useDokumentService } = await import("./dokumentService")

    const result = useDokumentService(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDokumentService } = await import("./dokumentService")

    const eli = ref(undefined)
    useDokumentService(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDokumentService } = await import("./dokumentService")

    const eli = ref<DokumentExpressionEli | undefined>(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useDokumentService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = undefined
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDokumentService } = await import("./dokumentService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useDokumentService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("appends the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDokumentService } = await import("./dokumentService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useDokumentService(eli, { showMetadata: true })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
        expect.any(Object),
      ),
    )
  })

  it("does not append the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useDokumentService } = await import("./dokumentService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useDokumentService(eli, { showMetadata: false })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
        expect.any(Object),
      ),
    )
  })

  describe("useGetDokumentHtml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetDokumentHtml } = await import("./dokumentService")

      useGetDokumentHtml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        {
          showMetadata: true,
        },
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetDokumentHtml } = await import("./dokumentService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetDokumentHtml(eli, {
        showMetadata: true,
      })

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.any(Object),
        )
      })
    })
  })

  describe("useGetDokumentXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetDokumentXml } = await import("./dokumentService")

      useGetDokumentXml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
            }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetDokumentXml } = await import("./dokumentService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetDokumentXml(eli)

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
            }),
          }),
        ),
      )

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1?",
          expect.any(Object),
        )
      })
    })
  })

  describe("usePutDokumentXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API on changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutDokumentXml } = await import("./dokumentService")

      const xml = ref("oldXml")
      const { execute } = usePutDokumentXml(
        xml,
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      xml.value = "newXml"
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
              "Content-Type": "application/xml",
            }),
            method: "PUT",
            body: "newXml",
          }),
        ),
      )
    })
  })
})
