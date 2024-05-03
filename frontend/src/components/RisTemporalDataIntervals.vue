<script setup lang="ts">
import { defineModel, nextTick, ref, watch, computed } from "vue"
import RisDateInput from "@/components/controls/RisDateInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import DeleteOutlineIcon from "~icons/ic/outline-delete"
import SortOutlineIcon from "~icons/ic/outline-arrow-downward"
import dayjs from "dayjs"

interface DateEntry {
  date: string
  eventRefEid: string
}

const dates = defineModel("dates", {
  type: Array as () => DateEntry[],
  default: () => [],
})

const newDate = ref<string | undefined>()
const isDeleteDisabled = computed(() => dates.value.length <= 1)

function removeDateInput(index: number) {
  if (index > -1) {
    dates.value.splice(index, 1)
  }
}

watch(newDate, async (newDateValue) => {
  if (newDateValue && dayjs(newDateValue, "YYYY-MM-DD", true).isValid()) {
    dates.value.push({
      date: newDateValue,
      eventRefEid: "",
    })

    // Need to wait for one tick to give the date input some time to update
    // it's internal state, otherwise we'll keep seeing the previous value.
    await nextTick()
    newDate.value = undefined
  }
})
</script>

<template>
  <form class="grid grid-cols-[6rem,1fr,min-content] items-center gap-4">
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
      <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
      <label :for="`date-${index}`">Zeitgrenze {{ index + 1 }}</label>
      <RisDateInput
        :id="`date-${index}`"
        v-model="dateEntry.date"
        size="small"
        data-testid="date-input-field"
      />
      <RisTextButton
        :icon="DeleteOutlineIcon"
        variant="ghost"
        class="shrink-0"
        :label="`Zeitgrenze ${index + 1} löschen`"
        type="button"
        :disabled="isDeleteDisabled"
        :data-testid="`delete-button-${index}`"
        icon-only
        @click.prevent="removeDateInput(index)"
      />
    </template>

    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label for="new-date">Zeitgrenze hinzufügen</label>
    <RisDateInput
      id="new-date"
      v-model="newDate"
      size="small"
      data-testid="new-date-input-field"
    />
  </form>
</template>
