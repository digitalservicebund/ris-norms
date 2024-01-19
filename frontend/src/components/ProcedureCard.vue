<script setup lang="ts">
import ExpandMoreIcon from "~icons/ic/baseline-expand-more"
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementNumber: number
  printAnnouncementPage: number
  publicationDate: Date
}>()

const formatDate = (date: Date) => {
  const day = String(date.getDate()).padStart(2, "0")
  const month = String(date.getMonth() + 1).padStart(2, "0")
  const year = date.getFullYear()

  return `${day}.${month}.${year}`
}
</script>

<template>
  <div class="flex gap-8 bg-white p-16 hover:bg-blue-200">
    <h1 class="ds-label-02-bold w-128 flex-none">
      {{ printAnnouncementGazette }} {{ printAnnouncementYear }} Nr.
      {{ printAnnouncementNumber }}
    <button class="text-blue-800">
      <ExpandMoreIcon class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
</template>
