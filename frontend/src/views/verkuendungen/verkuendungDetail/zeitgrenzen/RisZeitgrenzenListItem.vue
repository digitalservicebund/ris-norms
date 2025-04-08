<script setup lang="ts">
import RisDateInput from "@/components/controls/RisDateInput.vue"
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { useElementId } from "@/composables/useElementId"
import { formatDate } from "@/lib/dateTime"
import type { Zeitgrenze, ZeitgrenzeArt } from "@/types/zeitgrenze"
import Button from "primevue/button"
import Checkbox from "primevue/checkbox"
import Select from "primevue/select"
import { computed, onMounted, useTemplateRef } from "vue"
import IcBaselineClear from "~icons/ic/baseline-clear"

/** Zeitgrenze to be displayed and edited in the component. */
const zeitgrenze = defineModel<Zeitgrenze>({ required: true })

const { autofocus } = defineProps<{
  /** Position of the Zeitgrenze. Used for determining the color. */
  index: number

  /** If true, will focus the Geltungszeit input once when mounted. */
  autofocus?: boolean
}>()

defineEmits<{
  /** Emitted when the user pressed the delete button. */
  delete: []
}>()

const { unbestimmtCheckboxId, geltungszeitInputId, artSelectId } =
  useElementId()

const geltungszeitEl = useTemplateRef("geltungszeitEl")

onMounted(() => {
  if (autofocus && geltungszeitEl.value) geltungszeitEl.value.focus()
})

const deleteButtonHint = computed(() =>
  zeitgrenze.value.date
    ? `Zeitgrenze vom ${formatDate(zeitgrenze.value.date)} entfernen`
    : `Zeitgrenze entfernen`,
)

const geltungszeit = computed({
  get() {
    return zeitgrenze.value.date
  },
  set(value) {
    zeitgrenze.value = { ...zeitgrenze.value, date: value ?? "" }
  },
})

const artOptions: Array<{ label: string; value: ZeitgrenzeArt }> = [
  { label: "Inkrafttreten", value: "inkrafttreten" },
  { label: "Außerkrafttreten", value: "ausserkrafttreten" },
]

const art = computed({
  get() {
    return zeitgrenze.value.art
  },
  set(value) {
    zeitgrenze.value = { ...zeitgrenze.value, art: value }
  },
})
</script>

<template>
  <label
    class="ris-label1-regular flex flex-nowrap items-center justify-center gap-12"
    :for="unbestimmtCheckboxId"
  >
    <Checkbox :input-id="unbestimmtCheckboxId" binary disabled></Checkbox>
    <span class="sr-only @lg:not-sr-only">unbestimmt</span>
  </label>

  <div class="pl-8">
    <RisHighlightColorSwatch :color-index="index" :size="['h-28', 'w-28']" />
  </div>

  <label :for="geltungszeitInputId">
    <span class="sr-only">Geltungszeit</span>
    <RisDateInput
      :id="geltungszeitInputId"
      ref="geltungszeitEl"
      v-model="geltungszeit"
      fluid
    />
  </label>

  <label :for="artSelectId">
    <span class="sr-only">Art</span>
    <Select
      v-model="art"
      :label-id="artSelectId"
      :options="artOptions"
      fluid
      option-label="label"
      option-value="value"
    />
  </label>

  <div>
    <Button :aria-label="deleteButtonHint" text @click="$emit('delete')">
      <template #icon><IcBaselineClear /></template>
    </Button>
  </div>
</template>
