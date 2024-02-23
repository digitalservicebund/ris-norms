<script setup lang="ts">
import { RouterLink } from "vue-router"
import RisAmendingLawCard from "@/components/amendinglaws/RisAmendingLawCard.vue"
import { AmendingLaw, getAmendingLaws } from "@/services/amendingLawsService"
import { onMounted, ref } from "vue"

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
        :to="`/amendinglaws/${amendingLaw.eli}`"
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
