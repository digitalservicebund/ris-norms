<script setup lang="ts">
import RisAmendingLawCard from "@/components/amendingLaws/RisAmendingLawCard.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useGetAmendingLaws } from "@/services/announcementService"
import { RouterLink } from "vue-router"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import Button from "primevue/button"

const { isFetching, error, data: amendingLaws } = useGetAmendingLaws()
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-24">
    <div class="mb-24 flex items-center justify-between">
      <h1 class="ris-heading2-regular">Verkündungen</h1>
      <RouterLink :to="{ name: 'UploadAnnouncement' }">
        <Button severity="primary" label="Verkündung manuell hinzufügen" />
      </RouterLink>
    </div>
    <div>
      <div v-if="error" class="w-1/2">
        <RisErrorCallout :error />
      </div>
      <RisLoadingSpinner v-if="isFetching" />
      <div v-else class="flex flex-col gap-8">
        <RouterLink
          v-for="amendingLaw in amendingLaws"
          :key="amendingLaw.eli"
          :to="`/amending-laws/${amendingLaw.eli}`"
          class="block"
        >
          <RisAmendingLawCard
            :key="amendingLaw.eli"
            :eli="amendingLaw.eli"
            :frbr-name="amendingLaw.frbrName"
            :frbr-number="amendingLaw.frbrNumber"
            :frbr-date-verkuendung="amendingLaw.frbrDateVerkuendung"
          />
        </RouterLink>
      </div>
    </div>
  </div>
</template>
