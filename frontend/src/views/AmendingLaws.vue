<script setup lang="ts">
import RisAmendingLawCard from "@/components/amendingLaws/RisAmendingLawCard.vue"
import { useAmendingLaws } from "@/services/announcementService"
import { RouterLink } from "vue-router"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisCallout from "@/components/controls/RisCallout.vue"

const { isFetching, error, data: amendingLaws } = useAmendingLaws()
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100 p-40">
    <h1 class="ds-heading-02-reg mb-40">Vorgänge</h1>
    <div>
      <div v-if="error" class="w-1/2">
        <RisCallout
          title="Die Liste der Verkündungen konnte nicht geladen werden."
          variant="error"
        >
          <p>Lösungsvorschlag</p>
        </RisCallout>
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
