<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLaw } from "@/composables/useTargetLaw"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import IconArrowBack from "~icons/ic/baseline-arrow-back"

const amendingLawEli = useEliPathParameter()
const affectedDocumentEli = useEliPathParameter("affectedDocument")

const amendingLaw = useAmendingLaw(amendingLawEli)

const targetLaw = useTargetLaw(affectedDocumentEli)
const targetLawXml = useTargetLawXml(affectedDocumentEli)
</script>

<template>
  <div v-if="amendingLaw">
    <RisAmendingLawInfoHeader :amending-law />

    <RouterLink
      class="ds-link-01-bold -mb-28 flex h-80 items-center gap-12 px-40 text-blue-800"
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
          label="Speichern"
          size="small"
          class="h-fit flex-none self-end"
          disabled
          @click="() => {}"
        />

        <RisTextButton
          label="Vorschau generieren"
          size="small"
          variant="tertiary"
          class="h-fit flex-none self-end"
          disabled
          @click="() => {}"
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
            :initial-content="targetLawXml ?? ''"
          />
        </section>

        <section
          class="flex flex-col gap-8"
          aria-labelledby="affectedDocumentPreview"
        >
          <h3 id="affectedDocumentPreview" class="ds-label-02-bold">
            Vorschau
          </h3>
          <RisCodeEditor
            class="flex-grow"
            readonly
            :editable="false"
            :initial-content="targetLawXml ?? ''"
          />
        </section>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
