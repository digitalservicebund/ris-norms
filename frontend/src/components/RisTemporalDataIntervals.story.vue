<script setup lang="ts">
import { ref } from "vue"
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"

const initialDates = [
  { date: "2024-12-31", eventRefEid: "event-1" },
  { date: "2025-01-01", eventRefEid: "event-2" },
]

const dates = ref([...initialDates])
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Default">
      <RisTemporalDataIntervals v-model:dates="dates" />
    </Variant>
  </Story>
</template>
