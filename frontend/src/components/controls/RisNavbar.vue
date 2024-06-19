<script setup lang="ts">
import neurisLogo from "@/assets/neuRIS-logo.svg"
import { RouterLink } from "vue-router"
</script>

<template>
  <nav
    class="flex h-80 items-center justify-between border-b border-gray-400 bg-white px-16"
  >
    <RouterLink :to="{ name: 'Home' }" class="flex gap-16">
      <img alt="" :src="neurisLogo" />
      <span>
        <span class="ds-label-03-bold block">Rechtsinformationen</span>
        <span class="ds-label-03-reg block">des Bundes</span>
      </span>
    </RouterLink>
  </nav>
</template>
