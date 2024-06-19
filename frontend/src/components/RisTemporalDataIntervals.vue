<script setup lang="ts">
import { nextTick, ref, watch, computed } from "vue"
import RisDateInput from "@/components/controls/RisDateInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import DeleteOutlineIcon from "~icons/ic/outline-delete"
import SortOutlineIcon from "~icons/ic/outline-arrow-downward"
import dayjs from "dayjs"
import { TemporalDataResponse } from "@/types/temporalDataResponse"

const MAX_DATES = 100

/** The current list of dates. */
const dates = defineModel<TemporalDataResponse[]>("dates", {
  default: () => [],
})

const newDate = ref<string | undefined>()

const isDeleteDisabled = computed(() => dates.value.length <= 1)

function addDate(newDateValue: string) {
  if (newDateValue && dayjs(newDateValue, "YYYY-MM-DD", true).isValid()) {
    dates.value = [...dates.value, { date: newDateValue, eventRefEid: "" }]
  }
}

function removeDateInput(index: number) {
  if (index > -1) {
    dates.value = [
      ...dates.value.slice(0, index),
      ...dates.value.slice(index + 1),
    ]
  }
}

watch(newDate, async (newDateValue) => {
  if (newDateValue) {
    addDate(newDateValue)

    // Need to wait for one tick to give the date input some time to update
    // it's internal state, otherwise we'll keep seeing the previous value.
    await nextTick()
    newDate.value = undefined
  }
})
</script>

<template>
  <form class="grid grid-cols-[6rem,1fr,min-content] items-start gap-10">
    <RisTextButton
      :icon="SortOutlineIcon"
      label="Nach Datum sortieren"
      variant="link"
      class="col-start-2 ml-auto"
      disabled
      @click.prevent="() => {}"
    />
    <div></div>

    <template v-for="(dateEntry, index) in dates" :key="index">
      <RisDateInput
        :id="`date-${index}`"
        v-model="dateEntry.date"
        :label="`Zeitgrenze ${index + 1}`"
        label-position="left"
        size="medium"
        class="col-span-2 grid w-full grid-cols-subgrid"
        label-class="w-[6rem]"
      />
      <RisTextButton
        :icon="DeleteOutlineIcon"
        variant="ghost"
        class="shrink-0"
        :label="`Zeitgrenze ${index + 1} löschen`"
        type="button"
        :disabled="isDeleteDisabled"
        icon-only
        @click.prevent="removeDateInput(index)"
      />
    </template>

    <RisDateInput
      v-if="dates.length < MAX_DATES"
      id="new-date"
      v-model="newDate"
      label="Zeitgrenze hinzufügen"
      label-position="left"
      size="medium"
      class="col-span-2 grid w-full grid-cols-subgrid"
      label-class="w-[6rem]"
    />
  </form>
</template>
