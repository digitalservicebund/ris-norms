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
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Vorgang",
    route: { name: "AmendingLaw" },
    children: [
      {
        label: "Zeitgrenzen anlegen",
        route: { name: "TemporalData" },
      },
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
  <div
    v-if="amendingLaw"
    class="grid h-[calc(100dvh-5rem)] grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] overflow-hidden bg-gray-100"
  >
    <RisAmendingLawInfoHeader :amending-law="amendingLaw" class="col-span-2" />

    <RisNavbarSide
      class="col-span-1 w-full overflow-auto border-r border-gray-400 bg-white"
      go-back-label="Zurück"
      :go-back-route="{ name: 'Home' }"
      :menu-items="menuItems"
    />

    <div class="col-span-1 overflow-auto">
      <RisAlert
        v-for="[key, { variant, message }] in alerts"
        :key="key"
        :variant="variant"
        @click="hideAlert(key)"
      >
        {{ message }}
      </RisAlert>
      <RouterView />
    </div>
  </div>
  <RisLoadingSpinner v-else />
</template>
