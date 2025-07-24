<script setup lang="ts">
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import { formatDate, formatDateTime } from "@/lib/dateTime"
import { computed } from "vue"

const props = defineProps<{
  /** Title of the Verkündung */
  title?: string | null
  /** Publication date of the Verkündung in the format YYYY-MM-DD */
  veroeffentlichungsdatum?: string | null
  /** Creation date of the Verkündung in the format YYYY-MM-DD */
  ausfertigungsdatum?: string | null
  /** Import date of the Verkündung in the format YYYY-MM-DD */
  datenlieferungsdatum?: string | null
  /** FNA of the Verkündung */
  fna?: string | null
}>()

const formattedVeroeffentlichungsdatum = computed(() =>
  props.veroeffentlichungsdatum
    ? formatDate(props.veroeffentlichungsdatum)
    : "",
)

const formattedAusfertigungsdatum = computed(() =>
  props.ausfertigungsdatum ? formatDate(props.ausfertigungsdatum) : "",
)

const formattedDatenlieferungsdatum = computed(() =>
  props.datenlieferungsdatum ? formatDateTime(props.datenlieferungsdatum) : "",
)

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
        :value="formattedVeroeffentlichungsdatum"
      />

      <RisPropertyValue
        property="Ausfertigungsdatum"
        :value="formattedAusfertigungsdatum"
      />

      <RisPropertyValue
        property="Datenlieferungsdatum"
        :value="formattedDatenlieferungsdatum"
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

      <RouterLink
        :to="{ name: 'VerkuendungZielnormen' }"
        class="ris-link1-bold"
      >
        Zielnormen verknüpfen
      </RouterLink>

      <RouterLink
        :to="{ name: 'VerkuendungExpressionenErzeugen' }"
        class="ris-link1-bold"
      >
        Expressionen erzeugen
      </RouterLink>
    </div>
  </div>
</template>
