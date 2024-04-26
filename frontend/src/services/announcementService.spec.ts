import { describe, it, expect, vi, beforeEach } from "vitest"

describe("announcementService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getAmendingLaws()", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce([
        {
          eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
          printAnnouncementGazette: "bgbl-1",
          printAnnouncementMedium: undefined,
          frbrDateVerkuendung: "2017-03-15",
          printAnnouncementPage: "419",
          digitalAnnouncementEdition: undefined,
          articles: [
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "1",
            },
            {
              eid: "article eid 2",
              title: "article eli 2",
              enumeration: "2",
            },
          ],
        },
      ])

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLaws } = await import("./announcementService")

      const result = await getAmendingLaws()
      expect(result.length).toBe(1)
      expect(result[0].eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      expect(fetchMock).toHaveBeenCalledWith("/announcements")
    })
  })
})
