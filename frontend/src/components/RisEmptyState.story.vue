<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"

const textContent =
  "Wählen sie eine Zeitgrenze, um eine Vorschau des konsolidierten Änderungsbefehls zu sehen"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Simple">
      <RisEmptyState id="risEmptyState" :text-content="textContent" />
    </Variant>
    <Variant title="Extended variant with recommended action">
      <RisEmptyState
        id="risEmptyState"
        variant="extended"
        text-content="Keine Verkündungen vorhanden"
        recommended-action="Neue Verkündungen hinzufügen"
        :icon="UploadFileOutlineRounded"
      >
        <template #recommended-action>
          <p class="ds-link-01-bold items-center text-blue-800" :to="'/upload'">
            <span>Neue Verkündungen hinzufügen</span>
          </p>
        </template>
      </RisEmptyState>
    </Variant>
    <Variant title="Extended variant with no recommended action and no icon">
      <RisEmptyState
        id="risEmptyState"
        variant="extended"
        text-content="Keine Verkündungen vorhanden"
        recommended-action="Neue Verkündungen hinzufügen"
      >
      </RisEmptyState>
    </Variant>
  </Story>
</template>
