<script setup lang="ts">
import RisNavbar from "@/components/controls/RisNavbar.vue"
import Toast from "primevue/toast"
import { RouterView } from "vue-router"
</script>

<template>
  <Toast />

  <RisNavbar class="fixed inset-x-0 top-0 z-10" />

  <div class="pt-80">
    <RouterView />
  </div>
</template>
