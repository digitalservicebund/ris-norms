<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import RisMetadataEditorOutlineElement from "@/components/metadata-editor/outline-element/RisMetadataEditorOutlineElement.vue"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
const elementEid = useEidPathParameter()
</script>

<template>
  <RisMetadataEditorOutlineElement
    v-if="elementEid"
    :dokument-expression-eli="dokumentExpressionEli"
    :e-id="elementEid"
  />
</template>
