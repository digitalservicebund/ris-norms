<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"

import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { storeToRefs } from "pinia"
import { useRouter } from "vue-router"

const router = useRouter()

const amendingLawsStore = useAmendingLawsStore()
const { loadedAmendingLaw } = storeToRefs(amendingLawsStore)

const ARTICLE_EID = "hauptteil-1_art-1"
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="(article, index) in loadedAmendingLaw?.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      :href="
        router.resolve({
          name: 'AmendingLawArticleEditor',
          params: { eid: ARTICLE_EID },
        }).href
      "
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
