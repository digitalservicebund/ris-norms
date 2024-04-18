import { describe, it, expect, vi, beforeEach } from "vitest"

describe("amendingLawTemporalDataService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("Getting all temporal data", () => {
    it("returns HTML content and temporal data intervals for the law's entry into force section", async () => {
      const mockResponseHtml = `
<div>
<h2><strong>Artikel 7</strong> - Inkrafttreten</h2>
<br>
<p>(1) Dieses Gesetz tritt vorbehaltlich der Absätze 2 bis 5 am Tag nach der Verkündung in Kraft.</p>
<p>(2) Artikel 1 Nummer 10, 12 Buchstabe a Doppelbuchstabe bb, Buchstabe b, Nummer 13, Artikel 2 Nummer 4, 9 Buchstabe a Doppelbuchstabe aa, Buchstabe b, Nummer 10, Artikel 3 Nummer 1, 3, 5 Buchstabe a, Doppelbuchstabe bb, Buchstabe b, Nummer 6, Artikel 4 Nummer 2 und 4 treten am 1. November 2023 in Kraft.</p>
<p>(3) Artikel 1 Nummer 1 Buchstabe a, Nummer 3 Buchstabe b Doppelbuchstabe bb, Buchstabe e, f, Nummer 4 und 17 tritt am 1. Januar 2024 in Kraft.</p>
<p>(4) Artikel 1 Nummer 8, 12 Buchstabe a Doppelbuchstabe aa, Artikel 2 Nummer 9 Buchstabe a Doppelbuchstabe bb, Nummer 12, Artikel 3 Nummer 4, 5 Buchstabe a Doppelbuchstabe aa und Nummer 7 treten am 1. November 2024 in Kraft.</p>
<p>(5) Artikel 1 Nummer 3 Buchstabe b Doppelbuchstabe aa, Nummer 14 Buchstabe b und Artikel 2 Nummer 11 Buchstabe b treten am 1. November 2025 in Kraft.</p>
</div>
`
      const expectedDates: string[] = [
        "2023-04-01T00:00:00Z",
        "2023-05-15T00:00:00Z",
        "2023-06-20T00:00:00Z",
        "2023-07-25T00:00:00Z",
      ]

      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(mockResponseHtml)
        .mockResolvedValueOnce(expectedDates)

      vi.doMock("@/services/apiService", () => ({
        apiFetch: fetchMock,
      }))

      const {
        getAmendingLawEntryIntoForceHtml,
        getAmendingLawTemporalDataIntervals,
      } = await import("./amendingLawTemporalDataService")

      const htmlResult = await getAmendingLawEntryIntoForceHtml(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      )
      expect(htmlResult).toBe(mockResponseHtml)

      const datesResult = await getAmendingLawTemporalDataIntervals()
      expect(datesResult).toEqual(expectedDates)
    })
  })

  describe("updateAmendingLawTemporalDataIntervals", () => {
    it("updates the temporal data related to an amending law", async () => {
      const eli = "test-eli"
      const dates = ["2024-04-01T00:00:00Z", "2024-05-15T00:00:00Z"]
      const expectedResponse = dates

      const fetchMock = vi.fn().mockResolvedValueOnce(expectedResponse)
      vi.doMock("@/services/apiService", () => ({ apiFetch: fetchMock }))

      const { updateAmendingLawTemporalDataIntervals } = await import(
        "./amendingLawTemporalDataService"
      )

      const result = await updateAmendingLawTemporalDataIntervals(eli, dates)
      expect(result).toEqual(expectedResponse)
    })
  })
})
