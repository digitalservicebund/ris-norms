<script lang="ts" setup>
import { ref } from "vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "uploadAnnouncement",
    title: "Verkündung manuell hinzufügen",
  },
])
</script>

<template>
  <RisHeader
    class="col-span-2"
    :back-destination="{ name: 'Home' }"
    :breadcrumbs="breadcrumbs"
  />

  <div class="flex min-h-screen flex-col bg-gray-100 p-24">
    <h1 class="ds-heading-02-reg mb-8">Verkündung Manuell Hinzufügen</h1>
    <div class="mt-64 flex flex-col items-center">
      <span class="ds-body-01-reg"
        >Importieren Sie eine XML-Datei, um die Verkündung hinzuzufügen</span
      >
      <div
        class="min-w-sm mt-24 w-full max-w-xl rounded-lg border-2 border-dashed border-blue-500 bg-white p-12 text-center"
      >
        <div></div>
      </div>
    </div>
  </div>
</template>
