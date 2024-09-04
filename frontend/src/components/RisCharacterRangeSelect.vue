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
  const eId = eIdWithCharacterRange.value?.split("/")[0]
  const characterRange = eIdWithCharacterRange.value
    ?.split("/")[1]
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

  // We use the CSS highlight api for showing the selected text. This Api is supported by chromium based browsers and in the newest nightly build by firefox (2024-09-03). TS definitions are still partly missing. So we need to do some checks if the api is available
  if ("Highlight" in window && "highlights" in CSS) {
    const highlight = new Highlight(htmlRange)

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
      ).set("current-character-range", highlight)
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
</style>
