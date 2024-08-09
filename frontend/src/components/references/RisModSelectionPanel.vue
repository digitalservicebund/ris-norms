<script setup lang="ts">
import RisLawPreview, {
  AknElementClickEvent,
} from "@/components/RisLawPreview.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"

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
  @apply block border border-dotted border-blue-800 bg-highlight-mod-1-default px-2;
}

:deep(.akn-mod.selected) {
  @apply block bg-highlight-mod-1-selected;
}

:deep(.akn-mod):hover {
  @apply block bg-highlight-mod-1-hover;
}
</style>
