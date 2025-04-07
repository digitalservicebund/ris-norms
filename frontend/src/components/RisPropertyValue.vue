<script setup lang="ts">
const { property, value = "-" } = defineProps<{
  /**
   * Name of the property.
   */
  property: string

  /**
   * Value of the property.
   *
   * @default "-"
   */
  value?: string
}>()
</script>

<template>
  <figure class="ris-label2-regular">
    <figcaption class="text-gray-900">{{ property }}</figcaption>
    <span class="break-words">{{ value }}</span>
  </figure>
</template>
