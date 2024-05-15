<script setup lang="ts">
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { computed } from "vue"
import RisTextAreaInput from "@/components/controls/RisTextAreaInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import CheckIcon from "~icons/ic/check"

const props = defineProps<{
  /** Unique ID for the dro. */
  id: string
  /** Either replacement, insertion or repeal */
  textualModType: string
  /** the items for the dropdown. */
  timeBoundaries: DropdownItem[]
  /** Optional selected time boundary of the format YYYY-MM-DD */
  selectedTimeBoundary?: string
  /** Destination Href for mod */
  destinationHref: string
  /** This is the text that will be replaced */
  quotedTextFirst?: string
  /** This is the text that replaces quotedTextFirst */
  quotedTextSecond?: string
}>()

const selectedTimeBoundaryModel = defineModel<string | undefined>(
  "selectedTimeBoundary",
)
const destinationHrefModel = defineModel<string>("destinationHref")
const quotedTextSecondModel = defineModel<string | undefined>(
  "quotedTextSecond",
)

const timeBoundaries = computed(() => {
  return [
    ...props.timeBoundaries,
    { label: "Keine Angabe", value: "no_choice" },
  ]
})
const selectedElement = computed({
  get() {
    return selectedTimeBoundaryModel.value || "no_choice"
  },
  set(value: string) {
    selectedTimeBoundaryModel.value = value === "no_choice" ? undefined : value
  },
})

const destinationHrefEli = computed(() =>
  destinationHrefModel.value?.split("/").slice(0, -2).join("/"),
)

const destinationHrefEid = computed({
  get() {
    return destinationHrefModel.value?.split("/").slice(-2).join("/") || ""
  },
  set(newValue: string) {
    if (destinationHrefModel.value) {
      const parts = destinationHrefModel.value.split("/")
      parts.splice(-2, 2, ...newValue.split("/"))
      destinationHrefModel.value = parts.join("/")
    }
  },
})
</script>

<template>
  <form :id="id" class="grid grid-cols-1 gap-y-20" role="form">
    <div class="grid grid-cols-2 gap-x-80">
      <RisTextInput
        id="textualModeType"
        label="Änderungstyp"
        model-value="ersetzen"
        read-only
        size="small"
      />
      <RisDropdownInput
        id="timeBoundaries"
        v-model="selectedElement"
        label="Zeitgrenze"
        :items="timeBoundaries"
      />
    </div>

    <RisTextInput
      id="destinationHrefEli"
      label="ELI Zielgesetz"
      :model-value="destinationHrefEli"
      read-only
      size="small"
    />
    <RisTextInput
      v-if="textualModType === 'replacement'"
      id="destinationHrefEid"
      v-model="destinationHrefEid"
      label="zu ersetzende Textstelle"
      size="small"
    />
    <RisTextAreaInput
      v-if="textualModType === 'replacement'"
      id="quotedTextFirst"
      label="zu ersetzender Text"
      :model-value="quotedTextFirst"
      read-only
      :rows="8"
    />
    <RisTextAreaInput
      id="quotedTextSecond"
      v-model="quotedTextSecondModel"
      label="Neuer Text Inhalt"
      :rows="8"
    />
    <div class="flex gap-20">
      <RisTextButton label="Vorschau" variant="tertiary" />
      <RisTextButton label="Speichern" :icon="CheckIcon" />
    </div>
  </form>
</template>
