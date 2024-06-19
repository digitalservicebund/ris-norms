import { describe, it, expect, vi, beforeEach } from "vitest"
import { nextTick, ref } from "vue"

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

    it("reacts to changes of the eli", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response("<div>1</div>"))
        .mockResolvedValueOnce(new Response("<div>2</div>"))

      const { useGetEntryIntoForceHtml } = await import(
        "@/services/temporalDataService"
      )

      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { data, isFinished } = useGetEntryIntoForceHtml(eli)

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div>1</div>")

      eli.value = "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1"
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div>2</div>")

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.anything(),
      )
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.anything(),
      )
    })
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

    it("reacts to eli changes", async () => {
      const expectedDates = [
        { date: "2023-11-01T00:00:00Z", eventRefEid: "event-1" },
        { date: "2023-12-01T00:00:00Z", eventRefEid: "event-2" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response(JSON.stringify(expectedDates)))
        .mockResolvedValueOnce(new Response(JSON.stringify([])))

      const { useGetTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { isFinished, data } = useGetTemporalDataTimeBoundaries(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedDates)

      eli.value = "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1"
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual([])

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/timeBoundaries",
        expect.anything(),
      )

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/timeBoundaries",
        expect.anything(),
      )
    })
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

    it("reacts to eli and dates changes and does not auto-refetch", async () => {
      const dates = ref([
        { date: "2024-04-01T00:00:00Z", eventRefEid: "event-3" },
        { date: "2024-05-15T00:00:00Z", eventRefEid: "event-4" },
      ])

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response(JSON.stringify([])))

      const { useUpdateTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )
      const eli = ref(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      const { data, execute } = useUpdateTemporalDataTimeBoundaries(eli, dates)
      eli.value = "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1"
      dates.value = []
      await nextTick()
      await execute()

      expect(data.value).toEqual([])

      expect(fetchSpy).toHaveBeenCalledOnce()
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/timeBoundaries",
        expect.objectContaining({
          method: "PUT",
          body: JSON.stringify([]),
        }),
      )
    })

    it("should reset isFinished once data changes", async () => {
      vi.spyOn(global, "fetch").mockResolvedValueOnce(new Response("[]"))

      const { useUpdateTemporalDataTimeBoundaries } = await import(
        "@/services/temporalDataService"
      )

      const dates = ref([
        { date: "2024-04-01T00:00:00Z", eventRefEid: "event-3" },
      ])
      const { execute, isFinished } = useUpdateTemporalDataTimeBoundaries(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        dates,
      )
      expect(isFinished.value).toBe(false)
      await execute()
      expect(isFinished.value).toBe(true)
      dates.value = []
      await nextTick()
      expect(isFinished.value).toBe(false)
    })
  })
})
