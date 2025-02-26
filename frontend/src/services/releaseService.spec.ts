import { describe, it, vi, beforeEach, expect } from "vitest"
import { nextTick, ref } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("releaseService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGetReleases", () => {
    it("sends a GET request and returns the releases", async () => {
      const expectedReleases = [
        {
          releaseAt: "2024-03-25T10:37:29.658954Z",
          norms: [
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
            "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
          ],
        },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(expectedReleases)))

      const { useGetReleases } = await import("./releaseService")

      const { isFinished, data } = useGetReleases(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedReleases)

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases`,
        expect.objectContaining({
          method: "GET",
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      )
    })

    it("reacts to eli changes", async () => {
      const expectedReleases = [
        {
          releaseAt: "2024-03-25T10:37:29.658954Z",
          norms: [
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
            "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
          ],
        },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response(JSON.stringify(expectedReleases)))
        .mockResolvedValueOnce(new Response(JSON.stringify([])))

      const { useGetReleases } = await import("./releaseService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const { isFinished, data } = useGetReleases(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedReleases)

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1",
      )
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual([])

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases`,
        expect.anything(),
      )

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/releases`,
        expect.anything(),
      )
    })
  })

  describe("usePostRelease", () => {
    it("sends a POST request and returns the release information", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        norms: [
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
          "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(mockReleaseResponse)))

      const { usePostRelease } = await import("./releaseService")

      const { data, execute } = usePostRelease(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      await execute()

      expect(data.value).toEqual(mockReleaseResponse)

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/releases`,
        expect.objectContaining({
          method: "POST",
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      )
    })

    it("reacts to eli changes and does not auto-refetch", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        norms: [
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
          "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(
          new Response(JSON.stringify(mockReleaseResponse)),
        )

      const { usePostRelease } = await import("./releaseService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const { data, execute } = usePostRelease(eli)
      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1",
      )
      await nextTick()
      await execute()

      expect(data.value).toEqual(mockReleaseResponse)

      expect(fetchSpy).toHaveBeenCalledOnce()
      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/releases`,
        expect.objectContaining({
          method: "POST",
        }),
      )
    })
  })
})
