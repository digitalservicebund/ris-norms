<script lang="ts" setup>
import { ValidationError } from "@/types/validationError"
import dayjs from "dayjs"
import customParseFormat from "dayjs/plugin/customParseFormat"
import { computed, ref, watch } from "vue"
import IconErrorOutline from "~icons/ic/baseline-error-outline"
import InputMask from "primevue/inputmask"

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

const inputCompleted = computed(() => {
  const datePattern = /^\d{2}\.\d{2}\.\d{4}$/
  return datePattern.test(inputValue.value || "")
})

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
  <div
    :class="[
      'flex w-full gap-2',
      {
        'flex-col items-center bg-red-900': props.labelPosition === 'above',
        'grid grid-cols-[min-content,1fr] items-center':
          props.labelPosition === 'left',
      },
      { 'grid-rows-[1fr,min-content]': localValidationError },
    ]"
  >
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label class="ris-label2-regular min-w-[6rem]" :for="id">
      {{ label }}
    </label>

    <InputMask
      :id="id"
      v-model="inputValue"
      placeholder="TT.MM.JJJJ"
      :readonly="isReadOnly"
      :auto-clear="false"
      mask="99.99.9999"
      :invalid="effectiveHasError"
      class="w-full"
      @blur="onBlur"
      @keydown="backspaceDelete"
    />

    <small v-if="localValidationError" :id="`${id}-hint`">
      <IconErrorOutline />
      {{ errorMessage }}
    </small>
  </div>
</template>
