<script lang="ts" setup>
import EditOutlineIcon from "~icons/ic/outline-edit"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { RouteLocationRaw } from "vue-router"

const props = withDefaults(
  defineProps<{
    ariaLabel?: string
    title: string
    description?: string
    iconText?: string
    to?: RouteLocationRaw
  }>(),
  {
    ariaLabel: "Infomodal",
    description: "",
    iconText: "",
    href: undefined,
    target: undefined,
    to: undefined,
  },
)
</script>

<template>
  <div
    :aria-label="props.ariaLabel || 'Infomodal'"
    class="mt-32 flex w-full justify-between gap-[1.5rem] border-l-[0.125rem] bg-blue-200 px-[1rem] py-[1.125rem]"
  >
    <div class="flex flex-col">
      <span class="ds-label-02-bold">{{ title }}</span>
      <span class="ds-body-01-reg">{{ description }}</span>
    </div>

    <RisTextButton
      :to
      :icon="EditOutlineIcon"
      :label="iconText"
      class="flex w-288 items-center"
      variant="ghost"
    />
  </div>
</template>
