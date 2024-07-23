<script setup lang="ts">
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import RisDropdownInput from "@/components/controls/RisDropdownInput.vue"
import RisTextAreaInput from "@/components/controls/RisTextAreaInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { ModType } from "@/types/ModType"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { computed, nextTick, ref, watch } from "vue"
import CheckIcon from "~icons/ic/check"

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
  updateError?: any // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are `any`
  targetLawHtmlHtml?: string
}>()

const emit = defineEmits<{
  "generate-preview": []
  "update-mod": []
}>()
/** Optional selected time boundary of the format YYYY-MM-DD */
const selectedTimeBoundaryModel = defineModel<TemporalDataResponse | undefined>(
  "selectedTimeBoundary",
)
/** Destination Href for mod */
const destinationHrefModel = defineModel<string>("destinationHref")

/** This is the range of the text that will be replaced */
const destinationUpToModel = defineModel<string>("destinationUpTo")

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
    return selectedTimeBoundaryModel.value?.date ?? "no_choice"
  },
  set(value: string) {
    selectedTimeBoundaryModel.value =
      value === "no_choice"
        ? undefined
        : props.timeBoundaries.find((tb) => tb.date === value)
  },
})

const destinationHrefEli = computed(() => {
  const parts = destinationHrefModel.value?.split("/") || []

  if (
    props.textualModType === "aenderungsbefehl-ersetzen" &&
    props.quotedStructureContent
  ) {
    return parts.slice(0, -1).join("/")
  } else if (props.textualModType === "aenderungsbefehl-ersetzen") {
    return parts.slice(0, -2).join("/")
  }
  return ""
})

const destinationHrefEid = computed({
  get() {
    let eid = ""
    if (
      props.textualModType === "aenderungsbefehl-ersetzen" &&
      props.quotedStructureContent
    ) {
      eid = destinationHrefModel.value?.split("/").slice(-1).join("/") ?? ""
      return eid.replace(".xml", "")
    } else if (props.textualModType === "aenderungsbefehl-ersetzen") {
      eid = destinationHrefModel.value?.split("/").slice(-2).join("/") ?? ""
    }
    return eid
  },
  set(newValue: string) {
    if (destinationHrefModel.value) {
      const parts = destinationHrefModel.value.split("/")
      const newParts = newValue.split("/")

      if (
        props.textualModType === "aenderungsbefehl-ersetzen" &&
        props.quotedStructureContent
      ) {
        if (newParts.length === 1) {
          parts.splice(-1, 1, newParts[0])
        }
      } else if (props.textualModType === "aenderungsbefehl-ersetzen") {
        if (newParts.length === 2) {
          parts.splice(-2, 2, ...newParts)
        } else if (newParts.length === 1) {
          parts.splice(-2, 2, newParts[0], "")
        }
      }
      destinationHrefModel.value = parts.join("/")
      emit("generate-preview")
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

const destinationRangeUptoEid = computed({
  get() {
    let eid = ""
    eid = destinationUpToModel.value?.split("/").slice(-1).join("/") ?? ""
    return eid.replace(".xml", "")
  },
  set(newValue: string) {
    if (newValue) {
      destinationUpToModel.value = `${destinationHrefEli.value}/${newValue}`
    } else {
      destinationUpToModel.value = ""
    }
    emit("generate-preview")
  },
})

function getAllEidsBetween(
  startEid: string,
  endEid: string,
  container: HTMLElement,
) {
  const eids: string[] = []
  let collect = false

  const elements = container.querySelectorAll("[data-eid]")
  elements.forEach((el) => {
    const eid = el.getAttribute("data-eid")
    if (eid === startEid || eid === endEid) {
      if (!collect) {
        eids.push(eid)
        collect = true
      } else {
        eids.push(eid!)
        collect = false
      }
    } else if (collect && eid) {
      eids.push(eid)
    }
  })
  return eids
}

function handleAknElementClick({
  eid,
  originalEvent,
}: {
  eid: string
  originalEvent: MouseEvent | KeyboardEvent
}) {
  originalEvent.preventDefault()
  if (originalEvent.shiftKey) {
    destinationRangeUptoEid.value = eid
  } else {
    destinationHrefEid.value = eid
    destinationRangeUptoEid.value = ""
  }
}

const elementToBeReplacedRef = ref<InstanceType<typeof RisLawPreview> | null>(
  null,
)

const selectedElements = ref()

watch(
  [
    destinationRangeUptoEid,
    destinationHrefEid,
    elementToBeReplacedRef,
    () => props.targetLawHtmlHtml,
  ],
  async () => {
    if (destinationHrefEid.value == null) {
      selectedElements.value = []
      return
    }
    if (destinationRangeUptoEid.value == "") {
      selectedElements.value = [destinationHrefEid.value]
      return
    }

    const container = elementToBeReplacedRef.value?.$el
    if (container == null) {
      return
    }
    await nextTick()
    selectedElements.value = getAllEidsBetween(
      destinationHrefEid.value,
      destinationRangeUptoEid.value,
      elementToBeReplacedRef.value?.$el,
    )
  },
  { immediate: true },
)

watch(
  [destinationHrefEid, () => props.targetLawHtmlHtml],
  () => {
    if (destinationHrefEid.value) {
      nextTick(() => {
        const container = elementToBeReplacedRef.value?.$el
        if (container) {
          const element = container.querySelector(
            `[data-eid="${destinationHrefEid.value}"]`,
          )
          if (element) {
            element.scrollIntoView({
              behavior: "smooth",
              block: "center",
              inline: "start",
            })
          } else {
            container.scrollTo?.({ top: 0, behavior: "smooth" })
          }
        }
      })
    }
  },
  { immediate: true },
)
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
    <template
      v-if="
        textualModType === 'aenderungsbefehl-ersetzen' && quotedStructureContent
      "
    >
      <div class="mt-4">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label"
          >zu ersetzendes Element</label
        >
        <RisLawPreview
          id="elementToBeReplaced"
          ref="elementToBeReplacedRef"
          class="max-h-[300px] overflow-y-auto"
          data-testid="elementToBeReplaced"
          :content="targetLawHtmlHtml ?? ''"
          highlight-affected-document
          :rows="8"
          :selected="selectedElements"
          @click:akn:list="handleAknElementClick"
          @click:akn:long-title="handleAknElementClick"
          @click:akn:citations="handleAknElementClick"
          @click:akn:recitals="handleAknElementClick"
          @click:akn:recital="handleAknElementClick"
          @click:akn:block-container="handleAknElementClick"
          @click:akn:book="handleAknElementClick"
          @click:akn:part="handleAknElementClick"
          @click:akn:chapter="handleAknElementClick"
          @click:akn:subchapter="handleAknElementClick"
          @click:akn:section="handleAknElementClick"
          @click:akn:subsection="handleAknElementClick"
          @click:akn:title="handleAknElementClick"
          @click:akn:subtitle="handleAknElementClick"
          @click:akn:article="handleAknElementClick"
          @click:akn:paragraph="handleAknElementClick"
          @click:akn:document-ref="handleAknElementClick"
          @click:akn:component-ref="handleAknElementClick"
          @click:akn:point="handleAknElementClick"
          @click:akn:wrap-up="handleAknElementClick"
          @click:akn:foreign="handleAknElementClick"
          @click:akn:tblock="handleAknElementClick"
          @click:akn:toc="handleAknElementClick"
          @click:akn:toc-item="handleAknElementClick"
          @click:akn:p="handleAknElementClick"
          @click:akn:block="handleAknElementClick"
          @click:akn:num="handleAknElementClick"
          @click:akn:heading="handleAknElementClick"
          @click:akn:td="handleAknElementClick"
          @click:akn:th="handleAknElementClick"
          @click:akn:tr="handleAknElementClick"
          @click:akn:ol="handleAknElementClick"
          @click:akn:ul="handleAknElementClick"
          @click:akn:table="handleAknElementClick"
        />
      </div>
      <div class="mt-4">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label">Neues Element</label>
        <RisLawPreview
          id="replacingElement"
          class="max-h-[200px] overflow-y-auto"
          data-testid="replacingElement"
          highlight-affected-document
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
          :visible="isUpdatingFinished"
          :title="
            updateError ? 'Fehler beim Speichern' : 'Speichern erfolgreich'
          "
          alignment="right"
          attachment="top"
          :variant="updateError ? 'error' : 'success'"
          allow-dismiss
        >
          <template #default="{ ariaDescribedby }">
            <RisTextButton
              :aria-describedby="ariaDescribedby"
              label="Speichern"
              :icon="CheckIcon"
              :loading="isUpdating"
              @click.prevent="$emit('update-mod')"
            />
          </template>

          <template #message>
            <RisCopyableLabel
              v-if="updateError?.sentryEventId"
              name="Fehler-ID"
              text="Fehler-ID kopieren"
              :value="updateError?.sentryEventId"
            />
          </template>
        </RisTooltip>
      </div>
    </div>
  </form>
</template>

<style scoped>
:deep([class^="akn-"]):before,
:deep([class^="akn-"]):after {
  @apply border border-solid border-gray-500 bg-gray-300 px-2;
}

:deep([class^="akn-"]):before {
  border-radius: 0.375rem 0 0 0.375rem;
  border-right: none;
}

:deep([class^="akn-"]):after {
  border-radius: 0 0.375rem 0.375rem 0;
  border-left: none;
}

:deep(.akn-longTitle):before,
:deep(.akn-longTitle):after {
  content: "long title";
}

:deep(.akn-citations):before,
:deep(.akn-citations):after {
  content: "citations";
}

:deep(.akn-recitals):before,
:deep(.akn-recitals):after {
  content: "recitals";
}

:deep(.akn-recital):before,
:deep(.akn-recital):after {
  content: "recital";
}

:deep(.akn-blockContainer):before,
:deep(.akn-blockContainer):after {
  content: "block container";
}

:deep(.akn-book):before,
:deep(.akn-book):after {
  content: "book";
}

:deep(.akn-part):before,
:deep(.akn-part):after {
  content: "part";
}

:deep(.akn-chapter):before,
:deep(.akn-chapter):after {
  content: "chapter";
}

:deep(.akn-subchapter):before,
:deep(.akn-subchapter):after {
  content: "subchapter";
}

:deep(.akn-section):before,
:deep(.akn-section):after {
  content: "section";
}

:deep(.akn-subsection):before,
:deep(.akn-subsection):after {
  content: "subsection";
}

:deep(.akn-title):before,
:deep(.akn-title):after {
  content: "title";
}

:deep(.akn-subtitle):before,
:deep(.akn-subtitle):after {
  content: "subtitle";
}

:deep(.akn-article):before,
:deep(.akn-article):after {
  content: "article";
}

:deep(.akn-paragraph):before,
:deep(.akn-paragraph):after {
  content: "paragraph";
}

:deep(.akn-list):before,
:deep(.akn-list):after {
  content: "list";
}

:deep(.akn-documentRef):before,
:deep(.akn-documentRef):after {
  content: "document ref";
}

:deep(.akn-componentRef):before,
:deep(.akn-componentRef):after {
  content: "component ref";
}

:deep(.akn-point):before,
:deep(.akn-point):after {
  content: "point";
}

:deep(.akn-wrapUp):before,
:deep(.akn-wrapUp):after {
  content: "wrap up";
}

:deep(.akn-foreign):before,
:deep(.akn-foreign):after {
  content: "foreign";
}

:deep(.akn-tblock):before,
:deep(.akn-tblock):after {
  content: "tblock";
}

:deep(.akn-toc):before,
:deep(.akn-toc):after {
  content: "toc";
}

:deep(.akn-tocItem):before,
:deep(.akn-tocItem):after {
  content: "toc item";
}

:deep(.akn-p):before,
:deep(.akn-p):after {
  content: "p";
}

:deep(.akn-block):before,
:deep(.akn-block):after {
  content: "block";
}

:deep(.akn-num):before,
:deep(.akn-num):after {
  content: "num";
}

:deep(.akn-heading):before,
:deep(.akn-heading):after {
  content: "heading";
}

:deep(.akn-td):before,
:deep(.akn-td):after {
  content: "td";
}

:deep(.akn-th):before,
:deep(.akn-th):after {
  content: "th";
}

:deep(.akn-tr):before,
:deep(.akn-tr):after {
  content: "tr";
}

:deep(.akn-ol):before,
:deep(.akn-ol):after {
  content: "ol";
}

:deep(.akn-ul):before,
:deep(.akn-ul):after {
  content: "ul";
}

:deep(.akn-table):before,
:deep(.akn-table):after {
  content: "table";
}

:deep([class^="akn-"]:hover):not(:has([class^="akn-"]:hover)) {
  @apply border-highlight-quotedStructure-hover-border bg-highlight-quotedStructure-hover-background;
}

:deep([class^="akn-"]:hover):not(:has([class^="akn-"]:hover)):before,
:deep([class^="akn-"]:hover):not(:has([class^="akn-"]:hover)):after {
  @apply border-highlight-quotedStructure-hover-innerHover-border bg-highlight-quotedStructure-hover-innerHover-background;
}

:deep([class^="akn-"].selected) {
  @apply border-highlight-quotedStructure-hover-border bg-highlight-quotedStructure-selected-content;
}

:deep([class^="akn-"].selected):before,
:deep([class^="akn-"].selected):after {
  @apply border-highlight-quotedStructure-selected-pseudo-border bg-highlight-quotedStructure-selected-pseudo-background;
}
</style>
