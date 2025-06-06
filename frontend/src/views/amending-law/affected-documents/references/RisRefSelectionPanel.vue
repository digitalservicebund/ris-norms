<script setup lang="ts">
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import type { AknElementClickEvent } from "@/components/RisLawPreview.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import { htmlRenderRangeToLdmlDeRange } from "@/lib/htmlRangeToLdmlDeRange"
import { getNodeByEid } from "@/lib/ldmlde"
import { createNewRefElement, deleteRef } from "@/lib/ref"
import {
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/lib/xml"
import { useDebounce } from "@vueuse/core"
import { computed, ref, triggerRef, watch } from "vue"
import CloseIcon from "~icons/ic/close"
import { useAknTextSelection } from "./useAknTextSelection"

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
  selectedRef.value = undefined
}

function eidToSlotName(eid: string) {
  return `eid:${eid}`
}
</script>

<template>
  <div class="flex max-h-full flex-col">
    <RisErrorCallout
      v-if="!debouncedRenderLoading && renderError"
      :error="renderError"
    />
    <div v-else ref="preview" class="grid grow grid-cols-1 overflow-auto">
      <RisLawPreview
        class="col-start-1 row-start-1 min-h-[100px] grow p-2"
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
          <button
            :key="selectedRef"
            class="absolute! -translate-x-6 -translate-y-10 rounded-full bg-blue-700 leading-none outline-offset-2 hover:outline hover:outline-2 hover:outline-gray-600 focus:outline focus:outline-4 focus:outline-blue-800"
            @click.stop="handleDelete(selectedRef)"
          >
            <CloseIcon class="h-20 w-20 flex-none text-white" />
            <span class="sr-only">Löschen</span>
          </button>
        </template>
      </RisLawPreview>
      <div
        v-if="debouncedRenderLoading"
        class="bg-opacity-75 col-start-1 row-start-1 grow bg-gray-500"
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
  background-color: var(--color-highlight-1-default);
  padding-inline: var(--spacing-2);
  outline: 1px dotted var(--color-blue-800);
}

:deep(.akn-ref):hover {
  background-color: var(--color-highlight-1-hover);
  padding-inline: var(--spacing-2);
  outline: 2px dotted var(--color-blue-800);
}

:deep(.akn-ref.selected) {
  background-color: var(--color-highlight-1-selected);
  padding-inline: var(--spacing-2);
  outline: 2px dotted var(--color-blue-800);
}
</style>
