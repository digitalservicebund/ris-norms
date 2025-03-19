<script lang="ts" setup>
import type { HeaderBreadcrumb } from "@/components/controls/RisHeader.vue"
import RisHeader from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNormHtml } from "@/services/normService"
import { ref, watch, computed } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useRouter } from "vue-router"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisAnnouncementDetails from "./RisAnnouncementDetails.vue"
import { useGetAnnouncementService } from "@/services/announcementService"

const eli = useDokumentExpressionEliPathParameter()
const normExpressionEli = computed(() => eli.value.asNormEli())

const {
  data: amendingLawHtml,
  isFetching: isFetchingAmendingLawHtml,
  error: loadingErrorAmendingLawHtml,
} = useGetNormHtml(eli)
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
const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      announcement.value
        ? (getFrbrDisplayText(announcement.value) ?? "...")
        : "...",
    to: `/announcements/${eli.value}`,
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
      <main class="flex-grow overflow-hidden">
        <Splitter class="h-full" layout="horizontal">
          <SplitterPanel :size="75" :min-size="10">
            <section
              aria-label="Bekanntmachungsdetails"
              class="flex flex-col gap-24 p-24"
            >
              <RisAnnouncementDetails
                :title="announcement?.title"
                :veroeffentlichungsdatum="announcement?.frbrDateVerkuendung"
                :ausfertigungsdatum="announcement?.dateAusfertigung"
                :datenlieferungsdatum="announcement?.importedAt"
                :fna="announcement?.fna"
              />

              <div class="flex flex-col gap-16">
                <h2 class="ris-body1-bold">Zielnormen</h2>
                <RisEmptyState
                  text-content="Es sind noch keine Zielnormen vorhanden"
                />
              </div>
            </section>
          </SplitterPanel>

          <SplitterPanel :size="25" :min-size="10">
            <section aria-label="Bekanntmachungsvorschau" class="h-full">
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
                aria-label="Volltext der Bekanntmachung"
              />
            </section>
          </SplitterPanel>
        </Splitter>
      </main>
    </RisHeader>
  </div>
</template>
