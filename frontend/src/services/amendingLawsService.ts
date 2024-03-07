import { apiFetch } from "@/services/apiService"
import { AmendingLaw } from "@/types/amendingLaw"

/**
 * Load all amending laws from the API.
 */
export async function getAmendingLaws(): Promise<AmendingLaw[]> {
  return await apiFetch("/amending-laws")
}

/**
 * Load an amending law from the API.
 *
 * @param eli Eli of the amending law
 */
export async function getAmendingLawByEli(eli: string): Promise<AmendingLaw> {
  return await apiFetch(`/amending-laws/${eli}`)
}

/**
 * Load the xml version of an amending law from the API.
 *
 * @param eli Eli of the amending law
 */
export async function getAmendingLawXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/amending-laws/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}

/**
 * Save the xml version of an amending law to the API.
 *
 * @param eli Eli of the amending law
 * @param xml New xml of the amending law
 */
export async function putAmendingLawXml(eli: string, xml: string) {
  return await apiFetch.raw<undefined>(`/amending-laws/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
    },
    body: xml,
  })
}
