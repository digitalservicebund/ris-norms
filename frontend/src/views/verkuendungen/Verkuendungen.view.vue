<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useAnnouncementsService } from "@/services/announcementService"
import Button from "primevue/button"
import Message from "primevue/message"
import { RouterLink } from "vue-router"
import RisVerkuendungListItem from "./RisVerkuendungListItem.vue"

const { isFetching, error, data: amendingLaws } = useAnnouncementsService()
</script>

<template>
  <RisViewLayout :loading="isFetching" :errors="[error]">
    <header class="mb-24 flex items-center justify-between">
      <h1 class="ris-heading2-regular">Verkündungen</h1>
      <RouterLink :to="{ name: 'VerkuendungUpload' }">
        <Button severity="primary" label="Verkündung manuell hinzufügen" />
      </RouterLink>
    </header>

    <div>
      <div v-if="amendingLaws?.length === 0">
        <Message severity="info">Keine Verkündungen gefunden.</Message>
      </div>

      <div v-else class="flex flex-col gap-8">
        <RouterLink
          v-for="amendingLaw in amendingLaws"
          :key="amendingLaw.eli"
          :to="`/verkuendungen/${amendingLaw.eli}`"
          class="block"
        >
          <RisVerkuendungListItem
            :key="amendingLaw.eli"
            :eli="amendingLaw.eli"
            :frbr-name="amendingLaw.frbrName"
            :frbr-number="amendingLaw.frbrNumber"
            :frbr-date-verkuendung="amendingLaw.frbrDateVerkuendung"
          />
        </RouterLink>
      </div>
    </div>
  </RisViewLayout>
</template>
