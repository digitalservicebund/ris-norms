<script setup lang="ts">
import RisRadioInput from "./RisRadioInput.vue"
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default | Small">
      <label for="small-radio" class="flex items-center gap-8">
        <RisRadioInput id="small-radio" />
        Small radio
      </label>
    </Variant>

    <Variant title="Medium">
      <label for="medium-radio" class="flex items-center gap-8">
        <RisRadioInput id="medium-radio" size="medium" />
        Medium radio
      </label>
    </Variant>

    <Variant title="Large">
      <label for="large-radio" class="flex items-center gap-8">
        <RisRadioInput id="large-radio" size="large" />
        Large radio
      </label>
    </Variant>
  </Story>
</template>
