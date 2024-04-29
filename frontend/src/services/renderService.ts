import { apiFetch } from "@/services/apiService"
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
