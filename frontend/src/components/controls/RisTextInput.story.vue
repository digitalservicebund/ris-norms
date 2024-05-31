<script lang="ts" setup>
import RisTextInput from "./RisTextInput.vue"
import { ref } from "vue"

const externalValidationError = {
  message: "Externes Fehler",
}

const textEntry = ref("Invalid entry")
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
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

    <Variant title="Label positioned left">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="small"
        label="Änderungstyp"
        label-position="left"
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

    <Variant title="With validation error">
      <RisTextInput
        id="error-id"
        v-model="textEntry"
        size="medium"
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
