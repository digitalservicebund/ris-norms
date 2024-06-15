<script setup lang="ts">
import RisLoadingSpinner from "./RisLoadingSpinner.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Default">
      <RisLoadingSpinner />
    </Variant>

    <Variant title="Custom size and color">
      <RisLoadingSpinner class="h-24 w-24 border-red-700" />
    </Variant>
  </Story>
</template>
