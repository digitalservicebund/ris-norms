<script setup lang="ts">
import RisAffectedDocumentPanel from "@/views/amending-law/affected-documents/RisAffectedDocumentPanel.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useAffectedDocuments } from "@/views/amending-law/affected-documents/useAffectedDocuments"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { onUnmounted } from "vue"

const eli = useEliPathParameter()
const { data: affectedDocuments, isFetching, error } = useAffectedDocuments(eli)

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({
  title: "Betroffene Normenkomplexe",
})
onUnmounted(() => cleanupBreadcrumbs())
</script>

<template>
  <div class="p-24">
    <h1 class="ris-heading2-regular mb-24">Betroffene Normenkomplexe</h1>

    <div v-if="isFetching" class="mt-20 flex items-center justify-center">
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <RisErrorCallout v-else-if="error" :error />

    <ul v-else class="space-y-16">
      <RisAffectedDocumentPanel
        v-for="affectedDocumentEli in affectedDocuments"
        :key="affectedDocumentEli"
        :eli="affectedDocumentEli"
        as-list-item
      />
    </ul>
  </div>
</template>
