<script lang="ts" setup>
import RisTextInput from "./RisTextInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default (medium size)">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
      ></RisTextInput>
    </Variant>

    <Variant title="Regular size">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="regular"
      ></RisTextInput>
    </Variant>

    <Variant title="Small size">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="small"
      ></RisTextInput>
    </Variant>

    <Variant title="Read-only">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
        read-only
      ></RisTextInput>
    </Variant>
  </Story>
</template>
