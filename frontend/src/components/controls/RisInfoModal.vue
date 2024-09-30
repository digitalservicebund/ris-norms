<script lang="ts" setup>
import EditOutlineIcon from "~icons/ic/outline-edit"
import Button from "primevue/button"
import { RouteLocationRaw } from "vue-router"

withDefaults(
  defineProps<{
    /** The main text content of the info modal. */
    title: string

    /** Additional text content of the info modal. */
    description?: string

    /** Label of the edit button. */
    iconText?: string

    /** Route that is opened when the edit button is clicked. */
    to?: RouteLocationRaw
  }>(),
  {
    description: "",
    iconText: "",
    to: undefined,
  },
)
</script>

<template>
  <div class="mt-16 flex w-full justify-between gap-24 bg-blue-200 p-24">
    <div class="flex flex-col">
      <span class="ris-label2-bold">{{ title }}</span>
      <span class="ris-body1-regular">{{ description }}</span>
    </div>

    <RouterLink :to>
      <Button text :label="iconText">
        <template #icon>
          <EditOutlineIcon />
        </template>
      </Button>
    </RouterLink>
  </div>
</template>
