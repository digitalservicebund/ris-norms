import { nextTick, ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import * as temporalDataService from "./temporalDataService"
import type { UseFetchReturn } from "@vueuse/core"
import type { TemporalDataResponse } from "@/views/amending-law/articles/editor/temporalDataResponse"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

describe("useTemporalData", () => {
  it("should load the time boundaries", async () => {
    const mockReleaseDates = [
      { date: "2023-04-01T00:00:00Z", eventRefEid: "event-1" },
      { date: "2023-05-15T00:00:00Z", eventRefEid: "event-2" },
      { date: "2023-06-20T00:00:00Z", eventRefEid: "event-3" },
      { date: "2023-07-25T00:00:00Z", eventRefEid: "event-4" },
    ]
    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      ),
    )

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

    const { useTemporalData } = await import(
      "@/views/amending-law/articles/editor/useTemporalData"
    )

    const { data } = useTemporalData(eli)
    dataRef.value = mockReleaseDates
    await nextTick()

    expect(data.value).toEqual(mockReleaseDates)
  })

  it("updates dates", async () => {
    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
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

    const { useTemporalData } = await import(
      "@/views/amending-law/articles/editor/useTemporalData"
    )
    const {
      data,
      update: { execute },
    } = useTemporalData(eli, newDates)

    await execute()

    expect(data.value).toEqual(newDates)
  })
})
