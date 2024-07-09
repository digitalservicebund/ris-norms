<script setup lang="ts">
import RisEmptyState from "./RisEmptyState.vue"
import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"

const textContent =
  "Wählen sie eine Zeitgrenze, um eine Vorschau des konsolidierten Änderungsbefehls zu sehen"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Simple">
      <RisEmptyState :text-content="textContent" />
    </Variant>

    <Variant title="Extended variant with recommended action">
      <RisEmptyState
        text-content="Keine Verkündungen vorhanden"
        :icon="UploadFileOutlineRounded"
      >
        <template #recommended-action>
          <a class="ds-link-01-bold text-blue-800" href="#">
            <span>Neue Verkündungen hinzufügen</span>
          </a>
        </template>
      </RisEmptyState>
    </Variant>
  </Story>
</template>
