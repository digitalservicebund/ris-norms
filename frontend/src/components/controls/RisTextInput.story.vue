<script lang="ts" setup>
import RisTextInput from "./RisTextInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default (medium size)/label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Regular size/label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="regular"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Small size/label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="small"
        label="Änderungstyp"
      ></RisTextInput>
    </Variant>

    <Variant title="Read-only/No label">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
        read-only
      ></RisTextInput>
    </Variant>
  </Story>
</template>
