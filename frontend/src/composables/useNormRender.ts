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
    /** Passive modifications coming into effect before this date should be applied before rendering the HTML */
    at?: MaybeRefOrGetter<Date | undefined>
    /** The XMLs of norms which are referenced by the norm (e.g. in passiveModifications) and should be used instead of the data stored. */
    customNorms?: MaybeRefOrGetter<string[] | undefined>
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
      const atVal = toValue(options?.at)
      if (atVal) searchParams.set("atIsoDate", atVal.toISOString())

      const queryString = searchParams.toString()
      return queryString ? `renderings?${queryString}` : "renderings"
    }),
    {
      headers: {
        Accept: "text/html",
        "Content-Type": "application/json",
      },
    },
    {
      refetch: true,
    },
  ).post(
    computed(() => ({
      norm: toValue(normXml),
      customNorms: toValue(options?.customNorms),
    })),
  )
}

/**
 * Composable for rendering the XML of a norm as XML.
 *
 * @param normXml XML of the norm that should be rendered
 * @param customNorms The XMLs of norms which are referenced by the norm (e.g. in passiveModifications) and should be used instead of the data stored.
 * @param at Passive modifications coming into effect before this date should be applied before rendering the HTML
 */
export function useNormRenderXml(
  normXml: MaybeRefOrGetter<string | undefined>,
  at?: MaybeRefOrGetter<Date | undefined>,
  customNorms?: MaybeRefOrGetter<string[] | undefined>,
): UseFetchReturn<string> {
  return useApiFetch<string>(
    computed(() => {
      if (!toValue(normXml)) return INVALID_URL

      const searchParams = new URLSearchParams()
      const atValue = toValue(at)
      if (atValue) {
        searchParams.set("atIsoDate", atValue.toISOString())
      }

      return (
        "renderings" +
        (searchParams.size > 0 ? "?" : "") +
        searchParams.toString()
      )
    }),
    {
      headers: {
        Accept: "application/xml",
        "Content-Type": "application/json",
      },
    },
    {
      refetch: true,
    },
  ).post(
    computed(() => ({
      norm: toValue(normXml),
      customNorms: toValue(customNorms),
    })),
  )
}
