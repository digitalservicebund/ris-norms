<script lang="ts" setup>
import { computed } from "vue"

export type DropdownInputModelType = string
export type DropdownItem = {
  label: string
  value: string
}

const props = defineProps<{
  /** the items for the dropdown. */
  items: DropdownItem[]
  /** Value reflected by choice of item . */
  modelValue?: DropdownInputModelType
  /** Placeholder text if needed. */
  placeholder?: string
}>()

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [DropdownInputModelType | undefined]
}>()

const localModelValue = computed({
  get: () => props.modelValue ?? "",
  set: (value) => {
    emit("update:modelValue", value)
  },
})

const hasPlaceholder = computed(() =>
  Boolean(!props.modelValue && props.placeholder),
)
</script>

<template>
  <!-- Label should come from the surrounding context, e.g. InputField component -->
  <!-- eslint-disable vuejs-accessibility/form-control-has-label -->
  <select
    v-model="localModelValue"
    class="ds-select ds-select-medium"
    :data-placeholder="hasPlaceholder ? true : undefined"
    tabindex="0"
  >
    <option v-if="placeholder && !localModelValue" disabled value="">
      {{ placeholder }}
    </option>
    <option v-for="item in items" :key="item.value" :value="item.value">
      {{ item.label }}
    </option>
  </select>
</template>
