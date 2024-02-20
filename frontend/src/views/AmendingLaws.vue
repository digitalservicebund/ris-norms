<script setup lang="ts">
import { RouterLink } from "vue-router"
import { storeToRefs } from "pinia"
import RisAmendingLawCard from "@/components/amendinglaws/RisAmendingLawCard.vue"
import { onMounted } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"

const amendingLawsStore = useAmendingLawsStore()
const { amendingLaws } = storeToRefs(amendingLawsStore)

onMounted(async () => {
  try {
    await amendingLawsStore.loadAmendingLaws()
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
        v-for="amendingLaw in amendingLaws"
        :key="amendingLaw.eli"
        :to="`/procedures/${encodeURIComponent(amendingLaw.eli)}`"
        class="block"
      >
        <RisAmendingLawCard
          :key="amendingLaw.eli"
          :eli="amendingLaw.eli"
          :print-announcement-gazette="amendingLaw.printAnnouncementGazette"
          :print-announcement-page="amendingLaw.printAnnouncementPage"
          :publication-date="amendingLaw.publicationDate"
        />
      </RouterLink>
    </div>
  </div>
</template>
