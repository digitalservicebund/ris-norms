<script lang="ts" setup>
import { computed } from "vue"

export type DropdownItem = {
  label: string
  value: string
}

const props = defineProps<{
  /** Unique ID for the dro. */
  id: string
  /** the items for the dropdown. */
  items: DropdownItem[]
  /** Value reflected by choice of item . */
  modelValue?: string
  /** Placeholder text if needed. */
  placeholder?: string
  /** Optional label for the field */
  label?: string
}>()

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [string | undefined]
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
  <div class="grid gap-2">
    <!-- Label should come from the surrounding context, e.g. InputField component -->
    <!-- eslint-disable vuejs-accessibility/form-control-has-label -->
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
    <select
      :id="id"
      v-model="localModelValue"
      class="ds-select ds-select-small"
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
  </div>
</template>
