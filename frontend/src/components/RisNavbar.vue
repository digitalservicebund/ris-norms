<script setup lang="ts">
import neurisLogo from "@/assets/neuRIS-logo.svg"
import { useAuthentication } from "@/lib/auth"
import { RouterLink, useRoute } from "vue-router"
import UseOutline from "~icons/ic/baseline-person-outline"
import { computed } from "vue"

const { getUsername, getLogoutLink } = useAuthentication()
const logoutLink = getLogoutLink()
const route = useRoute()

const activeTab = computed(() => {
  if (route.path.startsWith("/verkuendungen")) {
    return "verkuendungen"
  } else if (route.path.startsWith("/datenbank")) {
    return "datenbank"
  }

  return "verkuendungen"
})

const tabs = [
  {
    id: "verkuendungen",
    label: "Verkündungen",
    route: { name: "Verkuendungen" },
  },
  { id: "datenbank", label: "Datenbank", route: { name: "Datenbank" } },
]
</script>

<template>
  <nav
    class="flex h-80 items-center justify-between border-b border-gray-400 bg-white px-16"
    aria-labelledby="main-nav-label"
  >
    <span id="main-nav-label" class="sr-only">Hauptnavigation</span>
    <div class="flex items-center gap-48">
      <RouterLink :to="{ name: 'Home' }" class="flex gap-16">
        <img alt="" :src="neurisLogo" />
        <span>
          <span class="ris-label3-bold block">Rechtsinformationen</span>
          <span class="ris-label3-regular block">des Bundes</span>
        </span>
      </RouterLink>
      <ul class="flex gap-28">
        <li v-for="tab in tabs" :key="tab.id" class="contents">
          <RouterLink
            :to="tab.route"
            :aria-current="tab.id === activeTab ? 'page' : undefined"
            class="ris-link2-regular no-underline hover:underline hover:underline-offset-2"
            :class="{
              'underline decoration-2 underline-offset-2': tab.id === activeTab,
            }"
          >
            {{ tab.label }}
          </RouterLink>
        </li>
      </ul>
    </div>

    <div class="flex items-start gap-8 px-16">
      <UseOutline />
      <div class="flex flex-col">
        <span class="ris-label2-regular">
          {{ getUsername() ?? "Unbekannt" }}
        </span>
        <a :href="logoutLink" class="ris-link2-regular underline-offset-2">
          Abmelden
        </a>
      </div>
    </div>
  </nav>
</template>
