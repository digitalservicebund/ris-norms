<script setup lang="ts">
import RisNavbar from "@/components/RisNavbar.vue"
import RisToast from "@/components/RisToast.vue"
import { RouterView } from "vue-router"
import { inject } from "vue"
import { envInjectionKey } from "@/services/envService"

const env = inject(envInjectionKey)!
</script>

<template>
  <RisToast />

  <RisNavbar class="fixed inset-x-0 top-0 z-10" />

  <div class="pt-80">
    <RouterView />
  </div>

  <footer class="fixed right-0 bottom-0 text-xs text-gray-700">
    {{ env.commit }}
  </footer>
</template>
d
