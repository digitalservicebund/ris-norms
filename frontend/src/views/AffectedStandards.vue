<script setup lang="ts">
import { computed } from "vue"
import { useRoute } from "vue-router"
import RisInfoModal from "@/components/controls/RisInfoModal.vue"

const route = useRoute()
const eli = computed(() =>
  decodeURIComponent(route.params.id?.toString() || ""),
)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Betroffene Normenkomplexe</h1>
    <span class="ds-body-01-reg">
      Durch das Änderungsgesetz ändern sich folgende Normenkomplexe und Artikel:
    </span>
    <RisInfoModal
      title="Vereinsgesetz"
      :description="`${eli}`"
      icon-text="Metadaten editieren"
    />
  </div>
</template>
