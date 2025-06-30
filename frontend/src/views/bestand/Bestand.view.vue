<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
</script>

<template>
  <RisViewLayout>
    <header class="mb-24">
      <h1 class="ris-heading2-regular">Bestand</h1>
    </header>

    <div>
      <RisEmptyState text-content="Keine Werke im Bestand gefunden." />
    </div>
  </RisViewLayout>
</template>
