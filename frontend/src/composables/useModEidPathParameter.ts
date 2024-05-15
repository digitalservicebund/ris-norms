import { computed, WritableComputedRef } from "vue"
import { useRoute, useRouter } from "vue-router"

/**
 * Provides a reference to the selected akn:mod of the current route.
 *
 * Updating this ref will update the eid of the akn:mod in the path.
 *
 * @returns A reference to the eid of the selected akn:mod element of the current route
 */
export function useModEidPathParameter(): WritableComputedRef<string> {
  const router = useRouter()
  const route = useRoute()

  return computed({
    get() {
      if (Array.isArray(route.params.modEid)) {
        return route.params.modEid[0]
      }

      return route.params.modEid
    },
    set(eid) {
      void router.push({
        params: {
          modEid: eid,
        },
      })
    },
  })
}
