<script setup lang="ts">
import neurisLogo from "@/assets/neuRIS-logo.svg"
import { useAuthentication } from "@/lib/auth"
import { RouterLink } from "vue-router"
import UseOutline from "~icons/ic/baseline-person-outline"

const { getUsername, getLogoutLink } = useAuthentication()
const logoutLink = getLogoutLink()
</script>

<template>
  <nav
    class="flex h-80 items-center justify-between border-b border-gray-400 bg-white px-16"
    aria-labelledby="main-nav-label"
  >
    <span id="main-nav-label" class="sr-only">Hauptnavigation</span>
    <RouterLink :to="{ name: 'Home' }" class="flex gap-16">
      <img alt="" :src="neurisLogo" />
      <span>
        <span class="ris-label3-bold block">Rechtsinformationen</span>
        <span class="ris-label3-regular block">des Bundes</span>
      </span>
    </RouterLink>
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
