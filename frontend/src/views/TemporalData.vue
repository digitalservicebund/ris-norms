<script setup lang="ts">
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useAmendingLawTemporalData } from "@/composables/useAmendingLawTemporalData"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisTextButton from "@/components/controls/RisTextButton.vue"
const eli = useEliPathParameter()
const {
  htmlContent: entryIntoForceArticleHtml,
  timeBoundaries: dates,
  update,
} = useAmendingLawTemporalData(eli)

async function handleSave() {
  try {
    await update(dates.value)
    console.log("Dates saved successfully.")
  } catch (error) {
    console.error("Error saving dates:", error)
  }
}
</script>

<template>
  <div
    v-if="entryIntoForceArticleHtml"
    class="grid h-full grid-cols-3 grid-rows-[5rem,1fr] gap-x-32 overflow-hidden p-40"
  >
    <div class="col-span-3 mb-40 flex items-center justify-between">
      <h1 class="ds-heading-02-reg" data-testid="temporalDataHeading">
        Zeitgrenzen anlegen
      </h1>
      <RisTextButton
        label="Speichern"
        size="small"
        class="h-fit flex-none"
        @click="handleSave"
      />
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
  </div>
  <div v-else class="p-40">
    <p>Es wurde kein Inkrafttreten-Artikel gefunden.</p>
  </div>
</template>
