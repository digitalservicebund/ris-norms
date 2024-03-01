<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"

const eli = useEliPathParameter()
const amendingLaw = useAmendingLaw(eli)

const ARTICLE_EID = "hauptteil-1_art-1"
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="(article, index) in amendingLaw?.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      :to="{
        name: 'AmendingLawArticleEditor',
        params: { eid: ARTICLE_EID },
      }"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
