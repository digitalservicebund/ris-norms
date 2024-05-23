<script setup lang="ts">
import RisModForm from "@/components/RisModForm.vue"
import { ModType } from "@/types/ModType"

const textualModType: ModType = "aenderungsbefehl-ersetzen"
const timeBoundaries = [
  { date: "2024-12-31", eventRefEid: "event-1" },
  { date: "2025-01-01", eventRefEid: "event-2" },
  { date: "2026-06-15", eventRefEid: "event-3" },
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
