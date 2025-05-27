import { errorMessages } from "@/lib/errorMessages"
import { isErrorResponse, mapErrorResponse } from "@/lib/errorResponseMapper"
import type { MappedErrorResponse } from "@/types/errorResponse"
import type {
  ToastMessageOptions as BaseToastMessageOptions,
  ToastServiceMethods as BaseToastServiceMethods,
} from "primevue"
import { useToast as baseUseToast } from "primevue"
import type { MaybeRefOrGetter } from "vue"
import { toValue } from "vue"

/** Defines message options for the toast component. */
export type ToastMessageOptions = Omit<
  BaseToastMessageOptions,
  "group" | "life"
> & {
  /**
   * Duration in milliseconds for which the toast is going to be displayed.
   * If the value is null, the toast will be sticky. If the value is left
   * undefined, the toast will be displayed for the default duration:
   *
   * - For non-critical severities such as info and success, the lifetime
   *   is 5 seconds.
   * - For warnings, the lifetime is 10 seconds.
   * - Critical severity messages (errors) will be displayed as sticky
   *   messages.
   */
  life?: number | null
}

/**
 * Extends the default toast message options with details specific to displaying
 * error messages.
 */
export type ErrorToastPayload = Omit<ToastMessageOptions, "detail"> & {
  detail: MappedErrorResponse & { traceId?: string }
}

/** Toast service methods. */
export type ToastServiceMethods = Omit<BaseToastServiceMethods, "add"> & {
  /** Displays the message in a toast component. */
  add: (message: ToastMessageOptions) => void

  /** Displays an error in a toast component. */
  addError: (
    source: MaybeRefOrGetter<any>, // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any
    options?: {
      /** Optional Sentry trace ID to display */
      traceId?: MaybeRefOrGetter<string | undefined>
      /** Method returning the message to display, receives the mapped error message */
      message?: (errorTitle: string) => string
    },
  ) => void
}

const INFO_TOAST_LIFE = 5_000
const WARN_TOAST_LIFE = 10_000
const ERROR_TOAST_LIFE = undefined

/** The group name for all toasts. */
export const TOAST_GROUP = "ris-toast"

/** Provides methods for managing toasts. */
export function useToast(): ToastServiceMethods {
  const { add, ...rest } = baseUseToast()

  function addWithDefaultLife(message: ToastMessageOptions) {
    let life: BaseToastMessageOptions["life"]
    if (message.life === undefined) {
      switch (message.severity) {
        case "error":
          life = ERROR_TOAST_LIFE
          break
        case "warn":
          life = WARN_TOAST_LIFE
          break
        default:
          life = INFO_TOAST_LIFE
      }
    } else if (message.life === null) life = undefined
    else if (Number.isFinite(message.life)) life = message.life

    add({ ...message, life, group: TOAST_GROUP })
  }

  const addError: ToastServiceMethods["addError"] = (source, options) => {
    const sourceVal = toValue(source)

    const mapped = isErrorResponse(sourceVal)
      ? mapErrorResponse(sourceVal)
      : { title: errorMessages.__fallback__() }

    const summary = options?.message
      ? options.message(mapped.title)
      : `Fehler: ${mapped.title}`

    const payload: ErrorToastPayload = {
      severity: "error",
      summary,
      detail: { ...mapped, traceId: toValue(options?.traceId) },
    }

    addWithDefaultLife(payload)
  }

  return {
    add: addWithDefaultLife,
    addError,
    ...rest,
  }
}
