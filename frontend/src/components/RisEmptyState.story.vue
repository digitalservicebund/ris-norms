<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
const textContent =
  "Wählen sie eine Zeitgrenze, um eine Vorschau des konsolidierten Änderungsbefehls zu sehen"
</script>

<template>
  <Story>
    <Variant title="Simple">
      <RisEmptyState id="risEmptyState" :text-content="textContent" />
    </Variant>
    <Variant title="Extended">
      <RisEmptyState
        id="risEmptyState"
        variant="extended"
        text-content="Keine Verkündungen vorhanden"
      />
    </Variant>
    <Variant title="Extended with Link">
      <RisEmptyState
        id="risEmptyState"
        variant="extended"
        text-content="Keine Verkündungen vorhanden"
        link-text="Neue Verkündungen hinzufügen"
      />
    </Variant>
  </Story>
</template>
