<script setup lang="ts">
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
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
  <div class="p-40">
    <h1 class="ds-heading-02-reg mb-40">Betroffene Normenkomplexe</h1>

    <div v-if="isFetching" class="mt-20 flex items-center justify-center">
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <div v-else-if="error" class="w-1/2">
      <RisCallout
        title="Die Liste der betroffenen Normkomplexen konnte nicht geladen werden."
        variant="error"
      />
    </div>

    <ul v-else>
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
