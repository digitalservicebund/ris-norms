<script setup lang="ts">
import { defineModel, ref, watch } from "vue"
import RisDateInput from "@/components/controls/RisDateInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import DeleteOutlineIcon from "~icons/ic/outline-delete"
import SortOutlineIcon from "~icons/ic/outline-arrow-downward"
import dayjs from "dayjs"

const dates = defineModel("dates", {
  type: Array as () => string[],
  default: () => [],
})
const newDate = ref()

function removeDateInput(index: number, event: Event) {
  event.stopPropagation()
  if (index > -1) {
    dates.value.splice(index, 1)
  }
}

watch(
  newDate,
  (newValue) => {
    if (newValue && dayjs(newValue, "YYYY-MM-DD", true).isValid()) {
      dates.value.push(newValue)
      newDate.value = ""
    }
  },
  { immediate: true },
)
</script>

<template>
  <div class="p-10">
    <div class="mr-[2rem] flex justify-end">
      <RisTextButton
        :icon="SortOutlineIcon"
        :label="`Nach Datum sortieren`"
        variant="ghost"
      />
    </div>
    <form class="p-10">
      <div class="flex flex-col space-y-4">
        <div v-for="(date, index) in dates" :key="date" class="mb-4">
          <div class="p-1o flex items-center justify-between">
            <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
            <label :for="'date-' + index" class="w-96 flex-none"
              >Zeitgrenze {{ index + 1 }}</label
            >
            <RisDateInput
              :id="'date-' + index"
              v-model="dates[index]"
              size="small"
            />
            <RisTextButton
              :icon="DeleteOutlineIcon"
              variant="ghost"
              class="shrink-0"
              label="Löschen"
              type="button"
              icon-only
              @click="removeDateInput(index, $event)"
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
          />
        </div>
      </div>
    </form>
  </div>
</template>

// norms eid article refert
