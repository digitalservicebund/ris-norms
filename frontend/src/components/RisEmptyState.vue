<script setup lang="ts">
import { computed, useSlots, type Component } from "vue"

const props = defineProps<{
  /** Text message to be displayed */
  textContent: string

  /** An optional icon for the extended variant. */
  icon?: Component
}>()

const slots = useSlots()

const variant = computed<"simple" | "extended">(() =>
  props.icon || slots["recommended-action"] ? "extended" : "simple",
)
</script>

<template>
  <div
    data-testid="empty-state"
    :data-variant="variant"
    class="rounded border-2 border-dashed border-blue-500 bg-gray-100 px-48 py-24 text-center text-xl text-gray-900 data-[variant=extended]:bg-white"
  >
    <component
      :is="icon"
      v-if="icon"
      class="ds-button-icon mx-auto mb-16 text-4xl text-blue-700"
    />

    <p class="my-4">{{ textContent }}</p>

    <slot name="recommended-action"></slot>
  </div>
</template>
