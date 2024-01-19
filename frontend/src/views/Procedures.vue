<script setup lang="ts">
import { ref } from "vue"
import ProcedureCard from "@/components/ProcedureCard.vue"

const procedures = ref([
  {
    eli: "eli/bund/bgbl-1/1964/s593/regelungstext-1",
    printAnnouncementGazette: "BGBl I",
    printAnnouncementYear: 1964,
    printAnnouncementNumber: 7,
    printAnnouncementPage: 593,
    publicationDate: new Date("2023-03-12"),
  },
  {
    eli: "eli/bund/bgbl-1/1982/s22/regelungstext-1",
    printAnnouncementGazette: "BGBl II",
    printAnnouncementYear: 1982,
    printAnnouncementNumber: 3,
    printAnnouncementPage: 22,
    publicationDate: new Date("2024-01-01"),
  },
])
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-40">
    <h1 class="ds-heading-02-reg mb-40">Vorgänge</h1>
    <div class="flex flex-col gap-8">
      <ProcedureCard
        v-for="procedure in procedures"
        :key="procedure.eli"
        :eli="procedure.eli"
        :print-announcement-gazette="procedure.printAnnouncementGazette"
        :print-announcement-number="procedure.printAnnouncementNumber"
        :print-announcement-page="procedure.printAnnouncementPage"
        :print-announcement-year="procedure.printAnnouncementYear"
        :publication-date="procedure.publicationDate"
      />
    </div>
  </div>
</template>
