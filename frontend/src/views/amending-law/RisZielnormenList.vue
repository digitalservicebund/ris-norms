<script setup lang="ts">
import { getHighlightClasses } from "@/composables/useTimeBoundaryHighlightClasses"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { Norm } from "@/types/norm"
import Accordion from "primevue/accordion"
import AccordionContent from "primevue/accordioncontent"
import AccordionHeader from "primevue/accordionheader"
import AccordionPanel from "primevue/accordionpanel"
import Column from "primevue/column"
import DataTable from "primevue/datatable"
import { computed } from "vue"

/** Input data to the Zielnormen list. */
export type RisZielnormenListItem = {
  /** Name of the Zielnorm */
  title: string
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
    formattedDate: { color: string[]; label: string }
    formattedStatus: string
  }>
}

const { items } = defineProps<{ items: RisZielnormenListItem[] }>()

function formatDate(dateString: string | undefined): string {
  return dateString
    ? new Date(dateString).toLocaleDateString("de-DE", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      })
    : ""
}

const mappedItems = computed<MappedRisZielnormenListItem[]>(() =>
  items.map<MappedRisZielnormenListItem>((item) => ({
    title: item.title,
    eli: item.eli,
    fna: item.fna,
    expressions: item.expressions.map((expr, i) => ({
      normExpressionEli: NormExpressionEli.fromString(expr.eli).toString(),
      formattedDate: {
        label: formatDate(expr.frbrDateVerkuendung),
        color: getHighlightClasses(i).default,
      },
      formattedStatus: "neu",
    })),
  })),
)
</script>

<template>
  <Accordion>
    <AccordionPanel v-for="(item, i) in mappedItems" :key="item.eli" :value="i">
      <AccordionHeader>
        <div>
          <div class="ris-body1-bold mb-8">{{ item.title }}</div>
          <div class="flex gap-32">
            <div>
              <span class="text-gray-800">FNA</span>
              {{ item.fna }}
            </div>
            <span>{{ item.eli }}</span>
          </div>
        </div>
      </AccordionHeader>

      <AccordionContent>
        <Accordion class="border-none">
          <AccordionPanel value="textkonsolidierung">
            <AccordionHeader>
              <div class="ris-link1-bold">Textkonsolidierung</div>
            </AccordionHeader>
            <AccordionContent>
              <DataTable
                v-if="item.expressions.length"
                :show-headers="false"
                :value="item.expressions"
                data-key="eli"
              >
                <Column field="normExpressionEli" header="ELI"></Column>
                <Column field="formattedDate" header="Datum">
                  <template #body="{ data }">
                    <div class="flex items-center gap-8">
                      <span
                        :class="[
                          'h-20 w-20 rounded-full border border-dotted border-blue-800',
                          data.formattedDate.color,
                        ]"
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

          <AccordionPanel value="metadata" disabled>
            <AccordionHeader>
              <div class="ris-link1-bold text-gray-600">Metadaten</div>
            </AccordionHeader>
            <AccordionContent class="pl-24"></AccordionContent>
          </AccordionPanel>

          <AccordionPanel value="publishing" disabled>
            <AccordionHeader>
              <div class="ris-link1-bold text-gray-600">Abgabe</div>
            </AccordionHeader>
            <AccordionContent class="pl-24"></AccordionContent>
          </AccordionPanel>
        </Accordion>
      </AccordionContent>
    </AccordionPanel>
  </Accordion>
</template>
