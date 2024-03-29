import { describe, it, expect, vi, beforeEach } from "vitest"

describe("targetLawsService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getTargetLawByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        title:
          "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
      })

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getTargetLawByEli } = await import("./targetLawsService")

      const result = await getTargetLawByEli(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
      expect(result.eli).toBe(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
      expect(result.title).toBe(
        "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
      )

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
    })
  })

  describe("getTargetLawXmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<?xml version="1.0" encoding="UTF-8"?></xml>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getTargetLawXmlByEli } = await import("./targetLawsService")

      const result = await getTargetLawXmlByEli(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/xml",
          }),
        }),
      )
    })
  })

  describe("getTargetLawHtmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<span></span>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getTargetLawHtmlByEli } = await import("./targetLawsService")

      const result = await getTargetLawHtmlByEli(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
      expect(result).toBe("<span></span>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })
  })

  describe("putTargetLawXml(eli, xml)", () => {
    it("sends the data to the API", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce('<?xml version="1.0" encoding="UTF-8"?></xml>')

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { putTargetLawXml } = await import("./targetLawsService")

      const result = await putTargetLawXml(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        '<?xml version="1.0" encoding="UTF-8"?></xml>',
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
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

  describe("previewTargetLaw(eli, xml)", () => {
    it("calls the api correctly", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<?xml version="1.0" encoding="UTF-8"?></xml>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { previewTargetLaw } = await import("./targetLawsService")

      const result = await previewTargetLaw(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml></xml>",
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview",
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

      const { previewTargetLawAsHtml } = await import("./targetLawsService")

      const result = await previewTargetLawAsHtml(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml></xml>",
      )
      expect(result).toBe("<span></span>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/target-laws/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/preview",
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
