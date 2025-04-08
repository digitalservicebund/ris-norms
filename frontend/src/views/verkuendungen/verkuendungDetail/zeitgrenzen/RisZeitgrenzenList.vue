<script setup lang="ts">
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import Button from "primevue/button"
import IcBaselinePlus from "~icons/ic/baseline-plus"
import RisZeitgrenzenListItem from "./RisZeitgrenzenListItem.vue"
import { computed } from "vue"

const ZEITGRENZEN_MAX_CAPACITY = 100

/** List of Zeitgrenzen to be displayed and edited in the component. */
const zeitgrenzen = defineModel<Zeitgrenze[]>({ required: true })

function updateZeitgrenze(position: number, value: Zeitgrenze) {
  zeitgrenzen.value = zeitgrenzen.value.with(position, value)
}

function deleteZeitgrenze(position: number) {
  zeitgrenzen.value = zeitgrenzen.value.toSpliced(position, 1)
}

function insertZeitgrenze() {
  zeitgrenzen.value = zeitgrenzen.value.concat({
    date: "",
    art: "inkrafttreten",
  })
}

const isEmpty = computed(() => !zeitgrenzen.value.length)

const isFull = computed(
  () => zeitgrenzen.value.length >= ZEITGRENZEN_MAX_CAPACITY,
)
</script>

<template>
  <RisEmptyState
    v-if="isEmpty"
    text-content="Es wurden noch keine Geltungszeiten angelegt."
  >
    <template #recommendedAction>
      <Button label="Geltungszeit hinzufügen" @click="insertZeitgrenze()">
        <template #icon><IcBaselinePlus /></template>
      </Button>
    </template>
  </RisEmptyState>

  <div
    v-else
    class="@container my-24 grid grid-cols-[min-content_min-content_1fr_1fr_min-content] items-center gap-8"
  >
    <!-- Header -->
    <div class="ris-label2-regular">
      <span class="@lg:sr-only">unbestimmt</span>
    </div>
    <div class="ris-label2-regular"></div>
    <div class="ris-label2-regular">Geltungszeit</div>
    <div class="ris-label2-regular">Art</div>
    <div class="ris-label2-regular"></div>

    <ul class="contents">
      <!-- eslint-disable vue/require-v-for-key -- we don't have a reasonable key here -->
      <li v-for="(zeitgrenze, i) in zeitgrenzen" class="contents">
        <!-- eslint-disable vuejs-accessibility/no-autofocus -- it's not the native HTML autofocus -->
        <RisZeitgrenzenListItem
          :autofocus="i === zeitgrenzen.length - 1 && !zeitgrenze.date"
          :model-value="zeitgrenze"
          :index="i"
          @update:model-value="updateZeitgrenze(i, $event)"
          @delete="deleteZeitgrenze(i)"
        />
        <!-- eslint-enable vuejs-accessibility/no-autofocus -->
      </li>
      <!-- eslint-enable vue/require-v-for-key -->
    </ul>
  </div>

  <Button
    v-if="!isEmpty"
    :disabled="isFull"
    label="Geltungszeit hinzufügen"
    @click="insertZeitgrenze()"
  >
    <template #icon><IcBaselinePlus /></template>
  </Button>
</template>
