<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { useGetElement, useGetElementHtml } from "@/services/elementService"
import Message from "primevue/message"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { onBeforeUnmount, ref, watch } from "vue"
import { useHeaderContext } from "@/components/RisHeader.vue"

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

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(
  () => props.dokumentExpressionEli,
  () => props.eId,
)

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

    <div v-else class="flex flex-col overflow-hidden p-24">
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
          class="mt-32 flex flex-col gap-8"
          aria-label="Metadaten dokumentieren"
        >
          <Message severity="warn">
            Aktuell sind keine Metadaten auf Gliederungsebene implementiert.
          </Message>
        </section>
      </div>
    </div>
  </div>
</template>
