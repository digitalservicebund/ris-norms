<script setup lang="ts">
import { computed } from "vue"
import ExpandMoreIcon from "~icons/ic/baseline-expand-more"

const props = defineProps<{
  eli: string
  printAnnouncementGazette?: string
  digitalAnnouncementMedium?: string
  publicationDate: string
  printAnnouncementPage?: string
  digitalAnnouncementEdition?: string
}>()

const gazetteOrMediumUpper = computed(() => {
  return (
    props.printAnnouncementGazette?.toUpperCase() ??
    props.digitalAnnouncementMedium?.toUpperCase() ??
    ""
  )
})

const pageOrEdition = computed(() => {
  return props.printAnnouncementPage ?? props.digitalAnnouncementEdition ?? ""
})

const publicationYear = computed(() => props.publicationDate.substring(0, 4))

const publicationDateGerman = computed(() => {
  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }
  return new Date(props.publicationDate).toLocaleDateString("de-DE", options)
})
</script>

<template>
  <div class="flex justify-between bg-white p-16 hover:bg-blue-200">
    <div class="flex items-center">
      <span class="ds-label-02-bold w-128 flex-none whitespace-nowrap">
        {{ gazetteOrMediumUpper }} {{ publicationYear }} Nr.
        {{ pageOrEdition }}
      </span>

      <span class="publication-date ml-40">
        {{ publicationDateGerman }}
      </span>
    </div>

    <button class="text-blue-800">
      <ExpandMoreIcon class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
  </div>
</template>
