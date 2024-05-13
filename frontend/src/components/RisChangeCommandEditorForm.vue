<script setup lang="ts">
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { computed } from "vue"
import RisTextAreaInput from "@/components/controls/RisTextAreaInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"

const props = defineProps<{
  /** Unique ID for the dro. */
  id: string
  /** Either replacement, insertion or repeal */
  commandType: string
  /** the items for the dropdown. */
  timeBoundaries: DropdownItem[]
  /**  */
  replaceWith: string
  /** */
  eli: string
  /** */
  textToBeReplaced: string
  /** */
  textThatReplaces: string
}>()

const timeBoundaries = computed(() => props.timeBoundaries)
</script>

<template>
  <form :id="id" class="grid grid-cols-1 gap-y-20">
    <div class="grid grid-cols-3 gap-x-80">
      <RisTextInput
        id="TBD1"
        label="Änderungstyp"
        model-value="ersetzen"
        read-only
        size="small"
      />
      <RisDropdownInput
        id="TBD2"
        label="Zeitgrenze"
        :items="timeBoundaries"
        placeholder="Keine Angabe"
      />
    </div>

    <RisTextInput
      id="TBD3"
      label="ELI Zielgesetz"
      :model-value="eli"
      read-only
      size="small"
    />
    <RisTextInput
      v-if="commandType == 'replacement'"
      id="TBD4"
      label="zu ersetzende Textstelle"
      :model-value="replaceWith"
      size="small"
    />
    <RisTextAreaInput
      v-if="commandType == 'replacement'"
      id="TBD5"
      label="zu ersetzender Text"
      :model-value="textToBeReplaced"
      read-only
      :rows="8"
    />
    <RisTextAreaInput
      id="TBD6"
      label="Neuer Text Inhalt"
      :model-value="textThatReplaces"
      :rows="8"
    />
    <div class="flex gap-20">
      <RisTextButton label="Vorschau" variant="tertiary" />
      <RisTextButton label="Speichern" />
    </div>
  </form>
</template>
