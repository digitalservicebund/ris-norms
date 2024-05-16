<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useArticle } from "@/composables/useArticle"
import { useArticleXml } from "@/composables/useArticleXml"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed, ref, watch, onMounted } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { renderHtmlLaw } from "@/services/renderService"
import RisModForm from "@/components/RisModForm.vue"
import { useTemporalData } from "@/composables/useTemporalData"
import { useMod } from "@/composables/useMod"
import { useModEidPathParameter } from "@/composables/useModEidPathParameter"
import RisEmptyState from "@/components/RisEmptyState.vue"

const eid = useEidPathParameter()
const eli = useEliPathParameter()
const amendingLaw = useAmendingLaw(eli)
const selectedMod = useModEidPathParameter()

const identifier = computed<LawElementIdentifier | undefined>(() =>
  eli.value && eid.value ? { eli: eli.value, eid: eid.value } : undefined,
)
const article = useArticle(identifier)
const { xml: articleXml } = useArticleXml(identifier)
const currentArticleXml = ref("")
const renderedHtml = ref("")
const previewXml = ref<string>("")
const previewHtml = ref<string>("")
const amendingLawActiveTab = ref("text")

async function fetchAmendingLawRenderedHtml() {
  try {
    if (currentArticleXml.value) {
      renderedHtml.value = await renderHtmlLaw(currentArticleXml.value, false)
    }
  } catch (error) {
    console.error("Error fetching rendered HTML content:", error)
  }
}

const initialize = async () => {
  await fetchAmendingLawRenderedHtml()
}
onMounted(() => {
  initialize()
})

watch(articleXml, fetchAmendingLawRenderedHtml, { immediate: true })
watch(currentArticleXml, (newXml, oldXml) => {
  if (newXml !== oldXml && amendingLawActiveTab.value === "text") {
    fetchAmendingLawRenderedHtml()
  }
})
watch(amendingLawActiveTab, (newActiveTab, oldActiveTab) => {
  if (
    newActiveTab === "text" &&
    newActiveTab !== oldActiveTab &&
    currentArticleXml.value
  ) {
    fetchAmendingLawRenderedHtml()
  }
})
watch(articleXml, (articleXml) => {
  if (articleXml) {
    currentArticleXml.value = articleXml
  }
})

function handleAknModClick({ eid }: { eid: string }) {
  selectedMod.value = eid
}

function handlePreviewClick() {
  selectedMod.value = ""
}

const { timeBoundaries } = useTemporalData(eli)
const {
  textualModType,
  destinationHref,
  quotedTextFirst,
  quotedTextSecond,
  timeBoundary,
} = useMod(selectedMod, articleXml)
</script>

<template>
  <div v-if="amendingLaw">
    <RisAmendingLawInfoHeader :amending-law="amendingLaw" />

    <router-link
      class="ds-link-01-bold -mb-28 inline-flex h-80 items-center gap-12 px-40 text-blue-800"
      :to="{ name: 'AmendingLawArticles' }"
    >
      <IconArrowBack class="text-18" alt="" />
      <span>Zurück</span>
    </router-link>

    <div class="flex min-h-[calc(100dvh-5rem)] flex-col p-40">
      <div class="mb-40 flex gap-16">
        <div class="flex-grow">
          <h1 class="ds-heading-02-reg">Artikel {{ article?.enumeration }}</h1>
          <h2 class="ds-heading-03-reg">Änderungsbefehle prüfen</h2>
        </div>
      </div>
      <div class="gap grid min-h-0 flex-grow grid-cols-3 gap-32">
        <section
          class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden"
          aria-labelledby="changeCommandsEditor"
        >
          <h3
            id="changeCommandsEditor"
            class="ds-label-02-bold"
            data-testid="amendingLawHeading"
          >
            <span class="block">Änderungsbefehle</span>
            <span>{{ article?.title }}</span>
          </h3>
          <RisTabs
            v-model:active-tab="amendingLawActiveTab"
            :tabs="[
              { id: 'text', label: 'Text' },
              { id: 'xml', label: 'XML' },
            ]"
          >
            <template #text>
              <RisLawPreview
                class="ds-textarea flex-grow p-2"
                :content="renderedHtml"
                highlight-mods
                highlight-affected-document
                :selected="selectedMod ? [selectedMod] : []"
                @click:akn:mod="handleAknModClick"
                @click="handlePreviewClick"
              />
            </template>
            <template #xml>
              <RisCodeEditor
                v-model="currentArticleXml"
                class="flex-grow"
              ></RisCodeEditor>
            </template>
          </RisTabs>
        </section>
        <section
          v-if="selectedMod"
          class="col-span-1 mt-32 flex h-fit flex-col gap-8"
          aria-labelledby="originalArticleTitle"
        >
          <h3
            id="originalArticleTitle"
            class="ds-label-02-bold"
            data-testid="targetLawHeading"
          >
            Änderungsbefehle bearbeiten
          </h3>
          <RisModForm
            id="risModForm"
            v-model:textual-mod-type="textualModType"
            v-model:destination-href="destinationHref"
            v-model:quoted-text-second="quotedTextSecond"
            v-model:selected-time-boundary="timeBoundary"
            :quoted-text-first="quotedTextFirst"
            :time-boundaries="timeBoundaries.map((boundary) => boundary.date)"
          />
        </section>
        <section
          v-if="selectedMod && timeBoundary"
          class="col-span-1 mt-24 flex max-h-full flex-col gap-8 overflow-hidden"
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
              <RisLawPreview
                class="ds-textarea flex-grow p-2"
                :content="previewHtml"
              />
            </template>
            <template #xml>
              <RisCodeEditor
                class="flex-grow"
                :readonly="true"
                :model-value="previewXml"
              ></RisCodeEditor>
            </template>
          </RisTabs>
        </section>
        <div v-if="selectedMod && !timeBoundary" class="gap flex-grow gap-32">
          <RisEmptyState
            text-content="Wählen sie eine Zeitgrenze, um eine Vorschau des konsolidierten Änderungsbefehls zu sehen."
            class="mt-[85px] h-fit"
          />
        </div>
        <div
          v-if="!selectedMod"
          class="gap col-span-2 grid flex-grow grid-cols-2 gap-32"
        >
          <RisEmptyState
            text-content="Wählen sie einen Änderungsbefehl zur Bearbeitung aus."
            class="mt-[85px] h-fit"
          />
        </div>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
