<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import IconErrorOutline from "~icons/ic/outline-error-outline"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import { useGetEntryIntoForceHtml } from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { onUnmounted, ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import Toast from "primevue/toast"
import { useToast } from "primevue/usetoast"

const eli = useEliPathParameter()
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

function showToast() {
  if (saveError.value) {
    addToast({
      group: "error-toast",
      summary: "Fehler beim Speichern",
      severity: "error",
    })
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

    <template v-else-if="entryIntoForceError">
      <div class="col-span-3">
        <RisErrorCallout :error="entryIntoForceError" />
      </div>
    </template>

    <template v-else-if="loadTimeBoundariesError">
      <div class="col-span-3">
        <RisErrorCallout :error="loadTimeBoundariesError" />
      </div>
    </template>

    <template v-else-if="entryIntoForceArticleHtml">
      <div class="relative col-span-3 mb-24 flex items-center justify-between">
        <h1 class="ds-heading-02-reg">Zeitgrenzen anlegen</h1>
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
    <Toast group="error-toast">
      <template #message="slot">
        <div class="flex w-320 gap-10">
          <IconErrorOutline class="text-red-800" />
          <div>
            <p class="ris-body2-bold">{{ slot.message.summary }}</p>
            <div v-if="saveError" class="flex gap-8">
              <RisCopyableLabel
                name="Trace-ID"
                text="Trace-ID kopieren"
                :value="sentryTraceId"
              />
            </div>
          </div>
        </div>
      </template>
    </Toast>
    <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
      <div class="relative">
        <RisTextButton
          :disabled="isFetchingTemporalData || isFetchingEntryIntoForce"
          :loading="isSaving"
          label="Speichern"
          @click="saveTemporalData"
        />
      </div>
    </Teleport>
  </div>
</template>
