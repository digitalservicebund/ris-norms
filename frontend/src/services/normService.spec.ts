import { describe, it, expect, vi, beforeEach } from "vitest"

describe("normService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getNormByEli(eli, options)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        printAnnouncementGazette: "bgbl-1",
        printAnnouncementMedium: undefined,
        publicationDate: "2017-03-15",
        printAnnouncementPage: "419",
        digitalAnnouncementEdition: undefined,
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
      expect(result.publicationDate).toBe("2017-03-15")

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
  })

  describe("putNormXml(eli, xml)", () => {
    it("sends the data to the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce('<?xml version="1.0" encoding="UTF-8"?></xml>')

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { putNormXml } = await import("./normService")

      const result = await putNormXml(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        '<?xml version="1.0" encoding="UTF-8"?></xml>',
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
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

  describe("previewNorm(eli, xml)", () => {
    it("calls the api correctly", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<?xml version="1.0" encoding="UTF-8"?></xml>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { previewNorm } = await import("./normService")

      const result = await previewNorm(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml></xml>",
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview",
        expect.objectContaining({
          method: "POST",
          body: "<xml></xml>",
          headers: expect.objectContaining({
            Accept: "application/xml",
            "Content-Type": "application/xml",
          }),
        }),
      )
    })
  })

  describe("previewTargetLawAsHtml(eli, xml)", () => {
    it("calls the api correctly", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<span></span>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { previewNormAsHtml } = await import("./normService")

      const result = await previewNormAsHtml(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml></xml>",
      )
      expect(result).toBe("<span></span>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview",
        expect.objectContaining({
          method: "POST",
          body: "<xml></xml>",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/xml",
          }),
        }),
      )
    })
  })
})
