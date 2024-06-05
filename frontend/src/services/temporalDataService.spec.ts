import { describe, it, expect, vi, beforeEach } from "vitest"

describe("TemporalDataService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGetEntryIntoForceHtml", () => {
    it("fetches the HTML content of an amending law's entry into force section", async () => {
      const mockHtml = "<div></div>"

      const useFetchMock = {
        text: vi.fn().mockReturnValue(mockHtml),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useGetEntryIntoForceHtml } = await import(
        "@/services/temporalDataService"
      )

      const result = useGetEntryIntoForceHtml(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      expect(result).toBe(mockHtml)

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        {
          headers: {
            Accept: "text/html",
          },
        },
      )
    })
  })

  describe("useGetTemporalDataTimeBoundaries", () => {
    it("fetches temporal data time boundaries", async () => {
      const expectedDates = [
        { date: "2023-11-01T00:00:00Z", eventRefEid: "event-1" },
        { date: "2023-12-01T00:00:00Z", eventRefEid: "event-2" },
      ]

      const useFetchMock = {
        json: vi.fn().mockReturnValue(expectedDates),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useGetTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const result = useGetTemporalDataTimeBoundaries(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      expect(result).toEqual(expectedDates)
    })
  })

  describe("useUpdateTemporalDataTimeBoundaries", () => {
    it("updates the temporal data time boundaries", async () => {
      const eli = "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
      const dates = [
        { date: "2024-04-01T00:00:00Z", eventRefEid: "event-3" },
        { date: "2024-05-15T00:00:00Z", eventRefEid: "event-4" },
      ]
      const expectedResponse = dates

      const useFetchMock = {
        json: vi.fn().mockReturnValue(expectedResponse),
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue(useFetchMock),
      }))

      const { useUpdateTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const result = useUpdateTemporalDataTimeBoundaries(eli, dates)
      expect(result).toEqual(expectedResponse)

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledWith(`/norms/${eli}/timeBoundaries`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(dates),
      })
    })
  })
})
