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
import { computed, ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import dayjs from "dayjs"
import { useNormXml } from "@/composables/useNormXml"
import Message from "primevue/message"

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
  quotedStructureContent,
  destinationUpToHref,
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
} = useNormRenderHtml(targetNormZf0Xml, {
  at: computed(() =>
    timeBoundary.value ? new Date(timeBoundary.value.date) : undefined,
  ),
  customNorms: previewCustomNorms,
})
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

const quotedStructureHtmlContent = ref<string | undefined>(undefined)
const { data: quotedStructureHtml } = useNormRenderHtml(
  quotedStructureContent,
  { snippet: true },
)

watch(
  () => quotedStructureHtml.value,
  (newValue) => {
    quotedStructureHtmlContent.value = newValue ?? undefined
  },
  { immediate: true },
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

const destinationHrefEli = computed(() => {
  const parts = destinationHref.value?.split("regelungstext-1")
  return parts ? parts[0] + "regelungstext-1" : ""
})

const {
  data: originalTargetNormZf0Xml,
  error: targetNormZf0Error,
  isFetching: targetNormZf0IsFetching,
} = useNormXml(destinationHrefEli)

const dayBeforeTimeBoundary = computed(() => {
  if (!timeBoundary.value?.date) {
    return new Date(0)
  }
  const dateOfTimeBoundary = new Date(timeBoundary.value?.date)
  return dayjs(dateOfTimeBoundary).subtract(1, "day").toDate()
})

// We need the norms with the changes of mods that come into affect before this mod so the character ranges or elements can be selected correctly.
const {
  data: targetNormXmlBeforeCurrentMod,
  isFetching: targetNormXmlBeforeCurrentModIsFetching,
  error: targetNormXmlBeforeCurrentModError,
} = useNormRenderXml(
  computed(() => originalTargetNormZf0Xml.value ?? xml.value),
  dayBeforeTimeBoundary,
)
const {
  data: targetNormHtmlBeforeCurrentMod,
  isFetching: targetNormHtmlBeforeCurrentModIsFetching,
  error: targetNormHtmlBeforeCurrentModError,
} = useNormRenderHtml(
  computed(() => originalTargetNormZf0Xml.value ?? xml.value),
  {
    at: dayBeforeTimeBoundary,
  },
)

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

    <div v-else-if="textualModType !== 'aenderungsbefehl-ersetzen'">
      <Message severity="warn">
        Es können zurzeit nur "Ersetzen"-Änderungsbefehle bearbeitet werden.
      </Message>
    </div>

    <div v-else-if="loadTimeBoundariesError">
      <RisErrorCallout :error="loadTimeBoundariesError" />
    </div>

    <RisModForm
      v-else
      id="risModForm"
      v-model:textual-mod-type="textualModType"
      v-model:destination-href="destinationHref"
      v-model:quoted-text-second="quotedTextSecond"
      v-model:selected-time-boundary="timeBoundary"
      v-model:destination-up-to="destinationUpToHref"
      :quoted-structure-content="quotedStructureHtmlContent"
      :quoted-text-first="quotedTextFirst"
      :time-boundaries="timeBoundaries ?? []"
      :is-updating="isUpdating"
      :is-updating-finished="isUpdatingFinished"
      :update-error="saveError"
      :target-law="targetNormXmlBeforeCurrentMod ?? undefined"
      :target-law-is-fetching="
        targetNormXmlBeforeCurrentModIsFetching || targetNormZf0IsFetching
      "
      :target-law-error="
        targetNormXmlBeforeCurrentModError || targetNormZf0Error
      "
      :target-law-html="targetNormHtmlBeforeCurrentMod ?? undefined"
      :target-law-html-is-fetching="
        targetNormHtmlBeforeCurrentModIsFetching || targetNormZf0IsFetching
      "
      :target-law-html-error="
        targetNormHtmlBeforeCurrentModError || targetNormZf0Error
      "
      @generate-preview="preview"
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
          v-if="isFetchingPreviewData || isFetchingPreviewHtml"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="loadPreviewHtmlError">
          <RisErrorCallout :error="loadPreviewHtmlError" />
        </div>
        <div v-else-if="previewError">
          <RisErrorCallout :error="previewError" />
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
        <div v-else-if="loadPreviewXmlError">
          <RisErrorCallout :error="loadPreviewXmlError" />
        </div>
        <div v-else-if="previewError">
          <RisErrorCallout :error="previewError" />
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
