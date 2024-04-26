import { computed, WritableComputedRef } from "vue"
import { useRoute, useRouter } from "vue-router"

/**
 * Provides a reference to the Zeitgrenze of the current route.
 *
 * Updating this ref will update the zeitgrenze in the path.
 *
 * @returns A reference to the zeitgrenze of the current route
 */
export function useZeitgrenzePathParameter(): WritableComputedRef<string> {
  const router = useRouter()
  const route = useRoute()

  return computed({
    get() {
      if (Array.isArray(route.params.zeitgrenze)) {
        return route.params.zeitgrenze[0]
      }

      return route.params.zeitgrenze
    },
    set(zeitgrenze) {
      router.push({
        params: {
          zeitgrenze,
        },
      })
    },
  })
}
