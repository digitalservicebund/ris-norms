// services/amendingLawEntryIntoForceService.js
import { apiFetch } from "@/services/apiService"

/**
 * Fetches the HTML content of an amending law's entry into force section by ELI.
 *
 * @param eli ELI of the amending law
 * @returns HTML string
 */
export async function getAmendingLawEntryIntoForceHtml(): Promise<string> {
  return `
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
}

/**
 * Fetches the temporal data (zeitgrenzen) related to an amending law.
 *
 * @param eli ELI of the amending law
 * @returns Array of date strings in ISO format
 */
export async function getAmendingLawTemporalDates(): Promise<string[]> {
  return [
    "2023-04-01T00:00:00Z",
    "2023-05-15T00:00:00Z",
    "2023-06-20T00:00:00Z",
    "2023-07-25T00:00:00Z",
    "2023-05-15T00:00:00Z",
    "2023-06-20T00:00:00Z",
    "2023-07-25T00:00:00Z",
  ]
}

/**
 * Updates the temporal data (zeitgrenzen) related to an amending law by ELI.
 *
 * @param eli ELI of the amending law
 * @param dates Array of date strings in ISO format
 * @returns The updated array of dates
 */
export async function updateAmendingLawTemporalDates(
  eli: string,
  dates: string[],
): Promise<string[]> {
  return await apiFetch<string[]>(`/law/${eli}/zeitgrenzen`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify({ dates }),
  })
}
