<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { computed, ref, watch } from "vue"
import { useGetNorms } from "@/services/normService"
import RisDatenbankTable from "./RisDatenbankTable.vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const router = useRouter()

const currentPage = ref(0)
const pageSize = ref(100)

watch(
  () => route.query.page,
  (newPage) => {
    currentPage.value = Math.max(0, (Number(newPage) || 1) - 1)
  },
  { immediate: true },
)

const {
  data,
  isFetching: normsIsFetching,
  error: normsError,
} = useGetNorms(currentPage, pageSize)

const norms = computed(() => data.value?.content ?? [])
const total = computed(() => data.value?.page.totalElements ?? 0)

function onPageChange(page: number) {
  currentPage.value = page
  const userPage = page + 1
  router.push({
    query: {
      ...route.query,
      page: userPage.toString(),
    },
  })
}

watch(
  [() => route.query.page, () => data.value?.page.totalPages],
  ([pageParam, totalPages]) => {
    if (totalPages) {
      const requestedPage = Number(pageParam) || 1
      const maxPage = totalPages

      if (requestedPage > maxPage) {
        router.replace({
          query: { ...route.query, page: maxPage.toString() },
        })
      } else if (requestedPage < 1) {
        router.replace({
          query: { ...route.query, page: "1" },
        })
      }
    }
  },
)

watch(
  [() => data.value?.page.totalPages, () => route.query.page],
  ([totalPages, pageParam]) => {
    if (totalPages && totalPages > 1 && !pageParam) {
      router.replace({
        query: { ...route.query, page: "1" },
      })
    }
  },
  { immediate: true },
)
</script>

<template>
  <RisViewLayout
    :errors="[normsError]"
    :loading="normsIsFetching"
    :disable-loading-debounce="true"
  >
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
  </RisViewLayout>
</template>
