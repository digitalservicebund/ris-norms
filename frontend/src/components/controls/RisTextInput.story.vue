<script lang="ts" setup>
import RisTextInput from "./RisTextInput.vue"
import { ref } from "vue"

const externalValidationError = {
  message: "Externes Fehler",
  instance: "story-id",
}

const textEntry = ref("Invalid entry")
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
    
    <Variant title="Label positioned left">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        label="Änderungstyp"
        label-position="left"
      ></RisTextInput>
    </Variant>

    <Variant title="With validation error">
      <RisTextInput
        id="error-id"
        v-model="textEntry"
        label="Änderungstyp"
        :validation-error="externalValidationError"
      ></RisTextInput>
    </Variant>

    <Variant title="Validaion + Label positioned left">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="small"
        label="Änderungstyp"
        label-position="left"
        :validation-error="externalValidationError"
      ></RisTextInput>
    </Variant>
  </Story>
</template>
