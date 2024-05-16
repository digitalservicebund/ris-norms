<script lang="ts" setup>
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    /** HTML element ID of the form field. */
    id: string
    /** Value of the form field. */
    modelValue?: string
    /** Placeholder text if needed. */
    placeholder?: string
    /** If the input field should be read-only */
    readOnly?: boolean
    /** Visual size of the form field. */
    size?: "regular" | "medium" | "small"
    /** Label for the form field. */
    label?: string
    /** On blur handler for the input */
    blurHandler?: () => void
  }>(),
  {
    modelValue: "",
    placeholder: undefined,
    size: "regular",
    label: undefined,
    blurHandler: undefined,
  },
)

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [value: string | undefined]
}>()

const localModelValue = computed({
  get: () => props.modelValue,
  set: (value) =>
    value === ""
      ? emit("update:modelValue", undefined)
      : emit("update:modelValue", value),
})

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
      v-model="localModelValue"
      class="ds-input"
      :class="conditionalClasses"
      :placeholder="placeholder"
      :readonly="readOnly"
      :tabindex="tabindex"
      type="text"
      @blur="blurHandler"
    />
  </div>
</template>
