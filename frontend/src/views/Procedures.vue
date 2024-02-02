<script setup lang="ts">
import { RouterLink } from "vue-router"
import RisProcedureCard from "@/components/RisProcedureCard.vue"
import { computed, onMounted } from "vue"
import { useProceduresStore } from "@/store/loadProcedureStore"

const proceduresStore = useProceduresStore()
const procedures = computed(() => proceduresStore.procedures)

onMounted(() => {
  proceduresStore.loadProcedures()
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-40">
    <h1 class="ds-heading-02-reg mb-40">Vorgänge</h1>
    <div class="flex flex-col gap-8">
      <RouterLink
        v-for="procedure in procedures"
        :key="procedure.eli"
        :to="`/procedures/${procedure.eli}`"
        class="block"
      >
        <RisProcedureCard
          :key="procedure.eli"
          :eli="procedure.eli"
          :print-announcement-gazette="procedure.printAnnouncementGazette"
          :print-announcement-number="procedure.printAnnouncementNumber"
          :print-announcement-page="procedure.printAnnouncementPage"
          :print-announcement-year="procedure.printAnnouncementYear"
          :publication-date="procedure.publicationDate"
        />
      </RouterLink>
    </div>
  </div>
</template>
