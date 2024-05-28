import { apiFetch } from "@/services/apiService"

/**
 * Renders the XML of a norm as HTML.
 *
 * @param norm XML of the norm that should be rendered
 * @param customNorms The XMLs of norms which are referenced by the norm (e.g. in passiveModifications) and should be used instead of the data stored.
 * @param showMetadata Enable or disable metadata list at the beginning of the document
 * @param at Passive modifications coming into effect before this date should be applied before rendering the HTML
 * @returns Rendered HTML string of the norm
 */
export async function renderHtmlLaw(
  norm: string | undefined,
  showMetadata: boolean = true,
  at?: Date,
  customNorms?: string[],
): Promise<string> {
  return await apiFetch("renderings", {
    method: "POST",
    headers: {
      Accept: "text/html",
      "Content-Type": "application/json",
    },
    query: {
      atIsoDate: at?.toISOString(),
      showMetadata,
    },
    body: {
      norm,
      customNorms,
    },
  })
}
