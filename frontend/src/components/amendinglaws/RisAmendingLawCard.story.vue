<script setup lang="ts">
import RisProcedureCard from "./RisAmendingLawCard.vue"
</script>

<template>
  <Story>
    <RisProcedureCard
      eli="4711"
      print-announcement-gazette="Test"
      :print-announcement-number="815"
      :print-announcement-page="12"
      :publication-date="new Date(2024, 1, 19)"
    />
  </Story>
</template>
