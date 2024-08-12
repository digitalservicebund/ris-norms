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
            element.style.scrollMarginLeft = "20px"
            element.style.scrollMarginTop = "20px"
            element.scrollIntoView({
              behavior: "smooth",
              block: "start",
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
      <div class="flex flex-col gap-2">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label"
          >Zu ersetzendes Element</label
        >
        <RisLawPreview
          id="elementToBeReplaced"
          ref="elementToBeReplacedRef"
          class="ds-textarea max-h-[250px] overflow-y-auto p-2"
          data-testid="elementToBeReplaced"
          :content="targetLawHtmlHtml ?? ''"
          :selected="selectedElements"
          :arrow-focus="false"
          akn:long-title-aria-label="long title"
          akn:citations-aria-label="citations"
          akn:recitals-aria-label="recitals"
          akn:recital-aria-label="recital"
          akn:block-container-aria-label="block container"
          akn:book-aria-label="book"
          akn:part-aria-label="part"
          akn:chapter-aria-label="chapter"
          akn:subchapter-aria-label="subchapter"
          akn:section-aria-label="section"
          akn:subsection-aria-label="subsection"
          akn:title-aria-label="title"
          akn:subtitle-aria-label="subtitle"
          akn:article-aria-label="article"
          akn:paragraph-aria-label="paragraph"
          akn:list-aria-label="list"
          akn:document-ref-aria-label="document ref"
          akn:component-ref-aria-label="component ref"
          akn:point-aria-label="point"
          akn:wrap-up-aria-label="wrap up"
          akn:foreign-aria-label="foreign"
          akn:tblock-aria-label="tblock"
          akn:toc-aria-label="toc"
          akn:toc-item-aria-label="toc item"
          akn:p-aria-label="p"
          akn:block-aria-label="block"
          akn:num-aria-label="num"
          akn:heading-aria-label="heading"
          akn:td-aria-label="td"
          akn:th-aria-label="th"
          akn:tr-aria-label="tr"
          akn:ol-aria-label="ol"
          akn:ul-aria-label="ul"
          akn:table-aria-label="table"
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
          @keydown="handlePreviewKeyDown"
          @mousedown="handleMouseDown"
        />
      </div>
      <div class="flex flex-col gap-2">
        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label for="replacingElement" class="ds-label">Neues Element</label>
        <RisLawPreview
          id="replacingElement"
          class="ds-textarea h-[150px] overflow-y-auto p-2"
          data-testid="replacingElement"
          :arrow-focus="false"
          :content="quotedStructureContent"
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

<style scoped>
:deep(.akn-akomaNtoso :has(+ *)) {
  @apply mb-8;
}

:deep(
    :is(
        /* block elements + akn:num */
        .akn-article,
        .akn-attachment,
        .akn-attachments,
        .akn-block,
        .akn-blockContainer,
        .akn-blockList,
        .akn-body,
        .akn-book,
        .akn-caption,
        .akn-chapter,
        .akn-citation,
        .akn-citations,
        .akn-collectionBody,
        .akn-component,
        .akn-componentRef,
        .akn-components,
        .akn-conclusions,
        .akn-content,
        .akn-docAuthority,
        .akn-docDate,
        .akn-docNumber,
        .akn-docProponent,
        .akn-docStage,
        .akn-docTitle,
        .akn-docType,
        .akn-foreign,
        .akn-formula,
        .akn-hcontainer,
        .akn-heading,
        .akn-intro,
        .akn-item,
        .akn-li,
        .akn-list,
        .akn-listIntroduction,
        .akn-listWrapUp,
        .akn-longTitle,
        .akn-mainBody,
        .akn-mod,
        .akn-num,
        .akn-ol,
        .akn-p,
        .akn-paragraph,
        .akn-part,
        .akn-point,
        .akn-preamble,
        .akn-preface,
        .akn-quotedStructure,
        .akn-recital,
        .akn-recitals,
        .akn-section,
        .akn-signature,
        .akn-source,
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
  @apply block rounded border border-dashed border-highlight-quotedStructure-selected-border p-8;

  :deep(&):before {
    @apply ds-label-03-reg block px-2 pb-8 text-start font-[monospace] text-[#4E596A];
  }

  /**
   * Special styling to place akn:num and akn:heading in same row
   */

  :deep(& :is(h1, h2, h3, h4, h5):has(.akn-num):has(.akn-heading)) {
    @apply grid grid-cols-[max-content,1fr] gap-8;

    :deep(& .akn-heading) {
      @apply h-full;
    }

    :deep(& .akn-num) {
      @apply h-full;
    }
  }

  :deep(&.selected) {
    @apply -mx-1 border-2 border-solid border-black bg-[#B0EFFE];

    :deep(&):before {
      @apply ds-label-03-bold text-black;
    }
  }

  :deep(&):hover:not(:has([class^="akn-"]:hover)):not(.selected) {
    @apply -mx-1 border-2 border-[#004B76] bg-[#D6F7FE];
  }
}

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
  ):not(:empty) {
  @apply inline border border-[#004B76] bg-[#D6F7FE];
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

:deep(.akn-documentRef):before {
  content: "document ref";
}

:deep(.akn-componentRef):before {
  content: "component ref";
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
</style>
