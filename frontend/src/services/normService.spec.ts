import { Norm } from "@/types/norm"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("useNormService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: Norm = {
      eli: "fake/eli",
      title: "Example norm",
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useNormService } = await import("./normService")

    const result = useNormService("fake/eli")
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("")
    useNormService(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = ""
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = "fake/eli/2"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("appends the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, { showMetadata: true })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1?showMetadata=true",
        expect.any(Object),
      ),
    )
  })

  it("does not append the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, { showMetadata: false })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1?",
        expect.any(Object),
      ),
    )
  })

  it("appends the date parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, { at: new Date(2024, 5, 13) })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1?atIsoDate=2024-06-13T00%3A00%3A00.000Z",
        expect.any(Object),
      ),
    )
  })

  it("does not append the date parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref("fake/eli/1")
    useNormService(eli, { at: undefined })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1?",
        expect.any(Object),
      ),
    )
  })

  it("reloads with a new date value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const date = ref<Date | undefined>(new Date(2024, 5, 13))
    useNormService(
      "fake/eli/1",
      { at: date },
      { immediate: true, refetch: true },
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    date.value = undefined
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  describe("useGetNorm", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNorm } = await import("./normService")

      useGetNorm("fake/eli/1")

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1?",
          expect.objectContaining({
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
            },
          }),
        ),
      )
    })
  })

  describe("useGetNormHtml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormHtml } = await import("./normService")

      useGetNormHtml("fake/eli/1", {
        showMetadata: true,
        at: new Date("2024-05-13"),
      })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1?showMetadata=true&atIsoDate=2024-05-13T00%3A00%3A00.000Z",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        ),
      )
    })
  })

  describe("useGetNormXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormXml } = await import("./normService")

      useGetNormXml("fake/eli/1", {
        showMetadata: true,
        at: new Date("2024-05-13"),
      })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1?showMetadata=true&atIsoDate=2024-05-13T00%3A00%3A00.000Z",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
            }),
          }),
        ),
      )
    })
  })

  describe("usePutNormXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutNormXml } = await import("./normService")

      usePutNormXml("newXml", "fake/eli/1", undefined, { immediate: true })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
              "Content-Type": "application/xml",
            }),
            method: "PUT",
          }),
        ),
      )
    })
  })
})
