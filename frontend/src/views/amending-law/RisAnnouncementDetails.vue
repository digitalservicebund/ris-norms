<script setup lang="ts">
import { computed } from "vue"

const props = defineProps<{
  title?: string
  veroeffentlichungsdatum?: string
  ausfertigungsdatum?: string
  datenlieferungsdatum?: string
  fna?: string
}>()

const computedfrbrDateVerkuendung = computed(() => {
  if (!props.veroeffentlichungsdatum) return ""

  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }
  return new Date(props.veroeffentlichungsdatum).toLocaleDateString(
    "de-DE",
    options,
  )
})

const computedfrbrDateAusfertigung = computed(() => {
  if (!props.ausfertigungsdatum) return ""

  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }
  return new Date(props.ausfertigungsdatum).toLocaleDateString("de-DE", options)
})

const computedimportTimestamp = computed(() => {
  if (!props.datenlieferungsdatum) return ""

  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }
  return new Date(props.datenlieferungsdatum).toLocaleDateString(
    "de-DE",
    options,
  )
})
console.log(props.datenlieferungsdatum)
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
        <span class="ris-body2-regular text-gray-900"
          >Veröffentlichungsdatum</span
        >
        <span class="ris-body2-regular break-words">{{
          computedfrbrDateVerkuendung
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-900">Ausfertigungsdatum</span>
        <span class="ris-body2-regular break-words">{{
          computedfrbrDateAusfertigung
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-900"
          >Datenlieferungsdatum</span
        >
        <span class="ris-body2-regular break-words">{{
          computedimportTimestamp
        }}</span>
      </div>

      <div class="flex min-w-192 flex-1 flex-col">
        <span class="ris-body2-regular text-gray-900">FNA</span>
        <span class="ris-body2-regular break-words">{{ computedFna }}</span>
      </div>
    </div>

    <div class="flex flex-col items-start gap-4">
      <RouterLink :to="{ name: 'TemporalData' }" class="ris-link1-bold">
        Geltungszeitregeln anlegen
      </RouterLink>

      <span class="ris-link1-bold cursor-not-allowed text-gray-900">
        Zielnormen verknüpfen
      </span>
    </div>
  </div>
</template>
