<script setup lang="ts">
import { computed } from "vue"
import ExpandMoreIcon from "~icons/ic/baseline-expand-more"

const props = defineProps<{
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementPage: number
}>()

const printAnnouncementGazetteUpper = computed(() =>
  props.printAnnouncementGazette.toUpperCase(),
)
</script>

<template>
  <div class="flex justify-between gap-8 bg-white p-16 hover:bg-blue-200">
    <span class="ds-label-02-bold w-128 flex-none flex-grow">
      {{ printAnnouncementGazetteUpper }} {{ printAnnouncementYear }} Nr.
      {{ printAnnouncementPage }}
    </span>

    <button class="text-blue-800">
      <ExpandMoreIcon class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
  </div>
</template>
