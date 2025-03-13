<script setup lang="ts">
type MetadataItem = {
  label: string
  value: string
}

defineProps<{
  /**
   * The title of the announcement.
   */
  title: string

  /**
   * Metadata key-value pairs.
   */
  metaData?: MetadataItem[]
}>()
</script>

<template>
  <div
    class="flex flex-col gap-24 border-t border-b border-blue-300 bg-white p-24"
  >
    <span class="ris-body1-bold w-full">
      {{ title }}
    </span>
    <div class="flex flex-wrap gap-x-8 gap-y-4">
      <div
        v-for="item in metaData"
        :key="item.label"
        class="flex min-w-192 flex-1 flex-col"
      >
        <span class="ris-body2-regular text-gray-800">{{ item.label }}</span>
        <span class="ris-body2-regular break-words">{{ item.value }}</span>
      </div>
    </div>
    <div class="flex flex-col gap-4">
      <RouterLink :to="{ name: 'TemporalData' }" class="ris-link1-regular">
        Geltungszeitregeln anlegen
      </RouterLink>

      <span class="ris-link1-regular cursor-not-allowed text-gray-600">
        Zielnormen verknüpfen
      </span>
    </div>
  </div>
</template>
