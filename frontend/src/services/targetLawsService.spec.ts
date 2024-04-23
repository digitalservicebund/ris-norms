import { describe, it, expect, vi, beforeEach } from "vitest"

describe("targetLawsService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
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

      const { previewTargetLawAsHtml } = await import("./targetLawsService")

      const result = await previewTargetLawAsHtml(
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
