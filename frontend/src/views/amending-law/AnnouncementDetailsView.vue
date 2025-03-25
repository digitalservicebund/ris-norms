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
import {
  useGetAnnouncementService,
  useGetZielnormen,
} from "@/services/announcementService"
import RisZielnormenDataTable from "./RisZielnormenDataTable.vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

const eli = useDokumentExpressionEliPathParameter()
const normExpressionEli = computed(() => eli.value.asNormEli())

const {
  data: amendingLawHtml,
  isFetching: isFetchingAmendingLawHtml,
  error: loadingErrorAmendingLawHtml,
} = useGetNormHtml(eli)
const {
  data: zielnormen,
  isFetching: isFetchingZielnormen,
  error: loadingErrorZielnormen,
} = useGetZielnormen(normExpressionEli)

const groupedZielnormen = computed(() => {
  if (!zielnormen.value?.length) return []

  const map = new Map<
    string,
    {
      key: string
      eli: string
      title: string
      fna: string
      expressions: typeof zielnormen.value
    }
  >()

  for (const norm of zielnormen.value) {
    try {
      const parsed = DokumentExpressionEli.fromString(norm.eli)
      const key = `${parsed.agent}/${parsed.year}/${parsed.naturalIdentifier}`
      const eli = `eli/bund/${key}`

      if (!map.has(key)) {
        map.set(key, { key, eli, title: "", fna: "", expressions: [] })
      }

      map.get(key)!.expressions.push(norm)
    } catch (err) {
      console.error("Failed to parse ELI", norm.eli, err)
    }
  }

  return Array.from(map.values()).map((group) => {
    const sortedExpressions = [...group.expressions].sort((a, b) =>
      (b.frbrDateVerkuendung ?? "").localeCompare(a.frbrDateVerkuendung ?? ""),
    )
    const latest = sortedExpressions[0]

    return {
      ...group,
      title: latest.shortTitle || latest.title || "Unbenannt",
      fna: latest.fna || "nicht-vorhanden",
      expressions: sortedExpressions.reverse(),
    }
  })
})

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
          <SplitterPanel :size="75" :min-size="10" class="overflow-auto">
            <div class="flex h-full flex-col gap-24">
              <section class="shrink-0 p-24 pb-0">
                <span id="announcement-details-label" class="sr-only"
                  >Bekanntmachungsdetails</span
                >
                <RisAnnouncementDetails
                  :title="announcement?.title"
                  :veroeffentlichungsdatum="announcement?.frbrDateVerkuendung"
                  :ausfertigungsdatum="announcement?.dateAusfertigung"
                  :datenlieferungsdatum="announcement?.importedAt"
                  :fna="announcement?.fna"
                />
              </section>
              <section class="flex flex-grow flex-col gap-16 p-24 pt-0">
                <span id="zielnormen-label" class="sr-only">Zielnormen</span>
                <h2 class="ris-body1-bold">Zielnormen</h2>

                <div v-if="isFetchingZielnormen">
                  <RisLoadingSpinner />
                </div>

                <div
                  v-else-if="
                    loadingErrorZielnormen &&
                    loadingErrorZielnormen.status !== 404
                  "
                >
                  <RisErrorCallout :error="loadingErrorZielnormen" />
                </div>

                <template v-else>
                  <RisEmptyState
                    v-if="groupedZielnormen?.length === 0"
                    text-content="Es sind noch keine Zielnormen vorhanden"
                  />

                  <div v-else class="flex flex-col">
                    <RisZielnormenDataTable
                      v-for="(group, index) in groupedZielnormen"
                      :key="index"
                      :grouped-zielnorm="group"
                    />
                  </div>
                </template>
              </section>
            </div>
          </SplitterPanel>

          <SplitterPanel :size="25" :min-size="10">
            <section class="h-full">
              <span id="amending-law-preview-label" class="sr-only"
                >Bekanntmachungsvorschau</span
              >
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
