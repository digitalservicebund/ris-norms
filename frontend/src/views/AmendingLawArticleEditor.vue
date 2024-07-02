<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
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
import { useNormRender } from "@/composables/useNormRender"
import { useNormXml } from "@/composables/useNormXml"
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
  if (!currentXml.value) return undefined

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
const amendingLawActiveTab = ref("text")

watch(xml, (xml) => {
  if (xml) {
    currentXml.value = xml
  }
})

function handlePreviewClick() {
  deselectAllSelectedMods()
}

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

          <router-view
            v-if="selectedMods.length > 0"
            v-model:xml="currentXml"
            :selected-mods="selectedMods"
          ></router-view>

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
