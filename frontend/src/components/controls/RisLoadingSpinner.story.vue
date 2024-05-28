<script setup lang="ts">
import RisLoadingSpinner from "./RisLoadingSpinner.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Loading Spinner">
      <RisLoadingSpinner />
    </Variant>
  </Story>
</template>
