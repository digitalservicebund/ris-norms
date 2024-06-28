<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
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
import { Proprietary } from "@/types/proprietary"
import { produce } from "immer"
import { computed, ref, watch } from "vue"

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

const localData = ref<Proprietary | null>(null)

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
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
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
            <div v-if="isFetching" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="fetchError"
              variant="error"
              title="Die Metadaten konnten nicht geladen werden."
            />

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

            <RisCallout
              v-else-if="xmlError"
              variant="error"
              title="Die XML-Ansicht konnte nicht geladen werden."
            />

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
              v-slot="{ ariaDescribedby }"
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
              <RisTextButton
                :aria-describedby
                :disabled="isFetching || !!fetchError"
                :loading="isSaving"
                label="Speichern"
                @click="save()"
              />
            </RisTooltip>
          </div>
        </Teleport>
      </section>
    </div>
  </div>
</template>
