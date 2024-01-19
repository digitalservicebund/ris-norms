<script setup lang="ts">
defineProps<{
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementNumber: number
  printAnnouncementPage: number
  publicationDate: Date
}>()

const formatDate = (date: Date) => {
  return new Intl.DateTimeFormat("default", { dateStyle: "medium" }).format(
    date,
  )
}
</script>

<template>
  <div class="flex gap-8 bg-white p-16 hover:bg-blue-200">
    <h1 class="ds-label-02-bold w-128 flex-none">
      {{ printAnnouncementGazette }} {{ printAnnouncementYear }} Nr.
      {{ printAnnouncementNumber }}
    </h1>
    <span class="flex-auto">{{ formatDate(publicationDate) }}</span>
    <button class="material-icons">chevron_expand_more</button>
  </div>
</template>
