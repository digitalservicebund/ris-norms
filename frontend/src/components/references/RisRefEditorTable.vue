<script setup lang="ts">
import RefEditor from "@/components/references/RisRefEditor.vue"
import { computed, triggerRef } from "vue"
import {
  evaluateXPath,
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"

const selectedRef = defineModel<string>("selectedRef")
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

function isChildNode(node: Node): node is ChildNode {
  return node.parentNode !== null
}

function deleteRef(ref: Node) {
  if (!isChildNode(ref)) {
    return
  }

  const childNodes: Node[] = []
  ref.childNodes.forEach((e) => childNodes.push(e))
  ref.replaceWith(...childNodes)
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

function updateXmlString() {
  xmlSnippet.value = xmlNode.value ? xmlNodeToString(xmlNode.value) : ""
}

function getEId(ref: Node) {
  return evaluateXPathOnce("./@eId", ref)?.nodeValue ?? ""
}
</script>

<template>
  <div>
    <div
      class="grid max-h-full grid-cols-[1fr,1fr,max-content] items-center overflow-auto"
    >
      <div>Typ</div>
      <div>Eli</div>
      <div></div>

      <div
        v-for="ref in refs"
        :key="getEId(ref)"
        :data-testid="`ris-ref-editor-${getEId(ref)}`"
        class="col-span-3 m-2 grid grid-cols-subgrid"
        :class="{
          'ring-2 ring-blue-800': selectedRef === getEId(ref),
        }"
      >
        <RefEditor
          :xml-snippet="xmlNodeToString(ref)"
          @update:xml-snippet="(value: string) => replace(ref, value)"
          @delete="deleteRef(ref)"
        ></RefEditor>
      </div>
    </div>
  </div>
</template>
