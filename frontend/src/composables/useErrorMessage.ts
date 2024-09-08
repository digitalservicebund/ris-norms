import { errorMessages } from "@/lib/errorMessages"
import { isErrorResponse, mapErrorResponse } from "@/lib/errorResponseMapper"
import { MappedErrorResponse } from "@/types/errorResponse"
import { computed, ComputedRef, MaybeRefOrGetter, toValue } from "vue"

/**
 * For a potential error response received from the API, attempts to map the
 * response to a human-readable message based on the response's type.
 *
 * @param source Error reponse
 * @returns Reactive error message. The message will be undefined if `source` is
 *  falsy. If `source` is truthy, the value will be the mapped message or a
 *  fallback (even for sources that don't conform to the expected `ErrorResponse`
 *  type).
 */
export function useErrorMessage(
  source: MaybeRefOrGetter<any>, // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any
): ComputedRef<MappedErrorResponse | undefined> {
  return computed(() => {
    const sourceVal = toValue(source)
    if (!sourceVal) return undefined
    else if (isErrorResponse(sourceVal)) return mapErrorResponse(sourceVal)
    else return { title: errorMessages.__fallback__() }
  })
}
