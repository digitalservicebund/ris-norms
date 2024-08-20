<script setup lang="ts">
import RisRefEditorTable from "@/components/references/RisRefEditorTable.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisRefSelectionPanel from "@/components/references/RisRefSelectionPanel.vue"
import { computed, ref, triggerRef, watch } from "vue"
import {
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import { useRoute, useRouter } from "vue-router"

const props = defineProps<{
  normXml: string
  selectedModEId: string
  isSaving: boolean
  hasSaved: boolean
  saveError: unknown
}>()

const emit = defineEmits<{
  save: [string]
}>()

const amendingNormDocument = ref<Document>()

watch(
  [() => props.selectedModEId, () => props.normXml],
  () => {
    if (!props.normXml) {
      return
    }

    amendingNormDocument.value = xmlStringToDocument(props.normXml ?? "")
  },
  { immediate: true },
)

const selectedModQuotedContent = computed(() => {
  if (!props.selectedModEId || !amendingNormDocument.value) {
    return null
  }

  const node = getNodeByEid(amendingNormDocument.value, props.selectedModEId)

  if (!node) {
    return null
  }

  return evaluateXPathOnce("./akn:quotedStructure | ./akn:quotedText[2]", node)
})

const selectedModQuotedContentXmlString = computed({
  get() {
    if (selectedModQuotedContent.value) {
      return xmlNodeToString(selectedModQuotedContent.value)
    }

    return ""
  },
  set(newValue) {
    const parentNode = selectedModQuotedContent.value?.parentNode
    if (!parentNode) {
      return
    }

    const newNode = xmlStringToDocument(newValue).firstChild
    if (!newNode) {
      return
    }

    parentNode.replaceChild(newNode, selectedModQuotedContent.value)
    // we mutate the norm Document (as a side effect), so we need to trigger a recompute of all things that use it
    triggerRef(amendingNormDocument)
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
  if (!amendingNormDocument.value) {
    return
  }

  emit("save", xmlNodeToString(amendingNormDocument.value))
}
</script>

<template>
  <div class="grid grid-rows-[minmax(0,max-content),max-content,max-content]">
    <section aria-labelledby="textBasedMetadataHeading" class="flex flex-col">
      <h3 id="textBasedMetadataHeading" class="ds-label-02-bold mb-12 block">
        Textbasierte Metadaten
      </h3>
      <RisRefSelectionPanel
        v-if="selectedModQuotedContentXmlString"
        v-model:selected-ref="selectedRefEId"
        v-model:xml-snippet="selectedModQuotedContentXmlString"
        class="overflow-hidden"
      />
      <RisEmptyState
        v-else
        text-content="Wählen sie einen Änderungsbefehl zur Bearbeitung aus."
      />
    </section>
    <section aria-labelledby="referencesHeading" class="flex flex-col">
      <h3 id="referencesHeading" class="ds-label-02-bold mb-12 block">
        Verweise
      </h3>
      <RisRefEditorTable
        v-if="selectedModQuotedContentXmlString"
        v-model:selected-ref="selectedRefEId"
        v-model:xml-snippet="selectedModQuotedContentXmlString"
        class="overflow-hidden"
      />
    </section>

    <hr class="col-span-2 mb-16 mt-32 border border-solid border-gray-400" />

    <div class="col-span-2 flex flex-row-reverse">
      <RisTooltip
        v-slot="{ ariaDescribedby }"
        :title="
          hasSaved && saveError ? 'Speichern fehlgeschlagen' : 'Gespeichert!'
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
          @click="handleSave"
        />
      </RisTooltip>
    </div>
  </div>
</template>
