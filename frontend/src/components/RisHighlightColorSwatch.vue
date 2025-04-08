<script setup lang="ts">
import { getHighlightClasses } from "@/composables/useTimeBoundaryHighlightClasses"
import { computed } from "vue"

const { colorIndex, size = ["h-20", "w-20"] } = defineProps<{
  /** Index of the highlight color that should be shown. */
  colorIndex: number

  /**
   * Width and height of the swatch. These should be Tailwind class names.
   *
   * @default ["h-20", "w-20"]
   */
  size?: [height: string, width: string]
}>()

const colorClass = computed(() => getHighlightClasses(colorIndex))
</script>

<template>
  <div
    :class="[
      'flex-none rounded-full border border-dotted border-blue-800',
      size,
      colorClass.default,
    ]"
  />
</template>
