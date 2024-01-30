<script lang="ts" setup>
import { computed } from "vue"

interface Props {
  label: string
  value?: string
}

const props = defineProps<Props>()

const noValue = computed(() => {
  return props.value === undefined || props.value === ""
})
</script>

<template>
  <div class="flex items-baseline">
    <div class="ds-label-03-bold pr-[0.4rem] uppercase">{{ label }}</div>
    <div class="ds-label-03-bold">{{ noValue ? " - " : value }}</div>
  </div>
</template>
