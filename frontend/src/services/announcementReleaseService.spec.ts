import { describe, it, vi, beforeEach, expect } from "vitest"
import { nextTick, ref } from "vue"

describe("announcementReleaseService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGetRelease", () => {
    it("sends a GET request and returns the release status", async () => {
      const expectedRelease = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(expectedRelease)))

      const { useGetRelease } = await import("./announcementReleaseService")

      const { isFinished, data } = useGetRelease(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedRelease)

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release`,
        expect.objectContaining({
          method: "GET",
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      )
    })

    it("reacts to eli changes", async () => {
      const expectedRelease = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response(JSON.stringify(expectedRelease)))
        .mockResolvedValueOnce(new Response(JSON.stringify(null)))

      const { useGetRelease } = await import("./announcementReleaseService")

      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { isFinished, data } = useGetRelease(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedRelease)

      eli.value = "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1"
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(null)

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release`,
        expect.anything(),
      )

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/release`,
        expect.anything(),
      )
    })
  })

  describe("usePutRelease", () => {
    it("sends a PUT request and returns the release information", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(mockReleaseResponse)))

      const { usePutRelease } = await import("./announcementReleaseService")

      const { data, execute } = usePutRelease(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      await execute()

      expect(data.value).toEqual(mockReleaseResponse)

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release`,
        expect.objectContaining({
          method: "PUT",
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      )
    })

    it("reacts to eli changes and does not auto-refetch", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(
          new Response(JSON.stringify(mockReleaseResponse)),
        )

      const { usePutRelease } = await import("./announcementReleaseService")

      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { data, execute } = usePutRelease(eli)
      eli.value = "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1"
      await nextTick()
      await execute()

      expect(data.value).toEqual(mockReleaseResponse)

      expect(fetchSpy).toHaveBeenCalledOnce()
      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/release`,
        expect.objectContaining({
          method: "PUT",
        }),
      )
    })
  })
})
