<script lang="ts" setup>
import { RouterView } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisUnitInfoPanel from "@/components/controls/RisUnitInfoPanel.vue"
import { computed } from "vue"

/*  this is the first use of the side navigation and so where menuItems is used, 
can we leave these defined here for now inside the CustomLayout component. */
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

// this needs to come from the store
// make sure this matches the procedure from procedures view
const propertyInfos = computed(() => [
  {
    label: "FNA",
    value: "703-13",
  },
])
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100">
    <RisUnitInfoPanel
      heading="BGBl I 1964 Nr. 7"
      :property-infos="propertyInfos"
    />
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
