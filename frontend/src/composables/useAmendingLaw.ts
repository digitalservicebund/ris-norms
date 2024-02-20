import { MaybeRefOrGetter, ref, toValue, watch } from "vue"
import { getAmendingLawByEli } from "@/services/amendingLawsService"

export function useAmendingLaw(eli: MaybeRefOrGetter<string>) {
  const amendingLaw = ref()

  console.log("useAmendingLaw - ELI")

  console.log(eli)

  watch(
    () => toValue(eli),
    async (eli) => {
      try {
        if (eli) {
          console.log("I am here")
          amendingLaw.value = await getAmendingLawByEli(eli)
          console.log("useAmendingLaw - amendingLaw")
          console.log(amendingLaw)
        }
      } catch (error) {
        //TODO: handle error
      }
    },
    { immediate: true },
  )

  console.log(amendingLaw)
  return { amendingLaw }
}
