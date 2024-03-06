<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useArticles } from "@/composables/useArticles"
import { useEliPathParameter } from "@/composables/useEliPathParameter"

const eli = useEliPathParameter()
const articles = useArticles(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="article in articles"
      :key="article.eid"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      :to="{
        name: 'AmendingLawArticleEditor',
        params: { eid: article.eid },
      }"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
