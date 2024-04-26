<script setup lang="ts">
import RisInfoHeader from "@/components/controls/RisInfoHeader.vue"
import { Norm } from "@/types/norm"
import { computed, DeepReadonly } from "vue"

const props = defineProps<{
  amendingLaw: DeepReadonly<Norm> | Norm
}>()

const heading = computed(() => {
  const publicationYear = props.amendingLaw.frbrDateVerkuendung?.substring(0, 4)
  if (props.amendingLaw.frbrName && props.amendingLaw.frbrNumber) {
    if (props.amendingLaw.frbrNumber.startsWith("s")) {
      return `${props.amendingLaw.frbrName} ${publicationYear} S. ${props.amendingLaw.frbrNumber.replace("s", "")}`
    } else {
      return `${props.amendingLaw.frbrName} ${publicationYear} Nr. ${props.amendingLaw.frbrNumber}`
    }
  } else {
    return ""
  }
})
</script>

<template>
  <RisInfoHeader :heading="heading" :subtitle="amendingLaw.title" />
</template>
