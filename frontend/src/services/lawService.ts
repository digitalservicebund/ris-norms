import { apiFetch } from "@/services/apiService"
import { MaybeRefOrGetter } from "vue"

export async function renderHtmlLaw(
  xml: MaybeRefOrGetter<string | undefined>,
  showMetadata: boolean = true,
): Promise<string> {
  return await apiFetch(`laws/rendering?showMetadata=${showMetadata}`, {
    method: "POST",
    headers: {
      Accept: "text/html",
      "Content-Type": "application/xml",
    },
    body: xml,
  })
}
