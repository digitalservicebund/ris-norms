import { nextTick, ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import * as temporalDataService from "@/services/temporalDataService"
import { UseFetchReturn } from "@vueuse/core"
import { TemporalDataResponse } from "@/types/temporalDataResponse"

describe("useTemporalData", () => {
  it("should load the time boundaries", async () => {
    const mockReleaseDates = [
      { date: "2023-04-01T00:00:00Z", eventRefEid: "event-1" },
      { date: "2023-05-15T00:00:00Z", eventRefEid: "event-2" },
      { date: "2023-06-20T00:00:00Z", eventRefEid: "event-3" },
      { date: "2023-07-25T00:00:00Z", eventRefEid: "event-4" },
    ]
    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")

    const dataRef = ref<TemporalDataResponse[]>()
    vi.spyOn(
      temporalDataService,
      "useGetTemporalDataTimeBoundaries",
    ).mockReturnValue({
      data: dataRef,
    } as UseFetchReturn<TemporalDataResponse[]>)

    vi.spyOn(
      temporalDataService,
      "useUpdateTemporalDataTimeBoundaries",
    ).mockReturnValue({
      data: ref(),
    } as UseFetchReturn<TemporalDataResponse[]>)

    const { useTemporalData } = await import("@/composables/useTemporalData")

    const { timeBoundaries } = useTemporalData(eli)
    dataRef.value = mockReleaseDates
    await nextTick()

    expect(timeBoundaries.value).toEqual(mockReleaseDates)
  })

  it("updates dates", async () => {
    const eli = ref("some-eli")
    const newDates = [
      { date: "2024-04-01T00:00:00Z", eventRefEid: "new-event-1" },
      { date: "2024-05-15T00:00:00Z", eventRefEid: "new-event-2" },
    ]

    const updatedDatesRef = ref<TemporalDataResponse[]>()

    vi.spyOn(
      temporalDataService,
      "useGetTemporalDataTimeBoundaries",
    ).mockReturnValue({
      data: ref([]),
    } as unknown as UseFetchReturn<TemporalDataResponse[]>)
    vi.spyOn(
      temporalDataService,
      "useUpdateTemporalDataTimeBoundaries",
    ).mockReturnValue({
      data: updatedDatesRef,
      execute: vi.fn().mockImplementation(() => {
        updatedDatesRef.value = newDates
      }),
    } as unknown as UseFetchReturn<TemporalDataResponse[]>)

    const { useTemporalData } = await import("@/composables/useTemporalData")
    const { timeBoundaries, saveTemporalData } = useTemporalData(eli)

    await saveTemporalData(newDates)

    expect(timeBoundaries.value).toEqual(newDates)
  })
})
