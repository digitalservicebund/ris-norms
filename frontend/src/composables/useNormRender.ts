import { MaybeRefOrGetter, toValue, computed } from "vue"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Composable for rendering the XML of a norm as HTML.
 *
 * @param normXml XML of the norm that should be rendered
 * @param options optional additional filters and queries
 */
export function useNormRenderHtml(
  normXml: MaybeRefOrGetter<string | undefined>,
  options?: {
    /** Enable or disable metadata list at the beginning of the document */
    showMetadata?: MaybeRefOrGetter<boolean>
    /** If the XML sent is only a snippet of a norm */
    snippet?: MaybeRefOrGetter<boolean>
  },
): UseFetchReturn<string> {
  return useApiFetch<string>(
    computed(() => {
      if (!toValue(normXml)) return INVALID_URL

      const searchParams = new URLSearchParams()
      const showMetadataVal = toValue(options?.showMetadata)
      if (showMetadataVal)
        searchParams.set("showMetadata", showMetadataVal ? "true" : "false")
      const snippetVal = toValue(options?.snippet)
      if (snippetVal) searchParams.set("snippet", snippetVal ? "true" : "false")

      const queryString = searchParams.toString()
      return queryString ? `renderings?${queryString}` : "renderings"
    }),
    {
      headers: {
        Accept: "text/html",
        "Content-Type": "application/xml",
      },
    },
    {
      refetch: true,
    },
  ).post(normXml)
}
