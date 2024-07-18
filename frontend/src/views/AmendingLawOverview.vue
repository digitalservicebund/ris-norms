<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useModHighlightClasses } from "@/composables/useModHighlightClasses"
import { useNormXml } from "@/composables/useNormXml"
import { useGetNormHtml } from "@/services/normService"
import { xmlStringToDocument } from "@/services/xmlService"
import { computed, toValue } from "vue"

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
      <RisCallout
        title="Der Text der Verkündung konnte nicht geladen werden."
        variant="error"
      >
        <p v-if="error.sentryEventId || loadXmlError.sentryEventId">
          Fehler-ID:
          <RisCopyableLabel
            :text="error.sentryEventId || loadXmlError.sentryEventId"
            name="Fehler-ID"
          />
        </p>
      </RisCallout>
    </div>
    <RisLoadingSpinner v-else-if="isFetching || isFetchingXml" />
    <div v-else class="rounded-sm bg-white px-24 py-24 shadow-md">
      <RisLawPreview
        :content="amendingLawHtml ?? ''"
        highlight-affected-document
        :e-id-classes="classesForPreview"
      />
    </div>
  </div>
</template>
