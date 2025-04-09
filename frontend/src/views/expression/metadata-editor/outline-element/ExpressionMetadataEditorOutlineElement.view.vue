<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useGetElement, useGetElementHtml } from "@/services/elementService"
import Message from "primevue/message"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
const elementEid = useEidPathParameter()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(dokumentExpressionEli, elementEid)

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(dokumentExpressionEli, elementEid)
</script>

<template>
  <div
    v-if="elementIsLoading"
    class="flex h-full items-center justify-center p-24"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="elementError" class="p-24">
    <RisErrorCallout :error="elementError" />
  </div>

  <div v-else class="flex flex-col overflow-hidden p-24">
    <div class="flex gap-16">
      <div class="grow">
        <h2 class="ris-label2-bold">
          {{ element?.title }}
        </h2>
      </div>
    </div>

    <div class="gap grid min-h-0 grow grid-cols-2 grid-rows-1 gap-16">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout v-else-if="renderError" :error="renderError" />

        <RisLawPreview v-else class="grow p-2" :content="render ?? ''" />
      </section>

      <section
        class="mt-32 flex flex-col gap-8"
        aria-label="Metadaten dokumentieren"
      >
        <Message severity="warn">
          Aktuell sind keine Metadaten auf Gliederungsebene implementiert.
        </Message>
      </section>
    </div>
  </div>
</template>
