<script setup lang="ts">
import RisRefEditorTable from "@/views/amending-law/affected-documents/references/RisRefEditorTable.vue"
import Button from "primevue/button"
import RisRefSelectionPanel from "@/views/amending-law/affected-documents/references/RisRefSelectionPanel.vue"
import { computed, ref, triggerRef, watch } from "vue"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import { useRoute, useRouter } from "vue-router"
import { useToast } from "primevue/usetoast"

const props = defineProps<{
  /**
   * Xml of the norm in which references should be documented
   */
  normXml: string
  /**
   * eId of the element of the norm in which references should be documented
   */
  eId: string
  isSaving: boolean
  hasSaved: boolean
  saveError: unknown
}>()

const emit = defineEmits<{
  save: [string]
}>()

const normDocument = ref<Document>()

watch(
  [() => props.eId, () => props.normXml],
  () => {
    if (!props.normXml) {
      return
    }

    normDocument.value = xmlStringToDocument(props.normXml ?? "")
  },
  { immediate: true },
)

const nodeContent = computed(() => {
  if (!props.eId || !normDocument.value) {
    return null
  }

  return getNodeByEid(normDocument.value, props.eId)
})

const nodeContentXmlString = computed({
  get() {
    if (nodeContent.value) {
      return xmlNodeToString(nodeContent.value)
    }

    return ""
  },
  set(newValue) {
    const parentNode = nodeContent.value?.parentNode
    if (!parentNode) {
      return
    }

    const newNode = xmlStringToDocument(newValue).firstChild
    if (!newNode) {
      return
    }

    parentNode.replaceChild(newNode, nodeContent.value)
    // we mutate the norm Document (as a side effect), so we need to trigger a recompute of all things that use it
    triggerRef(normDocument)
  },
})

const router = useRouter()
const route = useRoute()

const selectedRefEId = computed({
  get() {
    return route.params.refEid?.toString()
  },
  set(newEid) {
    router.replace({
      params: {
        refEid: newEid ?? "",
      },
    })
  },
})

function handleSave() {
  if (!normDocument.value) {
    return
  }

  emit("save", xmlNodeToString(normDocument.value))
}

const { add: addToast } = useToast()

function showToast() {
  if (props.hasSaved) {
    if (props.saveError) {
      addToast({
        summary: "Speichern fehlgeschlagen",
        severity: "error",
      })
    } else {
      addToast({
        summary: "Gespeichert!",
        severity: "success",
      })
    }
  }
}

watch(
  () => props.hasSaved,
  (hasSaved) => {
    if (hasSaved) {
      showToast()
    }
  },
)
</script>

<template>
  <div class="grid grid-rows-[minmax(0,max-content)_max-content_max-content]">
    <section aria-labelledby="textBasedMetadataHeading" class="flex flex-col">
      <h3 id="textBasedMetadataHeading" class="ris-label2-bold mb-12 block">
        Textbasierte Metadaten
      </h3>
      <RisRefSelectionPanel
        v-model:selected-ref="selectedRefEId"
        v-model:xml-snippet="nodeContentXmlString"
        class="overflow-hidden"
      />
    </section>
    <section aria-labelledby="referencesHeading" class="flex flex-col">
      <h3 id="referencesHeading" class="ris-label2-bold mb-12 block">
        Verweise
      </h3>
      <RisRefEditorTable
        v-if="nodeContentXmlString"
        v-model:selected-ref="selectedRefEId"
        v-model:xml-snippet="nodeContentXmlString"
        class="overflow-hidden"
      />
    </section>

    <hr class="col-span-2 mt-32 mb-16 border border-solid border-gray-400" />

    <div class="col-span-2 flex flex-row-reverse">
      <Button
        :disabled="isSaving"
        :loading="isSaving"
        label="Speichern"
        @click="handleSave"
      />
    </div>
  </div>
</template>
