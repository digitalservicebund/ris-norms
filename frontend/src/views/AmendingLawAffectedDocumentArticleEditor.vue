<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { ref, watch } from "vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useNormHtml } from "@/composables/useNormHtml"

const affectedDocumentEli = useEliPathParameter("affectedDocument")

const { xml: targetLawXml } = useTargetLawXml(affectedDocumentEli)
const targetLawRender = useNormHtml(affectedDocumentEli)

const currentTargetLawXml = ref("")
function handleTargetLawXmlChange({ content }: { content: string }) {
  currentTargetLawXml.value = content
}

watch(targetLawXml, (targetLawXml) => {
  if (targetLawXml) {
    currentTargetLawXml.value = targetLawXml
  }
})
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-5rem)] flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">§ 7 Paßversagung</h2>
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
            <div
              class="grid gap-x-16 gap-y-8"
              style="grid-template-columns: max-content 1fr"
            >
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
              :initial-content="targetLawXml"
              @change="handleTargetLawXmlChange"
            />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
