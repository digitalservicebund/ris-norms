<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"

withDefaults(
  defineProps<{
    /** Title of the callout. */
    title: string

    /**
     * Shows an icon to hide the callout.
     * @default false
     */
    allowDismiss?: boolean
  }>(),
  {
    allowDismiss: false,
  },
)

const sentryTraceId = useSentryTraceId()
</script>

<template>
  <RisCallout variant="error" :title="title" :allow-dismiss="allowDismiss">
    <slot />

    <p>
      Trace-ID:
      <RisCopyableLabel :text="sentryTraceId" name="Trace-ID" />
    </p>
  </RisCallout>
</template>
