import {
  DeepReadonly,
  MaybeRefOrGetter,
  readonly,
  Ref,
  ref,
  toValue,
  watch,
} from "vue"
import { getArticleRenderByEliAndEid } from "@/services/articlesService"

/**
 * Get the rendered html of an article inside an amending law.
 *
 * @param eli A reference to the ELI of the norm for which the article XML will be returned.
 * @param eid A reference to the eId of the article for which the article XML will be returned.
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @returns A reference to the article HTML or undefined if it is not available (or
 *  still loading).
 */
export function useArticleHtml(
  eli: MaybeRefOrGetter<string>,
  eid: MaybeRefOrGetter<string | undefined>,
  at?: MaybeRefOrGetter<Date>,
): DeepReadonly<Ref<string | undefined>> {
  const html = ref<string>()

  watch(
    () => [toValue(eli), toValue(eid), toValue(at)],
    async () => {
      const eidValue = toValue(eid)

      if (!eidValue) {
        return
      }

      html.value = await getArticleRenderByEliAndEid(
        {
          eli: toValue(eli),
          eid: eidValue,
        },
        toValue(at),
      )
    },
    { immediate: true },
  )

  return readonly(html)
}
