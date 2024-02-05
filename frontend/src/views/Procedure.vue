<script lang="ts" setup>
import { RouterView, useRoute } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisUnitInfoPanel from "@/components/controls/RisUnitInfoPanel.vue"
import { useProceduresStore } from "@/store/loadProcedureStore"
import { computed, onMounted } from "vue"

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

const proceduresStore = useProceduresStore()
const procedure = computed(() => proceduresStore.getProcedureByEli(eli.value))

onMounted(() => {
  proceduresStore.loadProcedures()
})

const heading = computed(() => {
  if (procedure.value) {
    return `${procedure.value.printAnnouncementGazette} ${procedure.value.printAnnouncementYear} Nr. ${procedure.value.printAnnouncementNumber}`
  }
  return ""
})

const propertyInfos = computed(() => {
  if (procedure.value) {
    return [
      {
        label: "FNA",
        value: procedure.value.fna,
      },
    ]
  }
  return []
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100">
    <RisUnitInfoPanel :heading="heading" :property-infos="propertyInfos" />
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
