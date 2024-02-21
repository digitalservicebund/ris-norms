<script setup lang="ts">
import RisInfoModal from "@/components/controls/RisInfoModal.vue"
import { useRoute } from "vue-router"
import { computed, onMounted } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { storeToRefs } from "pinia"

const route = useRoute()
const eli = computed(() => decodeURIComponent(route.params.id?.toString()))

const amendingLawsStore = useAmendingLawsStore()
const { selectedAmendingLaw } = storeToRefs(amendingLawsStore)

onMounted(async () => {
  try {
    await amendingLawsStore.loadAmendingLawByEli(eli)
  } catch (error) {
    //TODO: handle error
  }
})
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Enthaltene Artikel</h1>
    <RisInfoModal
      v-for="(article, index) in selectedAmendingLaw.articles"
      :key="index"
      :title="`Artikel ${article.enumeration}`"
      :description="article.title"
      icon-text="Änderungsbefehl prüfen"
    />
  </div>
</template>
