<script setup lang="ts">
import neurisLogo from "@/assets/neuRIS-logo.svg"
import { RouterLink, useRouter } from "vue-router"
import Button from "primevue/button"
import { useLogoutService } from "@/services/logoutService"
import { useErrorToast } from "@/lib/errorToast"

const { addErrorToast } = useErrorToast()
const router = useRouter()

async function handleLogout() {
  const { error } = useLogoutService()

  if (error?.value) {
    addErrorToast(error.value)
  } else {
    await router.push({ name: "Home" })
  }
}
</script>

<template>
  <nav
    class="flex h-80 items-center justify-between border-b border-gray-400 bg-white px-16"
  >
    <RouterLink :to="{ name: 'Home' }" class="flex gap-16">
      <img alt="" :src="neurisLogo" />
      <span>
        <span class="ris-label3-bold block">Rechtsinformationen</span>
        <span class="ris-label3-regular block">des Bundes</span>
      </span>
    </RouterLink>
    <Button label="Logout" severity="primary" @click="handleLogout"></Button>
  </nav>
</template>
