<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisModForm from "@/components/RisModForm.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useArticle } from "@/composables/useArticle"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useMod } from "@/composables/useMod"
import { useNormRender } from "@/composables/useNormRender"
import { useNormXml } from "@/composables/useNormXml"
import { useTemporalData } from "@/composables/useTemporalData"
import { getFrbrDisplayText } from "@/lib/frbr"
import { getNodeByEid } from "@/services/ldmldeService"
import { useGetNorm } from "@/services/normService"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed, ref, watch } from "vue"
import { useModEidSelection } from "@/composables/useModEidSelection"

const eid = useEidPathParameter()
const eli = useEliPathParameter()
const {
  data: amendingLaw,
  isFetching: isFetchingAmendingLaw,
  error: loadAmendingLawError,
} = useGetNorm(eli)

const {
  values: selectedMods,
  deselectAll: deselectAllSelectedMods,
  handleAknModClick,
} = useModEidSelection()

const identifier = computed<LawElementIdentifier | undefined>(() =>
  eli.value && eid.value ? { eli: eli.value, eid: eid.value } : undefined,
)
const {
  data: article,
  isFetching: isFetchingArticle,
  error: loadArticleError,
} = useArticle(identifier)
const {
  data: xml,
  isFetching: isFetchingXml,
  error: loadXmlError,
} = useNormXml(eli)
const currentXml = ref("")
const articleXml = computed(() => {
  if (!eid.value) return undefined

  const xmlDocument = xmlStringToDocument(currentXml.value)
  const articleNode = getNodeByEid(xmlDocument, eid.value)

  if (!articleNode) {
    return undefined
  }

  return xmlNodeToString(articleNode)
})

const {
  data: articleHtml,
  isFetching: isFetchingArticleHtml,
  error: loadArticleHtmlError,
} = useNormRender(articleXml)
const previewXml = ref<string>("")
const amendingLawActiveTab = ref("text")

watch(currentXml, (newXml, oldXml) => {
  if (newXml !== oldXml) {
    preview()
  }
})
watch(xml, (xml) => {
  if (xml) {
    currentXml.value = xml
  }
})

function handlePreviewClick() {
  deselectAllSelectedMods()
}

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
  computed(() => selectedMods.value?.[0] ?? null),
  xml,
)

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
  computed(() =>
    timeBoundary.value ? new Date(timeBoundary.value.date) : undefined,
  ),
  previewCustomNorms,
)

watch(previewData, () => {
  if (!previewData.value) return

  previewXml.value = previewData.value.targetNormZf0Xml
})

watch(updateData, () => {
  if (!updateData.value) return

  currentXml.value = updateData.value.amendingNormXml
  previewXml.value = updateData.value.targetNormZf0Xml
})

watch(selectedMods, () => {
  preview()
})

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? getFrbrDisplayText(amendingLaw.value) ?? "..."
        : "...",
    to: `/amending-laws/${eli.value}/articles`,
  },
  {
    key: "article",
    title: () => article.value?.title ?? "...",
  },
  { key: "textconsolidation", title: "Textkonsolidierung" },
])
</script>

<template>
  <div
    v-if="isFetchingAmendingLaw || isFetchingArticle"
    class="mt-20 flex items-center justify-center"
  >
    <RisLoadingSpinner></RisLoadingSpinner>
  </div>

  <div v-else-if="loadAmendingLawError || !amendingLaw">
    <RisCallout
      title="Das Änderungsgesetz konnte nicht geladen werden."
      variant="error"
    />
  </div>

  <div v-else-if="loadArticleError">
    <RisCallout
      title="Der Artikel konnte nicht gefunden werden."
      variant="error"
    />
  </div>

  <div v-else>
    <RisHeader :breadcrumbs>
      <div class="flex h-[calc(100vh-5rem-5rem)] flex-col px-40 pt-40">
        <div class="gap grid min-h-0 flex-grow grid-cols-3 gap-32">
          <section
            class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
            aria-labelledby="changeCommandsEditor"
          >
            <h3 id="changeCommandsEditor" class="ds-label-02-bold">
              <span class="block">Änderungsbefehle</span>
              <span>{{ amendingLaw?.title }}</span>
            </h3>

            <div
              v-if="isFetchingXml"
              class="mt-20 flex items-center justify-center"
            >
              <RisLoadingSpinner></RisLoadingSpinner>
            </div>

            <div v-else-if="loadXmlError">
              <RisCallout
                title="Der Artikel konnte nicht geladen werden."
                variant="error"
              />
            </div>

            <RisTabs
              v-else
              v-model:active-tab="amendingLawActiveTab"
              :tabs="[
                { id: 'text', label: 'Text' },
                { id: 'xml', label: 'XML' },
              ]"
            >
              <template #text>
                <div
                  v-if="isFetchingArticleHtml"
                  class="flex items-center justify-center"
                >
                  <RisLoadingSpinner></RisLoadingSpinner>
                </div>
                <div v-else-if="loadArticleHtmlError">
                  <RisCallout
                    title="Die Artikel-Vorschau konnte nicht erzeugt werden."
                    variant="error"
                  />
                </div>
                <RisLawPreview
                  v-else
                  class="ds-textarea flex-grow p-2"
                  :content="articleHtml ?? ''"
                  highlight-mods
                  highlight-affected-document
                  :selected="selectedMods"
                  @click:akn:mod="handleAknModClick"
                  @click="handlePreviewClick"
                />
              </template>

              <template #xml>
                <RisCodeEditor
                  v-model="currentXml"
                  class="flex-grow"
                ></RisCodeEditor>
              </template>
            </RisTabs>
          </section>

          <section
            v-if="selectedMods.length > 0"
            class="col-span-1 mt-32 flex max-h-full flex-col gap-8 pb-40"
            aria-labelledby="originalArticleTitle"
          >
            <h3 id="originalArticleTitle" class="ds-label-02-bold">
              Änderungsbefehle bearbeiten
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
              v-else-if="selectedMods.length === 1"
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

            <RisEmptyState
              v-else
              text-content="Aktuell kann nur ein einzelner Änderungsbefehl zur Zeit bearbeitet werden."
            />
          </section>

          <section
            v-if="selectedMods.length > 0"
            class="col-span-1 mt-24 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
            aria-labelledby="changedArticlePreivew"
          >
            <h3 id="changedArticlePreivew" class="ds-label-02-bold">
              Vorschau
            </h3>
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

          <div v-else class="gap col-span-2 grid flex-grow grid-cols-2 gap-32">
            <RisEmptyState
              text-content="Wählen sie einen Änderungsbefehl zur Bearbeitung aus."
              class="mt-[85px] h-fit"
            />
          </div>
        </div>
      </div>
    </RisHeader>
  </div>
</template>
