<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useModHighlightClasses } from "@/composables/useModHighlightClasses"
import { useNormXml } from "@/composables/useNormXml"
import { useGetNormHtml } from "@/services/normService"
import { xmlStringToDocument } from "@/services/xmlService"
import { computed, toValue } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const eli = useEliPathParameter()

const { isFetching, error, data: amendingLawHtml } = useGetNormHtml(eli)

const {
  data: xml,
  isFetching: isFetchingXml,
  error: loadXmlError,
} = useNormXml(eli)

const normDocument = computed(() => {
  const xmlValue = toValue(xml)
  return xmlValue ? xmlStringToDocument(xmlValue) : null
})

const classesForPreview = useModHighlightClasses(normDocument, () => false)
</script>

<template>
  <div class="p-24">
    <h1 class="ds-heading-02-reg mb-24">Verkündung</h1>
    <div v-if="error || loadXmlError">
      <RisErrorCallout
        title="Der Text der Verkündung konnte nicht geladen werden."
      />
    </div>
    <RisLoadingSpinner v-else-if="isFetching || isFetchingXml" />
    <div v-else class="rounded-sm bg-white px-24 py-24 shadow-md">
      <RisLawPreview
        :content="amendingLawHtml ?? ''"
        :e-id-classes="classesForPreview"
      />
    </div>
  </div>
</template>

<style scoped>
:deep(.akn-affectedDocument) {
  @apply bg-highlight-affectedDocument-default px-2 outline outline-dotted outline-1 outline-blue-800;
}

:deep(.akn-affectedDocument):hover {
  @apply bg-highlight-affectedDocument-hover px-2 outline outline-dotted outline-2 outline-blue-800;
}
</style>
