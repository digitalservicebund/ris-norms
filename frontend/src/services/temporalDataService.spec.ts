import { describe, it, expect, vi, beforeEach } from "vitest"

describe("TemporalDataService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGetEntryIntoForceHtml", () => {
    it("fetches the HTML content of an amending law's entry into force section", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response("<div></div>"))

      const { useGetEntryIntoForceHtml } = await import(
        "@/services/temporalDataService"
      )

      const { data, isFinished } = useGetEntryIntoForceHtml(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div></div>")

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })

    // TODO: (Malte Laukötter, 2024-06-07) check that eli change works
  })

  describe("useGetTemporalDataTimeBoundaries", () => {
    it("fetches temporal data time boundaries", async () => {
      const expectedDates = [
        { date: "2023-11-01T00:00:00Z", eventRefEid: "event-1" },
        { date: "2023-12-01T00:00:00Z", eventRefEid: "event-2" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(expectedDates)))

      const { useGetTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const { isFinished, data } = useGetTemporalDataTimeBoundaries(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedDates)

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/timeBoundaries",
        expect.anything(),
      )
    })

    // TODO: (Malte Laukötter, 2024-06-07) check that eli change works
  })

  describe("useUpdateTemporalDataTimeBoundaries", () => {
    it("updates the temporal data time boundaries", async () => {
      const dates = [
        { date: "2024-04-01T00:00:00Z", eventRefEid: "event-3" },
        { date: "2024-05-15T00:00:00Z", eventRefEid: "event-4" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(dates)))

      const { useUpdateTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const { data, execute } = useUpdateTemporalDataTimeBoundaries(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        dates,
      )
      await execute()

      expect(data.value).toEqual(dates)

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/timeBoundaries",
        expect.objectContaining({
          method: "PUT",
          body: JSON.stringify(dates),
        }),
      )
    })

    // TODO: (Malte Laukötter, 2024-06-07) check that eli or dates change does not auto refetch
  })
})
