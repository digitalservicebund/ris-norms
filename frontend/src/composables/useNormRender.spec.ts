import { describe, it, expect, vi, beforeEach } from "vitest"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

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

    const { data, isFinished } = useNormRenderHtml(xml, { showMetadata: true })
    await vi.waitUntil(() => isFinished.value)
    expect(data.value).toBe(`<html>Metadata shown</html>`)

    expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
      "/api/v1/renderings?showMetadata=true",
      expect.objectContaining({
        method: "POST",
        headers: expect.objectContaining({
          Accept: "text/html",
          "Content-Type": "application/xml",
        }),
        body: expect.stringContaining(xml),
      }),
    )
  })

  it("calls the API with snippet set to true", async () => {
    const fetchSpy = vi
      .spyOn(global, "fetch")
      .mockResolvedValueOnce(new Response(`<html>Metadata shown</html>`))

    const xml = "<law></law>"
    const { useNormRenderHtml } = await import("./useNormRender")

    const { data, isFinished } = useNormRenderHtml(xml, { snippet: true })
    await vi.waitUntil(() => isFinished.value)
    expect(data.value).toBe(`<html>Metadata shown</html>`)

    expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
      "/api/v1/renderings?snippet=true",
      expect.objectContaining({
        method: "POST",
        headers: expect.objectContaining({
          Accept: "text/html",
          "Content-Type": "application/xml",
        }),
        body: expect.stringContaining(xml),
      }),
    )
  })
})
