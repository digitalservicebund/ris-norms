<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLawPreview, {
  AknElementClickEvent,
} from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import { computed, ref, watch } from "vue"
import {
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import { useAknTextSelection } from "@/composables/useAknTextSelection"
import { htmlRenderRangeToLdmlDeRange } from "@/lib/htmlRangeToLdmlDeRange"
import { createNewRefElement } from "@/lib/ref"

const selectedRef = defineModel<string>("selectedRef")
const xmlSnippet = defineModel<string>("xmlSnippet")

const {
  data: render,
  isFetching: renderLoading,
  error: renderError,
} = useNormRenderHtml(xmlSnippet, { snippet: true })

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

  selectedRef.value = newRefEId
}

function handleAknRefClick({ eid }: AknElementClickEvent) {
  selectedRef.value = eid
}
</script>

<template>
  <div class="flex flex-col">
    <div v-if="renderLoading" class="flex justify-center">
      <RisLoadingSpinner />
    </div>
    <RisCallout
      v-else-if="renderError"
      variant="error"
      title="Die Vorschau konnte nicht geladen werden."
    />
    <div ref="preview" class="flex flex-grow flex-col">
      <RisLawPreview
        class="ds-textarea flex-grow p-2"
        :content="render ?? ''"
        :selected="selectedRef ? [selectedRef] : []"
        @focusin="handleSelectionStart"
        @focusout="handleSelectionEnd"
        @mousedown="handleSelectionStart"
        @mouseup="handleSelectionEnd"
        @click:akn:ref="handleAknRefClick"
      />
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
