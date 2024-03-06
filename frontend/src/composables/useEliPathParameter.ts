import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"

/**
 * Provides a reference to the eli of the current route
 *
 * @returns a reference to the eli of the current route
 */
export function useEliPathParameter(): ComputedRef<string> {
  const { params } = useRoute()

  return computed(() => {
    if (
      params.eliJurisdiction == undefined ||
      params.eliAgent == undefined ||
      params.eliYear == undefined ||
      params.eliNaturalIdentifier == undefined ||
      params.eliPointInTime == undefined ||
      params.eliVersion == undefined ||
      params.eliLanguage == undefined ||
      params.eliSubtype == undefined
    ) {
      throw new Error(
        "useEliPathParameter: You can only use this composable on pages which have an eli in their route",
      )
    }

    return `eli/${params.eliJurisdiction}/${params.eliAgent}/${params.eliYear}/${params.eliNaturalIdentifier}/${params.eliPointInTime}/${params.eliVersion}/${params.eliLanguage}/${params.eliSubtype}`
  })
}
