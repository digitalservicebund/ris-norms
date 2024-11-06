import { Article } from "@/types/article"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { MaybeRefOrGetter, toValue, computed } from "vue"
import { INVALID_URL, useApiFetch } from "@/services/apiService"

/**
 * Get the data of an article inside an amending law.
 *
 * @param identifier A reference to the ELI/eId combination for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI/eId combination.
 * @returns A reference to the article data or undefined if it is not available (or
 *  still loading).
 */
export function useArticle(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
) {
  return useApiFetch(
    computed(() => {
      const identifierValue = toValue(identifier)
      if (identifierValue == undefined) {
        return INVALID_URL
      }

      return `/norms/${identifierValue.eli}/articles/${identifierValue.eid}`
    }),
    {
      refetch: true,
    },
  ).json<Article>()
}
