<script lang="ts" setup>
import { computed } from "vue"

interface Props {
  id: string
  /** label of input field . */
  label: string | string[]
  /** Makes the input required */
  required?: boolean
  /** Position of the label*/
  labelPosition?: string
  /** Class of the label */
  labelClass?: string
  /** visually hide the label */
  visuallyHideLabel?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  required: false,
  labelPosition: "top",
  labelClass: undefined,
})

defineSlots<{
  default(props: { id: Props["id"]; hasError: boolean }): unknown
}>()

/* -------------------------------------------------- *
 * Label                                              *
 * -------------------------------------------------- */

const labelConverted = computed(() => {
  if (props.label) {
    return Array.isArray(props.label) ? props.label : [props.label]
  } else return []
})
</script>

<template>
  <div class="flex-start flex w-full gap-4">
    <div
      v-if="labelConverted && labelConverted.length !== 0"
      class="flex flex-row items-center"
      :class="{
        'sr-only': visuallyHideLabel,
      }"
      data-testid="label-wrapper"
    >
      <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
      <label
        class="grid items-center"
        :class="[labelClass ? labelClass : 'ds-label-02-reg']"
        :for="id"
      >
        <span v-for="(line, index) in labelConverted" :key="line">
          {{ line }}
          <span
            v-if="index === labelConverted.length - 1 && !!required"
            class="ml-4"
            >*</span
          >
        </span>
      </label>
    </div>
  </div>
</template>
