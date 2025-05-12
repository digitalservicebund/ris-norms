<script setup lang="ts">
import Accordion from "primevue/accordion"
import AccordionContent from "primevue/accordioncontent"
import AccordionHeader from "primevue/accordionheader"
import AccordionPanel from "primevue/accordionpanel"
import Button from "primevue/button"
import type { RisZielnormExpressionsTableItem } from "@/views/verkuendungen/verkuendungDetail/expressionen-erzeugen/RisZielnormExpressionsTable.vue"
import RisZielnormExpressionsTable from "@/views/verkuendungen/verkuendungDetail/expressionen-erzeugen/RisZielnormExpressionsTable.vue"

/** Input data to the Zielnormen list. */
export type RisZielnormenListItem = {
  /** Name of the Zielnorm */
  title: string
  /** Short Title of the Zielnorm */
  shortTitle: string
  /** Newly created expressions for this Zielnorm */
  expressions: RisZielnormExpressionsTableItem[]
}

const { items } = defineProps<{ items: RisZielnormenListItem[] }>()
</script>

<template>
  <Accordion class="flex flex-col gap-8 border-0! bg-inherit!">
    <AccordionPanel
      v-for="(item, i) in items"
      :key="item.title"
      class="border-t border-b border-blue-300 bg-white"
      :value="i"
    >
      <AccordionHeader>
        <div>
          <div class="ris-body1-bold">
            <span>{{ item.title }}</span>
            <span v-if="item.shortTitle"> ({{ item.shortTitle }})</span>
          </div>
        </div>
      </AccordionHeader>

      <AccordionContent>
        <div v-if="item.expressions.length">
          <RisZielnormExpressionsTable
            :items="item.expressions"
          ></RisZielnormExpressionsTable>

          <Button class="my-12" label="Expressionen erzeugen"></Button>
        </div>

        <div v-else class="ris-body2-regular px-14 py-12">
          Keine Daten vorhanden.
        </div>
      </AccordionContent>
    </AccordionPanel>
  </Accordion>
</template>
