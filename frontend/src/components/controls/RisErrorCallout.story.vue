<script setup lang="ts">
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <RisErrorCallout title="Hello world" />
  </Story>
</template>
