<script setup lang="ts">
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { computed, ref, watch } from "vue"
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

// TODO unit test for this component
// TODO textualModType translation
// TODO ICON in Submit Button
// TODO remove console logs

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
const selectedTimeBoundaryRef = ref(selectedElement.value)
const destinationHrefEid = computed(() =>
  props.destinationHref.split("/").slice(-2).join("/"),
)
const destinationHrefEidRef = ref(destinationHrefEid.value)
const destinationHrefEli = computed(() =>
  props.destinationHref.split("/").slice(0, -2).join("/"),
)
const localQuotedTextSecond = ref(props.quotedTextSecond)
const localDestinationHref = ref(props.destinationHref)

watch(
  () => props.quotedTextSecond,
  (newVal) => {
    localQuotedTextSecond.value = newVal
  },
)

watch(destinationHrefEidRef, (newVal) => {
  localDestinationHref.value = `${destinationHrefEli.value}/${newVal}}`
  console.log("localDestinationHref", localDestinationHref.value)
})

watch(selectedTimeBoundaryRef, (newVal) => {
  console.log("selectedTimeBoundaryRef", newVal)
})
</script>

<template>
  <form :id="id" class="grid grid-cols-1 gap-y-20" data-testid="risModForm">
    <div class="grid grid-cols-3 gap-x-80">
      <RisTextInput
        id="textualModeType"
        label="Änderungstyp"
        model-value="ersetzen"
        read-only
        size="small"
        data-testid="textualModeType"
      />
      <RisDropdownInput
        id="timeBoundaries"
        v-model="selectedTimeBoundaryRef"
        label="Zeitgrenze"
        :items="timeBoundaries"
        data-testid="timeBoundaries"
      />
    </div>

    <RisTextInput
      id="destinationHrefEli"
      label="ELI Zielgesetz"
      :model-value="destinationHrefEli"
      read-only
      size="small"
      data-testid="destinationHrefEli"
    />
    <RisTextInput
      v-if="textualModType == 'replacement'"
      id="destinationHrefEid"
      v-model="destinationHrefEidRef"
      label="zu ersetzende Textstelle"
      size="small"
      data-testid="destinationHrefEid"
    />
    <RisTextAreaInput
      v-if="textualModType == 'replacement'"
      id="quotedTextFirst"
      label="zu ersetzender Text"
      :model-value="quotedTextFirst"
      read-only
      :rows="8"
      data-testid="quotedTextFirst"
    />
    <RisTextAreaInput
      id="quotedTextSecond"
      v-model="localQuotedTextSecond"
      label="Neuer Text Inhalt"
      :rows="8"
      data-testid="quotedTextSecond"
    />
    <div class="flex gap-20">
      <RisTextButton label="Vorschau" variant="tertiary" />
      <RisTextButton label="Dummy-Save" />
    </div>
  </form>
</template>
