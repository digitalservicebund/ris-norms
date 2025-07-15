<script setup lang="ts">
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useGetNorm, useGetNormHtml } from "@/services/normService"
import {
  useGetRahmenProprietary,
  usePutRahmenProprietary,
} from "@/services/proprietaryService"
import type { RahmenProprietary } from "@/types/proprietary"
import { useToast } from "@/composables/useToast"
import { onBeforeUnmount, ref, watch } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisMetadataEditorRahmenForm from "@/components/metadata-editor/rahmen/RisMetadataEditorRahmenForm.vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

const props = defineProps<{
  normExpressionEli: NormExpressionEli
}>()

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const { data: normData } = useGetNorm(() => props.normExpressionEli)

const localData = ref<RahmenProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetRahmenProprietary(() =>
  DokumentExpressionEli.fromNormExpressionEli(props.normExpressionEli),
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
} = usePutRahmenProprietary(localData, () =>
  DokumentExpressionEli.fromNormExpressionEli(props.normExpressionEli),
).put(localData)

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
} = useGetNormHtml(() => props.normExpressionEli)

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

const cleanupBreadcrumb = pushBreadcrumb({
  title: "Rahmen",
})

onBeforeUnmount(() => cleanupBreadcrumb())
</script>

<template>
  <div class="flex h-full flex-col overflow-hidden p-24">
    <div class="flex gap-16">
      <div class="grow">
        <h2 class="ris-label2-bold">{{ normData?.shortTitle ?? "Rahmen" }}</h2>
      </div>
    </div>
    <div class="gap grid min-h-0 grow grid-cols-2 grid-rows-1 gap-16">
      <section
        class="mt-16 flex min-h-0 grow flex-col gap-8"
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

      <section class="flex flex-col gap-8" aria-label="Metadaten dokumentieren">
        <div v-if="isFetching" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout v-else-if="fetchError" :error="fetchError" />

        <RisMetadataEditorRahmenForm v-else v-model="localData" />

        <slot
          name="save"
          :disabled="isFetching || !!fetchError"
          :loading="isSaving"
          :save="save"
        />
      </section>
    </div>
  </div>
</template>
