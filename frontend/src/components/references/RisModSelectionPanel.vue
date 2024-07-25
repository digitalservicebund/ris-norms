<script setup lang="ts">
import RisLawPreview, {
  AknElementClickEvent,
} from "@/components/RisLawPreview.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"

const props = defineProps<{
  normXml: string
}>()

const selectedModEId = defineModel<string>()

const {
  data: normRender,
  isFetching: normRenderLoading,
  error: normRenderError,
} = useNormRenderHtml(props.normXml)

function handleAknModClick({ eid }: AknElementClickEvent) {
  selectedModEId.value = eid
}
</script>

<template>
  <div class="flex flex-col">
    <div v-if="normRenderLoading" class="flex justify-center">
      <RisLoadingSpinner />
    </div>
    <RisCallout
      v-else-if="normRenderError"
      variant="error"
      title="Die Vorschau konnte nicht geladen werden."
    />
    <RisLawPreview
      class="ds-textarea flex-grow p-2"
      :selected="selectedModEId ? [selectedModEId] : []"
      :content="normRender ?? ''"
      @click:akn:mod="handleAknModClick"
    />
  </div>
</template>

<style scoped>
:deep(.akn-mod) {
  @apply block border border-[#4299F7] bg-[#E7E7E766] px-2;
}

:deep(.akn-mod.selected) {
  @apply block bg-[#fef7bd];
}

:deep(.akn-mod):hover {
  @apply block bg-[#fef7bd];
}
</style>
