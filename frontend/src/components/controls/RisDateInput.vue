<script lang="ts" setup>
import { ValidationError } from "@/types/validationError"
import dayjs from "dayjs"
import customParseFormat from "dayjs/plugin/customParseFormat"
import { MaskaDetail, vMaska } from "maska"
import { computed, ref, watch } from "vue"

const props = defineProps<{
  /** HTML element ID of the form field. */
  id: string

  /** Value of the form field. */
  modelValue?: string

  /** Error validation state of the form field. */
  hasError?: boolean

  /** Visual size of the form field. */
  size?: "regular" | "medium" | "small"

  /** Enable or disable editing the form field. */
  isReadOnly?: boolean
}>()

const emit = defineEmits<{
  /**
   * Emitted when the user changes the value of the form field. Note that this
   * is only emitted when the value is empty or a valid date. All other states
   * (e.g. partial dates while typing) are handled internally and not emitted.
   */
  "update:modelValue": [value?: string]

  /**
   * Emitted when the form field enters an invalid state based on user
   * inputs (e.g. the date is invalid).
   */
  "update:validationError": [value?: ValidationError]
}>()

const inputCompleted = ref<boolean>(false)

const inputValue = ref(
  props.modelValue ? dayjs(props.modelValue).format("DD.MM.YYYY") : undefined,
)

dayjs.extend(customParseFormat)

const isValidDate = computed(() => {
  return dayjs(inputValue.value, "DD.MM.YYYY", true).isValid()
})

const onMaska = (event: CustomEvent<MaskaDetail>) => {
  inputCompleted.value = event.detail.completed
}

const effectiveHasError = computed(
  () => props.hasError || (inputCompleted.value && !isValidDate.value),
)

const conditionalClasses = computed(() => ({
  "has-error": effectiveHasError.value,
  "ds-input-medium": props.size === "medium",
  "ds-input-small": props.size === "small",
}))

function validateInput() {
  if (inputCompleted.value) {
    if (isValidDate.value) {
      emit("update:validationError", undefined)
    } else {
      emit("update:validationError", {
        message: "Kein valides Datum",
        instance: props.id,
      })
    }
  } else if (inputValue.value) {
    emit("update:validationError", {
      message: "Unvollständiges Datum",
      instance: props.id,
    })
  } else {
    emit("update:validationError", undefined)
  }
}

function backspaceDelete() {
  emit("update:validationError", undefined)
  if (inputValue.value === "") emit("update:modelValue", inputValue.value)
}

function onBlur() {
  validateInput()
}

watch(
  () => props.modelValue,
  (is) => {
    inputValue.value = is
      ? dayjs(is, "YYYY-MM-DD", true).format("DD.MM.YYYY")
      : undefined
  },
)

watch(inputValue, (is) => {
  if (is === "") emit("update:modelValue", undefined)
  else if (isValidDate.value) {
    emit(
      "update:modelValue",
      dayjs(is, "DD.MM.YYYY", true).format("YYYY-MM-DD"),
    )
  }
})

watch(inputCompleted, (is) => {
  if (is) validateInput()
})
</script>

<template>
  <input
    :id="id"
    v-model="inputValue"
    v-maska
    class="ds-input"
    :class="conditionalClasses"
    data-maska="##.##.####"
    placeholder="TT.MM.JJJJ"
    :readonly="isReadOnly"
    @blur="onBlur"
    @keydown.delete="backspaceDelete"
    @maska="onMaska"
  />
</template>
