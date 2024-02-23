import { computed } from "vue"
import { useRoute } from "vue-router"

/**
 * Provides a reference to the eli of the current route
 *
 * @returns a reference to the eli of the current route
 */
export function useEliPathParameter() {
  const { params } = useRoute()

  return computed(() => {
    if (
      params.eliJurisdiction == undefined ||
      params.eliAgent == undefined ||
      params.eliYear == undefined ||
      params.eliNaturalIdentifier == undefined
    ) {
      throw new Error(
        "useEliPathParameter: You can only use this composables on pages which have an eli in their route",
      )
    }

    return `eli/${params.eliJurisdiction}/${params.eliAgent}/${params.eliYear}/${params.eliNaturalIdentifier}`
  })
}
