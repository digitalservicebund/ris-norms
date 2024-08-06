<script setup lang="ts">
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useAffectedDocuments } from "@/composables/useAffectedDocuments"
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
    <h1 class="ds-heading-02-reg mb-24">Betroffene Normenkomplexe</h1>

    <div v-if="isFetching" class="mt-20 flex items-center justify-center">
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <RisErrorCallout
      v-else-if="error"
      title="Die Liste der betroffenen Normkomplexe konnte nicht geladen werden."
    />

    <ul v-else class="space-y-16">
      <RisAffectedDocumentPanel
        v-for="{ eli: affectedDocumentEli, zf0Eli } in affectedDocuments"
        :key="affectedDocumentEli"
        :eli="affectedDocumentEli"
        :zf0-eli="zf0Eli"
        as-list-item
      />
    </ul>
  </div>
</template>
