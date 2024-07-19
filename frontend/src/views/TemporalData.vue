<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import { useGetEntryIntoForceHtml } from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { onUnmounted, ref, watch } from "vue"

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
</script>

<template>
  <div
    class="grid h-full grid-cols-3 grid-rows-[5rem,1fr] gap-x-32 overflow-hidden p-24"
  >
    <template v-if="isFetchingEntryIntoForce || isFetchingTemporalData">
      <div class="col-span-3 flex items-center justify-center">
        <RisLoadingSpinner />
      </div>
    </template>

    <template v-else-if="entryIntoForceError">
      <div class="col-span-3">
        <RisCallout
          title="Es wurde kein Inkrafttreten-Artikel gefunden."
          variant="error"
        >
          <p v-if="entryIntoForceError.sentryEventId">
            Fehler-ID:
            <RisCopyableLabel
              :text="entryIntoForceError.sentryEventId"
              name="Fehler-ID"
            />
          </p>
        </RisCallout>
      </div>
    </template>

    <template v-else-if="loadTimeBoundariesError">
      <div class="col-span-3">
        <RisCallout
          title="Zeitgrenzen konnten nicht geladen werden."
          variant="error"
        >
          <p v-if="loadTimeBoundariesError.sentryEventId">
            Fehler-ID:
            <RisCopyableLabel
              :text="loadTimeBoundariesError.sentryEventId"
              name="Fehler-ID"
            />
          </p>
        </RisCallout>
      </div>
    </template>

    <template v-else-if="entryIntoForceArticleHtml">
      <div class="relative col-span-3 mb-40 flex items-center justify-between">
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

    <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
      <div class="relative">
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          :title="saveError ? 'Fehler beim Speichern' : 'Speichern erfolgreich'"
          :variant="saveError ? 'error' : 'success'"
          :visible="isSavingFinished"
          allow-dismiss
          alignment="right"
          attachment="bottom"
        >
          <RisTextButton
            :aria-describedby="ariaDescribedby"
            :disabled="isFetchingTemporalData || isFetchingEntryIntoForce"
            :loading="isSaving"
            label="Speichern"
            @click="saveTemporalData"
          />
        </RisTooltip>
      </div>
    </Teleport>
  </div>
</template>
