<script lang="ts" setup>
import RisTextAreaInput from "./RisTextAreaInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Default (medium size)/label">
      <RisTextAreaInput
        id="test-id"
        placeholder="Placeholder text"
        label="Text area label"
        :rows="4"
        model-value="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      ></RisTextAreaInput>
    </Variant>
  </Story>
</template>
