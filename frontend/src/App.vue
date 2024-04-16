<script setup lang="ts">
import { RouterView } from "vue-router"
import RisNavbar from "@/components/controls/RisNavbar.vue"
</script>

<template>
  <RisNavbar class="fixed inset-x-0 top-0" />

  <div class="pt-80">
    <RouterView />
  </div>
</template>
