import { describe, it, expect, vi, beforeEach } from "vitest"
import { ref } from "vue"

describe("useGetReferences", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("calls the API with the correct URL", async () => {
    const responseXml =
      '<?xml version="1.0" encoding="UTF-8"?><references></references>'
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValueOnce(
      new Response(responseXml, {
        headers: { "Content-Type": "application/xml" },
      }),
    )

    const { useGetReferences } = await import("./referencesService")
    const eli = "some-eli"
    const { data, isFinished } = useGetReferences(ref(eli))

    await vi.waitUntil(() => isFinished.value)

    expect(data.value).toBe(responseXml)
    expect(fetchSpy).toHaveBeenCalledWith(
      `/api/v1/references/${eli}`,
      expect.objectContaining({
        method: "POST",
        headers: {
          Accept: "application/xml",
          "Content-Type": "application/xml",
        },
      }),
    )
  })

  it("handles 404 response correctly", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(
        new Response('{"type":"/errors/example"}', { status: 404 }),
      )

    const { useGetReferences } = await import("./referencesService")
    const eli = "some-eli"
    const { error } = useGetReferences(eli).json()

    await vi.waitFor(() =>
      expect(error.value).toEqual({ type: "/errors/example" }),
    )

    expect(fetchSpy).toHaveBeenCalledWith(
      `/api/v1/references/${eli}`,
      expect.objectContaining({
        method: "POST",
        headers: {
          Accept: "application/xml",
          "Content-Type": "application/xml",
        },
      }),
    )
  })

  it("does not call the API if ELI is undefined", async () => {
    const fetchSpy = vi.spyOn(global, "fetch")

    const { useGetReferences } = await import("./referencesService")
    const eli = undefined
    const { data, isFinished } = useGetReferences(ref(eli))

    await vi.waitUntil(() => isFinished.value)

    expect(data.value).toBeNull()
    expect(fetchSpy).not.toHaveBeenCalled()
  })
})
