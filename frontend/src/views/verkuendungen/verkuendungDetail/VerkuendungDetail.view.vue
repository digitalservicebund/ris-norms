<script lang="ts" setup>
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import type { HeaderBreadcrumb } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import { getFrbrDisplayText } from "@/lib/frbr"
import {
  useGetAnnouncementService,
  useGetZielnormen,
} from "@/services/announcementService"
import { useGetNormHtml } from "@/services/normService"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { computed, ref } from "vue"
import RisVerkuendungHeader from "./RisVerkuendungHeader.vue"
import RisZielnormenList from "./RisZielnormenList.vue"
import { useGroupedZielnormen } from "./useGroupedZielnormen"

const eli = useDokumentExpressionEliPathParameter()
const normExpressionEli = computed(() => eli.value.asNormEli())

const {
  data: verkuendungPreview,
  isFetching: isFetchingVerkuendungPreview,
  error: verkuendungPreviewError,
} = useGetNormHtml(eli)

const {
  data: zielnormen,
  isFetching: isFetchingZielnormen,
  error: zielnormenError,
} = useGetZielnormen(normExpressionEli)

const {
  data: verkuendung,
  isFetching: isFetchingVerkuendung,
  error: verkuendungError,
} = useGetAnnouncementService(normExpressionEli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      verkuendung.value
        ? (getFrbrDisplayText(verkuendung.value) ?? "...")
        : "...",
    to: `/verkuendungen/${eli.value}`,
  },
])

const {
  verkuendungDetailsLabelId,
  zielnormenLabelId,
  verkuendungPreviewLabelId,
} = useElementId()

const groupedZielnormen = useGroupedZielnormen(zielnormen)
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, zielnormenError]"
    :header-back-destination="{ name: 'Home' }"
    :loading="isFetchingVerkuendung"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel :size="75" :min-size="30">
        <div class="flex h-full flex-col gap-24 overflow-auto bg-gray-100">
          <section
            class="shrink-0 p-24 pb-0"
            :aria-labelledby="verkuendungDetailsLabelId"
          >
            <span :id="verkuendungDetailsLabelId" class="sr-only">
              Verkündungs-Details
            </span>
            <RisVerkuendungHeader
              :title="verkuendung?.title"
              :veroeffentlichungsdatum="verkuendung?.frbrDateVerkuendung"
              :ausfertigungsdatum="verkuendung?.dateAusfertigung"
              :datenlieferungsdatum="verkuendung?.importedAt"
              :fna="verkuendung?.fna"
            />
          </section>

          <section
            class="flex flex-grow flex-col gap-16 p-24 pt-0"
            :aria-labelledby="zielnormenLabelId"
          >
            <span :id="zielnormenLabelId" class="sr-only">Zielnormen</span>
            <h2 class="ris-body1-bold">Zielnormen</h2>

            <div v-if="isFetchingZielnormen">
              <RisLoadingSpinner />
            </div>

            <div v-else-if="zielnormenError">
              <RisErrorCallout :error="zielnormenError" />
            </div>

            <template v-else>
              <RisEmptyState
                v-if="groupedZielnormen.length === 0"
                text-content="Es sind noch keine Zielnormen vorhanden"
              />

              <div v-else class="flex flex-col">
                <RisZielnormenList :items="groupedZielnormen" />
              </div>
            </template>
          </section>
        </div>
      </SplitterPanel>

      <SplitterPanel :size="25" :min-size="25">
        <section class="h-full" :aria-labelledby="verkuendungPreviewLabelId">
          <span :id="verkuendungPreviewLabelId" class="sr-only">
            Verkündungstext
          </span>
          <div
            v-if="isFetchingVerkuendungPreview"
            class="flex items-center justify-center"
          >
            <RisLoadingSpinner></RisLoadingSpinner>
          </div>
          <div v-else-if="verkuendungPreviewError">
            <RisErrorCallout :error="verkuendungPreviewError" />
          </div>
          <RisLawPreview
            class="h-full w-full"
            :content="verkuendungPreview ?? ''"
            :arrow-focus="false"
          />
        </section>
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
