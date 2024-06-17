<script lang="ts" setup>
import RisTextInput from "./RisTextInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default (small size) with label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Medium size with label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Regular size with label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="regular"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Read-only without label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
        read-only
      ></RisTextInput>
    </Variant>
  </Story>
</template>
