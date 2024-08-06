<script setup lang="ts">
import IcOutlineClose from "~icons/ic/outline-close"
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

    /**
     * Shows an icon to hide the callout.
     * @default false
     */
    allowDismiss?: boolean
  }>(),
  {
    variant: "neutral",
    allowDismiss: false,
  },
)

/**
 * Manages the visibility of the callout. This gets updates by the component
 * when the user presses the "dismiss" button, but you can also use it
 * independently from user actions.
 */
const visible = defineModel<boolean>("visible", { default: true })
</script>

<template>
  <div
    v-if="visible"
    data-testid="callout-wrapper"
    role="alert"
    :data-variant="variant"
    class="group grid grid-cols-[min-content,1fr,min-content] items-center gap-x-8 border-l-8 border-l-blue-900 bg-blue-200 p-16 data-[variant=error]:border-l-red-900 data-[variant=success]:border-l-green-900 data-[variant=warning]:border-l-yellow-900 data-[variant=error]:bg-red-200 data-[variant=success]:bg-green-200 data-[variant=warning]:bg-yellow-200"
  >
    <div
      class="group-data-[variant=error]:text-red-900 group-data-[variant=success]:text-green-900 group-data-[variant=warning]:text-yellow-900"
    >
      <slot name="icon">
        <IconErrorOutline />
      </slot>
    </div>

    <p class="ds-label-02-bold">{{ title }}</p>

    <button
      v-if="allowDismiss"
      class="mt-1 rounded-sm group-data-[variant=error]:hover:bg-red-400 group-data-[variant=neutral]:hover:bg-blue-400 group-data-[variant=success]:hover:bg-green-400 group-data-[variant=warning]:hover:bg-yellow-400"
      @click="visible = false"
    >
      <IcOutlineClose />
      <span class="sr-only">Hinweis schließen</span>
    </button>

    <div
      v-if="$slots.default"
      class="col-span-2 col-start-2 flex flex-col items-start gap-6"
    >
      <slot />
    </div>
  </div>
</template>
