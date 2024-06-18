<script lang="ts" setup>
import { ValidationError } from "@/types/validationError"
import dayjs from "dayjs"
import customParseFormat from "dayjs/plugin/customParseFormat"
import { MaskaDetail, vMaska } from "maska"
import { computed, ref, watch } from "vue"
import IconErrorOutline from "~icons/ic/baseline-error-outline"

const props = withDefaults(
  defineProps<{
    /** HTML element ID of the form field. */
    id: string
    /** Value of the form field. */
    modelValue?: string
    /** Visual size of the form field. */
    size?: "regular" | "medium" | "small"
    /** Enable or disable editing the form field. */
    isReadOnly?: boolean
    /** Label of the form field. */
    label?: string
    /** Validation error and message to display. */
    validationError?: ValidationError
    /** Label position: 'above' or 'left' */
    labelPosition?: "above" | "left"
  }>(),
  {
    modelValue: "",
    size: "small",
    isReadOnly: false,
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

const localValidationError = ref<ValidationError | undefined>(undefined)

const internalHasError = computed(() => {
  return inputCompleted.value && !isValidDate.value
})

const effectiveHasError = computed(() => {
  return internalHasError.value || !!localValidationError.value
})

watch(
  () => props.validationError,
  (newVal) => {
    localValidationError.value = newVal
  },
  { immediate: true },
)

const errorMessage = computed(() => {
  if (!localValidationError.value) return undefined
  return localValidationError.value.message || "Ungültige Eingabe"
})

dayjs.extend(customParseFormat)

const isValidDate = computed(() => {
  return dayjs(inputValue.value, "DD.MM.YYYY", true).isValid()
})

const onMaska = (event: CustomEvent<MaskaDetail>) => {
  inputCompleted.value = event.detail.completed
}

const conditionalClasses = computed(() => ({
  "has-error": effectiveHasError.value,
  "ds-input-medium": props.size === "medium",
  "label-left": props.labelPosition === "left",
  "ds-input-small": props.size === "small" || !props.size,
}))

function validateInput() {
  if (inputCompleted.value) {
    if (isValidDate.value) {
      emit("update:validationError", undefined)
    } else {
      const validationError = {
        message: "Kein valides Datum",
        instance: props.id,
      }
      emit("update:validationError", validationError)
    }
  } else if (inputValue.value) {
    const validationError = {
      message: "Unvollständiges Datum",
      instance: props.id,
    }
    emit("update:validationError", validationError)
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
  <label
    :for="id"
    :class="[
      'ds-label',
      {
        'grid grid-cols-[min-content,1fr] items-center':
          props.labelPosition === 'left',
        'grid-rows-[1fr,min-content]': localValidationError,
      },
      `shrink-0`,
    ]"
  >
    <span class="min-w-[6rem]">
      {{ label }}
    </span>
    <input
      :id="id"
      v-model="inputValue"
      v-maska
      class="ds-input w-full"
      :class="conditionalClasses"
      data-maska="##.##.####"
      placeholder="TT.MM.JJJJ"
      :readonly="isReadOnly"
      @blur="onBlur"
      @keydown.delete="backspaceDelete"
      @maska="onMaska"
    />
    <span
      v-if="localValidationError"
      class="col-start-2 mb-4 mt-4 flex items-start text-sm text-red-800"
    >
      <IconErrorOutline class="mr-4 text-red-800" />

      {{ errorMessage }}
    </span>
  </label>
</template>
