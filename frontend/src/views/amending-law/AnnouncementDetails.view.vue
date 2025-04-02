<script lang="ts" setup>
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import type { HeaderBreadcrumb } from "@/components/controls/RisHeader.vue"
import RisHeader from "@/components/controls/RisHeader.vue"
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
import { computed, ref, watch } from "vue"
import { useRouter } from "vue-router"
import RisAnnouncementDetails from "./RisAnnouncementDetails.vue"
import type { RisZielnormenListItem } from "./RisZielnormenList.vue"
import RisZielnormenList from "./RisZielnormenList.vue"

const eli = useDokumentExpressionEliPathParameter()
const normExpressionEli = computed(() => eli.value.asNormEli())

const {
  data: amendingLawHtml,
  isFetching: isFetchingAmendingLawHtml,
  error: loadingErrorAmendingLawHtml,
} = useGetNormHtml(eli)

const { isFetching: isFetchingZielnormen, error: loadingErrorZielnormen } =
  useGetZielnormen(normExpressionEli)

const {
  data: announcement,
  isFetching: isFetchingAnnouncement,
  error: loadingErrorAnnouncement,
} = useGetAnnouncementService(normExpressionEli)

const router = useRouter()

watch(
  () => loadingErrorAnnouncement.value,
  (err) => {
    if (err && err.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)

watch(
  () => loadingErrorZielnormen.value,
  (err) => {
    if (err && err.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      announcement.value
        ? (getFrbrDisplayText(announcement.value) ?? "...")
        : "...",
    to: `/verkuendungen/${eli.value}`,
  },
])

const {
  verkuendungDetailsLabelId,
  zielnormenLabelId,
  verkuendungPreviewLabelId,
} = useElementId()

const zielnormenListItems = ref<RisZielnormenListItem[]>([])
</script>

<template>
  <div
    v-if="isFetchingAnnouncement"
    class="flex h-[calc(100dvh-5rem)] items-center justify-center"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="loadingErrorAnnouncement" class="m-24">
    <RisErrorCallout
      v-if="loadingErrorAnnouncement.status !== 404"
      :error="loadingErrorAnnouncement"
    />
  </div>

  <div v-else class="h-[calc(100dvh-5rem)] bg-white">
    <RisHeader :back-destination="{ name: 'Home' }" :breadcrumbs>
      <main class="h-[calc(100dvh-5rem-5rem)] overflow-hidden">
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
                <RisAnnouncementDetails
                  :title="announcement?.title"
                  :veroeffentlichungsdatum="announcement?.frbrDateVerkuendung"
                  :ausfertigungsdatum="announcement?.dateAusfertigung"
                  :datenlieferungsdatum="announcement?.importedAt"
                  :fna="announcement?.fna"
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

                <div v-else-if="loadingErrorZielnormen">
                  <RisErrorCallout :error="loadingErrorZielnormen" />
                </div>

                <template v-else>
                  <RisEmptyState
                    v-if="zielnormenListItems.length === 0"
                    text-content="Es sind noch keine Zielnormen vorhanden"
                  />

                  <div v-else class="flex flex-col">
                    <RisZielnormenList :items="zielnormenListItems" />
                  </div>
                </template>
              </section>
            </div>
          </SplitterPanel>

          <SplitterPanel :size="25" :min-size="25">
            <section
              class="h-full"
              :aria-labelledby="verkuendungPreviewLabelId"
            >
              <span :id="verkuendungPreviewLabelId" class="sr-only">
                Verkündungstext
              </span>
              <div
                v-if="isFetchingAmendingLawHtml"
                class="flex items-center justify-center"
              >
                <RisLoadingSpinner></RisLoadingSpinner>
              </div>
              <div v-else-if="loadingErrorAmendingLawHtml">
                <RisErrorCallout :error="loadingErrorAmendingLawHtml" />
              </div>
              <RisLawPreview
                class="h-full w-full"
                :content="amendingLawHtml ?? ''"
                :arrow-focus="false"
              />
            </section>
          </SplitterPanel>
        </Splitter>
      </main>
    </RisHeader>
  </div>
</template>
