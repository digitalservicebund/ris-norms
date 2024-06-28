<script lang="ts" setup>
const props = withDefaults(
  defineProps<{
    /** Unique ID for the radio button */
    id: string

    /** Optional label for the radio button */
    label?: string

    /** Optional size for the radio button */
    size?: "large" | "medium" | "small"

    /** Optional name of the radio button */
    name?: string

    /** Value of the radio button */
    value: string

    /** Disabled state of the radio button */
    disabled?: boolean
  }>(),
  {
    label: undefined,
    size: "small",
    name: undefined,
  },
)

const modelValue = defineModel<string>({ default: undefined })
</script>

<template>
  <div class="flex items-center">
    <input
      :id
      v-model="modelValue"
      :disabled
      :name
      :value
      class="ds-radio focus:!shadow-[inset_0_0_0_0.25rem] focus:!shadow-blue-800"
      :class="{
        'mr-8': props.size === 'large',
        'ds-radio-small mr-4': props.size === 'medium',
        'ds-radio-mini mr-2': props.size === 'small',
      }"
      type="radio"
    />
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">{{ label }}</label>
  </div>
</template>
