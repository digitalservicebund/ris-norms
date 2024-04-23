import { apiFetch } from "@/services/apiService"

/**
 * Save the XML version of a target law to the API.
 *
 * @param eli ELI of the target law
 * @param xml New XML of the target law
 * @returns The newly saved XML
 */
export async function putTargetLawXml(
  eli: string,
  xml: string,
): Promise<string> {
  return await apiFetch<string>(`/norms/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
    body: xml,
  })
}

/**
 * Load a preview of the target law after the provided amending law is applied to it.
 *
 * @param eli Eli of the target law
 * @param amendingLawXml XML of the amending law that should be used for creating the preview
 */
export async function previewTargetLaw(
  eli: string,
  amendingLawXml: string,
): Promise<string> {
  return await apiFetch(`/norms/${eli}/preview`, {
    method: "POST",
    headers: {
      Accept: "application/xml",
      "Content-Type": "application/xml",
    },
    body: amendingLawXml,
  })
}

/**
 * Load the rendered HTML preview of the target law after the provided amending law is applied to it.
 *
 * @param eli Eli of the target law
 * @param amendingLawXml XML of the amending law that should be used for creating the preview
 */
export async function previewTargetLawAsHtml(
  eli: string,
  amendingLawXml: string,
): Promise<string> {
  return await apiFetch(`/norms/${eli}/preview`, {
    method: "POST",
    headers: {
      Accept: "text/html",
      "Content-Type": "application/xml",
    },
    body: amendingLawXml,
  })
}
