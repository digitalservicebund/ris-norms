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
import {
  useGetProprietary,
  usePutProprietary,
} from "@/services/proprietaryService"
import { Proprietary } from "@/types/proprietary"
import { computed, ref, watch } from "vue"
import { getNodeByEid } from "@/services/ldmldeService"
import {
  evaluateXPath,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import { useNormRender } from "@/composables/useNormRender"
import RisRefEditor from "@/components/RisRefEditor.vue"
import CloseIcon from "~icons/ic/close"
import { useDebounceFn } from "@vueuse/core"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<Proprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetProprietary(affectedDocumentEli, { atDate: timeBoundaryAsDate })

watch(data, (newData) => {
  localData.value = newData
})

const { data: savedData } = usePutProprietary(
  localData,
  affectedDocumentEli,
  { atDate: timeBoundaryAsDate },
  {
    afterFetch(c) {
      // Whenever the metadata has been saved successfully, reload the
      // XML to keep it in sync
      reloadXml()
      return c
    },
  },
).put(localData)

watch(savedData, (newData) => {
  localData.value = newData
})

/* -------------------------------------------------- *
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const currentXml = ref("")
const {
  data: xml,
  isFetching: xmlIsLoading,
  error: xmlError,
  execute: reloadXml,
  update: { execute: updateXml },
} = useNormXml(affectedDocumentEli, currentXml)

watch(xml, () => {
  if (xml.value) {
    currentXml.value = xml.value
  }
})

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useNormRender(currentXml, false, timeBoundaryAsDate)

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

  if (!eid.includes("hauptteil") || !eid.includes("text")) {
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

const doc = computed(() => {
  if (!currentXml.value) return

  return xmlStringToDocument(currentXml.value)
})

const refs = computed(() => {
  if (!doc.value) return []

  return evaluateXPath("//akn:ref", doc.value) as Element[]
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

function selectAknRef(eid: string) {
  selectedRef.value = eid
}

function handleAknRefClick({ eid }: { eid: string }) {
  selectAknRef(eid)
}

const handleRefChange = useDebounceFn(updateCurrentXml, 1000, { maxWait: 5000 })
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <div class="flex flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">Verweise</h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
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
          class="ds-textarea flex-grow p-2"
          :content="render ?? ''"
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
              class="relative -left-[12px] -top-[4px] w-0 rounded-full"
              style="padding: 0"
              :icon="CloseIcon"
              label="löschen"
              icon-only
              size="small"
              variant="ghost"
              @click="handleDeleteRef(aknRef)"
            ></RisTextButton>
          </template>
        </RisLawPreview>
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten bearbeiten">
        <RisTabs
          align="right"
          :tabs="[
            { id: 'editor', label: 'Rubriken' },
            { id: 'xml', label: 'XML' },
          ]"
        >
          <template #editor>
            <div v-if="isFetching" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="fetchError"
              variant="error"
              title="Die Metadaten konnten nicht geladen werden."
            />

            <div
              v-else
              class="grid grid-cols-3 items-center gap-y-14 overflow-auto"
            >
              <div>Typ</div>
              <div>Bezugsnorm</div>
              <div>Fassung</div>

              <RisRefEditor
                v-for="aknRef in refs"
                :key="getEid(aknRef)"
                :model-value="aknRef"
                class="col-span-4 grid grid-cols-subgrid"
                :class="{
                  'border border-2 border-blue-800':
                    selectedRef == getEid(aknRef),
                }"
                @focusin="selectAknRef(getEid(aknRef))"
                @change="handleRefChange"
              >
              </RisRefEditor>
            </div>

            <RisTextButton label="Speichern" @click="updateXml" />
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

            <RisCodeEditor
              v-else
              :model-value="currentXml ?? ''"
              :readonly="true"
              class="flex-grow"
            />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
