<script setup lang="ts">
import RisNavbar from "@/components/RisNavbar.vue"
import RisToast from "@/components/RisToast.vue"
import { RouterView } from "vue-router"
</script>

<template>
  <RisToast />

  <RisNavbar class="fixed inset-x-0 top-0 z-10" />

  <div class="pt-80">
    <RouterView />
  </div>
</template>
