<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useGetNormHtml } from "@/services/normService"
import { onUnmounted } from "vue"

const eli = useEliPathParameter()

const { isFetching, error, data: amendingLawHtml } = useGetNormHtml(eli)

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Verkündung" })
onUnmounted(() => cleanupBreadcrumbs())
</script>

<template>
  <div class="p-40">
    <h1 class="ds-heading-02-reg mb-40">Verkündung</h1>
    <div v-if="error" class="w-2/3">
      <RisCallout
        title="Der Text der Verkündung konnte nicht geladen werden."
        variant="error"
      />
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
