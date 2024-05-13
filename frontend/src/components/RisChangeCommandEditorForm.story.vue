<script setup lang="ts">
import RisChangeCommandEditorForm from "@/components/RisChangeCommandEditorForm.vue"

const commandType = "replacement"
const timeBoundaries = [
  { label: "31.12.2024", value: "2024-12-31" },
  { label: "01.01.2025", value: "2025-01-01" },
  { label: "15.06.2026", value: "2026-06-15" },
]
const eli = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1"
const replaceWith = "para-1_abs-1/5-53.xml"
const textToBeReplaced = "Bundesministerium des Innern, für Bau und Heimat"
const textThatReplaces = "Bundesministerium des Innern und für Heimat"
</script>

<template>
  <Story>
    <RisChangeCommandEditorForm
      id="ris-change-command-editor-form"
      :command-type="commandType"
      :time-boundaries="timeBoundaries"
      :eli="eli"
      :replace-with="replaceWith"
      :text-to-be-replaced="textToBeReplaced"
      :text-that-replaces="textThatReplaces"
    ></RisChangeCommandEditorForm>
  </Story>
</template>
