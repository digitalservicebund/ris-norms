<script setup lang="ts">
import RisErrorToast from "@/components/RisErrorToast.vue"
import RisNavbar from "@/components/RisNavbar.vue"
import Toast from "primevue/toast"
import { RouterView } from "vue-router"
</script>

<template>
  <Toast />
  <RisErrorToast />

  <RisNavbar class="fixed inset-x-0 top-0 z-10" />

  <div class="pt-80">
    <RouterView />
  </div>
</template>
