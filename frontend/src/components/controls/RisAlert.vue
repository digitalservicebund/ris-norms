<script setup lang="ts">
import IconCheck from "~icons/ic/baseline-check"
import IconErrorOutline from "~icons/ic/baseline-error-outline"
import IconClose from "~icons/ic/baseline-close"

defineProps<{
  /**
   * The variant of the alert
   */
  variant: "success" | "error"
}>()

defineEmits<{
  /**
   * Fired when the close button is clicked
   */
  close: []
}>()
</script>

<template>
  <div
    role="alert"
    class="flex gap-20 border-l-2 p-16"
    :class="{
      'border-l-green-700 bg-white': variant === 'success',
      'border-l-red-800 bg-red-200': variant === 'error',
    }"
  >
    <IconCheck
      v-if="variant === 'success'"
      width="24"
      height="24"
      class="text-green-700"
    />
    <IconErrorOutline
      v-else-if="variant === 'error'"
      width="24"
      height="24"
      class="text-red-900"
    />
    <span class="flex-grow font-bold"><slot /></span>
    <button
      class="h-24 w-24 rounded-full bg-white bg-opacity-50"
      name="Close"
      @click="$emit('close')"
    >
      <IconClose width="16" height="16" class="m-auto" />
    </button>
  </div>
</template>
