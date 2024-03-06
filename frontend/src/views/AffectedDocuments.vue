<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useArticles } from "@/composables/useArticles"
import { useEliPathParameter } from "@/composables/useEliPathParameter"

const eli = useEliPathParameter()
const articles = useArticles(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Betroffene Normenkomplexe</h1>

    <span class="ds-body-01-reg">
      Durch das Änderungsgesetz ändern sich folgende Normenkomplexe und Artikel:
    </span>

    <RisInfoModal
      v-for="article in articles"
      :key="article.eid"
      :title="`Artikel ${article.enumeration}`"
      :description="article.eid"
      icon-text="Metadaten editieren"
    />
  </div>
</template>
