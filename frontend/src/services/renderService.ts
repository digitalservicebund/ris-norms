import { apiFetch } from "@/services/apiService"

/**
 * Renders the XML of a law as HTML.
 *
 * @param xml Source that should be rendered
 * @param showMetadata Enable or disable metadata list at the beginning of the document
 * @returns Rendered HTML string of the law
 */
export async function renderHtmlLaw(
  xml: string | undefined,
  showMetadata: boolean = true,
): Promise<string> {
  return await apiFetch(`renderings?showMetadata=${showMetadata}`, {
    method: "POST",
    headers: {
      Accept: "text/html",
      "Content-Type": "application/xml",
    },
    body: xml,
  })
}
