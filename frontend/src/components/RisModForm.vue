<script setup lang="ts">
import RisDropdownInput from "@/components/controls/RisDropdownInput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { computed } from "vue"
import RisTextAreaInput from "@/components/controls/RisTextAreaInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import CheckIcon from "~icons/ic/check"
import { ModType } from "@/types/ModType"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"

const props = defineProps<{
  /** Unique ID for the dro. */
  id: string
  /** Either replacement, insertion or repeal */
  textualModType: ModType | ""
  /** the possible time boundaries in the format YYYY-MM-DD. */
  timeBoundaries: TemporalDataResponse[]
  /** This is the text that will be replaced */
  quotedTextFirst?: string
  /** The quoted structure content */
  quotedStructureContent?: string | null
  isUpdating?: boolean
  isUpdatingFinished?: boolean
  updateError?: Error
  targetLawHtmlHtml?: string
}>()

defineEmits<{
  "generate-preview": []
  "update-mod": []
}>()
/** Optional selected time boundary of the format YYYY-MM-DD */
const selectedTimeBoundaryModel = defineModel<TemporalDataResponse | undefined>(
  "selectedTimeBoundary",
)
/** Destination Href for mod */
const destinationHrefModel = defineModel<string>("destinationHref")
/** This is the text that replaces quotedTextFirst */
const quotedTextSecondModel = defineModel<string | undefined>(
  "quotedTextSecond",
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
    return selectedTimeBoundaryModel.value?.date || "no_choice"
  },
  set(value: string) {
    selectedTimeBoundaryModel.value =
      value === "no_choice"
        ? undefined
        : props.timeBoundaries.find((tb) => tb.date === value)
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
      const newParts = newValue.split("/")
      if (newParts.length === 2) {
        parts.splice(-2, 2, ...newParts)
      } else if (newParts.length === 1) {
        parts.splice(-2, 2, newParts[0], "")
      }
      destinationHrefModel.value = parts.join("/")
    }
  },
})

/**
 * Provides the human-readable label for the given ModType
 */
function modTypeLabel(modType: ModType | "") {
  switch (modType) {
    case "aenderungsbefehl-einfuegen":
      return "Einfügen"
    case "aenderungsbefehl-ersetzen":
      return "Ersetzen"
    case "aenderungsbefehl-streichen":
      return "Streichen"
    case "aenderungsbefehl-neufassung":
      return "Neufassen"
    case "aenderungsbefehl-ausserkrafttreten":
      return "Außerkrafttreten"
    case "":
      return "Keine Angabe"
  }
}
</script>

<template>
  <form :id="id" class="grid grid-cols-1 gap-y-12">
    <div class="grid grid-cols-2 gap-x-16">
      <RisDropdownInput
        id="timeBoundaries"
        v-model="selectedElement"
        label="Zeitgrenze"
        :items="timeBoundaries"
        @change="$emit('generate-preview')"
      />
      <RisTextInput
        id="textualModeType"
        label="Änderungstyp"
        :model-value="modTypeLabel(textualModType)"
        read-only
      />
    </div>

    <RisTextInput
      id="destinationHrefEli"
      label="ELI Zielgesetz"
      :model-value="destinationHrefEli"
      read-only
    />
    <!-- Conditional Rendering Based on ModType and Quoted Structure Presence -->
    <template
      v-if="
        textualModType === 'aenderungsbefehl-ersetzen' && quotedStructureContent
      "
    >
      <div class="mt-4 max-h-[300px] overflow-y-auto">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label"
          >zu ersetzendes Element</label
        >
        <RisLawPreview
          id="elementToBeReplaced"
          data-testid="elementToBeReplaced"
          :content="targetLawHtmlHtml ?? ''"
          :rows="8"
        />
      </div>
      <div class="mt-4">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label">Neues Element</label>
        <RisLawPreview
          id="replacingElement"
          data-testid="replacingElement"
          :content="quotedStructureContent"
          :rows="8"
        />
      </div>
    </template>
    <template v-else-if="textualModType === 'aenderungsbefehl-ersetzen'">
      <RisTextInput
        id="destinationHrefEid"
        v-model="destinationHrefEid"
        label="zu ersetzende Textstelle"
        @blur="$emit('generate-preview')"
      />
      <RisTextAreaInput
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
        @blur="$emit('generate-preview')"
      />
    </template>

    <div class="flex">
      <RisTextButton
        label="Vorschau"
        variant="tertiary"
        @click.prevent="$emit('generate-preview')"
      />

      <div class="relative ml-auto">
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          :visible="isUpdatingFinished"
          :title="
            updateError ? 'Fehler beim Speichern' : 'Speichern erfolgreich'
          "
          alignment="right"
          attachment="top"
          :variant="updateError ? 'error' : 'success'"
          allow-dismiss
        >
          <RisTextButton
            :aria-describedby="ariaDescribedby"
            label="Speichern"
            :icon="CheckIcon"
            :loading="isUpdating"
            @click.prevent="$emit('update-mod')"
          />
        </RisTooltip>
      </div>
    </div>
  </form>
</template>
