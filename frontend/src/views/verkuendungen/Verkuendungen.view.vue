<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useVerkuendungenService } from "@/services/verkuendungService"
import Button from "primevue/button"
import { RouterLink } from "vue-router"
import RisVerkuendungListItem from "./RisVerkuendungListItem.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

const { isFetching, error, data: amendingLaws } = useVerkuendungenService()
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
      <RisEmptyState
        v-if="amendingLaws?.length === 0"
        text-content="Keine Verkündungen gefunden."
      />
      <div v-else class="flex flex-col gap-8">
        <RouterLink
          v-for="amendingLaw in amendingLaws"
          :key="amendingLaw.eli"
          :to="`/verkuendungen/${DokumentExpressionEli.fromString(amendingLaw.eli).asNormEli().toString()}`"
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
