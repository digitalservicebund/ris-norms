import { describe, it, expect, vi, beforeEach } from "vitest"

describe("renderService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("renderHtmlLaw(xml, showMetadata)", () => {
    it("calls the API with showMetadata defaulting to true", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<html>Metadata shown</html>`)
      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const xml = "<law></law>"
      const { renderHtmlLaw } = await import("./renderService")

      const result = await renderHtmlLaw(xml)
      expect(result).toBe(`<html>Metadata shown</html>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "renderings?showMetadata=true",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/xml",
          }),
          body: xml,
        }),
      )
    })

    it("explicitly sets showMetadata to true and calls the API", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<html>Metadata explicitly shown</html>`)
      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const xml = "<law></law>"
      const { renderHtmlLaw } = await import("./renderService")

      const result = await renderHtmlLaw(xml, true)
      expect(result).toBe(`<html>Metadata explicitly shown</html>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "renderings?showMetadata=true",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/xml",
          }),
          body: xml,
        }),
      )
    })

    it("allows showMetadata to be explicitly set to false", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<html>Metadata not shown</html>`)
      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const xml = "<law></law>"
      const { renderHtmlLaw } = await import("./renderService")

      const result = await renderHtmlLaw(xml, false)
      expect(result).toBe(`<html>Metadata not shown</html>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "renderings?showMetadata=false",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/xml",
          }),
          body: xml,
        }),
      )
    })
  })
})
