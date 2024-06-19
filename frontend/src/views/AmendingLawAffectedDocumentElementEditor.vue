<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { useGetElement, useGetElementHtml } from "@/services/elementService"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const elementEid = useEidPathParameter()
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(affectedDocumentEli, elementEid, undefined, {
  refetch: true,
})

/* -------------------------------------------------- *
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const {
  data: xml,
  isFetching: xmlIsLoading,
  error: xmlError,
} = useNormXml(affectedDocumentEli)

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(
  affectedDocumentEli,
  elementEid,
  { at: timeBoundaryAsDate },
  { refetch: true },
)
</script>

<template>
  <div
    v-if="elementIsLoading"
    class="flex h-full items-center justify-center p-40"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="elementError" class="p-40">
    <RisCallout
      variant="error"
      title="Das Element konnte nicht geladen werden."
    />
  </div>

  <div v-else class="flex flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">
          {{ element?.title }}
        </h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisCallout
          v-else-if="renderError"
          variant="error"
          title="Die Vorschau konnte nicht geladen werden."
        />

        <RisLawPreview
          v-else
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
            <div v-if="xmlIsLoading" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="xmlError"
              variant="error"
              title="Die XML-Ansicht konnte nicht geladen werden."
            />

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
