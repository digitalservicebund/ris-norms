<script setup lang="ts">
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useArticles } from "@/services/articleService"
import { onUnmounted, watch } from "vue"
import { useRouter } from "vue-router"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const eli = useEliPathParameter()
const { data: articles, isFetching, error } = useArticles(eli)

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Artikelübersicht" })
onUnmounted(() => cleanupBreadcrumbs())

const router = useRouter()

watch(
  () => error?.value,
  (err) => {
    if (err?.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)
</script>

<template>
  <div class="p-24">
    <h1 class="ris-heading2-regular mb-24">Enthaltene Artikel</h1>
    <div v-if="isFetching" class="flex items-center justify-center">
      <RisLoadingSpinner />
    </div>
    <div v-else-if="error && error.status !== 404">
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
