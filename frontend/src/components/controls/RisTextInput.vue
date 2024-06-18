<script lang="ts" setup>
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    /** HTML element ID of the form field. */
    id: string
    /** Placeholder text if needed. */
    placeholder?: string
    /** If the input field should be read-only */
    readOnly?: boolean
    /** Visual size of the form field. */
    size?: "regular" | "medium" | "small"
    /** Label for the form field. */
    label?: string
  }>(),
  {
    placeholder: undefined,
    size: "small",
    label: undefined,
  },
)

defineEmits<{
  /** Emitted when the input field loses focus. */
  blur: []
}>()

/** Value of the form field. */
const modelValue = defineModel<string>()

const conditionalClasses = computed(() => ({
  "ds-input-medium": props.size === "medium",
  "ds-input-small": props.size === "small",
}))

const tabindex = computed(() => (props.readOnly ? -1 : 0))
</script>

<template>
  <div class="grid gap-2">
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
    <input
      :id="id"
      v-model="modelValue"
      class="ds-input"
      :class="conditionalClasses"
      :placeholder="placeholder"
      :readonly="readOnly"
      :tabindex="tabindex"
      type="text"
      @blur="$emit('blur')"
    />
  </div>
</template>
