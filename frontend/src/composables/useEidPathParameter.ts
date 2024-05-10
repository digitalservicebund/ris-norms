import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"

/**
 * Provides a reference to an eID path parameter in the current route.
 *
 * @returns Reactive reference to the eID path parameter.
 */
export function useEidPathParameter(): ComputedRef<string | undefined> {
  const route = useRoute()

  return computed(() => route.params.eid?.toString())
}
