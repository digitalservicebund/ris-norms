<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLawPreview, {
  AknElementClickEvent,
} from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import { computed, ref, triggerRef, watch } from "vue"
import {
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import { useAknTextSelection } from "@/composables/useAknTextSelection"
import { htmlRenderRangeToLdmlDeRange } from "@/lib/htmlRangeToLdmlDeRange"
import { createNewRefElement, deleteRef } from "@/lib/ref"
import { useDebounce } from "@vueuse/core"
import CloseIcon from "~icons/ic/close"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { getNodeByEid } from "@/services/ldmldeService"

/**
 * The eId of the currently selected akn:ref element.
 */
const selectedRef = defineModel<string>("selectedRef")
/**
 * The XML-String (LDML.de) of the akn:quotedText or akn:quotedStructure element in which akn:ref's should be displayed.
 */
const xmlSnippet = defineModel<string>("xmlSnippet")

const {
  data: render,
  isFetching: renderLoading,
  error: renderError,
} = useNormRenderHtml(xmlSnippet, { snippet: true })
/**
 * Debounced render loading.
 *
 * Most of the time the render request is really fast and the loading spinner is only shown for a few ms. This is a bit
 * annoying when marking many akn:ref elements. Therefore, we only show the loading spinner when the load takes a while.
 */
const debouncedRenderLoading = useDebounce(renderLoading, 500)

const xmlDocument = computed(() =>
  xmlSnippet.value ? xmlStringToDocument(xmlSnippet.value) : undefined,
)

function rangeToAknRef(range: Range) {
  const aknRef = createNewRefElement(range.startContainer)
  if (!aknRef) {
    return
  }

  range.surroundContents(aknRef)

  if (xmlDocument.value) {
    xmlSnippet.value = xmlNodeToString(xmlDocument.value)
  }

  return aknRef
}

const preview = ref<HTMLElement | null>()
const currentSelectedRange = useAknTextSelection(preview)
const selection = ref<Range>()

watch(currentSelectedRange, () => {
  if (!currentSelectedRange.value?.collapsed) {
    selection.value = currentSelectedRange.value
  } else {
    selection.value = undefined
  }
})

function handleSelectionStart() {
  selection.value = undefined
}

function handleSelectionEnd() {
  if (!selection.value || !xmlDocument.value) {
    return
  }

  const rangeInLdml = htmlRenderRangeToLdmlDeRange(
    selection.value,
    xmlDocument.value,
  )
  if (!rangeInLdml) {
    return
  }

  const refElement = rangeToAknRef(rangeInLdml)

  if (!refElement) {
    return
  }

  const newRefEId = evaluateXPathOnce("./@eId", refElement)?.nodeValue

  if (!newRefEId) {
    return
  }

  selection.value = undefined
  selectedRef.value = newRefEId
}

function handleAknRefClick({ eid }: AknElementClickEvent) {
  selectedRef.value = eid
}

function handleDelete(eId: string) {
  if (!xmlDocument.value) {
    return
  }

  const ref = getNodeByEid(xmlDocument.value, eId)
  if (!ref) {
    return
  }

  deleteRef(ref)
  triggerRef(xmlDocument)

  xmlSnippet.value = xmlNodeToString(xmlDocument.value)
}

function eidToSlotName(eid: string) {
  return `eid:${eid}`
}
</script>

<template>
  <div class="flex flex-col">
    <RisCallout
      v-if="!debouncedRenderLoading && renderError"
      variant="error"
      title="Die Vorschau konnte nicht geladen werden."
    />
    <div v-else ref="preview" class="grid flex-grow grid-cols-1">
      <RisLawPreview
        class="ds-textarea col-start-1 row-start-1 min-h-[100px] flex-grow p-2"
        :content="render ?? ''"
        :selected="selectedRef ? [selectedRef] : []"
        :e-id-classes="selectedRef ? { [selectedRef]: ['relative'] } : {}"
        @focusin="handleSelectionStart"
        @focusout="handleSelectionEnd"
        @mousedown="handleSelectionStart"
        @mouseup="handleSelectionEnd"
        @click:akn:ref="handleAknRefClick"
      >
        <template v-if="selectedRef" #[eidToSlotName(selectedRef)]>
          <RisTextButton
            :key="selectedRef"
            class="!absolute -translate-x-6 -translate-y-10 rounded-full !p-0"
            label="Löschen"
            :icon="CloseIcon"
            icon-only
            size="small"
            variant="ghost"
            @click="handleDelete(selectedRef)"
          >
            <template #icon>
              <CloseIcon
                class="h-[18px] w-[18px] flex-shrink-0 rounded-full bg-blue-700 text-white"
              ></CloseIcon>
            </template>
          </RisTextButton>
        </template>
      </RisLawPreview>
      <div
        v-if="debouncedRenderLoading"
        class="col-start-1 row-start-1 flex-grow bg-gray-500 bg-opacity-75"
      >
        <div class="mt-20 flex justify-center">
          <RisLoadingSpinner />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
:deep(.akn-quotedStructure .akn-quote-startQuote) {
  display: none;
}

:deep(.akn-quotedStructure .akn-quote-endQuote) {
  display: none;
}

:deep(.akn-ref) {
  @apply border border-dotted border-gray-900 bg-highlight-affectedDocument-default px-2;
}

:deep(.akn-ref):hover {
  @apply border border-dotted border-highlight-affectedDocument-border bg-highlight-affectedDocument-hover px-2;
}

:deep(.akn-ref.selected) {
  @apply border border-solid border-highlight-affectedDocument-border bg-highlight-affectedDocument-selected px-2;
}
</style>
