<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import RisTabs from "@/components/editor/RisTabs.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"

const props = defineProps<{
  selectedMods: string[]
}>()

const eli = useEliPathParameter()

const { isFetching: isFetchingTimeBoundaries, error: loadTimeBoundariesError } =
  useTemporalData(eli)
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

    <RisEmptyState
      v-else
      text-content="Aktuell kann nur ein einzelner Änderungsbefehl zur Zeit bearbeitet werden."
    />
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
