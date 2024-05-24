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
        "renderings",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/json",
          }),
          query: expect.objectContaining({
            showMetadata: true,
          }),
          body: expect.objectContaining({
            norm: xml,
          }),
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
        "renderings",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/json",
          }),
          query: expect.objectContaining({
            showMetadata: true,
          }),
          body: expect.objectContaining({
            norm: xml,
          }),
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
        "renderings",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/json",
          }),
          query: expect.objectContaining({
            showMetadata: false,
          }),
          body: expect.objectContaining({
            norm: xml,
          }),
        }),
      )
    })

    it("support setting a data", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<html></html>`)
      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const xml = "<law></law>"
      const { renderHtmlLaw } = await import("./renderService")

      const result = await renderHtmlLaw(
        xml,
        true,
        new Date(Date.UTC(2023, 0, 1)),
      )
      expect(result).toBe(`<html></html>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "renderings",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/json",
          }),
          query: expect.objectContaining({
            showMetadata: true,
            atIsoDate: "2023-01-01T00:00:00.000Z",
          }),
          body: expect.objectContaining({
            norm: xml,
          }),
        }),
      )
    })

    it("support setting custom norms", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<html></html>`)
      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const { renderHtmlLaw } = await import("./renderService")

      const result = await renderHtmlLaw("<law></law>", true, undefined, {
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1":
          "<xml>other-norm</xml>",
      })
      expect(result).toBe(`<html></html>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "renderings",
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "text/html",
            "Content-Type": "application/json",
          }),
          query: expect.objectContaining({
            showMetadata: true,
          }),
          body: expect.objectContaining({
            norm: "<law></law>",
            customNorms: {
              "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1":
                "<xml>other-norm</xml>",
            },
          }),
        }),
      )
    })
  })
})
