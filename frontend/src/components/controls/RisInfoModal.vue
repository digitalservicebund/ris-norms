<script lang="ts" setup>
import EditOutlineIcon from "~icons/ic/outline-edit"
import RisTextButton from "@/components/controls/RisTextButton.vue"
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
  <div class="mt-32 flex w-full justify-between gap-24 bg-blue-200 p-24">
    <div class="flex flex-col">
      <span class="ds-label-02-bold">{{ title }}</span>
      <span class="ds-body-01-reg">{{ description }}</span>
    </div>

    <RisTextButton
      :to
      :icon="EditOutlineIcon"
      :label="iconText"
      variant="ghost"
    />
  </div>
</template>
