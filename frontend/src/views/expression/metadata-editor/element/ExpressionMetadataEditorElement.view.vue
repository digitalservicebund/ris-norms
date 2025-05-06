<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useElementId } from "@/composables/useElementId"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useErrorToast } from "@/lib/errorToast"
import { useGetElement, useGetElementHtml } from "@/services/elementService"
import {
  useGetElementProprietary,
  usePutElementProprietary,
} from "@/services/proprietaryService"
import type { ElementProprietary } from "@/types/proprietary"
import { produce } from "immer"
import Button from "primevue/button"
import { useToast } from "primevue/usetoast"
import RadioButton from "primevue/radiobutton"
import { computed, ref, watch } from "vue"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
const elementEid = useEidPathParameter()
const { actionTeleportTarget } = useHeaderContext()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(dokumentExpressionEli, elementEid)

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<ElementProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetElementProprietary(dokumentExpressionEli, elementEid)

watch(data, (newData) => {
  localData.value = newData
})

const {
  data: savedData,
  isFetching: isSaving,
  isFinished: hasSaved,
  error: saveError,
  execute: save,
} = usePutElementProprietary(localData, dokumentExpressionEli, elementEid)

watch(savedData, (newData) => {
  localData.value = newData
})

/* -------------------------------------------------- *
 * Metadata form                                      *
 * -------------------------------------------------- */

const { artNormSnId, artNormAnId, artNormUnId } = useElementId()

const artNorm = computed<string | undefined>({
  get() {
    return localData.value?.artDerNorm
  },
  set(value) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.artDerNorm = value
    })
  },
})

/* -------------------------------------------------- *
 * HTML preview                                       *
 * -------------------------------------------------- */

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(dokumentExpressionEli, elementEid)

const sentryTraceId = useSentryTraceId()
const { add: addToast } = useToast()
const { addErrorToast } = useErrorToast()

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
        />
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten dokumentieren">
        <div v-if="isFetching" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout v-else-if="fetchError" :error="fetchError" />

        <form
          v-else
          class="grid grid-cols-[max-content_1fr] items-center gap-x-16 gap-y-14 overflow-auto"
          @submit.prevent
        >
          <fieldset class="contents">
            <legend class="ris-label2-bold col-span-2">Dokumenttyp</legend>

            <fieldset class="col-span-2 contents">
              <legend class="self-start">Art der Norm</legend>

              <div class="space-y-10">
                <div class="flex items-center">
                  <RadioButton
                    v-model="artNorm"
                    :input-id="artNormSnId"
                    value="SN"
                    name="artNorm"
                  />
                  <label :for="artNormSnId">SN - Stammnorm</label>
                </div>
                <div class="flex items-center">
                  <RadioButton
                    v-model="artNorm"
                    :input-id="artNormAnId"
                    value="ÄN"
                    name="artNorm"
                  />
                  <label :for="artNormAnId">ÄN - Änderungsnorm</label>
                </div>
                <div class="flex items-center">
                  <RadioButton
                    v-model="artNorm"
                    :input-id="artNormUnId"
                    value="ÜN"
                    name="artNorm"
                  />
                  <label :for="artNormUnId">ÜN - Übergangsnorm</label>
                </div>
              </div>
            </fieldset>
          </fieldset>
        </form>
        <!-- Save button -->
        <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
          <div class="relative">
            <Button
              :disabled="isFetching || !!fetchError"
              :loading="isSaving"
              label="Speichern"
              @click="save()"
            />
          </div>
        </Teleport>
      </section>
    </div>
  </div>
</template>
