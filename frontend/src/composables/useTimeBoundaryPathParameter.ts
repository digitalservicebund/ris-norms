import { computed, ComputedRef, WritableComputedRef } from "vue"
import { useRoute, useRouter } from "vue-router"

/**
 * Provides a reference to the time boundary of the current route, as well as
 * a Date object parsed based on the current value.
 *
 * Updating this ref will update the time boundary in the path.
 *
 * @returns A reference to the time boundary of the current route
 */
export function useTimeBoundaryPathParameter(): {
  /**
   * A reference to the time boundary of the current route. Updating this ref will
   * update the time boundary in the path.
   */
  timeBoundary: WritableComputedRef<string>
  /**
   * The current value parsed as a date. Undefined if the value is not set or not
   * parseable.
   */
  timeBoundaryAsDate: ComputedRef<Date | undefined>
} {
  const router = useRouter()
  const route = useRoute()

  const timeBoundary = computed({
    get() {
      return Array.isArray(route.params.timeBoundary)
        ? route.params.timeBoundary[0]
        : route.params.timeBoundary
    },
    set(timeBoundary) {
      router.replace({ params: { timeBoundary } })
    },
  })

  const timeBoundaryAsDate = computed(() =>
    timeBoundary.value ? new Date(timeBoundary.value) : undefined,
  )

  return { timeBoundary, timeBoundaryAsDate }
}
