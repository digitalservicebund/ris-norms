<script setup lang="ts">
import RisModForm from "@/components/RisModForm.vue"
import { ModType } from "@/types/ModType"

const textualModType: ModType = "aenderungsbefehl-ersetzen"
const timeBoundaries = [
  { label: "31.12.2024", value: "2024-12-31" },
  { label: "01.01.2025", value: "2025-01-01" },
  { label: "15.06.2026", value: "2026-06-15" },
]
const destinationHref =
  "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/para-1_abs-1/5-53.xml"
const quotedTextFirst = "Bundesministerium des Innern, für Bau und Heimat"
const quotedTextSecond = "Bundesministerium des Innern und für Heimat"
</script>

<template>
  <Story>
    <RisModForm
      id="ris-change-command-editor-form"
      :textual-mod-type="textualModType"
      :time-boundaries="timeBoundaries"
      :destination-href="destinationHref"
      :quoted-text-first="quotedTextFirst"
      :quoted-text-second="quotedTextSecond"
    />
  </Story>
</template>
