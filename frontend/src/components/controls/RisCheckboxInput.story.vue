<script lang="ts" setup>
import RisCheckboxInput from "./RisCheckboxInput.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '250px' }">
    <Variant title="Default regular size | checked">
      <RisCheckboxInput
        id="test-id"
        :model-value="true"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Default regular size | read-only and checked">
      <RisCheckboxInput
        id="test-id"
        read-only
        :model-value="true"
        label="Änderungsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Default regular size | unchecked">
      <RisCheckboxInput id="test-id" label="Übergangsnorm"></RisCheckboxInput>
    </Variant>
    <Variant title="Default regular size | read-only and unchecked">
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

    <Variant title="Mini size | checked">
      <RisCheckboxInput
        id="test-id"
        :model-value="true"
        size="mini"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Mini size | read-only and checked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="mini"
        :model-value="true"
        label="Änderungsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Mini size | unchecked">
      <RisCheckboxInput
        id="test-id"
        size="mini"
        label="Übergangsnorm"
      ></RisCheckboxInput>
    </Variant>
    <Variant title="Mini size | read-only and unchecked">
      <RisCheckboxInput
        id="test-id"
        read-only
        size="mini"
        label="Stammnorm"
      ></RisCheckboxInput>
    </Variant>
  </Story>
</template>
