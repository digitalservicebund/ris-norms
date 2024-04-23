import { apiFetch } from "@/services/apiService"

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
