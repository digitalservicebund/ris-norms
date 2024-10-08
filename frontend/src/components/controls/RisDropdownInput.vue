<script lang="ts" setup>
import { computed } from "vue"

/** Describes a selectable option in the dropdown. */
export type DropdownItem = {
  /** Label of the item. */
  label: string
  /** Value of the item. */
  value: string
  /** Whether the item can be selected. */
  disabled?: boolean
}

const props = defineProps<{
  /** Unique ID for the dropdown. */
  id: string
  /** the items for the dropdown. */
  items: DropdownItem[]
  /** Placeholder text if needed. */
  placeholder?: string
  /** Optional label for the field */
  label?: string
  /** Optional aria-label for the field */
  ariaLabel?: string
  /**
   * Additional classes for the select element
   */
  selectClasses?: string | Record<string, boolean>
}>()

/** Selected value of the dropdown. */
const modelValue = defineModel<string | undefined>()

const hasPlaceholder = computed(() =>
  Boolean(!modelValue.value && props.placeholder),
)
</script>

<template>
  <div class="grid gap-2">
    <!-- Label should come from the surrounding context -->
    <!-- eslint-disable vuejs-accessibility/form-control-has-label -->
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
    <select
      :id="id"
      v-model="modelValue"
      class="ds-select ds-select-small text-ellipsis"
      :class="selectClasses"
      :data-placeholder="hasPlaceholder ? true : undefined"
      tabindex="0"
      :aria-label="ariaLabel"
    >
      <option v-if="placeholder && !modelValue" disabled value="">
        {{ placeholder }}
      </option>
      <option
        v-for="item in items"
        :key="item.value"
        :value="item.value"
        :disabled="item.disabled || undefined"
      >
        {{ item.label }}
      </option>
    </select>
  </div>
</template>
