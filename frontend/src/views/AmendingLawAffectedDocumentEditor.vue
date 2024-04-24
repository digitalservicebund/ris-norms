<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { ref, watch } from "vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { FetchError } from "ofetch"
import { getNormHtmlByEli } from "@/services/normService"
import { Article } from "@/types/article"
import { AmendingLawTemporalDataReleaseResponse } from "@/types/amendingLawTemporalDataReleaseResponse"

const amendingLawEli = useEliPathParameter()
const affectedDocumentEli = useEliPathParameter("affectedDocument")

const amendingLaw = useAmendingLaw(amendingLawEli)

const targetLaw = useTargetLaw(affectedDocumentEli)

const { xml: targetLawXml, update: updateTargetLawXml } =
  useTargetLawXml(affectedDocumentEli)

const currentTargetLawXml = ref("")

function handleTargetLawXmlChange({ content }: { content: string }) {
  currentTargetLawXml.value = content
}

watch(targetLawXml, (targetLawXml) => {
  if (targetLawXml) {
    currentTargetLawXml.value = targetLawXml
  }
})

/** Save the updated XML to the API on click of the save button. */
async function handleSave() {
  try {
    await updateTargetLawXml(currentTargetLawXml.value)
  } catch (error) {
    alert("Metadaten nicht gespeichert")
    console.error(error)
  }
}

const previewHtml = ref("")
async function handleGeneratePreview() {
  try {
    previewHtml.value = await getNormHtmlByEli(affectedDocumentEli.value, true)
  } catch (e) {
    if (
      e instanceof FetchError &&
      e.status == 500 &&
      e.data !== "Internal Server Error"
    ) {
      previewHtml.value = e.data
    } else {
      throw e
    }
  }
}

const zeitgrenzen: AmendingLawTemporalDataReleaseResponse[] = [
  {
    date: "23.10.2023",
    eid: "unknown-eid-1",
  },
  {
    date: "02.12.2023",
    eid: "unknown-eid-2",
  },
  {
    date: "03.02.2024",
    eid: "unknown-eid-3",
  },
]

const articles: Article[] = [
  {
    eid: "hauptteil-1_art-1",
    title: "Passpflicht",
    enumeration: "1",
  },
  {
    eid: "hauptteil-1_art-3",
    title: "Befreiung von der Passpflichtblablabla",
    enumeration: "3",
  },
]
</script>

<template>
  <div
    v-if="amendingLaw"
    class="grid-rows-[5rem, 1fr] grid h-[calc(100dvh-5rem)] overflow-hidden bg-gray-100"
    style="grid-template-columns: 16rem 1fr"
  >
    <RisAmendingLawInfoHeader class="col-span-2" :amending-law="amendingLaw" />

    <aside
      class="col-span-1 flex w-full flex-col gap-[1rem] overflow-auto border-r border-gray-400 bg-white p-[1rem]"
      aria-labelledby="sidebarNavigation"
    >
      <span id="sidebarNavigation" hidden>SideBar Navigation</span>

      <div>
        <label for="zeitgrenzeSelect">
          <span class="ds-label-03-reg">Zeitgrenze</span>

          <select id="zeitgrenzeSelect" class="ds-select ds-select-small">
            <option
              v-for="zeitgrenze in zeitgrenzen"
              :key="zeitgrenze.eid"
              :value="zeitgrenze.eid"
            >
              {{ zeitgrenze.date }}
            </option>
          </select>
        </label>
      </div>

      <router-link
        class="ds-label-01-reg hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        to=""
      >
        Rahmen
      </router-link>

      <hr class="border-b border-gray-400" />

      <router-link
        v-for="article in articles"
        :key="article.eid"
        class="ds-label-02-reg block hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        :class="{
          'font-bold': article.eid === 'hauptteil-1_art-1',
        }"
        :to="`./${article.eid}`"
      >
        <span class="block overflow-hidden text-ellipsis whitespace-nowrap"
          >§{{ article.enumeration }} {{ article.title }}</span
        >
      </router-link>
    </aside>

    <div class="flex h-[calc(100dvh-5rem-5rem)] flex-col p-40">
      <div class="mb-40 flex gap-16">
        <div class="flex-grow">
          <h1 class="ds-heading-02-reg">{{ targetLaw?.title }}</h1>
          <h2 class="ds-heading-03-reg">Metadaten bearbeiten</h2>
        </div>

        <RisTextButton
          :disabled="targetLawXml === currentTargetLawXml"
          size="small"
          class="h-fit flex-none self-end"
          label="Speichern"
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

      <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
        <section
          class="flex flex-col gap-8"
          aria-labelledby="affectedDocumentEditor"
        >
          <h3 id="affectedDocumentEditor" class="ds-label-02-bold">
            Geändertes Gesetz
          </h3>
          <RisCodeEditor
            class="flex-grow"
            :initial-content="targetLawXml"
            @change="handleTargetLawXmlChange"
          />
        </section>

        <section
          class="flex flex-col gap-8"
          aria-labelledby="affectedDocumentPreview"
        >
          <h3 id="affectedDocumentPreview" class="ds-label-02-bold">
            Vorschau
          </h3>

          <RisLawPreview
            class="ds-textarea flex-grow p-2"
            :content="previewHtml"
          />
        </section>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
