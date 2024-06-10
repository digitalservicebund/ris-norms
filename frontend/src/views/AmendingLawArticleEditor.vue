<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useArticle } from "@/composables/useArticle"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed, ref, watch } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisModForm from "@/components/RisModForm.vue"
import { useTemporalData } from "@/composables/useTemporalData"
import { useMod } from "@/composables/useMod"
import { useModEidPathParameter } from "@/composables/useModEidPathParameter"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import { useNormXml } from "@/composables/useNormXml"
import { useNormRender } from "@/composables/useNormRender"

const eid = useEidPathParameter()
const eli = useEliPathParameter()
const amendingLaw = useAmendingLaw(eli)
const selectedMod = useModEidPathParameter()

const identifier = computed<LawElementIdentifier | undefined>(() =>
  eli.value && eid.value ? { eli: eli.value, eid: eid.value } : undefined,
)
const { data: article } = useArticle(identifier)
const { data: xml } = useNormXml(eli)
const targetLawEli = computed(() => article.value?.affectedDocumentEli)
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
const { data: articleHtml } = useNormRender(articleXml)
const previewXml = ref<string>("")
const amendingLawActiveTab = ref("text")

watch(currentXml, (newXml, oldXml) => {
  if (newXml !== oldXml) {
    handleGeneratePreview()
  }
})
watch(xml, (xml) => {
  if (xml) {
    currentXml.value = xml
  }
})

function handleAknModClick({ eid }: { eid: string }) {
  selectedMod.value = eid
}

function handlePreviewClick() {
  selectedMod.value = ""
}

async function handleGeneratePreview() {
  if (!targetLawEli.value || !selectedMod.value) return

  try {
    await preview()
  } catch (error) {
    alert("Vorschau konnte nicht erstellt werden")
    console.error(error)
  }
}

const { timeBoundaries } = useTemporalData(eli)
const {
  textualModType,
  destinationHref,
  quotedTextFirst,
  quotedTextSecond,
  timeBoundary,
  preview: { data: previewData, execute: preview },
  update: { data: updateData, execute: update },
} = useMod(eli, selectedMod, xml)

const previewCustomNorms = computed(() =>
  previewData.value ? [previewData.value.amendingNormXml] : [],
)
const { data: previewHtml } = useNormRender(
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

watch(selectedMod, () => {
  if (selectedMod.value !== "") {
    handleGeneratePreview()
  }
})
watch(targetLawEli, () => {
  handleGeneratePreview()
})

async function handleSave() {
  try {
    await update()
  } catch (error) {
    console.error("Error saving the mod:", error)
  }
}
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

    <div class="flex h-[calc(100vh-5rem)] flex-col px-40 pt-40">
      <div class="mb-40 flex gap-16">
        <div class="flex-grow">
          <h1 class="ds-heading-02-reg">Artikel {{ article?.enumeration }}</h1>
          <h2 class="ds-heading-03-reg">Änderungsbefehle prüfen</h2>
        </div>
      </div>
      <div class="gap grid min-h-0 flex-grow grid-cols-3 gap-32">
        <section
          class="col-span-1 flex max-h-full flex-col gap-8 overflow-hidden pb-40"
          aria-labelledby="changeCommandsEditor"
        >
          <h3 id="changeCommandsEditor" class="ds-label-02-bold">
            <span class="block">Änderungsbefehle</span>
            <span>{{ amendingLaw?.title }}</span>
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
                :content="articleHtml ?? ''"
                highlight-mods
                highlight-affected-document
                :selected="selectedMod ? [selectedMod] : []"
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
          v-if="selectedMod"
          class="col-span-1 mt-32 flex max-h-full flex-col gap-8 pb-40"
          aria-labelledby="originalArticleTitle"
        >
          <h3 id="originalArticleTitle" class="ds-label-02-bold">
            Änderungsbefehle bearbeiten
          </h3>
          <RisModForm
            id="risModForm"
            v-model:textual-mod-type="textualModType"
            v-model:destination-href="destinationHref"
            v-model:quoted-text-second="quotedTextSecond"
            v-model:selected-time-boundary="timeBoundary"
            :quoted-text-first="quotedTextFirst"
            :time-boundaries="timeBoundaries"
            @generate-preview="handleGeneratePreview"
            @update-mod="handleSave"
          />
        </section>
        <section
          v-if="selectedMod"
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
              <RisLawPreview
                class="ds-textarea flex-grow p-2"
                :content="previewHtml ?? ''"
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
        <div v-else class="gap col-span-2 grid flex-grow grid-cols-2 gap-32">
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
