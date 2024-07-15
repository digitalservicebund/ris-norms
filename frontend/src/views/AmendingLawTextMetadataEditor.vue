<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { computed, ref, watch } from "vue"
import { getNodeByEid } from "@/services/ldmldeService"
import {
  evaluateXPath,
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import RisRefEditor from "@/components/RisRefEditor.vue"
import CloseIcon from "~icons/ic/close"
import { useDebounceFn } from "@vueuse/core"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"

const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()
const amendingLawEli = useEliPathParameter()

/* -------------------------------------------------- *
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const currentXml = ref("")
const {
  data: xml,
  isFetching: xmlIsLoading,
  error: xmlError,
  update: {
    execute: updateXml,
    isFetching: isSaving,
    isFinished: hasSaved,
    error: saveError,
  },
} = useNormXml(amendingLawEli, currentXml)

watch(xml, () => {
  if (xml.value) {
    currentXml.value = xml.value
  }
})

const doc = computed(() => {
  if (!currentXml.value) return

  return xmlStringToDocument(currentXml.value)
})

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useNormRenderHtml(currentXml, false, timeBoundaryAsDate)

const selectedMod = ref()

const selectedModNewContentNode = computed(() => {
  if (!doc.value || !selectedMod.value) return ""

  const modNode = getNodeByEid(doc.value, selectedMod.value)
  if (!modNode) return ""

  return evaluateXPathOnce(
    "./akn:quotedStructure | ./akn:quotedText[2]",
    modNode,
  )
})

const { data: render2 } = useNormRenderHtml(
  computed(() => {
    if (!selectedModNewContentNode.value) return ""
    return xmlNodeToString(selectedModNewContentNode.value)
  }),
  false,
  timeBoundaryAsDate,
)

function lengthWithoutWhitespace(str: string): number {
  return str.replaceAll(/\s/g, "").length
}

/**
 * Converts a position within a string that ignores whitespace to one that includes the whitespace characters
 */
function positionWithoutWhitespaceToPosition(
  str: string,
  positionWithoutWhitespace: number,
): number {
  let charCount = 0
  let nonWhitespaceCharCount = 0
  while (nonWhitespaceCharCount < positionWithoutWhitespace) {
    if (!str[charCount].match(/\s/)) {
      nonWhitespaceCharCount++
    }
    charCount++
  }

  return charCount
}

function findChildNodeForRange(
  node: Node,
  startWithoutWhitespace: number,
  endWithoutWhitespace: number,
): {
  node: Node | null
  startWithWhitespace: number
  endWithWhitespace: number
} {
  // find the child node for the range and the total length of the previous siblings
  const { previousSiblingsTextLength, node: nodeOfRange } = Array.from(
    node.childNodes,
  ).reduce(
    ({ previousSiblingsTextLength, node }, childNode) => {
      if (node) {
        return { previousSiblingsTextLength, node }
      }

      const textContent = childNode.textContent ?? ""

      if (
        previousSiblingsTextLength <= startWithoutWhitespace &&
        previousSiblingsTextLength + lengthWithoutWhitespace(textContent) >=
          endWithoutWhitespace
      ) {
        return { previousSiblingsTextLength, node: childNode }
      }

      return {
        previousSiblingsTextLength:
          previousSiblingsTextLength + lengthWithoutWhitespace(textContent),
        node,
      }
    },
    { previousSiblingsTextLength: 0, node: null as Node | null },
  )

  const textContent = nodeOfRange?.textContent ?? ""

  let startWithWhitespace = positionWithoutWhitespaceToPosition(
    textContent,
    startWithoutWhitespace - previousSiblingsTextLength,
  )
  // do not start with a space
  while (textContent[startWithWhitespace]?.match(/\s/)) {
    startWithWhitespace++
  }

  return {
    node: nodeOfRange,
    startWithWhitespace,
    endWithWhitespace: positionWithoutWhitespaceToPosition(
      textContent,
      endWithoutWhitespace - previousSiblingsTextLength,
    ),
  }
}

async function convertSelectionToRef({
  eid,
  start,
  end,
}: {
  eid: string
  start: number
  end: number
}): Promise<Element | void> {
  if (!doc.value) {
    return
  }

  const node = getNodeByEid(doc.value, eid)

  if (!node) {
    return
  }

  const {
    startWithWhitespace,
    endWithWhitespace,
    node: childNodeForRange,
  } = findChildNodeForRange(node, start, end)

  if (!childNodeForRange) {
    return
  }

  const range = new Range()
  range.selectNode(node)
  range.setStart(childNodeForRange, startWithWhitespace)
  range.setEnd(childNodeForRange, endWithWhitespace)

  const refElement: Element = doc.value.createElement("akn:ref")
  refElement.setAttribute("eId", Math.random().toString().replace(".", "-"))
  refElement.setAttribute("type", "Zitierung")

  range.surroundContents(refElement)

  updateCurrentXml()
  return refElement
}

const refs = computed(() => {
  if (!selectedModNewContentNode.value) return []

  return evaluateXPath(
    ".//akn:ref",
    selectedModNewContentNode.value,
  ) as Element[]
})

async function handleSelect(
  selection: { eid: string; start: number; end: number } | null,
) {
  if (!selection || selection.start === selection.end) {
    return
  }

  const aknRef = await convertSelectionToRef(selection)
  if (!aknRef) return

  selectAknRef(getEid(aknRef))
}

function updateCurrentXml() {
  if (doc.value) {
    currentXml.value = xmlNodeToString(doc.value)
  }
}

function eidToSlotName(eid: string) {
  return `eid:${eid}`
}

function getEid(element: Element): string {
  return element.getAttribute("eId") ?? ""
}

function handleDeleteRef(element: Element) {
  const childNodes: Node[] = []
  element.childNodes.forEach((e) => childNodes.push(e))
  element.replaceWith(...childNodes)
  updateCurrentXml()
}

const selectedRef = ref<string>()

watch(selectedMod, () => (selectedRef.value = undefined))

function selectAknRef(eid: string) {
  selectedRef.value = eid
}

function handleAknRefClick({ eid }: { eid: string }) {
  selectAknRef(eid)
}

const handleRefChange = useDebounceFn(updateCurrentXml, 1000, { maxWait: 5000 })

const {
  data: amendingLaw,
  isFetching: amendingLawIsLoading,
  error: amendingLawError,
} = useGetNorm(amendingLawEli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? getFrbrDisplayText(amendingLaw.value) ?? "..."
        : "...",
    to: `/amending-laws/${amendingLawEli.value}`,
  },
  { key: "metadataEditor", title: "Textbasierte Metadaten" },
])

function handleModClick(e: { eid: string }) {
  selectedMod.value = e.eid
}

watch(selectedRef, () => {
  document
    .querySelector(`#mod-preview [data-eId="${selectedRef.value}"]`)
    ?.scrollIntoView({
      behavior: "smooth",
      block: "nearest",
    })

  document
    .getElementById(`ris-ref-editor-${selectedRef.value}`)
    ?.scrollIntoView({
      behavior: "smooth",
      block: "nearest",
    })
})
</script>

<template>
  <div class="h-[calc(100dvh-5rem)] bg-gray-100">
    <div
      v-if="amendingLawIsLoading"
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="amendingLawError" class="p-40">
      <RisCallout
        title="Das Gesetz konnte nicht geladen werden."
        variant="error"
      />
    </div>

    <RisHeader v-else :breadcrumbs>
      <!-- eslint-disable vuejs-accessibility/label-has-for -->
      <div class="flex h-[calc(100vh-5rem-5rem)] flex-col p-40">
        <div class="gap grid min-h-0 flex-grow grid-cols-3 grid-rows-1 gap-32">
          <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
            <div
              v-if="renderIsLoading && !render"
              class="my-16 flex justify-center"
            >
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="renderError"
              variant="error"
              title="Die Vorschau konnte nicht geladen werden."
            />

            <RisLawPreview
              v-else
              id="norm-preview"
              class="ds-textarea flex-grow p-2"
              :content="render ?? ''"
              :selected="selectedMod ? [selectedMod] : []"
              @click:akn:mod="handleModClick"
            ></RisLawPreview>
          </section>

          <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
            <RisCallout
              v-if="!selectedMod"
              variant="neutral"
              title="Bitte wähle einen Änderungsbefehl aus"
            />

            <div
              v-else-if="renderIsLoading && !render"
              class="my-16 flex justify-center"
            >
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="renderError"
              variant="error"
              title="Die Vorschau konnte nicht geladen werden."
            />

            <RisLawPreview
              v-else
              id="mod-preview"
              class="ds-textarea flex-grow p-2"
              :content="render2 ?? ''"
              :selected="selectedRef ? [selectedRef] : []"
              @click:akn:ref="handleAknRefClick"
              @select="handleSelect"
            >
              <template
                v-for="aknRef in refs"
                #[eidToSlotName(getEid(aknRef))]
                :key="getEid(aknRef)"
              >
                <RisTextButton
                  class="relative -left-[7px] -top-[7px] w-0 rounded-full"
                  style="padding: 0"
                  label="löschen"
                  :icon="CloseIcon"
                  icon-only
                  size="small"
                  variant="ghost"
                  @click="handleDeleteRef(aknRef)"
                >
                  <template #icon>
                    <CloseIcon
                      class="h-[18px] w-[18px] flex-shrink-0 rounded-full bg-blue-700 text-white"
                    ></CloseIcon>
                  </template>
                </RisTextButton>
              </template>
            </RisLawPreview>
          </section>

          <section
            v-if="selectedMod"
            class="flex flex-col gap-8"
            aria-label="Metadaten bearbeiten"
          >
            <RisTabs
              align="right"
              :tabs="[
                { id: 'editor', label: 'Rubriken' },
                { id: 'xml', label: 'XML' },
              ]"
            >
              <template #editor>
                <div
                  class="grid grid-cols-[1fr,1fr,1fr,max-content] items-center gap-y-10 overflow-auto pb-1"
                >
                  <div>Typ</div>
                  <div>Bezugsnorm</div>
                  <div>Fassung</div>
                  <div></div>

                  <RisRefEditor
                    v-for="aknRef in refs"
                    :id="`ris-ref-editor-${getEid(aknRef)}`"
                    :key="getEid(aknRef)"
                    :model-value="aknRef"
                    class="col-span-4 mx-1 grid grid-cols-subgrid"
                    :class="{
                      'ring ring-1 ring-blue-800':
                        selectedRef == getEid(aknRef),
                    }"
                    @focusin="selectAknRef(getEid(aknRef))"
                    @change="handleRefChange"
                    @delete="handleDeleteRef(aknRef)"
                  >
                  </RisRefEditor>
                </div>
              </template>

              <template #xml>
                <div v-if="xmlIsLoading" class="my-16 flex justify-center">
                  <RisLoadingSpinner />
                </div>

                <RisCallout
                  v-else-if="xmlError"
                  variant="error"
                  title="Die XML-Ansicht konnte nicht geladen werden."
                />

                <RisCodeEditor v-else v-model="currentXml" class="flex-grow" />
              </template>
            </RisTabs>
          </section>
        </div>
      </div>

      <template #action>
        <div class="relative">
          <RisTooltip
            v-slot="{ ariaDescribedby }"
            :title="
              hasSaved && saveError
                ? 'Speichern fehlgeschlagen'
                : 'Gespeichert!'
            "
            :variant="hasSaved && saveError ? 'error' : 'success'"
            :visible="hasSaved"
            allow-dismiss
            alignment="right"
            attachment="bottom"
          >
            <RisTextButton
              :aria-describedby
              :disabled="isSaving"
              :loading="isSaving"
              label="Speichern"
              @click="updateXml()"
            />
          </RisTooltip>
        </div>
      </template>
    </RisHeader>
  </div>
</template>

<style scoped>
#norm-preview :deep(.akn-mod) {
  @apply block border border-[#4299F7] bg-[#E7E7E766] px-2;
}

#norm-preview :deep(.akn-mod.selected) {
  @apply block bg-[#fef7bd];
}

#norm-preview :deep(.akn-mod):hover {
  @apply block bg-[#fef7bd];
}

#norm-preview :deep(.akn-quotedStructure .akn-ref) {
  @apply bg-highlight-mod-10-default;
}

#norm-preview :deep(.akn-quotedText .akn-ref) {
  @apply bg-highlight-mod-10-default;
}

#mod-preview :deep(.akn-ref) {
  @apply border border-dotted border-gray-900 bg-highlight-affectedDocument-default px-2;
}

#mod-preview :deep(.akn-ref):hover {
  @apply border border-dotted border-highlight-affectedDocument-border bg-highlight-affectedDocument-hover px-2;
}

#mod-preview :deep(.akn-ref.selected) {
  @apply border border-solid border-highlight-affectedDocument-border bg-highlight-affectedDocument-selected px-2;
}
</style>
