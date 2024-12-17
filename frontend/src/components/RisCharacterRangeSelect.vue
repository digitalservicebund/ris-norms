<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import { computed, ref, watch } from "vue"
import { xmlStringToDocument } from "@/services/xmlService"
import { useAknTextSelection } from "@/composables/useAknTextSelection"
import {
  htmlRenderRangeToLdmlDeRange,
  ldmlRangeToHtmlRenderRange,
} from "@/lib/htmlRangeToLdmlDeRange"

const props = defineProps<{
  render: string | null
  xml: string | null
  selectedModType?: string | null
}>()
const eIdWithCharacterRange = defineModel<string>()

const xmlDocument = computed(() =>
  props.xml ? xmlStringToDocument(props.xml) : undefined,
)

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

function highlightCurrentCharacterRange() {
  const eIdWithDefaultRange = eIdWithCharacterRange.value?.includes("/")
    ? eIdWithCharacterRange.value
    : `${eIdWithCharacterRange.value}/0-0.xml`

  const [eId, rangePart] = eIdWithDefaultRange.split("/")
  const characterRange = rangePart
    .replace(".xml", "")
    .split("-")
    .map(Number) as [number, number]

  const containerNode = xmlDocument.value?.querySelector(
    `[eId="${eId}"]`,
  )?.firstChild

  if (!containerNode || !preview.value) {
    return
  }

  // create the range in the ldml xml
  const range = new Range()
  try {
    range.setStart(containerNode, characterRange[0])
    range.setEnd(containerNode, characterRange[1])
  } catch (e) {
    if (e instanceof Error && e.name === "IndexSizeError") {
      console.warn("Couldn't find selected character range:", e)
      return
    }
    throw e
  }

  const htmlRange = ldmlRangeToHtmlRenderRange(range, preview.value)

  if (!htmlRange) {
    return
  }

  if ("Highlight" in window && "highlights" in CSS) {
    const highlight = new Highlight(htmlRange)
    const highlightId =
      props.selectedModType === "aenderungsbefehl-streichen"
        ? "current-character-range-repeal"
        : "current-character-range"

    if (
      CSS.highlights &&
      (
        CSS.highlights as unknown as {
          set: (id: string, highlight: Highlight) => void
        }
      ).set
    ) {
      ;(
        CSS.highlights as unknown as {
          set: (id: string, highlight: Highlight) => void
        }
      ).set(highlightId, highlight)
    }
  }

  htmlRange.commonAncestorContainer.parentElement?.scrollIntoView({
    behavior: "instant",
    block: "start",
  })
}

watch(
  () => eIdWithCharacterRange.value,
  () => {
    highlightCurrentCharacterRange()
  },
  { immediate: true },
)

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

  let eId: string | undefined

  if (rangeInLdml.startContainer instanceof Element) {
    eId = rangeInLdml.startContainer.attributes.getNamedItem("eId")?.value
  } else {
    eId =
      rangeInLdml.startContainer.parentElement?.attributes.getNamedItem(
        "eId",
      )?.value
  }

  if (eId) {
    eIdWithCharacterRange.value = `${eId}/${rangeInLdml.startOffset}-${rangeInLdml.endOffset}.xml`
  }

  selection.value = undefined
}

function handlePreviewRender(container: HTMLElement) {
  preview.value = container
  highlightCurrentCharacterRange()
}
</script>

<template>
  <div class="overflow-hidden">
    <RisLawPreview
      class="ds-textarea col-start-1 row-start-1 h-full min-h-[100px] flex-grow cursor-text p-2"
      :content="render ?? ''"
      @focusin="handleSelectionStart"
      @focusout="handleSelectionEnd"
      @mousedown="handleSelectionStart"
      @mouseup="handleSelectionEnd"
      @rendered="handlePreviewRender"
    ></RisLawPreview>
  </div>
</template>

<style scoped>
::highlight(current-character-range) {
  @apply bg-highlight-1-selected;
}

::highlight(current-character-range-repeal) {
  @apply bg-highlight-7-selected line-through;
}
</style>
