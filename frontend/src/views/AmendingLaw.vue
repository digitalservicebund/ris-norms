<script lang="ts" setup>
import RisAlert from "@/components/controls/RisAlert.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import { useAlerts } from "@/composables/useAlerts"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { ref } from "vue"
import { RouterView } from "vue-router"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Verkündung",
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
        label: "Referenzen Prototype",
        route: { name: "Prototype" },
      },
      {
        label: "Abgabe",
        route: { name: "AmendingLawPublishing" },
      },
    ],
  },
]

const eli = useEliPathParameter()
const { data: amendingLaw, isFetching, error } = useGetNorm(eli)

const { alerts, hideAlert } = useAlerts()

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? getFrbrDisplayText(amendingLaw.value) ?? "..."
        : "...",
    to: `/amending-laws/${eli.value}`,
  },
])
</script>

<template>
  <div
    v-if="isFetching"
    class="flex h-[calc(100dvh-5rem)] items-center justify-center"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="error || !amendingLaw">
    <RisCallout
      title="Das Änderungsgesetz konnte nicht geladen werden."
      variant="error"
    />
  </div>

  <div
    v-else
    class="grid h-[calc(100dvh-5rem)] grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] overflow-hidden bg-gray-100"
  >
    <RisHeader
      class="col-span-2"
      :back-destination="{ name: 'Home' }"
      :breadcrumbs
    >
      <RisNavbarSide
        class="col-span-1 w-full overflow-auto border-r border-gray-400 bg-white"
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
    </RisHeader>
  </div>
</template>
