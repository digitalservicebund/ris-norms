import { INVALID_URL } from "@/services/apiService"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import * as Auth from "@/lib/auth"

vi.mock("@/lib/auth", () => ({
  useAuthentication: () => ({
    addAuthorizationHeader: vi.fn().mockImplementation((init) => init),
    tryRefresh: vi.fn().mockResolvedValue(true),
  }),
}))

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
      expect(error.value).toEqual({ type: "/errors/example", status: 404 })
    })
  })

  it("replaces the exception with a fallback error if the response does not contain data", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(null, { status: 404 }),
    )

    const { useApiFetch } = await import("@/services/apiService")

    const { error } = useApiFetch("/example", { immediate: true }).json()

    await vi.waitFor(() => {
      expect(error.value).toEqual({ type: "__fallback__", status: 404 })
    })
  })

  it("adds the Authorization header if available", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    vi.spyOn(Auth, "useAuthentication").mockReturnValue({
      addAuthorizationHeader: (init) => {
        return { ...init, Authorization: "Bearer 1234" }
      },
      configure: vi.fn().mockResolvedValue(undefined),
      isConfigured: vi.fn().mockReturnValue(true),
      getUsername: vi.fn().mockReturnValue("Jane Doe"),
      getLogoutLink: vi.fn().mockReturnValue(undefined),
      tryRefresh: vi.fn().mockResolvedValue(true),
    })

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/foo/bar",
        expect.objectContaining({
          headers: expect.objectContaining({ Authorization: "Bearer 1234" }),
        }),
      ),
    )
  })

  it("doesn't add an Authorization header if none is available", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/foo/bar",
        expect.objectContaining({
          headers: expect.not.objectContaining({
            Authorization: "Bearer 1234",
          }),
        }),
      ),
    )
  })

  it("attempts a token refresh before sending the request", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(new Response("{}"))

    const tryRefresh = vi.fn().mockResolvedValue(true)

    vi.spyOn(Auth, "useAuthentication").mockReturnValue({
      addAuthorizationHeader: (init) => {
        return { ...init, Authorization: "Bearer 1234" }
      },
      configure: vi.fn().mockResolvedValue(undefined),
      isConfigured: vi.fn().mockReturnValue(true),
      getUsername: vi.fn().mockReturnValue("Jane Doe"),
      getLogoutLink: vi.fn().mockReturnValue(undefined),
      tryRefresh,
    })

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await vi.waitFor(() => expect(tryRefresh).toHaveBeenCalled())
  })

  it("sends the request if the token refresh is successful", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const tryRefresh = vi.fn().mockResolvedValue(true)

    vi.spyOn(Auth, "useAuthentication").mockReturnValue({
      addAuthorizationHeader: (init) => {
        return { ...init, Authorization: "Bearer 1234" }
      },
      configure: vi.fn().mockResolvedValue(undefined),
      isConfigured: vi.fn().mockReturnValue(true),
      getUsername: vi.fn().mockReturnValue("Jane Doe"),
      getLogoutLink: vi.fn().mockReturnValue(undefined),
      tryRefresh,
    })

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalled())
  })

  it("aborts the request if the token refresh fails", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const tryRefresh = vi.fn().mockResolvedValue(false)

    vi.spyOn(Auth, "useAuthentication").mockReturnValue({
      addAuthorizationHeader: (init) => {
        return { ...init, Authorization: "Bearer 1234" }
      },
      configure: vi.fn().mockResolvedValue(undefined),
      isConfigured: vi.fn().mockReturnValue(true),
      getUsername: vi.fn().mockReturnValue("Jane Doe"),
      getLogoutLink: vi.fn().mockReturnValue(undefined),
      tryRefresh,
    })

    const { useApiFetch } = await import("@/services/apiService")

    useApiFetch("foo/bar")

    await flushPromises()

    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("parses string error responses (for .text() calls)", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        '{"type":"/errors/article-of-type-not-found","articleType":"geltungszeitregel"}',
        { status: 404 },
      ),
    )

    const { useApiFetch } = await import("@/services/apiService")

    const { error } = useApiFetch("/example", { immediate: true }).text()

    await vi.waitFor(() => {
      expect(error.value).toEqual({
        type: "/errors/article-of-type-not-found",
        articleType: "geltungszeitregel",
        status: 404,
      })
    })
  })
})
