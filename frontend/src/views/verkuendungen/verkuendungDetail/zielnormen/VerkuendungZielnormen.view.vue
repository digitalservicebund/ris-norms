<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import { formatDate } from "@/lib/dateTime"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNormTableOfContents } from "@/services/tocService"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { useGeltungszeitenHtml } from "@/services/zeitgrenzenService"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { computed, ref } from "vue"
import RisDocumentExplorer from "./RisDocumentExplorer.vue"

const eli = useDokumentExpressionEliPathParameter()

const { geltungszeitenHtmlHeadingId } = useElementId()

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () =>
      verkuendung.value
        ? (getFrbrDisplayText(verkuendung.value) ?? "...")
        : "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "zeitgrenzen", title: "Zielnormen verknüpfen" },
])

const {
  data: tocItems,
  isFetching: tocIsLoading,
  error: tocError,
} = useGetNormTableOfContents(eli)

const {
  data: geltungszeitenHtml,
  isFetching: isFetchingGeltungszeitenHtml,
  error: geltungszeitenHtmlError,
} = useGeltungszeitenHtml(eli)

const {
  data: verkuendung,
  isFetching: isFetchingVerkuendung,
  error: verkuendungError,
} = useGetVerkuendungService(() => eli.value.asNormEli())
console.log("verkuendung", verkuendung)

const formattedVerkuendungsdatum = computed(() =>
  verkuendung.value?.frbrDateVerkuendung
    ? formatDate(verkuendung.value.frbrDateVerkuendung)
    : undefined,
)
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, tocError, geltungszeitenHtmlError]"
    :loading="isFetchingVerkuendung || tocIsLoading"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100"
      >
        <RisDocumentExplorer
          :items="tocItems"
          :toc-is-loading="tocIsLoading"
          :toc-error="tocError"
        />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100 p-24"
      >
        <RisEmptyState
          text-content="Bitte wählen Sie einen Artikel aus"
          class="h-fit"
        />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto p-24"
      >
        <section :aria-labelledby="geltungszeitenHtmlHeadingId">
          <h2 :id="geltungszeitenHtmlHeadingId" class="ris-subhead-bold mb-10">
            Verkündungsdaten
          </h2>
          <RisPropertyValue
            property="Verkündungsdatum"
            :value="formattedVerkuendungsdatum"
          />
          <hr class="mb-0" />

          <div
            v-if="isFetchingGeltungszeitenHtml"
            class="mt-24 flex items-center justify-center"
          >
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout
            v-else-if="geltungszeitenHtmlError"
            :error="geltungszeitenHtmlError"
          />
          <RisLawPreview
            v-else-if="geltungszeitenHtml"
            :arrow-focus="false"
            :content="geltungszeitenHtml"
            class="-mx-24"
          />
        </section>
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
