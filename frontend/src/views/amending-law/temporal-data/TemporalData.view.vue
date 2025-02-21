<script setup lang="ts">
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTemporalDataIntervals from "@/views/amending-law/temporal-data/RisTemporalDataIntervals.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useTemporalData } from "@/composables/useTemporalData"
import { useErrorToast } from "@/lib/errorToast"
import { useGetEntryIntoForceHtml } from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import Button from "primevue/button"
import { useToast } from "primevue/usetoast"
import { onUnmounted, ref, watch } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()
const eli = useDokumentExpressionEliPathParameter()
const dates = ref<TemporalDataResponse[]>([])
const {
  data: timeBoundaries,
  update: {
    error: saveError,
    isFetching: isSaving,
    isFinished: isSavingFinished,
    execute: saveTemporalData,
  },
  isFetching: isFetchingTemporalData,
  error: loadTimeBoundariesError,
} = useTemporalData(eli, dates)

watch(timeBoundaries, () => {
  dates.value = timeBoundaries.value ?? []
})

const {
  data: entryIntoForceArticleHtml,
  error: entryIntoForceError,
  isFetching: isFetchingEntryIntoForce,
} = useGetEntryIntoForceHtml(eli)

const { pushBreadcrumb, actionTeleportTarget } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Zeitgrenzen anlegen" })
onUnmounted(() => cleanupBreadcrumbs())

const sentryTraceId = useSentryTraceId()
const { add: addToast } = useToast()
const { addErrorToast } = useErrorToast()

function showToast() {
  if (saveError.value) {
    addErrorToast(saveError, sentryTraceId)
  } else {
    addToast({
      summary: "Speichern erfolgreich",
      severity: "success",
    })
  }
}

watch(isSavingFinished, (finished) => {
  if (finished) {
    showToast()
  }
})

function handleSaveTemporalData(event: MouseEvent) {
  event.preventDefault()
  saveTemporalData()
}

watch(
  [() => entryIntoForceError?.value, () => loadTimeBoundariesError?.value],
  ([entryError, timeBoundariesError]) => {
    if (entryError?.status === 404 || timeBoundariesError?.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)
</script>

<template>
  <div
    class="grid h-full grid-cols-3 grid-rows-[min-content,1fr] gap-x-32 overflow-hidden p-24"
  >
    <template v-if="isFetchingEntryIntoForce || isFetchingTemporalData">
      <div class="col-span-3 flex items-center justify-center">
        <RisLoadingSpinner />
      </div>
    </template>

    <template
      v-else-if="entryIntoForceError && entryIntoForceError.status !== 404"
    >
      <div class="col-span-3">
        <RisErrorCallout :error="entryIntoForceError" />
      </div>
    </template>

    <template
      v-else-if="
        loadTimeBoundariesError && loadTimeBoundariesError.status !== 404
      "
    >
      <div class="col-span-3">
        <RisErrorCallout :error="loadTimeBoundariesError" />
      </div>
    </template>

    <template v-else-if="entryIntoForceArticleHtml">
      <div class="relative col-span-3 mb-24 flex items-center justify-between">
        <h1 class="ris-heading2-regular">Zeitgrenzen anlegen</h1>
      </div>

      <div class="col-span-1 overflow-auto">
        <RisTemporalDataIntervals
          v-model:dates="dates"
          class="col-span-1 overflow-auto"
        />
      </div>

      <RisLawPreview
        :content="entryIntoForceArticleHtml ?? ''"
        class="col-span-2"
      />
    </template>
    <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
      <div class="relative">
        <Button
          :disabled="isFetchingTemporalData || isFetchingEntryIntoForce"
          :loading="isSaving"
          label="Speichern"
          @click="handleSaveTemporalData"
        />
      </div>
    </Teleport>
  </div>
</template>
