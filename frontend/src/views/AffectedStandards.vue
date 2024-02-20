<script setup lang="ts">
import { computed } from "vue"
import { useRoute } from "vue-router"
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"

const route = useRoute()
const eli = computed(() =>
  decodeURIComponent(route.params.id?.toString() || ""),
)
const amendingLawsStore = useAmendingLawsStore()
const amendingLaw = amendingLawsStore.loadAmendingLawByEli(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Betroffene Normenkomplexe</h1>
    <span class="ds-body-01-reg">
      Durch das Änderungsgesetz ändern sich folgende Normenkomplexe und Artikel:
    </span>
    <RisInfoModal
      v-for="(article, index) in amendingLaw.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.eli"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
