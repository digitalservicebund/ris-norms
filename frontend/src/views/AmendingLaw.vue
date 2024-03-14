<script lang="ts" setup>
import { RouterView } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import { useAlerts } from "@/composables/useAlerts"
import RisAlert from "@/components/controls/RisAlert.vue"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Vorgang",
    route: { name: "AmendingLaw" },
    children: [
      {
        label: "Artikelübersicht",
        route: { name: "AmendingLawArticles" },
      },
      {
        label: "Betroffene Normenkomplexe",
        route: { name: "AmendingLawAffectedDocuments" },
      },
      {
        label: "Abgabe",
        route: { name: "AmendingLawPublishing" },
      },
    ],
  },
]

const eli = useEliPathParameter()
const amendingLaw = useAmendingLaw(eli)

const { alerts, hideAlert } = useAlerts()
</script>

<template>
  <div v-if="amendingLaw" class="flex min-h-screen flex-col bg-gray-100">
    <RisAmendingLawInfoHeader :amending-law="amendingLaw" />
    <div class="flex">
      <RisNavbarSide
        class="min-h-screen flex-none border-r border-gray-400 bg-white"
        go-back-label="Zurück"
        :go-back-route="{ name: 'Home' }"
        :menu-items="menuItems"
      />
      <div class="w-full flex-1">
        <RisAlert
          v-for="[key, { variant, message }] in alerts"
          :key="key"
          :variant="variant"
          @click="hideAlert(key)"
        >
          {{ message }}
        </RisAlert>
        <div class="w-full p-40">
          <RouterView />
        </div>
      </div>
    </div>
  </div>
  <div v-else>Laden...</div>
</template>
