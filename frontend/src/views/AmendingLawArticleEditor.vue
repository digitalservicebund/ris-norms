<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useAmendingLawHtml } from "@/composables/useAmendingLawHtml"
import { useArticle } from "@/composables/useArticle"
import { useArticleXml } from "@/composables/useArticleXml"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { eidHasPart, reduceEidToPart } from "@/services/eidService"
import { renderHtmlLaw } from "@/services/lawService"
import {
  getChangeNewText,
  getChangeRefHref,
  getChangeType,
  getTargetLawHref,
  setChangeNewText,
  setChangeType,
} from "@/services/ldmlDeService"
import {
  getTargetLawHtmlByEli,
  previewTargetLaw,
  previewTargetLawAsHtml,
} from "@/services/targetLawsService"
import { xmlDocumentToString, xmlStringToDocument } from "@/services/xmlService"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { ComputedRef, computed, onMounted, ref, watch } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"

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
const { html: amendingLawHtml, update: updateAmendingLawHtml } =
  useAmendingLawHtml(eli)

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
    updateAmendingLawHtml()
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

const xmlDoc = computed(() =>
  xmlStringToDocument(currentArticleXml.value ?? ""),
)

function reduceToSingleValueOrMixed(values: (string | null | undefined)[]) {
  return values.reduce(
    (acc, current) => {
      if (acc === current || acc === null) {
        return current ?? null
      } else {
        return "mixed"
      }
    },
    null as string | null,
  )
}

const selectedChangeMods = ref<string[]>([])
const selectedBezugsDoc = ref<string | null>(null)

function handleEditorClick({
  eid,
  originalEvent,
}: {
  eid: string
  guid: string
  originalEvent: MouseEvent
}) {
  if (eidHasPart(eid, "ändbefehl")) {
    if (!originalEvent.shiftKey) {
      selectedChangeMods.value = []
    }
    selectedChangeMods.value.push(reduceEidToPart(eid, "ändbefehl"))
  }

  if (eidHasPart(eid, "bezugsdoc")) {
    selectedBezugsDoc.value = reduceEidToPart(eid, "bezugsdoc")
  }
}

const changeType = computed({
  get() {
    return reduceToSingleValueOrMixed(
      selectedChangeMods.value.map((eid) => getChangeType(xmlDoc.value, eid)),
    )
  },
  set(newValue) {
    if (!newValue) return

    for (const eid of selectedChangeMods.value) {
      setChangeType(xmlDoc.value, eid, newValue)
    }

    currentArticleXml.value = xmlDocumentToString(xmlDoc.value)
  },
})
const changeRefHref = computed(() =>
  reduceToSingleValueOrMixed(
    selectedChangeMods.value.map((eid) => getChangeRefHref(xmlDoc.value, eid)),
  ),
)
const changeNewText = computed({
  get() {
    return reduceToSingleValueOrMixed(
      selectedChangeMods.value.map((eid) =>
        getChangeNewText(xmlDoc.value, eid),
      ),
    )
  },
  set(newText) {
    if (!newText) return

    for (const eid of selectedChangeMods.value) {
      setChangeNewText(xmlDoc.value, eid, newText)
    }

    currentArticleXml.value = xmlDocumentToString(xmlDoc.value)
  },
})

const targetLawEliTest = ref<string>()
watch(
  selectedChangeMods,
  () =>
    (targetLawEliTest.value =
      reduceToSingleValueOrMixed(
        selectedChangeMods.value.map((eid) =>
          getChangeRefHref(xmlDoc.value, eid)
            ?.split("/")
            .slice(0, -2)
            .join("/"),
        ),
      ) ?? ""),
)

function handleUseEliClick(eid: string | null) {
  if (eid === null) return

  targetLawEliTest.value = getTargetLawHref(xmlDoc.value, eid) ?? ""
}

const selectedEids: ComputedRef<string[]> = computed(() => {
  const eIds: string[] = [...selectedChangeMods.value]
  if (selectedBezugsDoc.value) {
    eIds.push(selectedBezugsDoc.value)
  }
  return eIds
})

function eidToSlotName(eid: string) {
  return `eid:${eid}`
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

      <div class="gap grid min-h-0 flex-grow grid-cols-3 grid-rows-2 gap-32">
        <section
          class="row-span-2 flex flex-col gap-8"
          aria-labelledby="changeCommandsEditor"
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
          =======
          <h3 id="changeCommandsEditor" class="ds-label-02-bold">
            <span class="block">Änderungsbefehle</span>
            <span>{{ article?.title }}</span>
          </h3>
          <RisLawPreview
            highlight-mods
            highlight-affected-document
            class="ds-textarea h-1/2 p-2"
            :content="amendingLawHtml ?? ''"
            :selected-eids="selectedEids"
            @content:click="handleEditorClick"
          >
            <template
              v-for="changeModEid in selectedChangeMods"
              #[eidToSlotName(changeModEid)]
            >
              TEST
            </template>
            <template
              v-if="selectedBezugsDoc"
              #[eidToSlotName(selectedBezugsDoc)]
            >
              <div class="mt-4 flex flex-row gap-4">
                <button
                  class="ds-button ds-button-small ds-button-tertiary bg-white"
                  @click="() => handleUseEliClick(selectedBezugsDoc)"
                >
                  ELI übernehmen
                </button>
                <button
                  class="ds-button ds-button-small ds-button-tertiary bg-white"
                >
                  ELI kopiere
                </button>
                <button
                  class="ds-button ds-button-small ds-button-tertiary bg-white"
                >
                  Öffnen
                </button>
              </div>
            </template>
          </RisLawPreview>
          <RisCodeEditor
            class="h-1/2"
            :readonly="false"
            :editable="true"
            :initial-content="currentArticleXml"
            @change="handleArticleXMLChange"
          ></RisCodeEditor>
        </section>
        <section
          class="row-span-2 flex flex-col gap-8"
          aria-labelledby="visualEditor"
        >
          <h3 id="visualEditor" class="ds-label-02-bold">
            Änderungsbefehl bearbeiten
          </h3>

          <div>
            <label for="changeType"
              >Änderungstyp
              <select
                id="changeType"
                v-model="changeType"
                class="ds-select ds-select-medium"
              >
                <option disabled value="mixed">Verschiedene</option>
                <option value="aenderungsbefehl-einfuegen">Einfügen</option>
                <option value="aenderungsbefehl-ersetzen">Ersetzen</option>
                <option value="aenderungsbefehl-streichen">Streichen</option>
                <option value="aenderungsbefehl-neufassung">Neufassung</option>
                <option value="aenderungsbefehl-ausserkrafttreten">
                  Außerkrafttreten
                </option>
              </select></label
            >
          </div>

          <div>
            <label for="changeTargetLaw"
              >ELI Zielgesetz
              <input
                id="changeTargetLaw"
                class="ds-input ds-input-medium"
                type="text"
                :value="targetLawEliTest"
            /></label>
          </div>

          <div>
            <label for="changeRefHref"
              >zu ersetzende Textstelle
              <input
                id="changeRefHref"
                class="ds-input ds-input-medium"
                type="text"
                :value="changeRefHref"
            /></label>
          </div>
          <div>
            <label for="changeNewText"
              >Neuer Text Inhalt
              <textarea
                id="changeNewText"
                v-model="changeNewText"
                class="ds-textarea"
                type="text"
              />
            </label>
          </div>
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
