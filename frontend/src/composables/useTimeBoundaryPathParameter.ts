import { computed, WritableComputedRef } from "vue"
import { useRoute, useRouter } from "vue-router"

/**
 * Provides a reference to the time boundary of the current route.
 *
 * Updating this ref will update the time boundary in the path.
 *
 * @returns A reference to the time boundary of the current route
 */
export function useTimeBoundaryPathParameter(): WritableComputedRef<string> {
  const router = useRouter()
  const route = useRoute()

  return computed({
    get() {
      if (Array.isArray(route.params.timeBoundary)) {
        return route.params.timeBoundary[0]
      }

      return route.params.timeBoundary
    },
    set(timeBoundary) {
      router.push({
        params: {
          timeBoundary,
        },
      })
    },
  })
}
