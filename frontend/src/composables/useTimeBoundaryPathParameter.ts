import dayjs from "dayjs"
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
    set(newValue) {
      // Setting this to an empty value would cause the router to throw an error
      // about a missing parameter + it is most likely unintended behavior anyways.
      if (!newValue) return

      router.replace({ params: { timeBoundary: newValue } })
    },
  })

  const timeBoundaryAsDate = computed(() => {
    if (timeBoundary.value && dayjs(timeBoundary.value).isValid()) {
      return dayjs(timeBoundary.value).toDate()
    } else return undefined
  })

  return { timeBoundary, timeBoundaryAsDate }
}
