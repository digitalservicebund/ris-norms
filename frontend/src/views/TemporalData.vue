<script setup lang="ts">
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import { useGetEntryIntoForceHtml } from "@/services/temporalDataService"
import { ref, watch } from "vue"
import { TemporalDataResponse } from "@/types/temporalDataResponse"

const eli = useEliPathParameter()
const dates = ref<TemporalDataResponse[]>([])
const {
  timeBoundaries,
  saveTemporalData,
  saveError,
  isSaving,
  isFetching: isFetchingTemporalData,
  isSavingFinished,
} = useTemporalData(eli)

watch(timeBoundaries, () => {
  dates.value = timeBoundaries.value
})

const {
  data: entryIntoForceArticleHtml,
  error: entryIntoForceError,
  isFetching: isFetchingEntryIntoForce,
} = useGetEntryIntoForceHtml(eli)

async function handleSave() {
  try {
    saveError.value = null
    await saveTemporalData(dates.value)
  } catch (error) {
    console.error("Error saving dates:", error)
  }
}
</script>

<template>
  <div
    class="grid h-full grid-cols-3 grid-rows-[5rem,1fr] gap-x-32 overflow-hidden p-40"
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
        />
      </div>
    </template>

    <template v-else-if="entryIntoForceArticleHtml">
      <div class="col-span-3 mb-40 flex items-center justify-between">
        <h1 class="ds-heading-02-reg" data-testid="temporalDataHeading">
          Zeitgrenzen anlegen
        </h1>

        <RisTooltip
          v-slot="{ ariaDescribedby }"
          :visible="isSavingFinished"
          :title="saveError ? 'Fehler beim Speichern' : 'Speichern erfolgreich'"
          :content="saveError ? `${saveError.name}: ${saveError.message}` : ''"
          alignment="right"
          attachment="top"
          :variant="saveError ? 'error' : 'success'"
        >
          <RisTextButton
            :aria-describedby="ariaDescribedby"
            label="Speichern"
            size="small"
            class="h-fit flex-none"
            :disabled="isSaving"
            @click="handleSave"
          />
        </RisTooltip>
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
  </div>
</template>
