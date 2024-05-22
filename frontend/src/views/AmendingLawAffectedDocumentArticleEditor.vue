<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useElement } from "@/composables/useElement"
import { useElementHtml } from "@/composables/useElementHtml"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { computed } from "vue"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const articleEid = useEidPathParameter()
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

/**
 * The xml of the law whose metadata is edited on this view. As both this and
 * the rahmen metadata editor view both edit the same xml (which is not yet
 * stored in the database) we provide it from AmendingLawAffectedDocumentEditor.
 * That view also handles persisting the changes when requested.
 */
const xml = defineModel<string>("xml")

const identifier = computed<LawElementIdentifier | undefined>(() =>
  affectedDocumentEli.value && articleEid.value
    ? { eli: affectedDocumentEli.value, eid: articleEid.value }
    : undefined,
)

const element = useElement(identifier)

const render = useElementHtml(identifier, { at: timeBoundaryAsDate })
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-5rem)] flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">
          {{ element?.title }}
        </h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <RisLawPreview
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
            <div class="grid grid-cols-[max-content,1fr] gap-x-16 gap-y-8">
              <label
                for="select-dokumenttyp"
                class="col-span-2 grid grid-cols-subgrid"
              >
                <span class="ds-label-02-reg my-auto">Dokumenttyp</span>
                <select
                  id="select-dokumenttyp"
                  disabled
                  class="ds-select ds-select-small"
                ></select>
              </label>

              <label
                for="input-art-der-norm"
                class="col-span-2 grid grid-cols-subgrid"
              >
                <span class="ds-label-02-reg my-auto">Art der Norm</span>
                <input
                  id="input-art-der-norm"
                  disabled
                  class="ds-input ds-input-small"
                />
              </label>
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
