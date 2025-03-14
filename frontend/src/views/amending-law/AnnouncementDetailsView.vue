<script lang="ts" setup>
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm, useGetNormHtml } from "@/services/normService"
import { ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useRouter } from "vue-router"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisAnnouncementDetails from "./RisAnnouncementDetails.vue"
import { useGetAnnouncementService } from "@/services/announcementService"

const eli = useDokumentExpressionEliPathParameter()
const {
  data: amendingLaw,
  // isFetching: isFetchingAmendingLaw,
  // error: loadingErrorAmendingLaw,
} = useGetNorm(eli)

const {
  data: amendingLawHtml,
  isFetching: isFetchingAmendingLawHtml,
  error: loadingErrorAmendingLawHtml,
} = useGetNormHtml(eli)
const {
  data: announcement,
  isFetching: isFetchingAnnouncement,
  error: loadingErrorAnnouncement,
} = useGetAnnouncementService(eli)

const router = useRouter()
watch(
  () => loadingErrorAnnouncement.value,
  (err) => {
    if (err && err.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)

// TODO: CHECK IF CAN USE THE RETURNED ANNOUNCEMENT TO GET THE BREADCRUMB DETAILS
const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? (getFrbrDisplayText(amendingLaw.value) ?? "...")
        : "...",
    to: `/verkuendungen/${eli.value}`,
  },
])
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

  <div v-else class="flex h-[calc(100dvh-5rem)] flex-col bg-gray-100">
    <RisHeader :back-destination="{ name: 'Home' }" :breadcrumbs>
      <div class="flex-grow overflow-hidden">
        <Splitter class="h-full" layout="horizontal">
          <SplitterPanel
            :size="75"
            :min-size="10"
            class="flex flex-col gap-24 p-24"
          >
            <RisAnnouncementDetails
              :title="announcement?.title"
              :veroeffentlichungsdatum="announcement?.veroeffentlichungsdatum"
              :ausfertigungsdatum="announcement?.ausfertigungsdatum"
              :datenlieferungsdatum="announcement?.datenlieferungsdatum"
              :fna="announcement?.fna"
            />

            <div class="flex flex-col gap-16">
              <h2 class="ris-body1-bold">Zielnormen</h2>
              <RisEmptyState
                text-content="Es sind noch keine Zielnormen vorhanden"
              />
            </div>
          </SplitterPanel>
          <SplitterPanel :size="25" :min-size="10">
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
            />
          </SplitterPanel>
        </Splitter>
      </div>
    </RisHeader>
  </div>
</template>
