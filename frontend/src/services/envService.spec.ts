import { beforeEach, describe, expect, it, vi } from "vitest"
import { getEnv } from "./envService"

describe("envService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it("provides the data from the API", async () => {
    const fetchSpy = vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        JSON.stringify({
          name: "test",
          authClientId: "test-client",
          authRealm: "test-realm",
          authUrl: "http://test.url",
        }),
      ),
    )

    const result = await getEnv()

    expect(fetchSpy).toHaveBeenCalled()
    expect(result).toEqual({
      name: "test",
      authClientId: "test-client",
      authRealm: "test-realm",
      authUrl: "http://test.url",
    })
  })

  it("throws when the response contains a non-OK status code", async () => {
    vi.spyOn(window, "fetch").mockResolvedValue(
      new Response("{}", { status: 400 }),
    )

    await expect(() => getEnv()).rejects.toThrow()
  })
})
