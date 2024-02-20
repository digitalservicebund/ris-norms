<script setup lang="ts">
import { computed } from "vue"
import ExpandMoreIcon from "~icons/ic/baseline-expand-more"

const props = defineProps<{
  eli: string
  printAnnouncementGazette: string
  publicationDate: string
  printAnnouncementPage: string
}>()

const printAnnouncementGazetteUpper = computed(() =>
  props.printAnnouncementGazette.toUpperCase(),
)

const publicationYear = computed(() => props.publicationDate.substring(0, 4))
</script>

<template>
  <div class="flex justify-between bg-white p-16 hover:bg-blue-200">
    <div class="flex items-center">
      <span class="ds-label-02-bold w-128 flex-none whitespace-nowrap">
        {{ printAnnouncementGazetteUpper }} {{ publicationYear }} Nr.
        {{ printAnnouncementPage }}
      </span>

      <span class="publication-date ml-40">
        {{ publicationDate }}
      </span>
    </div>

    <button class="text-blue-800">
      <ExpandMoreIcon class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
  </div>
</template>
