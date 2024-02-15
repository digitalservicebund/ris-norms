import { MaybeRefOrGetter, ref, toValue, watch } from "vue"
import { getProcedureByEli } from "@/services/proceduresService"

export function useProcedure(eli: MaybeRefOrGetter<string>) {
  const procedure = ref()

  watch(
    () => toValue(eli),
    async (eli) => {
      try {
        if (eli) {
          procedure.value = await getProcedureByEli(eli)
        }
      } catch (error) {
        //TODO: handle error
      }
    },
    { immediate: true },
  )
  return { procedure }
}
