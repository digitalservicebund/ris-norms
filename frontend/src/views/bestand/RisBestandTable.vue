<script setup lang="ts">
import { Column, DataTable } from "primevue"
import type { DataTablePageEvent } from "primevue/datatable"
import Button from "primevue/button"

/** Input data to the table. */
export type BestandTableItem = {
  /** Work ELI of the norm */
  eli: string
  /** Title of the latest manifestation of the latest expression */
  title: string
}

defineProps<{
  items: BestandTableItem[]
  total: number
  currentPage: number
  pageSize: number
}>()

const emit = defineEmits<{
  page: [page: number]
}>()

function onPage(event: DataTablePageEvent) {
  emit("page", event.page)
}
</script>

<template>
  <DataTable
    :value="items"
    paginator
    :rows="pageSize"
    :total-records="total"
    :first="currentPage * pageSize"
    lazy
    data-key="eli"
    :row-class="() => `hover:bg-blue-200`"
    class="bg-white"
    paginator-template="CurrentPageReport"
    current-page-report-template="Seite {currentPage} von {totalPages}"
    paginator-position="bottom"
    @page="onPage"
  >
    <template #paginatorstart>
      <Button
        v-if="currentPage > 0"
        class="ris-body2-regular cursor-pointer"
        text
        @click="$emit('page', currentPage - 1)"
      >
        Zurück
      </Button>
      <span v-else style="visibility: hidden">Zurück</span>
    </template>
    <template #paginatorend>
      <Button
        v-if="currentPage < Math.ceil(total / pageSize) - 1"
        class="ris-body2-regular cursor-pointer"
        text
        @click="$emit('page', currentPage + 1)"
      >
        Weiter
      </Button>
      <span v-else style="visibility: hidden">Weiter</span>
    </template>
    <Column field="eli" header="ELI" class="w-1/5">
      <template #body="{ data }">
        <RouterLink
          :to="`/bestand/${data.eli}`"
          class="ris-link2-regular pointer-events-none"
        >
          {{ data.eli }}
        </RouterLink>
      </template>
    </Column>
    <Column field="title" header="Titel" class="w-4/5" />
  </DataTable>
</template>
