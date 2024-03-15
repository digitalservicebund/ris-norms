import { useArticles } from "@/composables/useArticles"
import { getTargetLawByEli } from "@/services/targetLawsService"
import { TargetLaw } from "@/types/targetLaw"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  computed,
  readonly,
  ref,
  watch,
} from "vue"

export type TargetLawWithZF0Eli = {
  targetLaw: TargetLaw
  targetLawZf0Eli: string
}

/**
 * Get the data of all target laws changed by the amending law with the
 * specified ELI. In the context of the amending law, those target laws
 * are called "affected documents".
 *
 * @param eli The ELI of the amending law whose affected documents we want
 *  to fetch.
 * @returns A reference to the affected documents (or empty array if there
 *  are none or the documents are still loading)
 */
export function useAffectedDocuments(
  eli: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<TargetLawWithZF0Eli[]>> {
  const targetLaws = ref<TargetLawWithZF0Eli[]>([])

  const articles = useArticles(eli)

  const affectedDocumentElis = computed(
    () =>
      articles.value?.map((article) => [
        article.affectedDocumentEli,
        article.affectedDocumentZf0Eli,
      ]) ?? [],
  )

  watch(
    affectedDocumentElis,
    async (is) => {
      targetLaws.value = await Promise.all(
        is.map(async ([eli, zf0Eli]) => {
          const targetLaw = await getTargetLawByEli(eli)
          debugger
          return {
            targetLaw,
            targetLawZf0Eli: zf0Eli,
          }
        }),
      )
    },
    { immediate: true },
  )

  return readonly(targetLaws)
}
