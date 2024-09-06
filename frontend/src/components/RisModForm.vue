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
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useEIdRange } from "@/composables/useEIdRange"
import RisCharacterRangeSelect from "@/components/RisCharacterRangeSelect.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"

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
  targetLawHtml?: string
  targetLawHtmlIsFetching?: boolean
  targetLawHtmlError?: any // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are `any`
  targetLaw?: string
  targetLawIsFetching?: boolean
  targetLawError?: any // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are `any`
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

const isQuotedStructure = computed(() => !!props.quotedStructureContent)

const destinationHrefEli = computed(() => {
  const parts = destinationHrefModel.value?.split("/") || []

  switch (props.textualModType) {
    case "aenderungsbefehl-ersetzen":
      if (isQuotedStructure.value) {
        return parts.slice(0, -1).join("/")
      } else {
        return parts.slice(0, -2).join("/")
      }
  }
  return ""
})

function updateHrefEId(
  oldHref: string,
  newEIdWithOptionalCharacterRange: string,
): string {
  const newParts = newEIdWithOptionalCharacterRange.split("/")

  switch (props.textualModType) {
    case "aenderungsbefehl-ersetzen":
      if (isQuotedStructure.value) {
        if (newParts.length !== 1) {
          return oldHref
        }

        return oldHref.split("/").toSpliced(-1, 1, newParts[0]).join("/")
      }

      if (newParts.length === 1) {
        return oldHref.split("/").toSpliced(-2, 2, newParts[0], "").join("/")
      }

      if (newParts.length === 2) {
        return oldHref
          .split("/")
          .toSpliced(-2, 2, ...newParts)
          .join("/")
      }

      return oldHref
  }

  return oldHref
}

const destinationHrefEid = computed({
  get() {
    switch (props.textualModType) {
      case "aenderungsbefehl-ersetzen":
        if (isQuotedStructure.value) {
          return (
            destinationHrefModel.value
              ?.split("/")
              .slice(-1)
              .join("/")
              ?.replace(".xml", "") ?? ""
          )
        } else {
          return (
            destinationHrefModel.value?.split("/").slice(-2).join("/") ?? ""
          )
        }
    }
    return ""
  },
  set(newValue: string) {
    if (!destinationHrefModel.value) {
      return
    }

    destinationHrefModel.value = updateHrefEId(
      destinationHrefModel.value,
      newValue,
    )

    emit("generate-preview")
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
    return (
      destinationUpToModel.value
        ?.split("/")
        .slice(-1)
        .join("/")
        .replace(".xml", "") ?? ""
    )
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

const selectedElements = useEIdRange(
  destinationHrefEid,
  destinationRangeUptoEid,
  computed(() => props.targetLawHtml),
)

watch(
  [
    destinationHrefEid,
    () => props.targetLawHtml,
    () => elementToBeReplacedRef.value,
  ],
  async () => {
    if (destinationHrefEid.value) {
      await nextTick()

      const container = elementToBeReplacedRef.value?.$el
      if (container) {
        const element = container.querySelector(
          `[data-eid="${destinationHrefEid.value}"]`,
        )
        if (element) {
          element.scrollIntoView({
            behavior: "smooth",
            block: "start",
            inline: "start",
          })
        } else {
          container.scrollTo?.({ top: 0, behavior: "smooth" })
        }
      }
    }
  },
  { immediate: true },
)

function handlePreviewKeyDown(e: KeyboardEvent) {
  if (e.key === "a" && (e.metaKey || e.ctrlKey)) {
    e.preventDefault()
  }
}

function handleMouseDown(e: MouseEvent) {
  if (e.ctrlKey || e.shiftKey || e.metaKey) {
    e.preventDefault()
  }
}

const sentryTraceId = useSentryTraceId()

const selectableAknElements: string[] = [
  "akn:list",
  "akn:long-title",
  "akn:citations",
  "akn:recitals",
  "akn:recital",
  "akn:block-container",
  "akn:book",
  "akn:part",
  "akn:chapter",
  "akn:subchapter",
  "akn:section",
  "akn:subsection",
  "akn:title",
  "akn:subtitle",
  "akn:article",
  "akn:paragraph",
  "akn:point",
  "akn:wrap-up",
  "akn:foreign",
  "akn:tblock",
  "akn:toc",
  "akn:toc-item",
  "akn:p",
  "akn:block",
  "akn:num",
  "akn:heading",
  "akn:td",
  "akn:th",
  "akn:tr",
  "akn:ol",
  "akn:ul",
  "akn:table",
]

const selectableAknElementsLabels = Object.fromEntries(
  selectableAknElements.map((aknElement) => [
    `${aknElement}-aria-label`,
    aknElement.split(":")[1].replaceAll("-", " "),
  ]),
)

const selectableAknElementsEventHandlers = Object.fromEntries(
  selectableAknElements.map((aknElement) => [
    `click:${aknElement}`,
    handleAknElementClick,
  ]),
)
</script>

<template>
  <form
    :id="id"
    class="grid h-full max-h-full grid-cols-1 grid-rows-[min-content,min-content,1fr,min-content] gap-y-12 overflow-auto"
  >
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
    <div
      v-if="
        textualModType === 'aenderungsbefehl-ersetzen' && quotedStructureContent
      "
      class="grid max-h-full grid-cols-subgrid grid-rows-[2fr,1fr] gap-y-12 overflow-hidden"
    >
      <div class="flex flex-col gap-2 overflow-hidden">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label"
          >Zu ersetzendes Element</label
        >
        <div
          v-if="targetLawHtmlIsFetching"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="targetLawHtmlError">
          <RisErrorCallout :error="targetLawHtmlError" />
        </div>
        <RisLawPreview
          v-else
          v-bind="selectableAknElementsLabels"
          id="elementToBeReplaced"
          ref="elementToBeReplacedRef"
          class="ds-textarea overflow-y-auto p-2"
          :class="$style.preview"
          data-testid="elementToBeReplaced"
          :content="targetLawHtml ?? ''"
          :selected="selectedElements"
          :arrow-focus="false"
          :styled="false"
          v-on="selectableAknElementsEventHandlers"
          @keydown="handlePreviewKeyDown"
          @mousedown="handleMouseDown"
        />
      </div>
      <div class="flex flex-col gap-2 overflow-hidden">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label">Neues Element</label>
        <RisLawPreview
          id="replacingElement"
          class="ds-textarea h-full overflow-y-auto p-2"
          :class="$style.preview"
          data-testid="replacingElement"
          :arrow-focus="false"
          :content="quotedStructureContent"
          :styled="false"
        />
      </div>
    </div>
    <div
      v-else-if="textualModType === 'aenderungsbefehl-ersetzen'"
      class="grid max-h-full grid-cols-subgrid grid-rows-[min-content,2fr,1fr] gap-y-12 overflow-hidden"
    >
      <RisTextInput
        id="destinationHrefEid"
        v-model="destinationHrefEid"
        label="zu ersetzende Textstelle"
        @blur="$emit('generate-preview')"
      />
      <div class="grid grid-rows-[max-content,1fr] gap-2 overflow-hidden">
        <div id="label-old-text" class="ds-label">zu ersetzender Text</div>
        <div
          v-if="
            targetLawIsFetching ||
            targetLawHtmlIsFetching ||
            (!targetLawHtml && !targetLawError) ||
            (!targetLawHtmlError && !targetLawHtml)
          "
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="targetLawError || targetLawHtmlError">
          <RisErrorCallout :error="targetLawError ?? targetLawHtmlError" />
        </div>
        <RisCharacterRangeSelect
          v-else
          v-model="destinationHrefEid"
          :xml="targetLaw ?? ''"
          :render="targetLawHtml ?? ''"
          aria-labelledby="label-old-text"
        />
      </div>
      <RisTextAreaInput
        id="quotedTextSecond"
        v-model="quotedTextSecondModel"
        class="h-full"
        label="Neuer Text Inhalt"
        @blur="$emit('generate-preview')"
      />
    </div>

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
              v-if="updateError"
              name="Trace-ID"
              text="Trace-ID kopieren"
              :value="sentryTraceId"
            />
          </template>
        </RisTooltip>
      </div>
    </div>
  </form>
</template>

<!-- We need to use a module for this part of the styling as there is a bug in vue that wrongly converts some tags in nested scoped styling -->
<style module>
.preview {
  [class^="akn-"],
  h1,
  h2,
  h3,
  h4,
  h5,
  tbody {
    @apply block w-min min-w-full;
  }

  :global(
      :is(
          .akn-article,
          .akn-block,
          .akn-blockContainer,
          .akn-book,
          .akn-chapter,
          .akn-citations,
          .akn-foreign,
          .akn-heading,
          .akn-list,
          .akn-longTitle,
          .akn-num,
          .akn-ol,
          .akn-p,
          .akn-paragraph,
          .akn-part,
          .akn-point,
          .akn-recital,
          .akn-recitals,
          .akn-section,
          .akn-subchapter,
          .akn-subsection,
          .akn-subtitle,
          .akn-table,
          .akn-tblock,
          .akn-td,
          .akn-th,
          .akn-title,
          .akn-toc,
          .akn-tocItem,
          .akn-tr,
          .akn-ul,
          .akn-wrapUp
        )
    ) {
    @apply rounded p-8 outline outline-dashed outline-1 outline-highlight-elementSelect-default-border;

    &:before {
      @apply ds-label-03-reg block px-2 pb-8 text-start font-[monospace] text-[#4E596A];
    }

    &:global(.selected) {
      @apply border-transparent bg-highlight-elementSelect-selected-background outline outline-2 outline-highlight-elementSelect-selected-border;

      &:before {
        @apply ds-label-03-bold text-black;
      }
    }

    /* The most deeply nested element that is currently hovered and not selected */

    &:hover:not(:has([class^="akn-"]:hover)):not(:global(.selected)) {
      @apply border-transparent bg-highlight-elementSelect-hover-background outline-dashed outline-2 outline-highlight-elementSelect-hover-border;
    }
  }

  :not(:is(:last-child)) {
    @apply mb-8 mr-0;
  }

  /**
   * Special styling for showing table rows in one row
   */

  :global(.akn-tr) {
    @apply grid auto-cols-fr grid-flow-col grid-rows-[min-content,1fr] gap-8;

    &:before {
      @apply -mb-8;
    }
  }

  :global(.akn-tr) > * {
    @apply row-start-2 mb-0;
  }
}

/**
 * Special styling for akn:num
 */
.preview :has(> :first-child:is(:global(.akn-num)):not(:only-child)),
.preview
  :has(
    > :first-child:is(h1, h2, h3, h4, h5) > :only-child:is(:global(.akn-num))
  ) {
  @apply grid grid-cols-[max-content,1fr] gap-x-8;

  &:before {
    @apply col-span-full;
  }

  :first-child {
    @apply col-span-1 col-start-1 h-fit;
  }
}

.preview :has(> :first-child:is(:global(.akn-num)):not(:only-child)) > *,
.preview
  :has(
    > :first-child:is(h1, h2, h3, h4, h5) > :only-child:is(:global(.akn-num))
  )
  > * {
  @apply col-span-1 col-start-2;
}
</style>

<style scoped>
#elementToBeReplaced * {
  @apply scroll-m-20;
}

.preview {
  :deep(
    :is(
        /* inline elements without akn:num */
        .akn-a,
        .akn-abbr,
        .akn-affectedDocument,
        .akn-authorialNote,
        .akn-b,
        .akn-br,
        .akn-date,
        .akn-documentRef,
        .akn-componentRef,
        .akn-eol,
        .akn-eop,
        .akn-i,
        .akn-img,
        .akn-inline,
        .akn-location,
        .akn-marker,
        .akn-organization,
        .akn-person,
        .akn-quotedText,
        .akn-ref,
        .akn-relatedDocument,
        .akn-role,
        .akn-rref,
        .akn-session,
        .akn-shortTitle,
        .akn-span,
        .akn-sub,
        .akn-sup,
        .akn-u
      )
  ) {
    :deep(&):not(:empty) {
      @apply inline border border-highlight-elementSelect-hover-border bg-highlight-elementSelect-hover-background;
    }
  }

  :deep(.akn-longTitle):before {
    content: "long title";
  }

  :deep(.akn-citations):before {
    content: "citations";
  }

  :deep(.akn-recitals):before {
    content: "recitals";
  }

  :deep(.akn-recital):before {
    content: "recital";
  }

  :deep(.akn-blockContainer):before {
    content: "block container";
  }

  :deep(.akn-book):before {
    content: "book";
  }

  :deep(.akn-part):before {
    content: "part";
  }

  :deep(.akn-chapter):before {
    content: "chapter";
  }

  :deep(.akn-subchapter):before {
    content: "subchapter";
  }

  :deep(.akn-section):before {
    content: "section";
  }

  :deep(.akn-subsection):before {
    content: "subsection";
  }

  :deep(.akn-title):before {
    content: "title";
  }

  :deep(.akn-subtitle):before {
    content: "subtitle";
  }

  :deep(.akn-article):before {
    content: "article";
  }

  :deep(.akn-paragraph):before {
    content: "paragraph";
  }

  :deep(.akn-list):before {
    content: "list";
  }

  :deep(.akn-point):before {
    content: "point";
  }

  :deep(.akn-wrapUp):before {
    content: "wrap up";
  }

  :deep(.akn-foreign):before {
    content: "foreign";
  }

  :deep(.akn-tblock):before {
    content: "tblock";
  }

  :deep(.akn-toc):before {
    content: "toc";
  }

  :deep(.akn-tocItem):before {
    content: "toc item";
  }

  :deep(.akn-p):before {
    content: "p";
  }

  :deep(.akn-block):before {
    content: "block";
  }

  :deep(.akn-num):before {
    content: "num";
  }

  :deep(.akn-heading):before {
    content: "heading";
  }

  :deep(.akn-td):before {
    content: "td";
  }

  :deep(.akn-th):before {
    content: "th";
  }

  :deep(.akn-tr):before {
    content: "tr";
  }

  :deep(.akn-ol):before {
    content: "ol";
  }

  :deep(.akn-ul):before {
    content: "ul";
  }

  :deep(.akn-table):before {
    content: "table";
  }
}
</style>
