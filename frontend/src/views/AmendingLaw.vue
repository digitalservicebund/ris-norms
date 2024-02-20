<script lang="ts" setup>
import { RouterView, useRoute } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisUnitInfoPanel from "@/components/controls/RisUnitInfoPanel.vue"
import { computed } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Vorgang",
    route: { name: "ProcedureArticleOverview" },
    children: [
      {
        label: "Artikelübersicht",
        route: { name: "ProcedureArticleOverview" },
      },
      {
        label: "Betroffene Normenkomplexe",
        route: { name: "ProcedureAffectedStandards" },
      },
    ],
  },
]

const route = useRoute()
const eli = computed(() => decodeURIComponent(route.params.id?.toString()))

const amendingLawsStore = useAmendingLawsStore()
const amendingLaw = amendingLawsStore.loadAmendingLawByEli(eli)

const heading = computed(() => {
  return amendingLaw?.printAnnouncementGazette
    ? `${amendingLaw.printAnnouncementGazette.toUpperCase()} Nr. ${amendingLaw.printAnnouncementPage}`
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
