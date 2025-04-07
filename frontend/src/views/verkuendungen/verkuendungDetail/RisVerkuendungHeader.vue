<script setup lang="ts">
import RisPropertyValue from "@/components/RisPropertyValue.vue"
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

const computedFna = computed(() => props.fna ?? "")
</script>

<template>
  <div
    class="flex flex-col gap-24 border-t border-b border-blue-300 bg-white p-24"
  >
    <span class="ris-body1-bold w-full">
      {{ title }}
    </span>

    <div class="grid grid-cols-[repeat(auto-fill,minmax(12rem,1fr))] gap-16">
      <RisPropertyValue
        property="Veröffentlichungsdatum"
        :value="computedfrbrDateVerkuendung"
      />

      <RisPropertyValue
        property="Ausfertigungsdatum"
        :value="computedfrbrDateAusfertigung"
      />

      <RisPropertyValue
        property="Datenlieferungsdatum"
        :value="computedimportTimestamp"
      />

      <RisPropertyValue property="FNA" :value="computedFna" />
    </div>

    <div class="flex flex-col items-start gap-4">
      <RouterLink
        :to="{ name: 'VerkuendungZeitgrenzen' }"
        class="ris-link1-bold"
      >
        Geltungszeitregeln anlegen
      </RouterLink>

      <span class="ris-link1-bold cursor-not-allowed text-gray-600">
        Zielnormen verknüpfen
      </span>
    </div>
  </div>
</template>
