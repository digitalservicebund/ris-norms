<script setup lang="ts">
import { RouterLink } from "vue-router"
import { storeToRefs } from "pinia"
import RisProcedureCard from "@/components/procedures/RisProcedureCard.vue"
import { onMounted } from "vue"
import { useProceduresStore } from "@/store/loadProcedureStore"

const proceduresStore = useProceduresStore()
const { procedures } = storeToRefs(proceduresStore)

onMounted(async () => {
  try {
    await proceduresStore.loadProcedures()
  } catch (error) {
    //TODO: handle error
  }
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-40">
    <h1 class="ds-heading-02-reg mb-40">Vorgänge</h1>
    <div class="flex flex-col gap-8">
      <RouterLink
        v-for="procedure in procedures"
        :key="procedure.eli"
        :to="`/procedures/${encodeURIComponent(procedure.eli)}`"
        class="block"
      >
        <RisProcedureCard
          :key="procedure.eli"
          :eli="procedure.eli"
          :print-announcement-gazette="procedure.printAnnouncementGazette"
          :print-announcement-page="procedure.printAnnouncementPage"
          :print-announcement-year="procedure.printAnnouncementYear"
        />
      </RouterLink>
    </div>
  </div>
</template>
