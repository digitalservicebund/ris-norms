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
  /**
   * Emitted when the input field loses focus.
   */
  blur: []
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
  <div class="flex flex-col gap-2">
    <label v-if="label" :for="id">
      {{ label }}
    </label>
    <textarea
      :id="id"
      v-model="localValue"
      class="h-unset h-full resize-none py-12"
      :placeholder="placeholder"
      :readonly="readOnly"
      :rows="rows"
      :tabindex="readOnly ? -1 : Number($attrs.tabindex) || undefined"
      @blur="$emit('blur')"
    ></textarea>
  </div>
</template>
