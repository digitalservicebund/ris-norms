import { useGetProprietary } from "@/services/proprietaryService"
import { Proprietary } from "@/types/proprietary"
import { MaybeRefOrGetter, Ref, ref } from "vue"

/**
 * Exposes functionality to read and change proprietary metadata of a norm.
 * Reloads when the parameters change.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 */
export function useProprietary(
  eli: MaybeRefOrGetter<string | undefined>,
  options?: {
    /**
     * If set, returns the state of the metadata at that date. Otherwise the
     * metadata of the current version will be returned.
     */
    atDate?: MaybeRefOrGetter<string | Date | undefined>
  },
): {
  data: Ref<Proprietary | null>
  fetchError: Ref<unknown>
  isFetching: Ref<boolean>
  isSaving: Ref<boolean>
  saveError: Ref<unknown>
} {
  const getResult = useGetProprietary(eli, options)

  return {
    data: getResult.data,
    fetchError: getResult.error,
    isFetching: getResult.isFetching,
    isSaving: ref(false),
    saveError: ref(undefined),
  }
}
