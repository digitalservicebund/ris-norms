<script setup lang="ts">
import RefEditor from "@/views/amending-law/affected-documents/references/RisRefEditor.vue"
import { computed, triggerRef } from "vue"
import {
  evaluateXPath,
  evaluateXPathOnce,
  isChildNode,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/lib/xml"
import { useDebounceFn } from "@vueuse/core"
import { deleteRef } from "@/lib/ref"
import RisEmptyState from "@/components/RisEmptyState.vue"

/**
 * The eId of the currently selected akn:ref element.
 */
const selectedRef = defineModel<string>("selectedRef")

/**
 * The XML-String (LDML.de) of the element whose akn:ref's should be displayed.
 */
const xmlSnippet = defineModel<string>("xmlSnippet")

const xmlNode = computed(() => {
  if (!xmlSnippet.value) {
    return null
  }

  return xmlStringToDocument(xmlSnippet.value)
})

const refs = computed<Node[]>(() =>
  xmlNode.value ? evaluateXPath(".//akn:ref", xmlNode.value) : [],
)

function handleDelete(ref: Node) {
  deleteRef(ref)
  triggerRef(xmlNode)
  updateXmlString()
  selectedRef.value = undefined
}

function replace(ref: Node, newXml?: string) {
  if (!newXml || !isChildNode(ref)) {
    return
  }

  const newNode = xmlStringToDocument(newXml).firstChild

  if (!newNode) {
    return
  }

  ref.replaceWith(newNode)
  triggerRef(xmlNode)

  updateXmlString()
}

const updateXmlString = useDebounceFn(() => {
  xmlSnippet.value = xmlNode.value ? xmlNodeToString(xmlNode.value) : ""
}, 250)

function getEId(ref: Node) {
  return evaluateXPathOnce("./@eId", ref)?.nodeValue ?? ""
}

function selectPreviousRef(relativeTo: number) {
  const index = Math.max(0, relativeTo - 1)
  if (refs.value[index]) selectedRef.value = getEId(refs.value[index])
}

function selectNextRef(relativeTo: number) {
  const index = Math.min(relativeTo + 1, refs.value.length)
  if (refs.value[index]) selectedRef.value = getEId(refs.value[index])
}
</script>

<template>
  <div>
    <div
      v-if="refs.length > 0"
      class="grid max-h-full grid-cols-[3fr_8fr_max-content] items-center overflow-auto"
    >
      <div>Typ</div>
      <div>ELI mit Zielstelle</div>
      <div></div>

      <section
        v-for="(ref, i) in refs"
        :key="getEId(ref)"
        class="col-span-3 grid grid-cols-subgrid p-4"
        :class="{
          'bg-blue-300': selectedRef === getEId(ref),
        }"
        :aria-label="ref.textContent ?? 'leere Referenz'"
        @focusin="selectedRef = getEId(ref)"
      >
        <RefEditor
          :xml-snippet="xmlNodeToString(ref)"
          :grab-focus="selectedRef === getEId(ref)"
          @update:xml-snippet="
            (value: string | undefined) => replace(ref, value)
          "
          @delete="handleDelete(ref)"
          @select-previous="selectPreviousRef(i)"
          @select-next="selectNextRef(i)"
        ></RefEditor>
      </section>
    </div>
    <RisEmptyState
      v-else
      text-content="Für die ausgewählte Textpassage sind noch keine Verweise dokumentiert. Markieren Sie links Text, um neue Verweise hinzuzufügen."
    />
  </div>
</template>
