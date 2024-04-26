<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useAmendingLawHtml } from "@/composables/useAmendingLawHtml"

const eli = useEliPathParameter()
const amendingLawHtml = useAmendingLawHtml(eli)
</script>

<template>
  <div class="p-40">
    <h1 class="ds-heading-02-reg mb-40">Verkündung</h1>
    <div class="rounded-sm bg-white px-24 py-24 shadow-md">
      <RisLawPreview
        :content="amendingLawHtml ?? ''"
        highlight-mods
        highlight-affected-document
      />
    </div>
  </div>
</template>
