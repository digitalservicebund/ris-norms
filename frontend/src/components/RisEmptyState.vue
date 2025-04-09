<script setup lang="ts">
import { computed, useSlots } from "vue"
import type { Slots, Component } from "vue"

const props = defineProps<{
  /** Text message to be displayed */
  textContent: string

  /** An optional icon for the extended variant. */
  icon?: Component
}>()

defineSlots<{
  recommendedAction: never
}>()

const slots = useSlots() as Slots

const variant = computed<"simple" | "extended">(() =>
  props.icon || slots["recommendedAction"] ? "extended" : "simple",
)
</script>

<template>
  <div
    data-testid="empty-state"
    :data-variant="variant"
    class="rounded-sm border-2 border-dashed border-blue-500 bg-gray-100 px-48 py-24 text-center text-xl text-gray-900 data-[variant=extended]:bg-white"
  >
    <component
      :is="icon"
      v-if="icon"
      data-testid="empty-state-icon"
      class="mx-auto mb-16 text-4xl text-blue-700"
    />

    <p>{{ textContent }}</p>

    <div v-if="$slots.recommendedAction" class="mt-16">
      <slot name="recommendedAction"></slot>
    </div>
  </div>
</template>
