<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { useErrorMessage } from "@/composables/useErrorMessage"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { computed } from "vue"

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
  <RisCallout
    data-testid="error-callout"
    variant="error"
    :title="effectiveTitle ?? 'Ein unbekannter Fehler ist aufgetreten.'"
    :allow-dismiss="allowDismiss"
  >
    <p v-if="errorMessage?.message">{{ errorMessage.message }}</p>

    <slot />

    <p>
      Trace-ID:
      <RisCopyableLabel :text="sentryTraceId" name="Trace-ID" />
    </p>
  </RisCallout>
</template>
