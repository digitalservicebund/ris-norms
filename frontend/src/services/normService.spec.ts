import { Norm } from "@/types/norm"
import { flushPromises } from "@vue/test-utils"
import {
  afterAll,
  beforeAll,
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from "vitest"
import { ref } from "vue"

describe("normService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
    vi.doUnmock("./apiService.ts")
  })

  describe("getNormByEli(eli, options)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        frbrName: "bgbl-1",
        frbrDateVerkuendung: "2017-03-15",
        frbrNumber: "s419",
      })

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormByEli } = await import("./normService")

      const result = await getNormByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result.eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result.frbrDateVerkuendung).toBe("2017-03-15")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        undefined,
      )
    })

    it("passes on request options", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({})

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormByEli } = await import("./normService")

      const options = {
        signal: new AbortController().signal,
      }

      await getNormByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        options,
      )

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        options,
      )
    })
  })

  describe("getNormXmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<?xml version="1.0" encoding="UTF-8"?></xml>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormXmlByEli } = await import("./normService")

      const result = await getNormXmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/xml",
          }),
        }),
      )
    })
  })

  describe("useNormService", () => {
    beforeAll(() => {
      vi.useFakeTimers()
    })

    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    afterAll(() => {
      vi.useRealTimers()
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
  })
})
