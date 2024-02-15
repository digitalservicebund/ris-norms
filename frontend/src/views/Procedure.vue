<script lang="ts" setup>
import { RouterView, useRoute } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisUnitInfoPanel from "@/components/controls/RisUnitInfoPanel.vue"
import { computed } from "vue"
import { useProcedure } from "@/composables/useProcedure"

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
const { procedure } = useProcedure(eli)

const heading = computed(() => {
  if (procedure.value) {
    return `${procedure.value?.printAnnouncementGazette.toUpperCase()} ${procedure.value.printAnnouncementYear} Nr. ${procedure.value.printAnnouncementPage}`
  }
  return ""
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
