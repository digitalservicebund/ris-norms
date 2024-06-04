<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useElementId } from "@/composables/useElementId"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormHtml } from "@/composables/useNormHtml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { useGetProprietary } from "@/services/proprietaryService"

/**
 * The xml of the law whose metadata is edited on this view. As both this and the article metadata editor vie both edit
 * the same xml (which is not yet stored in the database) we provide it from AmendingLawAffectedDocumentEditor. That
 * view also handles persisting the changes when requested.
 */
const xml = defineModel<string>("xml")

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

const { data } = useGetProprietary(affectedDocumentEli, {
  atDate: timeBoundaryAsDate,
})

const targetLawRender = useNormHtml(affectedDocumentEli, timeBoundaryAsDate)

const fnaId = useElementId()
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <div class="flex h-[calc(100dvh-5rem-5rem)] flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">Rahmen</h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <RisLawPreview
          class="ds-textarea flex-grow p-2"
          :content="targetLawRender ?? ''"
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
            <div
              class="grid grid-cols-[max-content,1fr] items-center gap-x-16 gap-y-8"
            >
              <h2 class="ds-label-02-bold col-span-2">Sachgebiet</h2>
              <label :for="fnaId">Sachgebiet FNA-Nummer</label>
              <RisTextInput
                :id="fnaId"
                :model-value="data?.fna.value"
                size="small"
                read-only
              />
            </div>
          </template>
          <template #xml>
            <RisCodeEditor v-model="xml" class="flex-grow" />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
