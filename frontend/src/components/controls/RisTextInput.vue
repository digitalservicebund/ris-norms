<script lang="ts" setup>
import { computed, ref, watch } from "vue"
import IconErrorOutline from "~icons/ic/baseline-error-outline"

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
    /** Validation error and message to display. */
    validationError?: { message: string }
    /** Label position: 'above' or 'left' */
    labelPosition?: "above" | "left"
  }>(),
  {
    modelValue: "",
    placeholder: undefined,
    size: "regular",
    label: undefined,
    validationError: undefined,
    labelPosition: "above",
  },
)

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [value: string | undefined]
  /**
   * Emitted when the input field loses focus.
   */
  blur: []
}>()

const localModelValue = computed({
  get: () => props.modelValue,
  set: (value) =>
    value === ""
      ? emit("update:modelValue", undefined)
      : emit("update:modelValue", value),
})

const effectiveHasError = computed(() => {
  return props.validationError || !!localValidationError.value
})

const conditionalClasses = computed(() => ({
  "has-error": effectiveHasError.value,
  "ds-input-medium": props.size === "medium",
  "ds-input-small": props.size === "small",
  "label-left": props.labelPosition === "left",
}))

const tabindex = computed(() => (props.readOnly ? -1 : 0))
const localValidationError = ref(props.validationError)

watch(
  () => props.validationError,
  (newVal) => {
    localValidationError.value = newVal
  },
  { immediate: true },
)

const errorMessage = computed(() => {
  return localValidationError.value?.message
})
</script>

<template>
  <div
    :class="
      props.labelPosition === 'left' ? 'flex items-center gap-8' : 'grid gap-2'
    "
  >
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label
      v-if="label"
      :for="id"
      :class="[
        'ds-label',
        { 'mb-24': props.labelPosition === 'left' && localValidationError },
      ]"
    >
      {{ label }}
    </label>
    <div class="flex flex-col">
      <input
        :id="id"
        v-model="localModelValue"
        class="ds-input"
        :class="conditionalClasses"
        :placeholder="placeholder"
        :readonly="readOnly"
        :tabindex="tabindex"
        type="text"
        @blur="$emit('blur')"
      />
      <div
        v-if="localValidationError"
        class="mt-4 flex items-center text-sm text-red-800"
      >
        <IconErrorOutline class="mr-2 text-red-800" />
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>
