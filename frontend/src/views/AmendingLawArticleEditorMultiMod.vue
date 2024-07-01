<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import RisTabs from "@/components/editor/RisTabs.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisDropdownInput from "@/components/controls/RisDropdownInput.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import { computed } from "vue"
import CheckIcon from "~icons/ic/check"
import { useMods } from "@/composables/useMods"

const xml = defineModel<string>("xml", {
  required: true,
})
const props = defineProps<{
  selectedMods: string[]
}>()

const eli = useEliPathParameter()

const mods = useMods(
  computed(() => props.selectedMods),
  xml,
)

const timeBoundary = computed({
  get: () => {
    const firstDate = mods.value[0].timeBoundary?.date

    if (mods.value.every((mod) => mod.timeBoundary?.date === firstDate)) {
      return firstDate ?? "no_choice"
    }

    return "multiple"
  },
  set: (value) => {
    mods.value = mods.value.map((mod) => ({
      ...mod,
      timeBoundary:
        value === "no_choice"
          ? undefined
          : (timeBoundaries.value ?? []).find((tb) => tb.date === value),
    }))
  },
})

const {
  data: timeBoundaries,
  isFetching: isFetchingTimeBoundaries,
  error: loadTimeBoundariesError,
} = useTemporalData(eli)

const timeBoundaryItems = computed(() => {
  return [
    ...(timeBoundaries.value ?? []).map((boundary) => ({
      label: new Date(boundary.date).toLocaleDateString("de"),
      value: boundary.date,
    })),
    { label: "Keine Angabe", value: "no_choice" },
    { label: "Mehrere", value: "multiple", disabled: true },
  ]
})
</script>
<template>
  <section
    class="col-span-1 mt-32 flex max-h-full flex-col gap-8 pb-40"
    aria-labelledby="originalArticleTitle"
  >
    <h3 id="originalArticleTitle" class="ds-label-02-bold">
      {{ props.selectedMods.length }} Änderungsbefehle bearbeiten
    </h3>

    <div
      v-if="isFetchingTimeBoundaries"
      class="flex items-center justify-center"
    >
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <div v-else-if="loadTimeBoundariesError">
      <RisCallout
        title="Die Zeitgrenzen konnten nicht geladen werden."
        variant="error"
      />
    </div>

    <form class="grid grid-cols-1 gap-y-20">
      <div class="grid grid-cols-2 gap-x-40">
        <RisDropdownInput
          id="timeBoundaries"
          v-model="timeBoundary"
          label="Zeitgrenze"
          :items="timeBoundaryItems"
        />
      </div>

      <div class="flex gap-20">
        <RisTextButton label="Vorschau" variant="tertiary" disabled />

        <div class="relative">
          <RisTooltip
            v-slot="{ ariaDescribedby }"
            :visible="false"
            :title="'Fehler beim Speichern'"
            alignment="right"
            attachment="top"
            :variant="'error'"
            allow-dismiss
          >
            <RisTextButton
              :aria-describedby="ariaDescribedby"
              label="Speichern"
              :icon="CheckIcon"
              disabled
            />
          </RisTooltip>
        </div>
      </div>
    </form>
  </section>

  <section
    class="col-span-1 mt-24 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
    aria-labelledby="changedArticlePreivew"
  >
    <h3 id="changedArticlePreview" class="ds-label-02-bold">Vorschau</h3>
    <RisTabs
      :tabs="[
        { id: 'text', label: 'Text' },
        { id: 'xml', label: 'XML' },
      ]"
    >
      <template #text>
        <RisEmptyState
          text-content="Aktuell kann keine Preview für mehrere Änderungsbefehle angezeigt werden."
        />
      </template>

      <template #xml>
        <RisEmptyState
          text-content="Aktuell kann keine Preview für mehrere Änderungsbefehle angezeigt werden."
        />
      </template>
    </RisTabs>
  </section>
</template>
