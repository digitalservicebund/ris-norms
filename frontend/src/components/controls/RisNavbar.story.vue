<script setup lang="ts">
import RisNavbar from "@/components/controls/RisNavbar.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Neutral">
      <RisNavbar />
    </Variant>
  </Story>
</template>
