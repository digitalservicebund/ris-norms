<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisRadioInput from "@/components/controls/RisRadioInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useElementId } from "@/composables/useElementId"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { useGetElement, useGetElementHtml } from "@/services/elementService"
import {
  useGetElementProprietary,
  usePutElementProprietary,
} from "@/services/proprietaryService"
import { ElementProprietary } from "@/types/proprietary"
import { produce } from "immer"
import { computed, ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const elementEid = useEidPathParameter()
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()
const { actionTeleportTarget } = useHeaderContext()

const {
  data: element,
  isFetching: elementIsLoading,
  error: elementError,
} = useGetElement(affectedDocumentEli, elementEid)

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<ElementProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetElementProprietary(affectedDocumentEli, elementEid, {
  atDate: timeBoundaryAsDate,
})

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
  affectedDocumentEli,
  elementEid,
  { atDate: timeBoundaryAsDate },
  {
    afterFetch(c) {
      // Whenever the metadata has been saved successfully, reload the
      // XML to keep it in sync
      reloadXml()
      return c
    },
  },
).put(localData)

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
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const {
  data: xml,
  isFetching: xmlIsLoading,
  error: xmlError,
  execute: reloadXml,
} = useNormXml(affectedDocumentEli)

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetElementHtml(affectedDocumentEli, elementEid, {
  at: timeBoundaryAsDate,
})

const sentryTraceId = useSentryTraceId()
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
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

        <RisErrorCallout v-else-if="renderError" :error="renderError" />

        <RisLawPreview
          v-else
          class="ds-textarea flex-grow p-2"
          :content="render ?? ''"
        />
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten dokumentieren">
        <RisTabs
          align="right"
          :tabs="[
            { id: 'editor', label: 'Rubriken' },
            { id: 'xml', label: 'XML' },
          ]"
        >
          <template #editor>
            <div v-if="isFetching" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisErrorCallout v-else-if="fetchError" :error="fetchError" />

            <form
              v-else
              class="grid grid-cols-[max-content,1fr] items-center gap-x-16 gap-y-14 overflow-auto"
              @submit.prevent
            >
              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Dokumenttyp</legend>

                <fieldset class="col-span-2 contents">
                  <legend class="self-start">Art der Norm</legend>

                  <div class="space-y-10">
                    <RisRadioInput
                      :id="artNormSnId"
                      v-model="artNorm"
                      value="SN"
                      name="artNorm"
                      label="SN - Stammnorm"
                    />
                    <RisRadioInput
                      :id="artNormAnId"
                      v-model="artNorm"
                      value="ÄN"
                      name="artNorm"
                      label="ÄN - Änderungsnorm"
                    />
                    <RisRadioInput
                      :id="artNormUnId"
                      v-model="artNorm"
                      value="ÜN"
                      name="artNorm"
                      label="ÜN - Übergangsnorm"
                    />
                  </div>
                </fieldset>
              </fieldset>
            </form>
          </template>

          <template #xml>
            <div v-if="xmlIsLoading" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisErrorCallout v-else-if="xmlError" :error="xmlError" />

            <RisCodeEditor
              :model-value="xml ?? ''"
              :readonly="true"
              class="flex-grow"
            />
          </template>
        </RisTabs>

        <!-- Save button -->
        <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
          <div class="relative">
            <RisTooltip
              :title="
                hasSaved && saveError
                  ? 'Speichern fehlgeschlagen'
                  : 'Gespeichert!'
              "
              :variant="hasSaved && saveError ? 'error' : 'success'"
              :visible="hasSaved"
              allow-dismiss
              alignment="right"
              attachment="bottom"
            >
              <template #default="{ ariaDescribedby }">
                <RisTextButton
                  :aria-describedby
                  :disabled="isFetching || !!fetchError"
                  :loading="isSaving"
                  label="Speichern"
                  @click="save()"
                />
              </template>

              <template #message>
                <RisCopyableLabel
                  v-if="saveError"
                  name="Trace-ID"
                  text="Trace-ID kopieren"
                  :value="sentryTraceId"
                />
              </template>
            </RisTooltip>
          </div>
        </Teleport>
      </section>
    </div>
  </div>
</template>
