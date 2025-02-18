<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisModForm from "@/views/amending-law/articles/editor/single-mods/RisModForm.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useMod } from "@/views/amending-law/articles/editor/single-mods/useMod"
import {
  useNormRenderHtml,
  useNormRenderXml,
} from "@/composables/useNormRender"
import { useTemporalData } from "@/composables/useTemporalData"
import { computed } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const xml = defineModel<string>("xml", {
  required: true,
})

const props = defineProps<{
  selectedMods: string[]
}>()

const eli = useEliPathParameter()

const {
  data: timeBoundaries,
  isFetching: isFetchingTimeBoundaries,
  error: loadTimeBoundariesError,
} = useTemporalData(eli)
const {
  timeBoundary,
  update: {
    execute: update,
    error: saveError,
    isFetching: isUpdating,
    isFinished: isUpdatingFinished,
  },
} = useMod(
  eli,
  computed(() => props.selectedMods[0]),
  xml,
)

const {
  data: previewHtml,
  isFetching: isFetchingPreviewHtml,
  error: loadPreviewHtmlError,
} = useNormRenderHtml(xml)
const {
  data: previewXml,
  isFetching: isFetchingPreviewXml,
  error: loadPreviewXmlError,
} = useNormRenderXml(xml)
</script>

<template>
  <section
    class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden pb-24"
    aria-labelledby="originalArticleTitle"
  >
    <h3 id="originalArticleTitle" class="ris-label2-bold mb-6">
      Änderungsbefehl bearbeiten
    </h3>

    <div
      v-if="isFetchingTimeBoundaries"
      class="flex items-center justify-center"
    >
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <div v-else-if="loadTimeBoundariesError">
      <RisErrorCallout :error="loadTimeBoundariesError" />
    </div>

    <RisModForm
      v-else
      id="risModForm"
      v-model:selected-time-boundary="timeBoundary"
      :time-boundaries="timeBoundaries ?? []"
      :is-updating="isUpdating"
      :is-updating-finished="isUpdatingFinished"
      :update-error="saveError"
      @update-mod="update"
    />
  </section>

  <section
    class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden pb-24"
    aria-labelledby="changedArticlePreivew"
  >
    <h3 id="changedArticlePreivew" class="ris-label2-bold">Vorschau</h3>
    <RisTabs
      :tabs="[
        { id: 'text', label: 'Text' },
        { id: 'xml', label: 'XML' },
      ]"
    >
      <template #text>
        <div
          v-if="isFetchingPreviewHtml"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="loadPreviewHtmlError">
          <RisErrorCallout :error="loadPreviewHtmlError" />
        </div>
        <RisLawPreview
          v-else
          class="ds-textarea flex-grow p-2"
          :content="previewHtml ?? ''"
        />
      </template>

      <template #xml>
        <div
          v-if="isFetchingPreviewXml"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="loadPreviewXmlError">
          <RisErrorCallout :error="loadPreviewXmlError" />
        </div>
        <RisCodeEditor
          v-else
          class="flex-grow"
          :readonly="true"
          :model-value="previewXml ?? ''"
        ></RisCodeEditor>
      </template>
    </RisTabs>
  </section>
</template>
