<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useArticle } from "@/composables/useArticle"
import { useArticleXml } from "@/composables/useArticleXml"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed, ref, watch, onMounted } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import {
  previewTargetLaw,
  getTargetLawHtmlByEli,
  previewTargetLawAsHtml,
} from "@/services/targetLawsService"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { renderHtmlLaw } from "@/services/lawService"

const eid = useEidPathParameter()
const eli = useEliPathParameter()
const amendingLaw = useAmendingLaw(eli)

const identifier = computed<LawElementIdentifier | undefined>(() =>
  eli.value && eid.value ? { eli: eli.value, eid: eid.value } : undefined,
)
const article = useArticle(identifier)
const { xml: articleXml, update: updateArticleXml } = useArticleXml(identifier)
const targetLawEli = computed(() => article.value?.affectedDocumentEli)
const targetLaw = useTargetLaw(targetLawEli)
const { xml: targetLawXml } = useTargetLawXml(targetLawEli)
const currentArticleXml = ref("")
const renderedHtml = ref("")
const previewXml = ref<string>("")
const previewHtml = ref<string>("")
const targetLawHtml = ref("")
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
function handleArticleXMLChange({ content }: { content: string }) {
  currentArticleXml.value = content
}
/**
 * Handle the click of the save button.
 */
async function handleSave() {
  try {
    // TODO: (Malte Laukötter, 2024-03-07) this is currently saving the whole amending law, we need to change this to a single article once we have adjusted the provided xml as well
    await updateArticleXml(currentArticleXml.value)
  } catch (error) {
    alert("Änderungsgesetz nicht gespeichert")
    console.error(error)
  }
}
async function handleGeneratePreview() {
  if (!targetLawEli.value) return
  try {
    if (targetLawEli.value) {
      const [xmlContent, htmlContent] = await Promise.all([
        previewTargetLaw(targetLawEli.value, currentArticleXml.value),
        previewTargetLawAsHtml(targetLawEli.value, currentArticleXml.value),
      ])
      previewXml.value = xmlContent
      previewHtml.value = htmlContent
    }
  } catch (error) {
    alert("Vorschau konnte nicht erstellt werden")
    console.error(error)
  }
}
async function fetchTargetLawHtmlContent() {
  try {
    if (targetLawEli.value) {
      targetLawHtml.value = await getTargetLawHtmlByEli(
        targetLawEli.value,
        false,
      )
    }
  } catch (error) {
    console.error("Failed to fetch HTML content:", error)
  }
}
const initialize = async () => {
  await fetchTargetLawHtmlContent()
  await fetchAmendingLawRenderedHtml()
}
onMounted(() => {
  initialize()
})

watch(articleXml, fetchAmendingLawRenderedHtml, { immediate: true })
watch(targetLawEli, fetchTargetLawHtmlContent, { immediate: true })
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

    <div class="flex h-dvh flex-col p-40">
      <div class="mb-40 flex gap-16">
        <div class="flex-grow">
          <h1 class="ds-heading-02-reg">Artikel {{ article?.enumeration }}</h1>
          <h2 class="ds-heading-03-reg">Änderungsbefehle prüfen</h2>
        </div>

        <RisTextButton
          label="Speichern"
          size="small"
          class="h-fit flex-none self-end"
          :disabled="articleXml === currentArticleXml"
          @click="handleSave"
        />

        <RisTextButton
          label="Vorschau generieren"
          size="small"
          variant="tertiary"
          class="h-fit flex-none self-end"
          @click="handleGeneratePreview"
        />
      </div>

      <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-2 gap-32">
        <section
          class="flex flex-col gap-8"
          aria-labelledby="originalArticleTitle"
        >
          <h3
            id="originalArticleTitle"
            class="ds-label-02-bold"
            data-testid="targetLawHeading"
          >
            {{ targetLaw?.title }}
          </h3>
          <RisTabs
            :tabs="[
              { id: 'text', label: 'Text' },
              { id: 'xml', label: 'XML' },
            ]"
          >
            <template #text>
              <RisLawPreview
                class="ds-textarea flex-grow p-2"
                :content="targetLawHtml"
              />
            </template>
            <template #xml>
              <RisCodeEditor
                class="flex-grow"
                :readonly="true"
                :editable="false"
                :initial-content="targetLawXml ?? ''"
              ></RisCodeEditor>
            </template>
          </RisTabs>
        </section>
        <section
          class="row-span-2 flex flex-col gap-8"
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
                :editable="false"
                :initial-content="previewXml"
              ></RisCodeEditor>
            </template>
          </RisTabs>
        </section>
        <section
          class="flex flex-col gap-8"
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
              />
            </template>
            <template #xml>
              <RisCodeEditor
                class="flex-grow"
                :initial-content="currentArticleXml"
                @change="handleArticleXMLChange"
              ></RisCodeEditor>
            </template>
          </RisTabs>
        </section>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
