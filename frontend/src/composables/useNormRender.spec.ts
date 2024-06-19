import { describe, it, expect, vi, beforeEach } from "vitest"

describe("useNormRender", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("calls the API with showMetadata set to true", async () => {
    const fetchSpy = vi
      .spyOn(global, "fetch")
      .mockResolvedValueOnce(new Response(`<html>Metadata shown</html>`))

    const xml = "<law></law>"
    const { useNormRender } = await import("./useNormRender")

    const { data, isFinished } = useNormRender(xml, true)
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

    const { useNormRender } = await import("./useNormRender")
    const { isFinished } = useNormRender("<law></law>", false)
    await vi.waitUntil(() => isFinished.value)

    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/renderings?showMetadata=false",
      expect.anything(),
    )
  })

  it("support setting a date", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValue(new Response())

    const { useNormRender } = await import("./useNormRender")
    const { isFinished } = useNormRender(
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

    const { useNormRender } = await import("./useNormRender")

    const { isFinished } = useNormRender("<law></law>", true, undefined, [
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
