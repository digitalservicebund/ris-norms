<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { useElementId } from "@/composables/useElementId"
import type { EditableZielnormReference } from "@/composables/useZielnormReferences"
import { formatDate } from "@/lib/dateTime"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import { Button, InputText, Select } from "primevue"
import { computed } from "vue"
import IcBaselineCheck from "~icons/ic/baseline-check"

const { zeitgrenzen = [] } = defineProps<{
  /** List of Zeitgrenzen to be offered as options for selection */
  zeitgrenzen?: Zeitgrenze[]

  /** When true, shows a loading state on the save button */
  loading?: boolean
}>()

/** Zielnorm reference that should be edited */
const model = defineModel<EditableZielnormReference>({ required: true })

defineEmits<{
  /** Emitted when the save button is clicked */
  save: []

  /** Emitted when the delete button is clicked */
  delete: []
}>()

const { formHeadingId, eliInputId, zeitgrenzeSelectId } = useElementId()

const zeitgrenzenOptions = computed(() =>
  zeitgrenzen
    .filter((i) => !!i.date)
    .map((zeitgrenze, i) => ({
      colorIndex: i,
      label: formatDate(zeitgrenze.date),
      value: zeitgrenze.id,
    })),
)

function findZeitgrenze(id: string) {
  return zeitgrenzenOptions.value.find((i) => i.value === id)
}

const zeitgrenze = computed({
  get() {
    return model.value.geltungszeit
      ? findZeitgrenze(model.value.geltungszeit)
      : undefined
  },
  set(value) {
    model.value = { ...model.value, geltungszeit: value?.value ?? "" }
  },
})

const eli = computed({
  get() {
    return model.value.zielnorm
  },
  set(value) {
    model.value = { ...model.value, zielnorm: value }
  },
})
</script>

<template>
  <form :aria-labelledby="formHeadingId" class="space-y-10">
    <div :id="formHeadingId" class="ris-subhead-bold mb-16">
      ELIs und Geltungszeitregeln verknüpfen
    </div>

    <div class="flex flex-col gap-2">
      <label :for="eliInputId">ELI Zielnormenkomplex</label>
      <InputText :id="eliInputId" v-model="eli" />
    </div>

    <div class="flex flex-col gap-2">
      <label :id="zeitgrenzeSelectId">Geltungszeitregel</label>
      <Select
        v-model="zeitgrenze"
        :aria-labelledby="zeitgrenzeSelectId"
        :options="zeitgrenzenOptions"
        option-label="label"
      >
        <template #value="{ value, placeholder }">
          <div v-if="value" class="flex items-center gap-8">
            <RisHighlightColorSwatch :color-index="value.colorIndex" />
            {{ value.label }}
          </div>

          <template v-else>{{ placeholder }}</template>
        </template>

        <template #option="{ option }">
          <div class="flex items-center gap-8">
            <RisHighlightColorSwatch :color-index="option.colorIndex" />
            {{ option.label }}
          </div>
        </template>

        <template #empty>
          <div class="px-24 py-16">Keine Geltungszeiten gefunden</div>
        </template>
      </Select>
    </div>

    <footer class="mt-16 flex justify-between gap-8">
      <Button label="Speichern" :disabled="loading" @click="$emit('save')">
        <template #icon>
          <IcBaselineCheck />
        </template>
      </Button>
      <Button
        label="Einträge entfernen"
        text
        :disabled="loading"
        @click="$emit('delete')"
      ></Button>
    </footer>
  </form>
</template>
