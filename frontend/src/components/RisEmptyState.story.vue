<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
const textContent =
  "Wählen sie eine Zeitgrenze, um eine Vorschau des konsolidierten Änderungsbefehls zu sehen"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
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
    <Variant title="Extended with recommended action">
      <RisEmptyState
        id="risEmptyState"
        variant="extended"
        text-content="Keine Verkündungen vorhanden"
        recommended-action="Neue Verkündungen hinzufügen"
      />
    </Variant>
  </Story>
</template>
