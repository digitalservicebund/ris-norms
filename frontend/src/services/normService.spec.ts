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

  describe("useGetNormXml(eli, xml)", () => {
    it("provides the data from the api", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(
          new Response('<?xml version="1.0" encoding="UTF-8"?></xml>'),
        )

      const { useGetNormXml } = await import("./normService")

      const { data: result, isFinished } = useGetNormXml(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      await vi.waitUntil(() => isFinished.value)

      expect(result.value).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          method: "GET",
          headers: expect.objectContaining({
            Accept: "application/xml",
          }),
        }),
      )
    })
  })

  describe("getNormHtmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormHtmlByEli } = await import("./normService")

      const result = await getNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result).toBe("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          query: expect.objectContaining({
            showMetadata: false,
          }),
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })

    it("allows showMetadata to be explicitly set to true", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormHtmlByEli } = await import("./normService")

      const result = await getNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        true,
      )
      expect(result).toBe("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          query: expect.objectContaining({
            showMetadata: true,
          }),
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })

    it("provides the data from the api with at-date", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getNormHtmlByEli } = await import("./normService")

      const date = new Date(Date.UTC(2023, 11, 11, 1, 2, 3, 4))
      const result = await getNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        false,
        date,
      )

      expect(result).toEqual("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          query: expect.objectContaining({
            atIsoDate: "2023-12-11T01:02:03.004Z",
          }),
          headers: expect.objectContaining({ Accept: "text/html" }),
        }),
      )
    })
  })

  describe("usePutNormXml(eli, xml)", () => {
    it("sends the data to the api", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(
          new Response('<?xml version="1.0" encoding="UTF-8"?></xml>'),
        )

      const { usePutNormXml } = await import("./normService")

      const { data: result, execute } = usePutNormXml(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        '<?xml version="1.0" encoding="UTF-8"?></xml>',
      )
      await execute()

      expect(result.value).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          method: "PUT",
          headers: expect.objectContaining({
            "Content-Type": "application/xml",
            Accept: "application/xml",
          }),
          body: '<?xml version="1.0" encoding="UTF-8"?></xml>',
        }),
      )
    })
  })

  describe("useGetNormHtmlByEli(eli, showMetadata, at)", () => {
    it("provides the HTML data from the api", async () => {
      const mockHtml = "<div></div>"

      const useFetchMock = {
        text: vi.fn().mockReturnValue(mockHtml),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useGetNormHtmlByEli } = await import("./normService")

      const result = useGetNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      expect(result).toBe(mockHtml)

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?showMetadata=false",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })

    it("allows showMetadata to be explicitly set to true", async () => {
      const mockHtml = "<div></div>"

      const useFetchMock = {
        text: vi.fn().mockReturnValue(mockHtml),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useGetNormHtmlByEli } = await import("./normService")

      const result = useGetNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        true,
      )

      expect(result).toBe(mockHtml)

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?showMetadata=true",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })

    it("provides the HTML data from the api with at-date", async () => {
      const mockHtml = "<div></div>"

      const useFetchMock = {
        text: vi.fn().mockReturnValue(mockHtml),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useGetNormHtmlByEli } = await import("./normService")

      const date = new Date(Date.UTC(2023, 11, 11, 1, 2, 3, 4))
      const result = useGetNormHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        false,
        date,
      )

      expect(result).toBe(mockHtml)

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1?showMetadata=false&atIsoDate=2023-12-11T01%3A02%3A03.004Z",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
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
      useNormService(eli)
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
