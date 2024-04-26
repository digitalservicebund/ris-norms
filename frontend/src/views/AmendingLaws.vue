<script setup lang="ts">
import RisAmendingLawCard from "@/components/amendingLaws/RisAmendingLawCard.vue"
import { getAmendingLaws } from "@/services/announcementService"
import { Norm } from "@/types/norm"
import { onMounted, ref } from "vue"
import { RouterLink } from "vue-router"

const amendingLaws = ref<Norm[]>([])

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
          :frbr-name="amendingLaw.frbrName"
          :frbr-number="amendingLaw.frbrNumber"
          :frbr-date-verkuendung="amendingLaw.frbrDateVerkuendung"
        />
      </RouterLink>
    </div>
  </div>
</template>
