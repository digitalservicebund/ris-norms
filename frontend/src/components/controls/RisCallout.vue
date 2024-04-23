<script setup lang="ts">
import IconErrorOutline from "~icons/ic/outline-error-outline"

withDefaults(
  defineProps<{
    /** Title of the callout. */
    title: string

    /**
     * Visual variant of the callout.
     * @default "neutral"
     */
    variant?: "neutral" | "error" | "warning" | "success"
  }>(),
  {
    variant: "neutral",
  },
)
</script>

<template>
  <div
    data-testid="callout-wrapper"
    :data-variant="variant"
    class="group grid grid-cols-[min-content,1fr] items-center gap-x-8 border-l-8 border-l-blue-900 bg-blue-200 p-16 data-[variant=error]:border-l-red-900 data-[variant=success]:border-l-green-900 data-[variant=warning]:border-l-yellow-900 data-[variant=error]:bg-red-200 data-[variant=success]:bg-green-200 data-[variant=warning]:bg-yellow-200"
  >
    <div
      class="group-data-[variant=error]:text-red-900 group-data-[variant=success]:text-green-900 group-data-[variant=warning]:text-yellow-900"
    >
      <slot name="icon">
        <IconErrorOutline />
      </slot>
    </div>

    <p class="ds-label-02-bold">{{ title }}</p>

    <div
      v-if="$slots.default"
      class="col-start-2 flex flex-col items-start gap-6"
    >
      <slot />
    </div>
  </div>
</template>
