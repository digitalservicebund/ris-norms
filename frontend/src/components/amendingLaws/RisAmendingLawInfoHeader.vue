<script setup lang="ts">
import RisInfoHeader from "@/components/controls/RisInfoHeader.vue"
import { computed, DeepReadonly } from "vue"
import { AmendingLaw } from "@/services/amendingLawsService"

const props = defineProps<{
  amendingLaw: DeepReadonly<AmendingLaw> | AmendingLaw
}>()

const heading = computed(() => {
  const publicationYear = props.amendingLaw.publicationDate.substring(0, 4)
  if (props.amendingLaw.printAnnouncementGazette) {
    return `${props.amendingLaw.printAnnouncementGazette} ${publicationYear} S. ${props.amendingLaw.printAnnouncementPage}`
  } else if (props.amendingLaw.digitalAnnouncementEdition) {
    return `${props.amendingLaw.digitalAnnouncementMedium} ${publicationYear} Nr. ${props.amendingLaw.digitalAnnouncementEdition}`
  } else {
    return ""
  }
})
</script>

<template>
  <RisInfoHeader :heading="heading" :subtitle="amendingLaw.title" />
</template>
