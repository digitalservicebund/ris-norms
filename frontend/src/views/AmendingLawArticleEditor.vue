<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useArticle } from "@/composables/useArticle"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useModEidSelection } from "@/composables/useModEidSelection"
import { useModHighlightClasses } from "@/composables/useModHighlightClasses"
import { useNormRenderHtml } from "@/composables/useNormRender"
import { useNormXml } from "@/composables/useNormXml"
import { getFrbrDisplayText } from "@/lib/frbr"
import { getModEIds } from "@/services/ldmldeModService"
import { getNodeByEid } from "@/services/ldmldeService"
import { useGetNorm } from "@/services/normService"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { useDebounce } from "@vueuse/core"
import { computed, Ref, ref, toValue, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const eid = useEidPathParameter()
const eli = useEliPathParameter()
const {
  data: amendingLaw,
  isFetching: isFetchingAmendingLaw,
  error: loadAmendingLawError,
} = useGetNorm(eli)

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

const normDocument = computed(() => {
  const xmlValue = toValue(currentXml)
  return xmlValue ? xmlStringToDocument(xmlValue) : null
})

const modEIds = computed(() => {
  if (!normDocument.value) {
    return []
  }

  return getModEIds(normDocument.value)
})

const {
  values: selectedMods,
  deselectAll: deselectAllSelectedMods,
  selectAll: selectAllMods,
  handleAknModClick,
} = useModEidSelection(modEIds)

const {
  data: articleHtml,
  isFetching: isFetchingArticleHtml,
  error: loadArticleHtmlError,
} = useNormRenderHtml(articleXml)
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
        ? (getFrbrDisplayText(amendingLaw.value) ?? "...")
        : "...",
    to: `/amending-laws/${eli.value}/articles`,
  },
  {
    key: "article",
    title: () => article.value?.title ?? "...",
  },
  { key: "textconsolidation", title: "Textkonsolidierung" },
])

// give the url a moment to be updated before rendering the child routes
const showEditor: Ref<boolean> = useDebounce(
  computed(() => selectedMods.value.length > 0),
  20,
)

const isSelected = (eId: string) => selectedMods.value.includes(eId)

const classesForPreview = useModHighlightClasses(normDocument, isSelected)

function handlePreviewKeyDown(e: KeyboardEvent) {
  if (e.key === "a" && (e.metaKey || e.ctrlKey)) {
    e.preventDefault()
    selectAllMods()
  }
}
</script>

<template>
  <div
    v-if="isFetchingAmendingLaw || isFetchingArticle"
    class="mt-20 flex items-center justify-center"
  >
    <RisLoadingSpinner></RisLoadingSpinner>
  </div>

  <div v-else-if="loadAmendingLawError || !amendingLaw" class="m-24">
    <RisErrorCallout :error="loadAmendingLawError" />
  </div>

  <div v-else-if="loadArticleError" class="m-24">
    <RisErrorCallout :error="loadArticleError" />
  </div>

  <div v-else>
    <RisHeader :breadcrumbs>
      <div class="flex h-[calc(100vh-5rem-5rem)] flex-col px-24 pt-24">
        <div class="gap grid min-h-0 flex-grow grid-cols-3 gap-24">
          <section
            class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden pb-24"
            aria-labelledby="changeCommandsEditor"
          >
            <h3 id="changeCommandsEditor" class="ds-label-02-bold">
              <span class="block">Änderungsbefehle</span>
            </h3>

            <div
              v-if="isFetchingXml"
              class="mt-20 flex items-center justify-center"
            >
              <RisLoadingSpinner></RisLoadingSpinner>
            </div>

            <div v-else-if="loadXmlError">
              <RisErrorCallout :error="loadXmlError" />
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
                  <RisErrorCallout :error="loadArticleHtmlError" />
                </div>
                <RisLawPreview
                  v-else
                  class="amendingLawPreview ds-textarea flex-grow p-2"
                  :content="articleHtml ?? ''"
                  :selected="selectedMods"
                  :e-id-classes="classesForPreview"
                  @click:akn:mod="handleAknModClick"
                  @click="handlePreviewClick"
                  @keydown="handlePreviewKeyDown"
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
            v-if="showEditor"
            :key="selectedMods[0]"
            v-model:xml="currentXml"
            :selected-mods="selectedMods"
          ></router-view>

          <div
            v-else
            class="gap col-span-2 mt-[60px] grid flex-grow grid-cols-2 gap-32"
          >
            <RisEmptyState
              text-content="Wählen Sie einen Änderungsbefehl zur Bearbeitung aus."
              class="h-fit"
            />
          </div>
        </div>
      </div>
    </RisHeader>
  </div>
</template>

<style scoped>
.amendingLawPreview :deep(.akn-affectedDocument) {
  @apply bg-highlight-affectedDocument-default px-2 outline outline-dotted outline-1 outline-blue-800;
}

.amendingLawPreview :deep(.akn-affectedDocument):hover {
  @apply bg-highlight-affectedDocument-hover px-2 outline outline-dotted outline-2 outline-blue-800;
}
</style>
