<script setup lang="ts">
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useArticles } from "@/services/articleService"
import { onUnmounted } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const eli = useEliPathParameter()
const { data: articles, isFetching, error } = useArticles(eli)

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Artikelübersicht" })
onUnmounted(() => cleanupBreadcrumbs())
</script>

<template>
  <div class="p-24">
    <h1 class="ds-heading-02-reg mb-24">Enthaltene Artikel</h1>
    <div v-if="isFetching" class="flex items-center justify-center">
      <RisLoadingSpinner />
    </div>
    <div v-else-if="error">
      <RisErrorCallout
        title="Die Liste der Artikel konnte nicht geladen werden."
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
