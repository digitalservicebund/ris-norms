<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { useGetElement, useGetElementHtml } from "@/services/elementService"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const elementEid = useEidPathParameter()
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(affectedDocumentEli, elementEid)

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(
  affectedDocumentEli,
  "hauptteil-1_abschnitt-erster_para-2",
  {
    at: timeBoundaryAsDate,
  },
)
</script>

<template>
  <div
    v-if="elementIsLoading"
    class="flex h-full items-center justify-center p-24"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="elementError" class="p-24">
    <RisErrorCallout title="Das Element konnte nicht geladen werden.">
    </RisErrorCallout>
  </div>

  <div v-else class="flex flex-col overflow-hidden p-24">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-label-02-bold">
          {{ element?.title }}
        </h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-16">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout
          v-else-if="renderError"
          title="Die Vorschau konnte nicht geladen werden."
        />

        <RisLawPreview
          v-else
          class="ds-textarea flex-grow p-2"
          :content="render ?? ''"
        />
      </section>

      <section
        class="mt-32 flex flex-col gap-8"
        aria-label="Metadaten dokumentieren"
      >
        <RisCallout
          variant="warning"
          title="Aktuell sind keine Metadaten auf Gliederungsebene implementiert"
        />
      </section>
    </div>
  </div>
</template>
