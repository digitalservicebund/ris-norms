<script setup lang="ts">
import RisInfoHeader from "@/components/controls/RisInfoHeader.vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import { Norm } from "@/types/norm"
import { computed, DeepReadonly } from "vue"

const props = defineProps<{
  /** The norm whose details should be shown on the header. */
  amendingLaw: DeepReadonly<Norm> | Norm
}>()

const heading = computed(() => getFrbrDisplayText(props.amendingLaw) ?? "")
</script>

<template>
  <RisInfoHeader :heading="heading" :subtitle="amendingLaw.title" />
</template>
