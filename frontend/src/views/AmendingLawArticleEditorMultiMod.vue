<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import RisTabs from "@/components/editor/RisTabs.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisDropdownInput from "@/components/controls/RisDropdownInput.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import { computed, ref, watch } from "vue"
import CheckIcon from "~icons/ic/check"
import { useMods } from "@/composables/useMods"
import { useNormRender } from "@/composables/useNormRender"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"

const xml = defineModel<string>("xml", {
  required: true,
})
const props = defineProps<{
  selectedMods: string[]
}>()

const eli = useEliPathParameter()

const previewXml = ref<string>("")

const {
  data: mods,
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
} = useMods(
  eli,
  computed(() => props.selectedMods),
  xml,
)

const timeBoundary = computed({
  get: () => {
    if (mods.value.length < 1) {
      return "no_choice"
    }

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

const previewCustomNorms = computed(() =>
  previewData.value ? [previewData.value.amendingNormXml] : [],
)

const {
  data: previewHtml,
  isFetching: isFetchingPreviewHtml,
  error: loadPreviewHtmlError,
} = useNormRender(
  previewXml,
  false,
  computed(() => {
    if (
      timeBoundary.value === "no_choice" ||
      timeBoundary.value === "multiple"
    ) {
      return undefined
    }

    return new Date(timeBoundary.value)
  }),
  previewCustomNorms,
)

watch(previewData, () => {
  if (!previewData.value) return

  previewXml.value = previewData.value.targetNormZf0Xml
})

watch(updateData, () => {
  if (!updateData.value) return

  xml.value = updateData.value.amendingNormXml
  previewXml.value = updateData.value.targetNormZf0Xml
})

watch(
  () => props.selectedMods,
  () => {
    if (props.selectedMods.length > 1) {
      preview()
    }
  },
  { deep: true, immediate: true },
)
</script>
<template>
  <!--
    The router-view that includes this route is only rendered when at least one mod is selected. When exactly one mod is
    selected this is represented in the url and another view will be rendered. But the router needs a moment (a few ms)
    to update after the selection has changed. In this time this view still tries to render. Therefore we show a loading
    spinner when only one mod is selected. This manly prevents some flakyness in some e2e tests as playwright otherwise
    tries to fill out the form in this view.
  -->
  <div
    v-if="props.selectedMods.length === 1"
    class="col-span-1 mt-32 flex max-h-full flex-col items-center justify-center gap-8 pb-40"
  >
    <RisLoadingSpinner></RisLoadingSpinner>
  </div>

  <section
    v-else
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
          @change="preview"
        />
      </div>

      <div class="flex gap-20">
        <RisTextButton
          label="Vorschau"
          variant="tertiary"
          :disabled="timeBoundary === 'multiple'"
          @click.prevent="preview"
        />

        <div class="relative">
          <RisTooltip
            v-slot="{ ariaDescribedby }"
            :visible="isUpdatingFinished"
            :title="
              saveError ? 'Fehler beim Speichern' : 'Speichern erfolgreich'
            "
            alignment="right"
            attachment="top"
            :variant="saveError ? 'error' : 'success'"
            allow-dismiss
          >
            <RisTextButton
              :aria-describedby="ariaDescribedby"
              label="Speichern"
              :icon="CheckIcon"
              :loading="isUpdating"
              :disabled="timeBoundary === 'multiple'"
              @click.prevent="update"
            />
          </RisTooltip>
        </div>
      </div>
    </form>
  </section>

  <div v-if="timeBoundary === 'multiple'" class="col-span-1 flex-grow">
    <RisEmptyState
      text-content="Eine Vorschau kann nur für Änderungsbefehle mit der selben Zeitgrenze generiert werden."
      class="mt-[85px] h-fit"
    />
  </div>
  <section
    v-else
    class="col-span-1 mt-24 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
    aria-labelledby="changedArticlePreview"
  >
    <h3 id="changedArticlePreview" class="ds-label-02-bold">Vorschau</h3>
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
          v-if="isFetchingPreviewData"
          class="flex items-center justify-center"
        >
          <RisLoadingSpinner></RisLoadingSpinner>
        </div>
        <div v-else-if="previewError">
          <RisCallout
            title="Die Vorschau konnte nicht erzeugt werden."
            variant="error"
          />
        </div>
        <RisCodeEditor
          v-else
          class="flex-grow"
          :readonly="true"
          :model-value="previewXml"
        ></RisCodeEditor>
      </template>
    </RisTabs>
  </section>
</template>
