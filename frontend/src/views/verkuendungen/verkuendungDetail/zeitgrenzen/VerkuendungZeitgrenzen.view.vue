<script setup lang="ts">
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import { formatDate } from "@/lib/dateTime"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetAnnouncementService } from "@/services/announcementService"
import { useGeltungszeitenHtml } from "@/services/zeitgrenzenService"
import Button from "primevue/button"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { computed, ref } from "vue"
import IcBaselineCheck from "~icons/ic/baseline-check"

const eli = useDokumentExpressionEliPathParameter()

const {
  data: verkuendung,
  isFetching: isFetchingVerkuendung,
  error: verkuendungError,
} = useGetAnnouncementService(() => eli.value.asNormEli())

const {
  data: geltungszeitenHtml,
  isFetching: isFetchingGeltungszeitenHtml,
  error: geltungszeitenHtmlError,
} = useGeltungszeitenHtml(eli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () =>
      verkuendung.value
        ? (getFrbrDisplayText(verkuendung.value) ?? "...")
        : "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "zeitgrenzen", title: "Geltungszeitregeln anlegen" },
])

const formattedVerkuendungsdatum = computed(() =>
  verkuendung.value?.frbrDateVerkuendung
    ? formatDate(verkuendung.value.frbrDateVerkuendung)
    : undefined,
)

const { geltungszeitenHtmlHeadingId } = useElementId()
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError]"
    :loading="isFetchingVerkuendung"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="66"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100 p-24"
      >
        <h1 class="ris-subhead-regular mb-24 font-bold">
          Geltungszeitregeln anlegen
        </h1>

        <RisEmptyState
          text-content="Es wurden noch keine Geltungszeiten angelegt."
        />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto p-24"
      >
        <section :aria-labelledby="geltungszeitenHtmlHeadingId">
          <h2
            :id="geltungszeitenHtmlHeadingId"
            class="ris-subhead-regular mb-10 font-bold"
          >
            Verkündungsdaten
          </h2>
          <RisPropertyValue
            property="Verkündungsdatum"
            :value="formattedVerkuendungsdatum"
          />
          <hr class="mb-0" />

          <div
            v-if="isFetchingGeltungszeitenHtml"
            class="flex items-center justify-center"
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

    <template #headerAction>
      <Button disabled label="Speichern">
        <template #icon><IcBaselineCheck /></template>
      </Button>
    </template>
  </RisViewLayout>
</template>
