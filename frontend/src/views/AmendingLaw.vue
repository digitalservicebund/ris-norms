<script lang="ts" setup>
import { RouterView, useRoute } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisUnitInfoPanel from "@/components/controls/RisUnitInfoPanel.vue"
import { computed, onUnmounted, watchEffect } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { storeToRefs } from "pinia"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Vorgang",
    route: { name: "AmendingLawArticleOverview" },
    children: [
      {
        label: "Artikelübersicht",
        route: { name: "AmendingLawArticleOverview" },
      },
      {
        label: "Betroffene Normenkomplexe",
        route: { name: "AmendingLawAffectedStandards" },
      },
    ],
  },
]

const route = useRoute()
const eli = computed(() => decodeURIComponent(route.params.id?.toString()))

const amendingLawsStore = useAmendingLawsStore()
const { loadedAmendingLaw } = storeToRefs(amendingLawsStore)

watchEffect(() => amendingLawsStore.loadAmendingLawByEli(eli.value))
onUnmounted(() => (loadedAmendingLaw.value = undefined))

const heading = computed(() => {
  return loadedAmendingLaw.value?.printAnnouncementGazette
    ? `${loadedAmendingLaw.value?.printAnnouncementGazette.toUpperCase()} Nr. ${loadedAmendingLaw.value?.printAnnouncementPage}`
    : ""
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100">
    <RisUnitInfoPanel :heading="heading" />
    <div class="flex">
      <RisNavbarSide
        class="min-h-screen flex-none border-r border-gray-400 bg-white"
        go-back-label="Zurück"
        :go-back-route="{ name: 'Home' }"
        :menu-items="menuItems"
      />
      <div class="w-full flex-1 p-48">
        <RouterView />
      </div>
    </div>
  </div>
</template>
