import { describe, it, expect, vi, beforeEach } from "vitest"

describe("announcementService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useAmendingLaws()", () => {
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
              enumeration: "1",
            },
            {
              eid: "article eid 2",
              title: "article eli 2",
              enumeration: "2",
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

      const { useGetAmendingLaws } = await import(
        "@/services/announcementService"
      )

      const result = useGetAmendingLaws()

      const data = result.data.value!

      expect(data.length).toBe(1)
      expect(data[0].eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith("/announcements")
    })
  })
})
