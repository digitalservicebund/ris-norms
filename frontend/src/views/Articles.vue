<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useArticles } from "@/services/articleService"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"

const eli = useEliPathParameter()
const { data: articles, isFetching, error } = useArticles(eli)
</script>

<template>
  <div class="p-40">
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <div v-if="isFetching" class="flex items-center justify-center">
      <RisLoadingSpinner />
    </div>
    <div v-else-if="error">
      <RisCallout
        title="Die Liste der Artikel konnte nicht geladen werden."
        variant="error"
      />
    </div>
    <RisInfoModal
      v-for="article in articles"
      v-else
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
