<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { computed, ref } from "vue"
import { useGetNorms } from "@/services/normService"
import RisDatenbankTable from "./RisDatenbankTable.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"

const currentPage = ref(0)
const pageSize = ref(100)

const {
  data,
  isFetching: normsIsFetching,
  error: normsError,
} = useGetNorms(currentPage, pageSize)

const norms = computed(() => data.value?.content ?? [])
const total = computed(() => data.value?.page.totalElements ?? 0)

function onPageChange(page: number) {
  currentPage.value = page
}
</script>

<template>
  <RisViewLayout :errors="[normsError]">
    <template v-if="normsIsFetching">
      <div class="flex items-center justify-center p-24">
        <RisLoadingSpinner />
      </div>
    </template>
    <template v-else>
      <header class="mb-24">
        <h1 class="ris-heading2-regular">Datenbank</h1>
      </header>

      <RisDatenbankTable
        v-if="norms.length"
        :items="norms"
        :total="total"
        :current-page="currentPage"
        :page-size="pageSize"
        @page="onPageChange"
      />

      <RisEmptyState v-else text-content="Keine Werke gefunden." />
    </template>
  </RisViewLayout>
</template>
