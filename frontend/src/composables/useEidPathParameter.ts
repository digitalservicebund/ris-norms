import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"

export function useEidPathParameter(): ComputedRef<string | undefined> {
  const route = useRoute()

  return computed(() => route.params.eid?.toString())
}
