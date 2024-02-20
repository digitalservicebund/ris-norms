<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useRoute } from "vue-router"
import { computed } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"

const route = useRoute()
const eli = computed(() => decodeURIComponent(route.params.id?.toString()))
const amendingLawsStore = useAmendingLawsStore()
const amendingLaw = amendingLawsStore.loadAmendingLawByEli(eli)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="(article, index) in amendingLaw.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
