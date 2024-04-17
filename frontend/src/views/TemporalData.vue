<script setup lang="ts">
import RisTemporalDataIntervals from "@/components/RisTemporalDataIntervals.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useAmendingLawTemporalData } from "@/composables/useAmendingLawTemporalData"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import dayjs from "dayjs"
const eli = useEliPathParameter()
const {
  htmlContent: mockHtml,
  dates: dates,
  update,
} = useAmendingLawTemporalData(eli)

async function handleSave() {
  const isoDates = dates.value.map((date) =>
    dayjs(date, "YYYY-MM-DD").toISOString(),
  )

  try {
    await update(isoDates)
    console.log("Dates saved successfully.")
  } catch (error) {
    console.error("Error saving dates:", error)
  }
}
</script>

<template>
  <div
    v-if="mockHtml"
    class="grid h-full grid-cols-3 grid-rows-[5rem,1fr] overflow-hidden p-40"
  >
    <div class="col-span-3 mb-40 flex items-center justify-between">
      <h1 class="ds-heading-02-reg">Zeitgrenzen anlegen</h1>
      <RisTextButton
        label="Speichern"
        size="small"
        class="h-fit flex-none"
        @click="handleSave"
      />
    </div>
    <RisTemporalDataIntervals
      v-model:dates="dates"
      class="col-span-1 overflow-auto"
    />
    <RisLawPreview :content="mockHtml ?? ''" class="col-span-2" />
  </div>
  <div v-else class="p-40">
    <p>Es wurde kein Inkrafttreten-Artikel gefunden.</p>
  </div>
</template>
