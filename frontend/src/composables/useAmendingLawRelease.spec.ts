import { describe, it, expect, vi, beforeEach } from "vitest"
import { ref } from "vue"
import { useAmendingLawRelease } from "./useAmendingLawRelease"
import * as announcementReleaseService from "@/services/announcementReleaseService"

describe("useAmendingLawRelease", () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  describe("fetchReleaseStatus", () => {
    it("should fetch the release status and update state", async () => {
      const mockReleaseStatus = {
        releaseAt: "2024-03-25T10:37:29.658954Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
        ],
      }

      vi.spyOn(announcementReleaseService, "getRelease").mockResolvedValue(
        mockReleaseStatus,
      )

      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { releasedAt, releasedElis, fetchReleaseStatus } =
        useAmendingLawRelease(eli)

      await fetchReleaseStatus()

      expect(releasedAt.value).toBeInstanceOf(Date)
      expect(releasedElis.value).toEqual(mockReleaseStatus)
    })
  })

  describe("releaseAmendingLaw", () => {
    it("should send a release request and update state with the new release info", async () => {
      const mockReleaseResponse = {
        releaseAt: "2024-03-25T11:00:00.000Z",
        amendingLawEli:
          "eli/bund/bgbl-1/2023/414/2023-12-30/1/deu/regelungstext-1",
        zf0Elis: [
          "eli/bund/bgbl-1/1991/s2955/2023-12-30/1/deu/regelungstext-2",
        ],
      }

      vi.spyOn(announcementReleaseService, "putRelease").mockResolvedValue(
        mockReleaseResponse,
      )

      const eli = ref(
        "eli/bund/bgbl-1/2023/414/2023-12-30/1/deu/regelungstext-1",
      )
      const { releasedAt, releasedElis, releaseAmendingLaw } =
        useAmendingLawRelease(eli)

      await releaseAmendingLaw()

      expect(releasedAt.value).toBeInstanceOf(Date)
      expect(releasedElis.value).toEqual(mockReleaseResponse)
    })
  })
})
