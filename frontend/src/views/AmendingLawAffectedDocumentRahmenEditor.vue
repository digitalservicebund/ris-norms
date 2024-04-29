<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useNormHtml } from "@/composables/useNormHtml"

/**
 * The xml of the law whose metadata is edited on this view. As both this and the article metadata editor vie both edit
 * the same xml (which is not yet stored in the database) we provide it from AmendingLawAffectedDocumentEditor. That
 * view also handles persisting the changes when requested.
 */
const xml = defineModel<string>("xml")

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const targetLawRender = useNormHtml(affectedDocumentEli)

function handleXmlChange({ content }: { content: string }) {
  xml.value = content
}
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-5rem)] flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">Rahmen</h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8">
        <RisLawPreview
          class="ds-textarea flex-grow p-2"
          :content="targetLawRender ?? ''"
        />
      </section>

      <section class="flex flex-col gap-8">
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
                for="input-sachgebiet"
                class="col-span-2 grid grid-cols-subgrid"
              >
                <span class="ds-label-02-reg my-auto">Sachgebiet</span>
                <input
                  id="input-sachgebiet"
                  disabled
                  class="ds-input ds-input-small"
                />
              </label>

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
            <RisCodeEditor
              class="flex-grow"
              :initial-content="xml"
              @change="handleXmlChange"
            />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
