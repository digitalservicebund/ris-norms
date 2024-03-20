<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { ref, watch } from "vue"
import IconArrowBack from "~icons/ic/baseline-arrow-back"
import { getTargetLawHtmlByEli } from "@/services/targetLawsService"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { FetchError } from "ofetch"

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
    previewHtml.value = await getTargetLawHtmlByEli(affectedDocumentEli.value)
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
</script>

<template>
  <div v-if="amendingLaw">
    <RisAmendingLawInfoHeader :amending-law="amendingLaw" />

    <RouterLink
      class="ds-link-01-bold -mb-28 inline-flex h-80 items-center gap-12 px-40 text-blue-800"
      :to="{ name: 'AmendingLawAffectedDocuments' }"
    >
      <IconArrowBack class="text-18" alt="" />
      <span>Zurück</span>
    </RouterLink>

    <div class="flex h-dvh flex-col p-40">
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

          <RisLawPreview class="flex-grow" :content="previewHtml" />
        </section>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
