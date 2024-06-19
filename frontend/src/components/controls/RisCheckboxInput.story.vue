<script lang="ts" setup>
import RisCheckboxInput from "./RisCheckboxInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '250px' }">
    <Variant title="Default mini size | checked">
      <RisCheckboxInput
        id="test-id"
        :model-value="true"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Default mini size | read-only and checked">
      <RisCheckboxInput
        id="test-id"
        read-only
        :model-value="true"
        label="Änderungsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Default mini size | unchecked">
      <RisCheckboxInput id="test-id" label="Übergangsnorm"></RisCheckboxInput>
    </Variant>
    <Variant title="Default mini size | read-only and unchecked">
      <RisCheckboxInput
        id="test-id"
        read-only
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>

    <Variant title="Small size | checked">
      <RisCheckboxInput
        id="test-id"
        :model-value="true"
        size="small"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Small size | read-only and checked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="small"
        :model-value="true"
        label="Änderungsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Small size | unchecked">
      <RisCheckboxInput
        id="test-id"
        size="small"
        label="Übergangsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Small size | read-only and unchecked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="small"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>

    <Variant title="Regular size | checked">
      <RisCheckboxInput
        id="test-id"
        :model-value="true"
        size="regular"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Regular size | read-only and checked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="regular"
        :model-value="true"
        label="Änderungsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Regular size | unchecked">
      <RisCheckboxInput
        id="test-id"
        size="regular"
        label="Übergangsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Regular size | read-only and unchecked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="regular"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
  </Story>
</template>
