import { apiFetch } from "@/services/apiService"
import { AmendingLaw } from "@/types/amendingLaw"

/**
 * Load a norm from the API.
 *
 * @param eli Eli of the amending law
 */
export async function getNormByEli(eli: string): Promise<AmendingLaw> {
  return await apiFetch(`/norms/${eli}`)
}

/**
 * Load the xml version of a norm from the API.
 *
 * @param eli Eli of the amending law
 */
export async function getNormXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/norms/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}

/**
 * Load the rendered html version of a norm from the api
 *
 * @param eli Eli of the norm
 */
export async function getNormHtmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/norms/${eli}`, {
    headers: {
      Accept: "text/html",
    },
  })
}

/**
 * Save the xml version of an norm to the API.
 *
 * @param eli Eli of the norm
 * @param xml New xml of the norm
 * @returns the newly saved xml
 */
export async function putNormXml(eli: string, xml: string) {
  return await apiFetch<string>(`/norms/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
    body: xml,
  })
}
