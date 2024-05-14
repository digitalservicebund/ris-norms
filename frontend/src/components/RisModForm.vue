<script setup lang="ts">
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { computed } from "vue"
import RisTextAreaInput from "@/components/controls/RisTextAreaInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"

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

// TODO one test about label is missing
// TODO unit test for this component
// TODO logic for inputType

const timeBoundaries = computed(() => {
  return [
    ...props.timeBoundaries,
    { label: "Keine Angabe", value: "no_choice" },
  ]
})

const selectedElement = computed(() => {
  if (!props.selectedTimeBoundary) {
    return "no_choice"
  }
  return props.selectedTimeBoundary
})

const destinationHrefEid = computed(() => {
  return props.destinationHref.split("/").slice(-2).join("/")
})

const destinationHrefEli = computed(() => {
  const split = props.destinationHref.split("/")
  return split.slice(0, split.length - 2).join("/")
})
</script>

<template>
  <form :id="id" class="grid grid-cols-1 gap-y-20">
    <div class="grid grid-cols-3 gap-x-80">
      <RisTextInput
        id="textualModeType"
        label="Änderungstyp"
        model-value="ersetzen"
        read-only
        size="small"
      />
      <RisDropdownInput
        id="timeBoundaries"
        label="Zeitgrenze"
        :model-value="selectedElement"
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
      v-if="textualModType == 'replacement'"
      id="destinationHrefEid"
      label="zu ersetzende Textstelle"
      :model-value="destinationHrefEid"
      size="small"
    />
    <RisTextAreaInput
      v-if="textualModType == 'replacement'"
      id="quotedTextFirst"
      label="zu ersetzender Text"
      :model-value="quotedTextFirst"
      read-only
      :rows="8"
    />
    <RisTextAreaInput
      id="quotedTextSecond"
      label="Neuer Text Inhalt"
      :model-value="quotedTextSecond"
      :rows="8"
    />
    <div class="flex gap-20">
      <RisTextButton label="Vorschau" variant="tertiary" />
      <RisTextButton label="Speichern" />
    </div>
  </form>
</template>
