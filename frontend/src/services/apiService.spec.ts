import { INVALID_URL } from "@/services/apiService"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"

describe("useApiFetch", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("defaults to JSON for request and response bodies", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/foo/bar",
        expect.objectContaining({
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
        }),
      ),
    )
  })

  it("allows replacing the content headers", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar", {
      beforeFetch(c) {
        c.options.headers = {
          ...c.options.headers,
          Accept: "text/html",
          "Content-Type": "text/html",
        }
      },
    })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/foo/bar",
        expect.objectContaining({
          headers: {
            Accept: "text/html",
            "Content-Type": "text/html",
          },
        }),
      ),
    )
  })

  it("aborts the request if the URL is marked as invalid", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch(INVALID_URL, { refetch: true })
    await flushPromises()

    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not set error if it is an abort error", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockRejectedValue(new DOMException("aborted", "AbortError"))

    const { useApiFetch } = await import("@/services/apiService")
    const { error } = useApiFetch("/url", { refetch: true })
    await flushPromises()

    expect(fetchSpy).toHaveBeenCalled()
    expect(error.value).toBeNull()
  })

  it("replaces the exception in case of an error with the response data", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response('{"type":"/errors/example"}', { status: 404 }),
    )

    const { useApiFetch } = await import("@/services/apiService")

    const { error } = useApiFetch("/example", { immediate: true }).json()

    await vi.waitFor(() => {
      expect(error.value).toEqual({ type: "/errors/example" })
    })
  })

  it("replaces the exception with a fallback error if the response does not contain data", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(null, { status: 404 }),
    )

    const { useApiFetch } = await import("@/services/apiService")

    const { error } = useApiFetch("/example", { immediate: true }).json()

    await vi.waitFor(() => {
      expect(error.value).toEqual({ type: "__fallback__" })
    })
  })
})
