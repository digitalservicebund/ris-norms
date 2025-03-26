<script lang="ts" setup>
import { ref, computed } from "vue"
import DataTable from "primevue/datatable"
import Column from "primevue/column"
import ChevronRightIcon from "~icons/ic/baseline-chevron-right"
import ChevronDownIcon from "~icons/ic/baseline-keyboard-arrow-down"

type ExpressionRow = {
  eli: string
  datum: string
  status?: string
  colorIndex: number
}

type TableRow = {
  id: string
  title: string
  fna: string
  workEli: string
  children: {
    label: string
    versions: ExpressionRow[]
  }[]
}

const props = defineProps<{
  groupedZielnorm: {
    key: string
    title: string
    fna: string
    eli: string
    expressions: {
      eli: string
      status?: string
      frbrDateVerkuendung?: string
    }[]
  }
}>()

const expandedRows = ref<TableRow[]>([])
const nestedExpandedRows = ref<string[]>([])

const laws = computed(() => {
  const sorted = [...props.groupedZielnorm.expressions].sort(
    (a, b) =>
      new Date(a.frbrDateVerkuendung ?? "").getTime() -
      new Date(b.frbrDateVerkuendung ?? "").getTime(),
  )

  return [
    {
      id: props.groupedZielnorm.key,
      title: props.groupedZielnorm.title,
      fna: props.groupedZielnorm.fna,
      workEli: props.groupedZielnorm.eli,
      children: [
        {
          label: "Textkonsolidierung",
          versions: sorted.map((expression, index) => ({
            eli: expression.eli,
            datum: new Date(
              expression.frbrDateVerkuendung ?? "",
            ).toLocaleDateString("de-DE", {
              year: "numeric",
              month: "2-digit",
              day: "2-digit",
            }),
            status: expression.status,
            colorIndex: (index % 10) + 1,
          })),
        },
      ],
    },
  ]
})

type NestedRow = { label: string; versions: ExpressionRow[] }
</script>

<template>
  <div class="bg-white">
    <DataTable
      v-model:expanded-rows="expandedRows"
      :value="laws"
      :show-headers="false"
      :data-key="'id'"
      row-hover
    >
      <Column
        :expander="true"
        :pt="{
          bodyCell: {
            class: 'align-top pt-10',
          },
        }"
      >
        <template #rowtoggleicon="{ rowExpanded, class: iconClass }">
          <ChevronRightIcon v-if="!rowExpanded" :class="iconClass" />
          <ChevronDownIcon v-else :class="iconClass" />
        </template>
      </Column>
      <Column>
        <template #body="{ data }">
          <div class="flex cursor-pointer flex-col gap-2">
            <span class="ris-body1-bold">{{ data.title }}</span>
            <span class="flex flex-row gap-20">
              <div class="flex flex-row gap-4">
                <span class="text-gray-800">FNA</span>
                <span>{{ data.fna }}</span>
              </div>
              <span>{{ data.workEli }}</span>
            </span>
          </div>
        </template>
      </Column>

      <template #expansion="{ data }">
        <DataTable
          v-model:expanded-rows="nestedExpandedRows"
          :value="data.children"
          :show-headers="false"
          :row-expandable="(row: NestedRow) => row.versions.length > 0"
          :data-key="'label'"
          :pt="{ bodyRow: { class: 'border-none' } }"
          class="pb-24"
        >
          <Column :expander="true">
            <template #rowtoggleicon="{ rowExpanded }">
              <div class="ml-20 flex items-center">
                <ChevronRightIcon v-if="!rowExpanded" />
                <ChevronDownIcon v-else />
              </div>
            </template>
          </Column>
          <Column>
            <template #body="{ data: rowData }">
              <div class="ris-link1-bold cursor-pointer">
                {{ rowData.label }}
              </div>
            </template>
          </Column>

          <template #expansion="{ data: subData }">
            <DataTable
              :value="subData.versions"
              :show-headers="false"
              class="pl-32"
              :pt="{
                bodyRow: { class: 'border-b border-blue-300' },
              }"
            >
              <Column
                field="eli"
                :pt="{
                  bodyCell: {
                    class: 'whitespace-nowrap w-2 pl-24',
                  },
                }"
              />
              <Column
                :pt="{
                  bodyCell: {
                    class: 'whitespace-nowrap w-2 text-right',
                  },
                }"
              >
                <template #body="{ data: expression }">
                  <div class="flex gap-8 pl-32">
                    <span
                      class="h-20 w-20 rounded-full border border-dotted"
                      :class="`bg-highlight-${expression.colorIndex}-default`"
                    />
                    {{ expression.datum }}
                  </div>
                </template>
              </Column>
              <Column field="status" />
            </DataTable>
          </template>
        </DataTable>
      </template>
    </DataTable>
  </div>
</template>
