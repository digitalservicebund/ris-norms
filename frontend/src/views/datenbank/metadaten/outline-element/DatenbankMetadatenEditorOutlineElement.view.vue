<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import RisMetadataEditorOutlineElement from "@/components/metadata-editor/outline-element/RisMetadataEditorOutlineElement.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { computed } from "vue"

const normExpressionEli = useNormExpressionEliPathParameter()
const dokumentExpressionEli = computed(() =>
  DokumentExpressionEli.fromNormExpressionEli(normExpressionEli.value),
)
const elementEid = useEidPathParameter()
</script>

<template>
  <RisMetadataEditorOutlineElement
    v-if="elementEid"
    :dokument-expression-eli="dokumentExpressionEli"
    :e-id="elementEid"
  />
</template>
