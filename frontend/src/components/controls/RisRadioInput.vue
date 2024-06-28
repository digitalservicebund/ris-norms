<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    size?: "large" | "medium" | "small"
  }>(),
  {
    size: "small",
  },
)

const modelValue = defineModel<string>({ default: undefined })
</script>

<template>
  <!-- Label should come from the surrounding context, e.g. InputField component -->
  <!-- eslint-disable vuejs-accessibility/form-control-has-label -->
  <input
    v-model="modelValue"
    class="ds-radio focus:!shadow-[inset_0_0_0_0.25rem] focus:!shadow-blue-800"
    :class="{
      'mr-8': props.size === 'large',
      'ds-radio-small mr-4': props.size === 'medium',
      'ds-radio-mini mr-2': props.size === 'small',
    }"
    type="radio"
  />
</template>
