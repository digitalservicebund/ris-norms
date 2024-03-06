import { ComputedRef, computed } from "vue"
import { useRoute } from "vue-router"

export function useEidPathParameter(): ComputedRef<string | undefined> {
  const { params } = useRoute()

  return computed(() => params.eid?.toString())
}
