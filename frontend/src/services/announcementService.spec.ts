import { describe, it, expect, vi, beforeEach } from "vitest"
import { ref } from "vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

describe("announcementService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useAnnouncements()", () => {
    it("provides the data from the api", async () => {
      const mockData = [
        {
          eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
          frbrName: "bgbl-1",
          frbrDateVerkuendung: "2017-03-15",
          frbrNumber: "s419",
          articles: [
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "Artikel 1",
            },
            {
              eid: "article eid 2",
              title: "article eli 2",
              enumeration: "Artikel 2",
            },
          ],
        },
      ]

      const useFetchMock = {
        data: { value: mockData },
        error: { value: null },
        isFetching: { value: false },
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue({
          json: vi.fn().mockReturnValue(useFetchMock),
        }),
      }))

      const { useAnnouncementsService } = await import(
        "@/services/announcementService"
      )

      const result = useAnnouncementsService()

      const data = result.data.value!

      expect(data.length).toBe(1)
      expect(data[0].eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith("/announcements")
    })
  })

  describe("useGetAnnouncementService()", () => {
    it("fetches a specific announcement by its ELI", async () => {
      const mockAnnouncement = {
        eli: "eli/bund/bgbl-1/2025/s65/2025-02-27/1/deu/regelungstext-1",
        title: "Gesetz zur Anpassung des Mutterschutzgesetzes",
        frbrDateVerkuendung: "27.02.2025",
        frbrDateAusfertigung: "24.02.2025",
        importTimestamp: "24.02.2025, 08:12",
        fna: "8052-5, 860-5, 2030-2-30-2, 51-1-23",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        data: ref(mockAnnouncement),
        json: vi.fn().mockReturnValue({
          data: ref(mockAnnouncement),
          error: ref(null),
          isFetching: ref(false),
          isFinished: ref(true),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetAnnouncementService } = await import(
        "@/services/announcementService"
      )

      const eliString =
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"
      const eli = ref<NormExpressionEli>(
        NormExpressionEli.fromString(eliString),
      )

      const { data } = useGetAnnouncementService(eli)

      expect(data.value).toEqual(mockAnnouncement)
      expect(data.value?.title).toBe(
        "Gesetz zur Anpassung des Mutterschutzgesetzes",
      )
      expect(data.value?.frbrDateVerkuendung).toBe("27.02.2025")
      expect(data.value?.frbrDateAusfertigung).toBe("24.02.2025")
      expect(data.value?.importTimestamp).toBe("24.02.2025, 08:12")
      expect(data.value?.fna).toBe("8052-5, 860-5, 2030-2-30-2, 51-1-23")
      expect(useApiFetch).toHaveBeenCalledTimes(1)
    })
  })
})
