<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"

import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { storeToRefs } from "pinia"

const amendingLawsStore = useAmendingLawsStore()
const { loadedAmendingLaw } = storeToRefs(amendingLawsStore)
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="(article, index) in loadedAmendingLaw?.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
