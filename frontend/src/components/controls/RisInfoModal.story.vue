<script lang="ts" setup>
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant>
      <RisInfoModal
        key="eid"
        title="Artikel 1"
        description="Änderung des Bundesverfassungsschutzgesetzes"
        icon-text="Änderungsbefehl prüfen"
      />
    </Variant>
  </Story>
</template>
