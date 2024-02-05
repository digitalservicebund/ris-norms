<script setup lang="ts">
import { computed } from "vue"
import ExpandMoreIcon from "~icons/ic/baseline-expand-more"

const props = defineProps<{
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementNumber: number
  printAnnouncementPage: number
  publicationDate: Date
}>()

const formattedDate = computed(() => {
  const day = String(props.publicationDate.getDate()).padStart(2, "0")
  const month = String(props.publicationDate.getMonth() + 1).padStart(2, "0")
  const year = props.publicationDate.getFullYear()

  return `${day}.${month}.${year}`
})
</script>

<template>
  <div class="flex gap-8 bg-white p-16 hover:bg-blue-200">
    <span class="ds-label-02-bold w-128 flex-none">
      {{ printAnnouncementGazette }} {{ printAnnouncementYear }} Nr.
      {{ printAnnouncementNumber }}
    </span>

    <span class="flex-auto">{{ formattedDate }}</span>

    <button class="text-blue-800">
      <ExpandMoreIcon class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
  </div>
</template>
