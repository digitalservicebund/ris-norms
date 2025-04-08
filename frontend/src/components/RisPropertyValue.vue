<script setup lang="ts">
import { useElementId } from "@/composables/useElementId"

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

const { id } = useElementId("property-value")
</script>

<template>
  <figure class="ris-label2-regular" :aria-labelledby="id">
    <figcaption :id class="text-gray-900">{{ property }}</figcaption>
    <span class="break-words">{{ value }}</span>
  </figure>
</template>
