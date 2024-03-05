import { useArticleStore } from "@/store/articleStore"
import { AmendingLawArticle } from "@/types/domain"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { storeToRefs } from "pinia"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  toValue,
  watch,
} from "vue"

/**
 * Get the data of an article inside an amending law.
 * @param identifier A reference to the ELI/eId combination for which the article data
 *  for which the law data will be returned. Changing the value of the reference will
 *  load the data for the new ELI/eId combination.
 * @returns A reference to the article data or undefined if it is not available (or
 *  still loading).
 */
export function useArticle(
  identifier: MaybeRefOrGetter<LawElementIdentifier>,
): DeepReadonly<Ref<AmendingLawArticle | undefined>> {
  const amendingLawStore = useArticleStore()
  const { loadedArticle } = storeToRefs(amendingLawStore)

  watch(
    () => toValue(identifier),
    (newIdentifier, oldIdentifier) => {
      if (
        newIdentifier.eli === oldIdentifier?.eli &&
        newIdentifier.eid === oldIdentifier?.eli
      ) {
        // Bail if only the object reference has changed, but the contents
        // are the same
        return
      }

      amendingLawStore.loadArticleByEliAndEid(newIdentifier)
    },
    { immediate: true },
  )

  return readonly(loadedArticle)
}
