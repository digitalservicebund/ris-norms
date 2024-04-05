<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useAmendingLawHtml } from "@/composables/useAmendingLawHtml"

const eli = useEliPathParameter()
const amendingLawHtml = useAmendingLawHtml(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Verkündung</h1>
    <RisLawPreview
      class="drop-shadow-md"
      :content="amendingLawHtml ?? ''"
      highlight-mods
      highlight-affected-document
    />
  </div>
</template>
