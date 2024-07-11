<script setup lang="ts">
import { ref } from "vue"
import RisNavbarSide from "./RisNavbarSide.vue"
import { createRouter, createWebHistory, Router } from "vue-router"
import type { App } from "vue"

const PlaceholderComponent = { template: "<div>Placeholder Content</div>" }

const routes = [
  {
    path: "/",
    name: "home",
    component: PlaceholderComponent,
  },
  {
    path: "/amending-laws",
    component: PlaceholderComponent,
    children: [
      { path: "", name: "AmendingLaw", component: PlaceholderComponent },
      {
        path: "temporal-data",
        name: "TemporalData",
        component: PlaceholderComponent,
      },
      {
        path: "affected-documents",
        name: "AmendingLawAffectedDocuments",
        component: PlaceholderComponent,
      },
      {
        path: "articles",
        name: "AmendingLawArticles",
        component: PlaceholderComponent,
      },
      {
        path: "publishing",
        name: "AmendingLawPublishing",
        component: PlaceholderComponent,
      },
    ],
  },
]

function setupApp({ app }: { app: App }) {
  const router: Router = createRouter({
    history: createWebHistory(),
    routes,
  })

  app.use(router)
}

const menuItems = ref([
  {
    label: "Verkündung",
    route: { name: "AmendingLaw" },
    children: [
      { label: "Zeitgrenzen anlegen", route: { name: "TemporalData" } },
      { label: "Artikelübersicht", route: { name: "AmendingLawArticles" } },
      {
        label: "Betroffene Normenkomplexe",
        route: { name: "AmendingLawAffectedDocuments" },
      },
      { label: "Abgabe", route: { name: "AmendingLawPublishing" } },
    ],
  },
])
</script>

<template>
  <Story title="RisNavbarSide" :setup-app="setupApp">
    <Variant title="Default">
      <RisNavbarSide
        :menu-items="menuItems"
        go-back-label="Go Back"
        :go-back-route="{ name: 'home' }"
      />
    </Variant>
  </Story>
</template>
