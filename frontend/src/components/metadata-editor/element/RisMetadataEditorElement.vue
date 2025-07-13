<script setup lang="ts">
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useGetElement, useGetElementHtml } from "@/services/elementService"
import { onBeforeUnmount, ref, watch } from "vue"
import type { ElementProprietary } from "@/types/proprietary"
import {
  useGetElementProprietary,
  usePutElementProprietary,
} from "@/services/proprietaryService"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useToast } from "@/composables/useToast"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisMetadataEditorElementForm from "@/components/metadata-editor/element/RisMetadataEditorElementForm.vue"

const props = defineProps<{
  dokumentExpressionEli: DokumentExpressionEli
  eId: string
}>()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(
  () => props.dokumentExpressionEli,
  () => props.eId,
)

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<ElementProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetElementProprietary(
  () => props.dokumentExpressionEli,
  () => props.eId,
)

watch(data, (newData) => {
  localData.value = newData
})

const {
  data: savedData,
  isFetching: isSaving,
  isFinished: hasSaved,
  error: saveError,
  execute: save,
} = usePutElementProprietary(
  localData,
  () => props.dokumentExpressionEli,
  () => props.eId,
)

watch(savedData, (newData) => {
  localData.value = newData
})

/* -------------------------------------------------- *
 * HTML preview                                       *
 * -------------------------------------------------- */

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(
  () => props.dokumentExpressionEli,
  () => props.eId,
)

const sentryTraceId = useSentryTraceId()
const { add: addToast, addError: addErrorToast } = useToast()

function showToast() {
  if (saveError.value) {
    addErrorToast(saveError, { traceId: sentryTraceId })
  } else {
    addToast({
      summary: "Gespeichert!",
      severity: "success",
    })
  }
}

watch(hasSaved, (finished) => {
  if (finished) {
    showToast()
  }
})

const { pushBreadcrumb } = useHeaderContext()

const cleanupBreadcrumb = ref<() => void>()

watch(
  () => element.value,
  () => {
    cleanupBreadcrumb.value?.()
    cleanupBreadcrumb.value = pushBreadcrumb({
      title: element.value?.title ?? "...",
    })
  },
  { immediate: true },
)

onBeforeUnmount(() => cleanupBreadcrumb.value?.())
</script>

<template>
  <div class="flex h-full flex-col overflow-hidden p-24">
    <div
      v-if="elementIsLoading"
      class="flex h-full items-center justify-center p-24"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="elementError" class="p-24">
      <RisErrorCallout :error="elementError" />
    </div>

    <div v-else class="flex flex-col overflow-hidden">
      <div class="flex gap-16">
        <div class="grow">
          <h2 class="ris-label2-bold">
            {{ element?.title }}
          </h2>
        </div>
      </div>

      <div class="gap grid min-h-0 grow grid-cols-2 grid-rows-1 gap-16">
        <section
          class="mt-16 flex flex-col gap-8 overflow-hidden"
          aria-label="Vorschau"
        >
          <div v-if="renderIsLoading" class="my-16 flex justify-center">
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout v-else-if="renderError" :error="renderError" />

          <RisLawPreview
            v-else
            class="h-full grow overflow-auto p-2"
            :content="render ?? ''"
            :arrow-focus="false"
          />
        </section>

        <section
          class="flex flex-col gap-8"
          aria-label="Metadaten dokumentieren"
        >
          <div v-if="isFetching" class="my-16 flex justify-center">
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout v-else-if="fetchError" :error="fetchError" />

          <RisMetadataEditorElementForm v-model="localData" />

          <slot
            name="save"
            :disabled="isFetching || !!fetchError"
            :loading="isSaving"
            :save="save"
          />
        </section>
      </div>
    </div>
  </div>
</template>
