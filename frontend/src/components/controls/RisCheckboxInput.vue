<script lang="ts" setup>
import { computed } from "vue"

const props = withDefaults(
  defineProps<{
    /** Unique ID for the checkbox. */
    id: string
    /** Value reflected of checkbox marked or not . */
    modelValue?: boolean
    /** Optional label for the field */
    label?: string
    /** Optional size for the field */
    size?: "small" | "regular"
    /** Optional read-only for the field */
    readOnly?: boolean
  }>(),
  {
    modelValue: false,
    label: undefined,
    size: "regular",
    readOnly: false,
  },
)

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field.
   */
  "update:modelValue": [boolean | undefined]
  /**
   * Emitted when the user ticks the checkbox.
   */
  input: []
}>()

const localModelValue = computed({
  get: () => props.modelValue,
  set: (value) => emit("update:modelValue", value),
})
</script>

<template>
  <div class="grid gap-2">
    <input
      :id="id"
      v-model="localModelValue"
      class="ds-checkbox"
      :class="{
        'ds-checkbox-small': size === 'small',
        '!bg-blue-300 !shadow-none': readOnly,
      }"
      :disabled="readOnly"
      type="checkbox"
      @keydown.space.prevent="localModelValue = !localModelValue"
    />
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
  </div>
</template>
