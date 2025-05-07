<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import Column from "primevue/column"
import DataTable from "primevue/datatable"
import Chip from "primevue/chip"
import IconCheckCircle from "~icons/ic/baseline-check-circle"
import IconReadMore from "~icons/ic/baseline-read-more"
import { computed } from "vue"
import dayjs from "dayjs"

/** Input data to the table. */
export type RisZielnormExpressionsTableItem = {
  normExpressionEli: NormExpressionEli
  isGegenstandslos: boolean
  erzeugtDurch: string
  isCreated: boolean
}

type InternalRisZielnormExpressionsTableItem =
  RisZielnormExpressionsTableItem & {
    isReplacingGegenstandslos: boolean
    untilDate: Date | undefined
    colorIndex: number | undefined
  }

const { items } = defineProps<{ items: RisZielnormExpressionsTableItem[] }>()

function itemIsCreatedByThisVerkuendung(
  item: RisZielnormExpressionsTableItem,
): boolean {
  return item.erzeugtDurch === "diese Verkündung"
}

const internalItems = computed(() => {
  let nextColorIndex = 0
  return items.map((item, index) => {
    let colorIndex = undefined

    if (itemIsCreatedByThisVerkuendung(item)) {
      colorIndex = nextColorIndex
      nextColorIndex++
    }

    return {
      ...item,
      isReplacingGegenstandslos: index > 0 && items[index - 1].isGegenstandslos,
      untilDate:
        items.length > index + 1
          ? dayjs(items[index + 1].normExpressionEli.pointInTime)
              .subtract(1, "day")
              .toDate()
          : undefined,
      colorIndex,
    } as InternalRisZielnormExpressionsTableItem
  })
})

function formatDateString(dateString: string | undefined): string {
  return formatDate(dateString ? new Date(dateString) : undefined)
}

function formatDate(date: Date | undefined): string {
  if (date === undefined) {
    return ""
  }

  return date.toLocaleDateString("de-DE", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  })
}
</script>

<template>
  <DataTable
    :value="internalItems"
    data-key="normExpressionEli"
    :row-class="() => `hover:bg-blue-200`"
  >
    <Column
      header-class="text-gray-900 font-light"
      :field="(data) => data.normExpressionEli.toString()"
      header="Expressions-ELI"
    >
      <template #body="{ data }">
        <div class="flex items-center gap-8">
          <IconReadMore
            v-if="data.isReplacingGegenstandslos"
            class="text-gray-600"
          />
          {{ data.normExpressionEli.toString() }}
        </div>
      </template>
    </Column>
    <Column
      header-class="text-gray-900 font-light"
      :field="(data) => data.normExpressionEli.pointInTime"
      header="gültig ab - gültig bis"
    >
      <template #body="{ data }">
        <div v-if="data.isGegenstandslos" class="pl-28">gegenstandslos</div>
        <div v-else class="flex items-center gap-8">
          <RisHighlightColorSwatch
            v-if="data.colorIndex !== undefined"
            :color-index="data.colorIndex"
          />
          <div
            :class="{
              'pl-28': data.colorIndex === undefined,
            }"
          >
            <span>
              {{ formatDateString(data.normExpressionEli.pointInTime) }}
            </span>
            <span v-if="data.untilDate !== undefined" class="text-gray-800">
              -
              {{ formatDate(data.untilDate) }}
            </span>
            <time></time>
          </div>
        </div>
      </template>
    </Column>
    <Column
      header-class="text-gray-900 font-light"
      field="erzeugtDurch"
      header="erzeugt durch"
    >
      <template #body="{ data }">
        <span
          :class="{
            'font-bold': !data.isGegenstandslos,
          }"
          >{{ data.erzeugtDurch }}</span
        >
      </template>
    </Column>
    <Column field="isCreated">
      <template #body="{ data }">
        <div
          v-if="data.isCreated && !data.isGegenstandslos"
          class="flex justify-end"
        >
          <Chip label="Expression erzeugt" class="bg-green-100">
            <template #icon>
              <IconCheckCircle class="text-green-900" />
            </template>
          </Chip>
        </div>
      </template>
    </Column>
  </DataTable>
</template>
