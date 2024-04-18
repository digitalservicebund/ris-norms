import { ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import { useAmendingLawTemporalData } from "@/composables/useAmendingLawTemporalData"
import * as amendingLawTemporalDataService from "@/services/amendingLawTemporalDataService"

describe("useAmendingLawTemporalData", () => {
  it("should load both the HTML content and dates", async () => {
    const expectedHtmlContent = `<div><h1>Test Content</h1></div>`
    const mockReleaseDates = [
      "2023-04-01T00:00:00Z",
      "2023-05-15T00:00:00Z",
      "2023-06-20T00:00:00Z",
      "2023-07-25T00:00:00Z",
    ]
    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")

    vi.spyOn(
      amendingLawTemporalDataService,
      "getAmendingLawEntryIntoForceHtml",
    ).mockResolvedValue(expectedHtmlContent)
    vi.spyOn(
      amendingLawTemporalDataService,
      "getAmendingLawTemporalDataIntervals",
    ).mockResolvedValue(mockReleaseDates)

    const { dates, htmlContent, loadData } = useAmendingLawTemporalData(eli)

    await loadData()
    expect(htmlContent.value).toBe(expectedHtmlContent)
    expect(dates.value).toEqual(mockReleaseDates)
  })

  it("updates dates and handles the service call", async () => {
    const eli = ref("some-eli")
    const newDates = ["2024-04-01T00:00:00Z", "2024-05-15T00:00:00Z"]
    const mockUpdateService = vi
      .spyOn(
        amendingLawTemporalDataService,
        "updateAmendingLawTemporalDataIntervals",
      )
      .mockResolvedValue(newDates)

    const { dates, update } = useAmendingLawTemporalData(eli)

    await update(newDates)

    expect(mockUpdateService).toHaveBeenCalledWith(eli.value, newDates)

    expect(dates.value).toEqual(newDates)
  })
})
