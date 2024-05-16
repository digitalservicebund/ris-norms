<script setup lang="ts">
import { type Component } from "vue"

const props = withDefaults(
  defineProps<{
    /** Unique ID for the dro. */
    id?: string
    /** Text message to be displayed */
    textContent: string
    /** Either simple or extended */
    variant?: string
    /** An optional icon for the extended variant. */
    icon?: Component
  }>(),
  {
    id: "emptyState",
    variant: "simple",
    icon: undefined,
  },
)
</script>

<template>
  <div
    v-if="variant === 'simple'"
    :id="id"
    class="rounded border-2 border-dashed border-blue-500 bg-gray-100 px-48 py-24 text-center text-xl text-gray-900"
  >
    {{ props.textContent }}
  </div>
  <div
    v-else
    :id="id"
    class="rounded border-2 border-dashed border-blue-500 bg-white px-48 py-24 text-center text-xl text-gray-900"
  >
    <component
      :is="icon"
      class="ds-button-icon mx-auto mb-4 text-4xl text-blue-700"
    />
    <div>{{ props.textContent }}</div>
    <slot name="recommended-action"></slot>
  </div>
</template>
