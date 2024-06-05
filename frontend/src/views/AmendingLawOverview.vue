<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import { useGetNormHtmlByEli } from "@/services/normService"

const eli = useEliPathParameter()
const { isFetching, error, data: amendingLawHtml } = useGetNormHtmlByEli(eli)
</script>

<template>
  <div class="p-40">
    <h1 class="ds-heading-02-reg mb-40">Verkündung</h1>
    <div v-if="error" class="w-2/3">
      <RisCallout
        title="Die Liste der Verkündungen konnte nicht geladen werden."
        variant="error"
      >
        <p>Lösungsvorschlag</p>
      </RisCallout>
    </div>
    <RisLoadingSpinner v-else-if="isFetching" />
    <div v-else class="rounded-sm bg-white px-24 py-24 shadow-md">
      <RisLawPreview
        :content="amendingLawHtml ?? ''"
        highlight-mods
        highlight-affected-document
      />
    </div>
  </div>
</template>
