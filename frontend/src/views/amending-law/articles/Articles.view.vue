<script setup lang="ts">
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisInfoModal from "@/views/amending-law/articles/RisInfoModal.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useArticles } from "@/services/articleService"
import { onUnmounted } from "vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"

const eli = useDokumentExpressionEliPathParameter()
const { data: articles, isFetching, error } = useArticles(eli)

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Artikelübersicht" })
onUnmounted(() => cleanupBreadcrumbs())
</script>

<template>
  <div class="p-24">
    <h1 class="ris-heading2-regular mb-24">Enthaltene Artikel</h1>
    <div v-if="isFetching" class="flex items-center justify-center">
      <RisLoadingSpinner />
    </div>
    <div v-else-if="error">
      <RisErrorCallout :error />
    </div>
    <RisInfoModal
      v-for="article in articles"
      v-else
      :key="article.eid"
      :title="article.enumeration"
      :description="article.title"
      :to="{
        name: 'AmendingLawArticleEditor',
        params: { eid: article.eid },
      }"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
