<script setup lang="ts">
import RisRadioInput from "./RisRadioInput.vue"
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default | Small">
      <RisRadioInput id="small-radio" label="Small radio" value="val" />
    </Variant>

    <Variant title="Medium">
      <RisRadioInput
        id="medium-radio"
        size="medium"
        label="Medium radio"
        value="val"
      />
    </Variant>

    <Variant title="Large">
      <RisRadioInput
        id="large-radio"
        size="large"
        label="Large radio"
        value="val"
      />
    </Variant>
  </Story>
</template>
