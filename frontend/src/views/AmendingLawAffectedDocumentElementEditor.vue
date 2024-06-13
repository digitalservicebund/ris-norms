<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useElement } from "@/composables/useElement"
import { useElementHtml } from "@/composables/useElementHtml"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed } from "vue"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const elementEid = useEidPathParameter()
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

const identifier = computed<LawElementIdentifier | undefined>(() =>
  affectedDocumentEli.value && elementEid.value
    ? { eli: affectedDocumentEli.value, eid: elementEid.value }
    : undefined,
)

const element = useElement(identifier)

/* -------------------------------------------------- *
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const { data: xml } = useNormXml(affectedDocumentEli)

const render = useElementHtml(identifier, { at: timeBoundaryAsDate })
</script>

<template>
  <div class="flex flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">
          {{ element?.title }}
        </h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <RisLawPreview
          class="ds-textarea flex-grow p-2"
          :content="render ?? ''"
        />
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten bearbeiten">
        <RisTabs
          align="right"
          :tabs="[
            { id: 'editor', label: 'Rubriken' },
            { id: 'xml', label: 'XML' },
          ]"
        >
          <template #editor>
            <RisEmptyState
              text-content="Für dieses Element existieren keine Metadaten."
            />
          </template>

          <template #xml>
            <RisCodeEditor
              :model-value="xml ?? ''"
              :editable="false"
              class="flex-grow"
            />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
