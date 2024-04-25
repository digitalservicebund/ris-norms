<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { ref, watch } from "vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { FetchError } from "ofetch"
import { getNormHtmlByEli } from "@/services/normService"

const affectedDocumentEli = useEliPathParameter("affectedDocument")

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
</script>

<template>
  <div class="flex flex-col">
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
        <h3 id="affectedDocumentPreview" class="ds-label-02-bold">Vorschau</h3>

        <RisLawPreview
          class="ds-textarea flex-grow p-2"
          :content="previewHtml"
        />
      </section>
    </div>
  </div>
</template>
