import { ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import { useTemporalData } from "@/composables/useTemporalData"
import * as temporalDataService from "@/services/temporalDataService"

describe("useTemporalData", () => {
  it("should load both the time boundaries", async () => {
    const mockReleaseDates = [
      { date: "2023-04-01T00:00:00Z", eventRefEid: "event-1" },
      { date: "2023-05-15T00:00:00Z", eventRefEid: "event-2" },
      { date: "2023-06-20T00:00:00Z", eventRefEid: "event-3" },
      { date: "2023-07-25T00:00:00Z", eventRefEid: "event-4" },
    ]
    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")

    vi.spyOn(
      temporalDataService,
      "getTemporalDataTimeBoundaries",
    ).mockResolvedValue(mockReleaseDates)

    const { timeBoundaries, loadData } = useTemporalData(eli)

    await loadData()
    expect(timeBoundaries.value).toEqual(mockReleaseDates)
  })

  it("updates dates and handles the service call", async () => {
    const eli = ref("some-eli")
    const newDates = [
      { date: "2024-04-01T00:00:00Z", eventRefEid: "new-event-1" },
      { date: "2024-05-15T00:00:00Z", eventRefEid: "new-event-2" },
    ]
    const mockUpdateService = vi
      .spyOn(temporalDataService, "updateTemporalDataTimeBoundaries")
      .mockResolvedValue(newDates)

    const { timeBoundaries, updateTemporalData } = useTemporalData(eli)

    await updateTemporalData(newDates)

    expect(mockUpdateService).toHaveBeenCalledWith(eli.value, newDates)

    expect(timeBoundaries.value).toEqual(newDates)
  })
})
