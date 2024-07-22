<script setup lang="ts">
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
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

    <RisCallout
      v-else-if="error"
      title="Die Liste der betroffenen Normkomplexe konnte nicht geladen werden."
      variant="error"
    >
      <p v-if="error.sentryEventId">
        Fehler-ID:
        <RisCopyableLabel :text="error.sentryEventId" name="Fehler-ID" />
      </p>
    </RisCallout>

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
