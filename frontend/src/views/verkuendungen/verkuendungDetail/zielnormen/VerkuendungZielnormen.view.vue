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
import type { EditableZielnormReference } from "@/composables/useZielnormReferences"
import { formatDate } from "@/lib/dateTime"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import {
  useGeltungszeitenHtml,
  useGetZeitgrenzen,
} from "@/services/zeitgrenzenService"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { computed, ref } from "vue"
import RisDokumentExplorer from "./RisDokumentExplorer.vue"
import RisZielnormForm from "./RisZielnormForm.vue"

const eli = useDokumentExpressionEliPathParameter()

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(() => eli.value.asNormEli())

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "zielnormen", title: "Zielnormen verknüpfen" },
])

// Geltungszeiten HTML ------------------------------------

const { geltungszeitenHtmlHeadingId } = useElementId()

const {
  data: geltungszeitenHtml,
  isFetching: isFetchingGeltungszeitenHtml,
  error: geltungszeitenHtmlError,
} = useGeltungszeitenHtml(eli)

const formattedVerkuendungsdatum = computed(() =>
  verkuendung.value?.frbrDateVerkuendung
    ? formatDate(verkuendung.value.frbrDateVerkuendung)
    : undefined,
)

// Editing ------------------------------------------------

const editedZielnormReference = ref<EditableZielnormReference>()

const { data: zeitgrenzen, error: zeitgrenzenError } = useGetZeitgrenzen(eli)
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, geltungszeitenHtmlError, zeitgrenzenError]"
    :loading="!verkuendungHasFinished"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100"
      >
        <RisDokumentExplorer :eli class="h-full" />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100 p-24"
      >
        <RisEmptyState
          v-if="!editedZielnormReference"
          text-content="Bitte wählen Sie einen Änderungsbefehl aus."
        />

        <RisZielnormForm
          v-else
          v-model="editedZielnormReference"
          :zeitgrenzen="zeitgrenzen ?? []"
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
