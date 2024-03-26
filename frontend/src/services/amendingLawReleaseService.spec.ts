import { describe, it, expect, vi, beforeEach } from "vitest"

describe("amendingLawReleaseService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("putReleaseAmendingLawXml(eli)", () => {
    it("sends a PUT request and returns the release information", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchMock = vi.fn().mockResolvedValueOnce(mockReleaseResponse)

      vi.doMock("./apiService", () => ({
        apiFetch: fetchMock,
      }))

      const { putReleaseAmendingLawXml } = await import(
        "./amendingLawReleaseService"
      )

      const result = await putReleaseAmendingLawXml(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )

      expect(result).toEqual(mockReleaseResponse)
      expect(fetchMock).toHaveBeenCalledWith(
        `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/xml",
            Accept: "application/json",
          },
        },
      )
    })
  })

  describe("getAmendingLawReleaseStatus(eli)", () => {
    it("sends a GET request and returns the release status", async () => {
      const mockReleaseStatus = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      const fetchMock = vi.fn().mockResolvedValueOnce(mockReleaseStatus)

      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLawReleaseStatus } = await import(
        "./amendingLawReleaseService"
      )

      const result = await getAmendingLawReleaseStatus(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )

      expect(result).toEqual(mockReleaseStatus)
      expect(fetchMock).toHaveBeenCalledWith(
        `/amending-laws/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/release`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/xml",
            Accept: "application/json",
          },
        },
      )
    })
  })
})
