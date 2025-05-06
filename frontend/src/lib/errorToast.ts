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
   * @param options Additional options for the error toast
   */
  function addErrorToast(
    source: MaybeRefOrGetter<any>, // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any
    options?: {
      /** Optional Sentry trace ID to display */
      traceId?: MaybeRefOrGetter<string | undefined>
      /** Method returning the message to display, receives the mapped error message */
      message?: (errorTitle: string) => string
    },
  ) {
    const sourceVal = toValue(source)

    const mapped = isErrorResponse(sourceVal)
      ? mapErrorResponse(sourceVal)
      : { title: errorMessages.__fallback__() }

    const summary = options?.message
      ? options.message(mapped.title)
      : `Fehler: ${mapped.title}`

    const payload: ErrorToastPayload = {
      group: ERROR_TOAST_GROUP,
      severity: "error",
      summary,
      detail: { ...mapped, traceId: toValue(options?.traceId) },
    }

    add(payload)
  }

  return { addErrorToast }
}
