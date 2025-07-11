<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { formatDate } from "@/lib/dateTime"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { Norm } from "@/types/norm"
import Accordion from "primevue/accordion"
import AccordionContent from "primevue/accordioncontent"
import AccordionHeader from "primevue/accordionheader"
import AccordionPanel from "primevue/accordionpanel"
import Column from "primevue/column"
import DataTable from "primevue/datatable"
import { computed } from "vue"
import { RouterLink } from "vue-router"

/** Input data to the Zielnormen list. */
export type RisZielnormenListItem = {
  /** Name of the Zielnorm */
  title: string
  /** Short Title of the Zielnorm */
  shortTitle: string
  /** FNA of the Zielnorm */
  fna: string
  /** ELI of the Zielnorm */
  eli: string
  /** Newly created expressions for this Zielnorm */
  expressions: Norm[]
}

type MappedRisZielnormenListItem = Omit<
  RisZielnormenListItem,
  "expressions"
> & {
  expressions: Array<{
    normExpressionEli: string
    formattedDate: { colorIndex: number; label: string }
    formattedStatus: string
  }>
}

const { items, verkuendungEli } = defineProps<{
  items: RisZielnormenListItem[]
  verkuendungEli: NormExpressionEli
}>()

const mappedItems = computed<MappedRisZielnormenListItem[]>(() =>
  items.map<MappedRisZielnormenListItem>((item) => ({
    title: item.title,
    shortTitle: item.shortTitle,
    eli: item.eli,
    fna: item.fna,
    expressions: item.expressions.map((expr, i) => {
      const normEli = NormExpressionEli.fromString(expr.eli)
      return {
        normExpressionEli: normEli.toString(),
        documentExpressionEli: expr.eli,
        formattedDate: {
          label: formatDate(normEli.pointInTime),
          colorIndex: i,
        },
        formattedStatus: "neu",
      }
    }),
  })),
)
</script>

<template>
  <Accordion>
    <AccordionPanel v-for="(item, i) in mappedItems" :key="item.eli" :value="i">
      <AccordionHeader>
        <div>
          <div class="ris-body1-bold mb-8">
            {{ item.shortTitle || item.title }}
          </div>
          <div class="flex gap-32">
            <div>
              <span class="text-gray-800">FNA</span>
              {{ item.fna }}
            </div>
            <span>
              <span class="sr-only">ELI</span>
              {{ item.eli }}
            </span>
          </div>
        </div>
      </AccordionHeader>

      <AccordionContent>
        <Accordion class="border-none">
          <AccordionPanel
            v-for="section in ['textkonsolidierung', 'metadata']"
            :key="section"
            :value="section"
          >
            <AccordionHeader>
              <div class="ris-body2-bold text-blue-800">
                {{
                  section === "textkonsolidierung"
                    ? "Textkonsolidierung"
                    : "Metadaten"
                }}
              </div>
            </AccordionHeader>

            <AccordionContent :class="{ 'pl-24': section === 'metadata' }">
              <DataTable
                v-if="item.expressions.length"
                :show-headers="false"
                :value="item.expressions"
                data-key="eli"
              >
                <Column field="normExpressionEli" header="ELI">
                  <template #body="{ data }">
                    <RouterLink
                      :to="
                        section === 'textkonsolidierung'
                          ? `/verkuendungen/${verkuendungEli.toString()}/textkonsolidierung/${data.normExpressionEli}`
                          : `/verkuendungen/${verkuendungEli.toString()}/metadaten/${data.documentExpressionEli}`
                      "
                      class="ris-link2-regular"
                    >
                      {{ data.normExpressionEli }}
                    </RouterLink>
                  </template>
                </Column>
                <Column field="formattedDate" header="Datum">
                  <template #body="{ data }">
                    <div class="flex items-center gap-8">
                      <RisHighlightColorSwatch
                        :color-index="data.formattedDate.colorIndex"
                      />
                      {{ data.formattedDate.label }}
                    </div>
                  </template>
                </Column>
                <Column
                  field="formattedStatus"
                  header="Status"
                  class="ris-body2-bold"
                ></Column>
              </DataTable>

              <div v-else class="ris-body2-regular px-14 py-12">
                Keine Daten vorhanden.
              </div>
            </AccordionContent>
          </AccordionPanel>
          <AccordionPanel value="abgabe">
            <RouterLink
              :to="`/verkuendungen/${verkuendungEli.toString()}/zielnorm/${item.eli}/abgabe`"
              class="group flex w-full cursor-pointer justify-start px-12 py-8 pl-40 text-left no-underline -outline-offset-4 outline-blue-800 hover:bg-gray-200 focus-visible:outline-4"
            >
              <span class="ris-link2-bold">Abgabe</span>
            </RouterLink>
          </AccordionPanel>
        </Accordion>
      </AccordionContent>
    </AccordionPanel>
  </Accordion>
</template>
