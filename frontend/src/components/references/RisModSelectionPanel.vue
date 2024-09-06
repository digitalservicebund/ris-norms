<script setup lang="ts">
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisLawPreview, {
  AknElementClickEvent,
} from "@/components/RisLawPreview.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import RisErrorCallout from "../controls/RisErrorCallout.vue"

const props = defineProps<{
  /**
   * XML-String (LDML.de) of the norm to display and in which a akn:mod element can be selected.
   */
  normXml: string
}>()

/**
 * The eId of the currently selected akn:mod element.
 */
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
    <RisErrorCallout v-else-if="normRenderError" :error="normRenderError" />
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
  @apply block bg-highlight-1-default px-2;
  @apply outline outline-dotted outline-1 outline-blue-800;
}

:deep(.akn-mod.selected) {
  @apply block bg-highlight-1-selected;
  @apply outline outline-2 outline-blue-800;
}

:deep(.akn-mod:hover) {
  @apply block bg-highlight-1-hover;
  @apply outline outline-dotted outline-2 outline-blue-800;
}
</style>
