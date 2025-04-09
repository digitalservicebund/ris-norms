<script setup lang="ts">
import { RisCopyableLabel } from "@digitalservicebund/ris-ui/components"
import { useErrorMessage } from "@/composables/useErrorMessage"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { computed } from "vue"
import Message from "primevue/message"

const { error } = defineProps<{
  /**
   * An error returned from the API. Will take precedence over `title` if
   * set. The component will try to display a human-readable message based
   * on the type of the error. If that's not possible, a fallback message
   * is shown.
   */
  error: any // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any

  /**
   * Shows an icon to hide the callout.
   * @default false
   */
  allowDismiss?: boolean
}>()

const sentryTraceId = useSentryTraceId()

const errorMessage = useErrorMessage(error)

const effectiveTitle = computed(() => errorMessage.value?.title)
</script>

<template>
  <Message
    data-testid="error-callout"
    severity="error"
    :closable="allowDismiss"
    data-variant="error"
  >
    <p class="ris-body1-bold">
      {{ effectiveTitle ?? "Ein unbekannter Fehler ist aufgetreten." }}
    </p>
    <p v-if="errorMessage?.message">{{ errorMessage.message }}</p>
    <p v-if="errorMessage?.suggestion">{{ errorMessage.suggestion }}</p>
    <slot />
    <p>
      Trace-ID:
      <RisCopyableLabel :text="sentryTraceId" name="Trace-ID" />
    </p>
  </Message>
</template>
