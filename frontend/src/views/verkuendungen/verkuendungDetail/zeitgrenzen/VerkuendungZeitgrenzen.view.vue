<script setup lang="ts">
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useElementId } from "@/composables/useElementId"
import { formatDate } from "@/lib/dateTime"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import {
  useGeltungszeitenHtml,
  useGetZeitgrenzen,
  usePutZeitgrenzen,
} from "@/services/zeitgrenzenService"
import { useToast } from "@/composables/useToast"
import Button from "primevue/button"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { computed, ref, watch } from "vue"
import IcBaselineCheck from "~icons/ic/baseline-check"
import RisZeitgrenzenList from "./RisZeitgrenzenList.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"

const eli = useNormExpressionEliPathParameter()

const { geltungszeitenHtmlHeadingId, geltungszeitenHeadingId } = useElementId()

const toast = useToast()

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "zeitgrenzen", title: "Geltungszeitregeln anlegen" },
])

const {
  data: verkuendung,
  isFetching: isFetchingVerkuendung,
  error: verkuendungError,
} = useGetVerkuendungService(eli)

const {
  data: geltungszeitenHtml,
  isFetching: isFetchingGeltungszeitenHtml,
  error: geltungszeitenHtmlError,
} = useGeltungszeitenHtml(eli)

const {
  data: zeitgrenzen,
  error: zeitgrenzenError,
  isFetching: isFetchingZeitgrenzen,
} = useGetZeitgrenzen(eli)

const {
  data: updatedZeitgrenzen,
  execute: saveZeitgrenzen,
  error: saveZeitgrenzenError,
  isFetching: saveZeitgrenzenIsFetching,
} = usePutZeitgrenzen(eli, zeitgrenzen)

watch(updatedZeitgrenzen, (newVal, oldVal) => {
  // null in this case means the request failed, keep old data in that case
  if (oldVal && newVal === null) return
  zeitgrenzen.value = newVal
})

const formattedVerkuendungsdatum = computed(() =>
  verkuendung.value?.frbrDateVerkuendung
    ? formatDate(verkuendung.value.frbrDateVerkuendung)
    : undefined,
)

async function onSaveZeitgrenzen() {
  await saveZeitgrenzen()
  if (!saveZeitgrenzenError.value) {
    toast.add({ summary: "Gespeichert!", severity: "success" })
  } else toast.addError(saveZeitgrenzenError)
}
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, zeitgrenzenError]"
    :loading="isFetchingVerkuendung || isFetchingZeitgrenzen"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="66"
        :min-size="33"
        class="h-full overflow-hidden bg-gray-100 p-24"
      >
        <section
          :aria-labelledby="geltungszeitenHeadingId"
          class="flex h-full flex-col gap-16"
        >
          <h1 :id="geltungszeitenHeadingId" class="ris-subhead-bold">
            Geltungszeitregeln anlegen
          </h1>
          <RisZeitgrenzenList
            v-if="zeitgrenzen"
            v-model="zeitgrenzen"
            class="min-h-0 flex-1"
          />
        </section>
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

    <template #headerAction>
      <Button
        label="Speichern"
        :loading="saveZeitgrenzenIsFetching"
        @click="onSaveZeitgrenzen()"
      >
        <template #icon><IcBaselineCheck /></template>
      </Button>
    </template>
  </RisViewLayout>
</template>
