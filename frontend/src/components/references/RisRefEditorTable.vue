<script setup lang="ts">
import RefEditor from "@/components/references/RisRefEditor.vue"
import { computed, triggerRef } from "vue"
import {
  evaluateXPath,
  evaluateXPathOnce,
  isChildNode,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import { useDebounceFn } from "@vueuse/core"
import { deleteRef } from "@/lib/ref"

/**
 * The eId of the currently selected akn:ref element.
 */
const selectedRef = defineModel<string>("selectedRef")
/**
 * The XML-String (LDML.de) of the akn:quotedText or akn:quotedStructure element whose akn:ref's should be displayed.
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
}

function replace(ref: Node, newXml: string) {
  if (!isChildNode(ref)) {
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
      class="grid max-h-full grid-cols-[3fr,8fr,max-content] items-center overflow-auto"
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
          @update:xml-snippet="(value: string) => replace(ref, value)"
          @delete="handleDelete(ref)"
          @select-previous="selectPreviousRef(i)"
          @select-next="selectNextRef(i)"
        ></RefEditor>
      </section>
    </div>
  </div>
</template>
