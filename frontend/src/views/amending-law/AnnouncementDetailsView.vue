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
import Button from "primevue/button"
import IconPlus from "~icons/ic/outline-plus"
import RisAnnouncementDetails from "./RisAnnouncementDetails.vue"

const router = useRouter()

const eli = useDokumentExpressionEliPathParameter()
const { data: amendingLaw, isFetching, error } = useGetNorm(eli)
const { data: amendingLawHtml } = useGetNormHtml(eli)

// TODO CHECK IF THIS IS A 404 TRIGGER OR NOT
watch(
  () => error.value,
  (err) => {
    if (err && err.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)

// TODO: THE DATA PUT INTO THE ANOUNCEMENT DETAIL IS A 404 TRIGGER

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
    v-if="isFetching"
    class="flex h-[calc(100dvh-5rem)] items-center justify-center"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="error" class="m-24">
    <RisErrorCallout v-if="error.status !== 404" :error />
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
              title="Gesetz zur Anpassung des Mutterschutzgesetzes und weiterer Gesetze – Anspruch auf Mutterschutzfristen nach einer Fehlgeburt (Mutterschutzanpassungsgesetz)"
              :meta-data="[
                { label: 'Veröffentlichungsdatum', value: '27.02.2025' },
                { label: 'Ausfertigungsdatum', value: '24.02.2025' },
                { label: 'Datenlieferungsdatum', value: '24.02.2025, 08:12' },
                {
                  label: 'FNA',
                  value:
                    '8052-5, 860-5, 2030-2-30-2, 51-1-23, 8052-5, 860-5, 2030-2-30-2, 51-1-23',
                },
              ]"
            />
            <div class="flex flex-col gap-16">
              <h2 class="ris-body1-bold">Zielnormen</h2>
              <RisEmptyState
                class="text-left"
                text-content="Es sind noch keine Zielnormen vorhanden"
              />
              <div>
                <Button severity="primary" label="Zielnorm hinzufügen">
                  <template #icon>
                    <IconPlus />
                  </template>
                </Button>
              </div>
            </div>
          </SplitterPanel>
          <SplitterPanel :size="25" :min-size="10">
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
