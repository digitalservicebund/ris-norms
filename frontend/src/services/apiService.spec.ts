import { INVALID_URL } from "@/services/apiService"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, test, vi } from "vitest"

describe("useApiFetch", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  test("defaults to JSON for request and response bodies", async () => {
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

  test("allows replacing the content headers", async () => {
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

  test("redirects to the 404 page if the API returns a 404", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response("{}", { status: 404 }),
    )

    const { useApiFetch, initializeApiService } = await import(
      "@/services/apiService"
    )

    const mockRouter = {
      push: vi.fn().mockResolvedValue(new Promise(() => {})),
    }
    // @ts-expect-error I know it's not a real router
    initializeApiService(mockRouter)

    useApiFetch("foo/bar")

    await vi.waitFor(() =>
      expect(mockRouter.push).toHaveBeenCalledWith({ name: "NotFound" }),
    )
  })

  test("aborts the request if the URL is marked as invalid", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch(INVALID_URL, { refetch: true })
    await flushPromises()

    expect(fetchSpy).not.toHaveBeenCalled()
  })
})
