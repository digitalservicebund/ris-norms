import { isErrorResponse, mapErrorResponse } from "@/lib/errorResponseMapper"
import { useToast } from "primevue/usetoast"
import type { MaybeRefOrGetter } from "vue"
import { toValue } from "vue"
import { errorMessages } from "./errorMessages"
import type { ToastMessageOptions } from "primevue/toast"
import type { MappedErrorResponse } from "@/types/errorResponse"

/**
 * Extends the default toast message options with details specific to displaying
 * error messages.
 */
export type ErrorToastPayload = Omit<ToastMessageOptions, "detail"> & {
  detail: MappedErrorResponse & { traceId?: string }
}

/**
 * Global identifier for toast groups created with `useErrorToast`.
 */
export const ERROR_TOAST_GROUP = "error-toast"

/**
 * Wrapper around `useToast().add` that simplifies displaying error toasts.
 */
export function useErrorToast() {
  const { add } = useToast()

  /**
   * Calls the toast service to show an error toast. The message and details
   * will be determined based on the provided `source` error.
   *
   * This requires a `RisErrorToast` to be present on the page in order to work.
   *
   * @param source Error that should be displayed
   * @param traceId Optional Sentry trace ID to display
   */
  function addErrorToast(
    source: MaybeRefOrGetter<any>, // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any
    traceId?: MaybeRefOrGetter<string | undefined>,
  ) {
    const sourceVal = toValue(source)

    const mapped = isErrorResponse(sourceVal)
      ? mapErrorResponse(sourceVal)
      : { title: errorMessages.__fallback__() }

    const payload: ErrorToastPayload = {
      group: ERROR_TOAST_GROUP,
      severity: "error",
      summary: `Fehler beim Speichern: ${mapped.title}`,
      detail: { ...mapped, traceId: toValue(traceId) },
    }

    add(payload)
  }

  return { addErrorToast }
}
