<script setup lang="ts">
import RisAmendingLawCard from "@/components/amendingLaws/RisAmendingLawCard.vue"
import { getAmendingLaws } from "@/services/amendingLawsService"
import { AmendingLaw } from "@/types/amendingLaw"
import { onMounted, ref } from "vue"
import { RouterLink } from "vue-router"

const amendingLaws = ref<AmendingLaw[]>([])

onMounted(async () => {
  amendingLaws.value = await getAmendingLaws()
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-40">
    <h1 class="ds-heading-02-reg mb-40">Vorgänge</h1>
    <div class="flex flex-col gap-8">
      <RouterLink
        v-for="amendingLaw in amendingLaws"
        :key="amendingLaw.eli"
        :to="`/amending-laws/${amendingLaw.eli}`"
        class="block"
      >
        <RisAmendingLawCard
          :key="amendingLaw.eli"
          :eli="amendingLaw.eli"
          :print-announcement-gazette="amendingLaw.printAnnouncementGazette"
          :print-announcement-page="amendingLaw.printAnnouncementPage"
          :digital-announcement-medium="amendingLaw.digitalAnnouncementMedium"
          :digital-announcement-edition="amendingLaw.digitalAnnouncementEdition"
          :publication-date="amendingLaw.publicationDate"
        />
      </RouterLink>
    </div>
  </div>
</template>
