<script setup lang="ts">
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
import { useAffectedDocuments } from "@/composables/useAffectedDocuments"
import { useEliPathParameter } from "@/composables/useEliPathParameter"

const eli = useEliPathParameter()

const affectedDocuments = useAffectedDocuments(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Betroffene Normenkomplexe</h1>

    <ul>
      <RisAffectedDocumentPanel
        v-for="affectedDocument in affectedDocuments"
        :key="affectedDocument.eli"
        :title="affectedDocument.title"
        :eli="affectedDocument.eli"
        :fna="affectedDocument.fna"
        :short-title="affectedDocument.shortTitle"
        as-list-item
      />
    </ul>
  </div>
</template>
