<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { useElementId } from "@/composables/useElementId"
import {
  INDETERMINATE_VALUE,
  type EditableZielnormReference,
} from "@/composables/useZielnormReferences"
import { formatDate } from "@/lib/dateTime"
import { getZeitgrenzeArtLabel } from "@/lib/zeitgrenze"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import { Button, InputText, Select } from "primevue"
import { computed } from "vue"
import IcBaselineCheck from "~icons/ic/baseline-check"

const { zeitgrenzen = [] } = defineProps<{
  /** List of Zeitgrenzen to be offered as options for selection */
  zeitgrenzen?: Zeitgrenze[]

  /** When true, shows a loading state on the save button */
  updating?: boolean

  /** When true, shows a loading state on the delete button */
  deleting?: boolean
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

type ZeitgrenzeOption = {
  colorIndex?: number
  label: string
  value: string
  disabled?: boolean
}

const zeitgrenzenOptions = computed(() => {
  const options = zeitgrenzen
    .filter((i) => !!i.date)
    .map<ZeitgrenzeOption>((zeitgrenze, i) => ({
      colorIndex: i,
      label: `${formatDate(zeitgrenze.date)} (${getZeitgrenzeArtLabel(zeitgrenze.art)})`,
      value: zeitgrenze.id,
    }))

  if (model.value.geltungszeit === INDETERMINATE_VALUE) {
    options.unshift({
      colorIndex: undefined,
      label: "Mehrere ausgewählt",
      value: INDETERMINATE_VALUE,
      disabled: true,
    })
  }

  return options
})

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
    return model.value.zielnorm === INDETERMINATE_VALUE
      ? ""
      : model.value.zielnorm
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
      <InputText
        :id="eliInputId"
        v-model="eli"
        :placeholder="
          model.zielnorm === INDETERMINATE_VALUE ? 'Mehrere' : undefined
        "
      />
    </div>

    <div class="flex flex-col gap-2">
      <label :id="zeitgrenzeSelectId">Geltungszeitregel</label>
      <Select
        v-model="zeitgrenze"
        :aria-labelledby="zeitgrenzeSelectId"
        :options="zeitgrenzenOptions"
        option-disabled="disabled"
        option-label="label"
      >
        <template #value="{ value, placeholder }">
          <div
            v-if="value"
            class="flex items-center gap-8"
            :class="{ 'text-gray-800': value.value === INDETERMINATE_VALUE }"
          >
            <RisHighlightColorSwatch :color-index="value.colorIndex" />
            {{ value.label }}
          </div>

          <template v-else>{{ placeholder }}</template>
        </template>

        <template #option="{ option }">
          <div
            class="flex items-center gap-8"
            :class="{ 'text-gray-800': option.value === INDETERMINATE_VALUE }"
          >
            <RisHighlightColorSwatch :color-index="option.colorIndex" />
            {{ option.label }}
          </div>
        </template>

        <template #empty>
          <div class="px-24 py-16">Keine Zeitgrenzen gefunden</div>
        </template>
      </Select>
    </div>

    <footer class="mt-16 flex justify-between gap-8">
      <Button
        :disabled="updating || deleting"
        :loading="updating"
        label="Speichern"
        @click="$emit('save')"
      >
        <template #icon>
          <IcBaselineCheck />
        </template>
      </Button>
      <Button
        :disabled="updating || deleting"
        :loading="deleting"
        label="Einträge entfernen"
        text
        @click="$emit('delete')"
      ></Button>
    </footer>
  </form>
</template>
