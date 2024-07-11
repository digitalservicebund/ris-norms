import { describe, it, expect, vi, beforeEach } from "vitest"

describe("useNormRenderHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("calls the API with showMetadata set to true", async () => {
    const fetchSpy = vi
      .spyOn(global, "fetch")
      .mockResolvedValueOnce(new Response(`<html>Metadata shown</html>`))

    const xml = "<law></law>"
    const { useNormRenderHtml } = await import("./useNormRender")

    const { data, isFinished } = useNormRenderHtml(xml, true)
    await vi.waitUntil(() => isFinished.value)
    expect(data.value).toBe(`<html>Metadata shown</html>`)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?showMetadata=true",
      expect.objectContaining({
        method: "POST",
        headers: expect.objectContaining({
          Accept: "text/html",
          "Content-Type": "application/json",
        }),
        body: expect.stringContaining(xml),
      }),
    )
  })

  it("allows showMetadata to be explicitly set to false", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRenderHtml } = await import("./useNormRender")
    const { isFinished } = useNormRenderHtml("<law></law>", false)
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?showMetadata=false",
      expect.anything(),
    )
  })

  it("support setting a date", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRenderHtml } = await import("./useNormRender")
    const { isFinished } = useNormRenderHtml(
      "<law></law>",
      true,
      new Date(Date.UTC(2023, 0, 1)),
    )
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?showMetadata=true&atIsoDate=2023-01-01T00%3A00%3A00.000Z",
      expect.anything(),
    )
  })

  it("support setting custom norms", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRenderHtml } = await import("./useNormRender")

    const { isFinished } = useNormRenderHtml("<law></law>", true, undefined, [
      "<xml>other-norm</xml>",
    ])
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?showMetadata=true",
      expect.objectContaining({
        body: expect.stringMatching("<xml>other-norm</xml>"),
      }),
    )
  })
})

describe("useNormRenderXml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("calls the API without date", async () => {
    const responseBody = `<?xml version="1.0" encoding="UTF-8"?><law>applied-passive-modification</law>`
    const fetchSpy = vi
      .spyOn(global, "fetch")
      .mockResolvedValueOnce(new Response(responseBody))

    const xml = "<law></law>"
    const { useNormRenderXml } = await import("./useNormRender")

    const { data, isFinished } = useNormRenderXml(xml)
    await vi.waitUntil(() => isFinished.value)
    expect(data.value).toBe(responseBody)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings",
      expect.objectContaining({
        method: "POST",
        headers: expect.objectContaining({
          Accept: "application/xml",
          "Content-Type": "application/json",
        }),
        body: expect.stringContaining(xml),
      }),
    )
  })

  it("support setting a date", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRenderXml } = await import("./useNormRender")
    const { isFinished } = useNormRenderXml(
      "<law></law>",
      new Date(Date.UTC(2023, 0, 1)),
    )
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?atIsoDate=2023-01-01T00%3A00%3A00.000Z",
      expect.anything(),
    )
  })

  it("support setting custom norms", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRenderXml } = await import("./useNormRender")

    const { isFinished } = useNormRenderXml("<law></law>", undefined, [
      "<xml>other-norm</xml>",
    ])
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings",
      expect.objectContaining({
        body: expect.stringMatching("<xml>other-norm</xml>"),
      }),
    )
  })
})
