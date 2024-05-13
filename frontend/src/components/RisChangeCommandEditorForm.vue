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
  <form :id="id">
    <RisTextInput
      id="TBD1"
      label="Änderungstyp"
      model-value="ersetzen"
      read-only
    />
    <RisDropdownInput
      id="TBD2"
      label="Zeitgrenze"
      :items="timeBoundaries"
      placeholder="Keine Angabe"
    />
    <RisTextInput
      id="TBD3"
      label="ELI Zielgesetz"
      :model-value="eli"
      read-only
    />
    <RisTextInput
      v-if="commandType == 'replacement'"
      id="TBD4"
      label="zu ersetzende Textstelle"
      :model-value="replaceWith"
    />
    <RisTextAreaInput
      v-if="commandType == 'replacement'"
      id="TBD5"
      label="zu ersetzender Text"
      :model-value="textToBeReplaced"
      read-only
    />
    <RisTextAreaInput
      id="TBD6"
      label="Neuer Text Inhalt"
      :model-value="textThatReplaces"
    />
    <div>
      <RisTextButton label="Vorschau" variant="tertiary" />
      <RisTextButton label="Speichern" />
    </div>
  </form>
</template>
