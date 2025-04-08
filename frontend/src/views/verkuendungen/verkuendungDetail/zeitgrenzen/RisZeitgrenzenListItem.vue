<script setup lang="ts">
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { formatDate } from "@/lib/dateTime"
import type { Zeitgrenze, ZeitgrenzeArt } from "@/types/zeitgrenze"
import Button from "primevue/button"
import Checkbox from "primevue/checkbox"
import InputText from "primevue/inputtext"
import Select from "primevue/select"
import { computed, onMounted, useTemplateRef } from "vue"
import IcBaselineClear from "~icons/ic/baseline-clear"

/** Zeitgrenze to be displayed and edited in the component. */
const zeitgrenze = defineModel<Zeitgrenze>({ required: true })

const { autofocus } = defineProps<{
  /** Position of the Zeitgrenze. Used for determining the color. */
  index: number

  /** If true, will focus the Geltungszeit input once when mounted. */
  autofocus?: boolean
}>()

defineEmits<{
  /** Emitted when the user pressed the delete button. */
  delete: []
}>()

const geltungszeitEl = useTemplateRef("geltungszeitEl")

onMounted(() => {
  // @ts-expect-error -- Complains about missing $el, but that is what PrimeVue recommends
  if (autofocus && geltungszeitEl.value) geltungszeitEl.value.$el?.focus()
})

const formattedDate = computed(() => formatDate(zeitgrenze.value.date))

const artOptions: Array<{ label: string; value: ZeitgrenzeArt }> = [
  { label: "Inkrafttreten", value: "inkrafttreten" },
  { label: "Außerkrafttreten", value: "ausserkrafttreten" },
]

const geltungszeit = computed({
  get() {
    return zeitgrenze.value.date
  },
  set(value) {
    zeitgrenze.value = { ...zeitgrenze.value, date: value }
  },
})

const art = computed({
  get() {
    return zeitgrenze.value.art
  },
  set(value) {
    zeitgrenze.value = { ...zeitgrenze.value, art: value }
  },
})
</script>

<template>
  <li class="contents">
    <label
      class="ris-label1-regular flex flex-nowrap items-center justify-center gap-12"
    >
      <Checkbox binary disabled></Checkbox>
      <span class="sr-only @lg:not-sr-only">unbestimmt</span>
    </label>

    <div class="pl-8">
      <RisHighlightColorSwatch :color-index="index" :size="['h-28', 'w-28']" />
    </div>

    <label>
      <span class="sr-only">Geltungszeit</span>
      <InputText ref="geltungszeitEl" v-model="geltungszeit" fluid />
    </label>

    <label>
      <span class="sr-only">Art</span>
      <Select
        v-model="art"
        :options="artOptions"
        fluid
        option-label="label"
        option-value="value"
      />
    </label>

    <div>
      <Button
        :aria-label="`Zeitgrenze vom ${formattedDate} entfernen`"
        text
        @click="$emit('delete')"
      >
        <template #icon><IcBaselineClear /></template>
      </Button>
    </div>
  </li>
</template>
