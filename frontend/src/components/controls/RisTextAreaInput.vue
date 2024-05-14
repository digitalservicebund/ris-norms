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
    /** The number of rows for the text area */
    rows?: number
    /** Label for the form field. */
    label?: string
  }>(),
  {
    modelValue: "",
    placeholder: "",
    readOnly: false,
    rows: 2,
    label: undefined,
  },
)

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [value: string]
}>()

const localValue = computed({
  get() {
    return props.modelValue
  },
  set(value: string) {
    emit("update:modelValue", value)
  },
})
</script>

<template>
  <div class="grid gap-2">
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
    <textarea
      :id="id"
      v-model="localValue"
      class="h-unset ds-input h-auto resize-none py-12"
      :placeholder="placeholder"
      :readonly="readOnly"
      :rows="rows"
      :tabindex="readOnly ? -1 : Number($attrs.tabindex) || undefined"
    ></textarea>
  </div>
</template>
