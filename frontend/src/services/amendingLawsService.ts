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
 * Load the rendered html version of an amending law from the api
 *
 * @param eli Eli of the amending law
 */
export async function getAmendingLawHtmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/amending-laws/${eli}`, {
    headers: {
      Accept: "text/html",
    },
  })
}

/**
 * Save the xml version of an amending law to the API.
 *
 * @param eli Eli of the amending law
 * @param xml New xml of the amending law
 * @returns the newly saved xml
 */
export async function putAmendingLawXml(eli: string, xml: string) {
  return await apiFetch<string>(`/amending-laws/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
    body: xml,
  })
}
