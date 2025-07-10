<script setup lang="ts">
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { Splitter, SplitterPanel } from "primevue"
import RisMetadataEditorNavigation from "@/components/metadata-editor/RisMetadataEditorNavigation.vue"

const expressionEli = useDokumentExpressionEliPathParameter()
const selectedEid = useEidPathParameter()
</script>

<template>
  <RisViewLayout header-back-destination="history-back">
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full overflow-auto bg-white"
      >
        <RisMetadataEditorNavigation
          route-name-editor-element="VerkuendungMetadatenEditorElement"
          route-name-editor-outline-element="VerkuendungMetadatenEditorOutlineElement"
          route-name-editor-rahmen="VerkuendungMetadatenEditorRahmen"
          :dokument-expression-eli="expressionEli"
          :selected-e-id="selectedEid"
        />
      </SplitterPanel>
      <SplitterPanel
        :size="80"
        :min-size="20"
        class="h-full overflow-hidden bg-gray-100"
      >
        <RouterView />
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
