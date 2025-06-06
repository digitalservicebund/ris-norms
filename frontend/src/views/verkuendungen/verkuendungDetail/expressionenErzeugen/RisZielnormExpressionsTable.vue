<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { formatDate } from "@/lib/dateTime"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import dayjs from "dayjs"
import { Badge, Column, DataTable } from "primevue"
import { computed } from "vue"
import IcBaselineCheckCircle from "~icons/ic/baseline-check-circle"
import IcBaselineErrorOutline from "~icons/ic/baseline-error-outline"
import IcBaselinePanoramaFishEye from "~icons/ic/baseline-panorama-fish-eye"
import IcBaselineReadMore from "~icons/ic/baseline-read-more"

/** Input data to the table. */
export type RisZielnormExpressionsTableItem = {
  normExpressionEli: NormExpressionEli
  isGegenstandslos: boolean
  createdBy: string
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
  return item.createdBy === "diese Verkündung"
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
  return dateString ? formatDate(dateString) : ""
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
          <IcBaselineReadMore
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
      field="createdBy"
      header="erzeugt durch"
    >
      <template #body="{ data }">
        <span
          :class="{
            'font-bold': !data.isGegenstandslos,
          }"
          >{{ data.createdBy }}</span
        >
      </template>
    </Column>
    <Column field="isCreated">
      <template #body="{ data }">
        <div class="flex justify-end">
          <Badge v-if="data.isGegenstandslos">
            <IcBaselineErrorOutline />
            Gegenstandslos
          </Badge>

          <Badge v-else-if="data.isCreated" severity="success">
            <IcBaselineCheckCircle />
            Expression erzeugt
          </Badge>

          <Badge v-else-if="!data.isCreated" severity="info">
            <IcBaselinePanoramaFishEye />
            Noch nicht erzeugt
          </Badge>
        </div>
      </template>
    </Column>
  </DataTable>
</template>
