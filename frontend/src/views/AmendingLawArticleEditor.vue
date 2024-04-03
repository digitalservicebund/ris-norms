<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useArticle } from "@/composables/useArticle"
import { useArticleXml } from "@/composables/useArticleXml"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed, ref, watch } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { previewTargetLaw } from "@/services/targetLawsService"

const eid = useEidPathParameter()
const eli = useEliPathParameter()

const identifier = computed<LawElementIdentifier | undefined>(() =>
  eli.value && eid.value ? { eli: eli.value, eid: eid.value } : undefined,
)

const amendingLaw = useAmendingLaw(eli)

const article = useArticle(identifier)
const { xml: articleXml, update: updateArticleXml } = useArticleXml(identifier)

const targetLawEli = computed(() => article.value?.affectedDocumentEli)
const targetLaw = useTargetLaw(targetLawEli)
const { xml: targetLawXml } = useTargetLawXml(targetLawEli)

const currentArticleXml = ref("")

function handleArticleXMLChange({ content }: { content: string }) {
  currentArticleXml.value = content
}

watch(articleXml, (articleXml) => {
  if (articleXml) {
    currentArticleXml.value = articleXml
  }
})

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

const previewXml = ref<string>("")
async function handleGeneratePreview() {
  try {
    if (targetLawEli.value) {
      previewXml.value = await previewTargetLaw(
        targetLawEli.value,
        currentArticleXml.value,
      )
    }
  } catch (error) {
    alert("Vorschau konnte nicht erstellt werden")
    console.error(error)
  }
}
</script>

<template>
  <div v-if="amendingLaw" class="flex min-h-screen flex-col bg-gray-100">
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
          <h3 id="originalArticleTitle" class="ds-label-02-bold">
            {{ targetLaw?.title }}
          </h3>
          <RisCodeEditor
            class="flex-grow"
            :readonly="true"
            :editable="false"
            :initial-content="targetLawXml ?? ''"
          ></RisCodeEditor>
        </section>

        <section
          class="row-span-2 flex flex-col gap-8"
          aria-labelledby="changedArticlePreivew"
        >
          <h3 id="changedArticlePreivew" class="ds-label-02-bold">Vorschau</h3>
          <RisCodeEditor
            class="flex-grow"
            :readonly="true"
            :editable="false"
            :initial-content="previewXml"
          ></RisCodeEditor>
        </section>

        <section
          class="flex flex-col gap-8"
          aria-labelledby="changeCommandsEditor"
        >
          <h3 id="changeCommandsEditor" class="ds-label-02-bold">
            <span class="block">Änderungsbefehle</span>
            <span>{{ article?.title }}</span>
          </h3>
          <RisCodeEditor
            class="flex-grow"
            :initial-content="articleXml"
            @change="handleArticleXMLChange"
          ></RisCodeEditor>
        </section>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
