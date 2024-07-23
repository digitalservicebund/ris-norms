import { ref } from "vue"
import { getCurrentScope } from "@sentry/vue"

/**
 * A reference to the current sentry trace id.
 *
 * Implementation-Note: I'm unsure if and how the traceId changes therefore this is a reference, so we only need to
 * change this implementation if it turns out that the traceId changes.
 */
export function useSentryTraceId() {
  return ref(getCurrentScope().getScopeData().propagationContext.traceId)
}
