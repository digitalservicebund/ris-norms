<script lang="ts" setup>
import RisAlert from "./RisAlert.vue"
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Success Alert">
      <RisAlert variant="success"> "success </RisAlert>
    </Variant>

    <Variant title="Error Alert">
      <RisAlert variant="error"> "error </RisAlert>
    </Variant>
  </Story>
</template>
