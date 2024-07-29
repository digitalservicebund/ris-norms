<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import CloseIcon from "~icons/ic/close"

/**
 * The XML-String (LDML.de) of the akn:ref element.
 */
defineModel<string>("xmlSnippet")

defineEmits<{
  /**
   * The akn:ref element should be removed.
   */
  delete: []
}>()
</script>

<template>
  <div>Typ Select</div>
  <div>ELI Input</div>
  <RisTextButton
    size="small"
    variant="ghost"
    label="Löschen"
    icon-only
    :icon="CloseIcon"
    @click="$emit('delete')"
  />
</template>
