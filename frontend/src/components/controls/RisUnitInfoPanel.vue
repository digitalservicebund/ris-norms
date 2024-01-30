<script lang="ts" setup>
import RisPropertyInfo from "@/components/controls/RisPropertyInfo.vue"

interface RisPropertyInfo {
  label: string
  value?: string
}

interface Props {
  heading?: string
  propertyInfos?: RisPropertyInfo[]
}

withDefaults(defineProps<Props>(), {
  heading: "",
  propertyInfos: () => [],
})
</script>

<template>
  <div
    class="flex h-80 items-center gap-20 border-b border-solid border-gray-400 bg-blue-200 px-[2rem]"
  >
    <div class="flex flex-col">
      <div class="ds-heading-03-reg mb-8">{{ heading }}</div>
      <div v-for="entry in propertyInfos" :key="entry.label" class="grow">
        <RisPropertyInfo
          :label="entry.label"
          :value="entry.value || ' - '"
        ></RisPropertyInfo>
      </div>
    </div>
  </div>
</template>
