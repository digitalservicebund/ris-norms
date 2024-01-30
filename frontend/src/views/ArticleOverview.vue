<script setup lang="ts">
import RisInfoModal from "@/components/RisInfoModal.vue"
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      title="Artikel 1"
      description="Änderung des Filmförderungsgesetzes
  geändertes Gesetz Vereinsgesetz "
      icon-text="Änderungsbefehl prüfen"
    />

    <RisInfoModal
      title="Artikel 2"
      description="Inkrafttreten"
      icon-text="Zeitgrenzen prüfen"
    />
  </div>
</template>
