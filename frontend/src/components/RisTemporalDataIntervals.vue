<script setup lang="ts">
import { defineModel, nextTick, ref, watch } from "vue"
import RisDateInput from "@/components/controls/RisDateInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import DeleteOutlineIcon from "~icons/ic/outline-delete"
import SortOutlineIcon from "~icons/ic/outline-arrow-downward"
import dayjs from "dayjs"

interface DateEntry {
  date: string
  eid: string
}

const dates = defineModel("dates", {
  type: Array as () => DateEntry[],
  default: () => [],
})

const newDate = ref<string | undefined>()

function removeDateInput(index: number) {
  if (index > -1) {
    dates.value.splice(index, 1)
  }
}

watch(newDate, async (newDateValue) => {
  if (newDateValue && dayjs(newDateValue, "YYYY-MM-DD", true).isValid()) {
    dates.value.push({
      date: newDateValue,
      eid: "",
    })

    // Need to wait for one tick to give the date input some time to update
    // it's internal state, otherwise we'll keep seeing the previous value.
    await nextTick()
    newDate.value = undefined
  }
})
</script>

<template>
  <div class="p-10">
    <div class="mr-[2rem] flex justify-end">
      <RisTextButton
        :icon="SortOutlineIcon"
        :label="`Nach Datum sortieren`"
        variant="ghost"
        disabled
      />
    </div>
    <form class="p-10">
      <div class="flex flex-col space-y-4">
        <div v-for="(dateEntry, index) in dates" :key="index" class="mb-4">
          <div class="flex items-center justify-between">
            <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
            <label :for="'date-' + index" class="w-96 flex-none"
              >Zeitgrenze {{ index + 1 }}</label
            >
            <RisDateInput
              :id="'date-' + index"
              v-model="dateEntry.date"
              size="small"
              data-testid="date-input-field"
              is-read-only
            />
            <RisTextButton
              :icon="DeleteOutlineIcon"
              variant="ghost"
              class="shrink-0"
              label="Löschen"
              type="button"
              icon-only
              @click.prevent="removeDateInput(index)"
            />
          </div>
        </div>
        <div class="flex flex-1 items-center">
          <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
          <label :for="'new-date'" class="w-96 flex-none"
            >Zeitgrenze hinzufügen</label
          >
          <RisDateInput
            id="new-date"
            v-model="newDate"
            size="small"
            class="w-[calc(100%-9rem)]"
            data-testid="new-date-input-field"
          />
        </div>
      </div>
    </form>
  </div>
</template>
