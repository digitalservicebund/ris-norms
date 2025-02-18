<script setup lang="ts">
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useErrorToast } from "@/lib/errorToast"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import Button from "primevue/button"
import InputText from "primevue/inputtext"
import Select from "primevue/select"
import { useToast } from "primevue/usetoast"
import { computed, watch } from "vue"
import IconCheck from "~icons/ic/baseline-check"
import { useElementId } from "@/composables/useElementId"

const props = defineProps<{
  /** the possible time boundaries in the format YYYY-MM-DD. */
  timeBoundaries: TemporalDataResponse[]
  isUpdating?: boolean
  isUpdatingFinished?: boolean
  updateError?: any // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are `any`
}>()

defineEmits<{
  "update-mod": []
}>()
/** Optional selected time boundary of the format YYYY-MM-DD */
const selectedTimeBoundaryModel = defineModel<TemporalDataResponse | undefined>(
  "selectedTimeBoundary",
)

const timeBoundaries = computed(() => {
  return [
    ...props.timeBoundaries.map((boundary) => ({
      label: new Date(boundary.date).toLocaleDateString("de", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      }),
      value: boundary.date,
    })),
    { label: "Keine Angabe", value: "no_choice" },
  ]
})
const selectedElement = computed({
  get() {
    return selectedTimeBoundaryModel.value?.date ?? "no_choice"
  },
  set(value: string) {
    selectedTimeBoundaryModel.value =
      value === "no_choice"
        ? undefined
        : props.timeBoundaries.find((tb) => tb.date === value)
  },
})

const { timeBoundariesId } = useElementId("timeBoundaries")

const destinationHrefEli = computed(() => {
  return "eli/bund/bgbl-1/2000/1"
})

const sentryTraceId = useSentryTraceId()
const { add: addToast } = useToast()
const { addErrorToast } = useErrorToast()

function showToast() {
  if (props.updateError) {
    addErrorToast(props.updateError, sentryTraceId)
  } else {
    addToast({
      summary: "Speichern erfolgreich",
      severity: "success",
    })
  }
}

watch(
  () => props.isUpdatingFinished,
  (finished) => {
    if (finished) {
      showToast()
    }
  },
)
</script>

<template>
  <form
    data-testid="mod-form"
    class="grid h-full max-h-full grid-cols-1 grid-rows-[min-content,min-content,1fr,min-content] gap-y-12 overflow-auto"
  >
    <div class="flex flex-col gap-6">
      <label :id="timeBoundariesId" class="ris-label2-regular"
        >Zeitgrenze</label
      >
      <Select
        v-model="selectedElement"
        :options="timeBoundaries"
        option-label="label"
        option-value="value"
        :aria-labelledby="timeBoundariesId"
      />
    </div>

    <div class="flex flex-col gap-6">
      <label class="ris-label2-regular" for="destinationHrefEli"
        >ELI Zielgesetz</label
      >
      <InputText
        id="destinationHrefEli"
        :model-value="destinationHrefEli"
        readonly
      />
    </div>
    <div class="flex">
      <Button
        class="relative ml-auto"
        label="Speichern"
        :loading="isUpdating"
        @click.prevent="$emit('update-mod')"
      >
        <template #icon>
          <IconCheck />
        </template>
      </Button>
    </div>
  </form>
</template>
