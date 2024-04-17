import { ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import { useAmendingLawTemporalData } from "@/composables/useAmendingLawTemporalData"
import * as amendingLawEntryIntoForceService from "@/services/amendingLawEntryIntoForceService"

describe("useAmendingLawTemporalData", () => {
  it("should load the temporal dates if they exist", async () => {
    const mockReleaseDates = [
      "2023-04-01T00:00:00Z",
      "2023-05-15T00:00:00Z",
      "2023-06-20T00:00:00Z",
      "2023-07-25T00:00:00Z",
      "2023-05-15T00:00:00Z",
      "2023-06-20T00:00:00Z",
      "2023-07-25T00:00:00Z",
    ]

    vi.spyOn(
      amendingLawEntryIntoForceService,
      "getAmendingLawTemporalDates",
    ).mockResolvedValue(mockReleaseDates)

    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
    const { dates, loadData } = useAmendingLawTemporalData(eli)
    await loadData()
    expect(dates.value).toEqual(mockReleaseDates)
  })
})

describe("useAmendingLawTemporalData - HTML Content Loading", () => {
  it("should load the HTML content when called", async () => {
    const expectedHtmlContent = `<div><h1>Test Content</h1></div>`
    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")

    vi.spyOn(
      amendingLawEntryIntoForceService,
      "getAmendingLawEntryIntoForceHtml",
    ).mockResolvedValue(expectedHtmlContent)

    const { htmlContent, loadData } = useAmendingLawTemporalData(eli)

    await loadData()
    expect(htmlContent.value).toBe(expectedHtmlContent)
  })
})
