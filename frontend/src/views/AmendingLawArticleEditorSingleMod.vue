<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisModForm from "@/components/RisModForm.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useMod } from "@/composables/useMod"
import {
  useNormRenderHtml,
  useNormRenderXml,
} from "@/composables/useNormRender"
import { useTemporalData } from "@/composables/useTemporalData"
import { computed, ref, watch } from "vue"

const xml = defineModel<string>("xml", {
  required: true,
})

const props = defineProps<{
  selectedMods: string[]
}>()

const eli = useEliPathParameter()

const targetNormZf0Xml = ref<string>("")

const {
  data: timeBoundaries,
  isFetching: isFetchingTimeBoundaries,
  error: loadTimeBoundariesError,
} = useTemporalData(eli)
const {
  textualModType,
  destinationHref,
  quotedTextFirst,
  quotedTextSecond,
  timeBoundary,
  preview: {
    data: previewData,
    execute: preview,
    error: previewError,
    isFetching: isFetchingPreviewData,
  },
  update: {
    data: updateData,
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

const previewCustomNorms = computed(() =>
  previewData.value ? [previewData.value.amendingNormXml] : [],
)
const {
  data: previewHtml,
  isFetching: isFetchingPreviewHtml,
  error: loadPreviewHtmlError,
} = useNormRenderHtml(
  targetNormZf0Xml,
  false,
  computed(() =>
    timeBoundary.value ? new Date(timeBoundary.value.date) : undefined,
  ),
  previewCustomNorms,
)
const {
  data: previewXml,
  isFetching: isFetchingPreviewXml,
  error: loadPreviewXmlError,
} = useNormRenderXml(
  targetNormZf0Xml,
  computed(() =>
    timeBoundary.value ? new Date(timeBoundary.value.date) : undefined,
  ),
  previewCustomNorms,
)

watch(
  xml,
  (newXml, oldXml) => {
    if (newXml !== oldXml) {
      preview()
    }
  },
  { immediate: true },
)

watch(previewData, () => {
  if (!previewData.value) return

  targetNormZf0Xml.value = previewData.value.targetNormZf0Xml
})

watch(updateData, () => {
  if (!updateData.value) return

  xml.value = updateData.value.amendingNormXml
  targetNormZf0Xml.value = updateData.value.targetNormZf0Xml
})

watch(
  () => props.selectedMods[0],
  () => {
    if (props.selectedMods.length === 1) {
      preview()
    }
  },
)
</script>

<template>
  <section
    class="col-span-1 mt-32 flex max-h-full flex-col gap-8 pb-40"
    aria-labelledby="originalArticleTitle"
  >
    <h3 id="originalArticleTitle" class="ds-label-02-bold">
      Änderungsbefehl bearbeiten
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

    <RisModForm
      v-else
      id="risModForm"
      v-model:textual-mod-type="textualModType"
      v-model:destination-href="destinationHref"
      v-model:quoted-text-second="quotedTextSecond"
      v-model:selected-time-boundary="timeBoundary"
      :quoted-text-first="quotedTextFirst"
      :time-boundaries="timeBoundaries ?? []"
      :is-updating="isUpdating"
      :is-updating-finished="isUpdatingFinished"
      :update-error="saveError"
      @generate-preview="preview"
      @update-mod="update"
    />
  </section>

  <section
    class="col-span-1 mt-24 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
    aria-labelledby="changedArticlePreivew"
  >
    <h3 id="changedArticlePreivew" class="ds-label-02-bold">Vorschau</h3>
    <RisTabs
      :tabs="[
        { id: 'text', label: 'Text' },
        { id: 'xml', label: 'XML' },
      ]"
    >
      <template #text>
        <div
          v-if="isFetchingPreviewData || isFetchingPreviewHtml"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="loadPreviewHtmlError || previewError">
          <RisCallout
            title="Die Vorschau konnte nicht erzeugt werden."
            variant="error"
          />
        </div>
        <RisLawPreview
          v-else
          class="ds-textarea flex-grow p-2"
          :content="previewHtml ?? ''"
        />
      </template>

      <template #xml>
        <div
          v-if="isFetchingPreviewData || isFetchingPreviewXml"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="loadPreviewXmlError || previewError">
          <RisCallout
            title="Die Vorschau konnte nicht erzeugt werden."
            variant="error"
          />
        </div>
        <RisCodeEditor
          v-else
          class="flex-grow"
          :readonly="true"
          :model-value="previewXml ?? targetNormZf0Xml"
        ></RisCodeEditor>
      </template>
    </RisTabs>
  </section>
</template>
