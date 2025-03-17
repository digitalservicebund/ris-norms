<script setup lang="ts">
import { computed } from "vue"

const props = defineProps<{
  title?: string
  frbrDateVerkuendung?: string
  frbrDateAusfertigung?: string
  importTimestamp?: string
  fna?: string
}>()

const computedfrbrDateVerkuendung = computed(
  () => props.frbrDateVerkuendung ?? "",
)
const computedfrbrDateAusfertigung = computed(
  () => props.frbrDateAusfertigung ?? "",
)
const computedimportTimestamp = computed(() => props.importTimestamp ?? "")
const computedFna = computed(() => props.fna ?? "")
</script>

<template>
  <div
    class="flex flex-col gap-24 border-t border-b border-blue-300 bg-white p-24"
  >
    <span class="ris-body1-bold w-full">
      {{ title }}
    </span>

    <div class="flex flex-wrap gap-x-8 gap-y-4">
      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-800"
          >Veröffentlichungsdatum</span
        >
        <span class="ris-body2-regular break-words">{{
          computedfrbrDateVerkuendung
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-800">Ausfertigungsdatum</span>
        <span class="ris-body2-regular break-words">{{
          computedfrbrDateAusfertigung
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-800"
          >Datenlieferungsdatum</span
        >
        <span class="ris-body2-regular break-words">{{
          computedimportTimestamp
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-800">FNA</span>
        <span class="ris-body2-regular break-words">{{ computedFna }}</span>
      </div>
    </div>

    <div class="flex flex-col gap-4">
      <RouterLink :to="{ name: 'TemporalData' }" class="ris-link1-bold">
        Geltungszeitregeln anlegen
      </RouterLink>

      <span class="ris-link1-bold cursor-not-allowed text-gray-600">
        Zielnormen verknüpfen
      </span>
    </div>
  </div>
</template>
