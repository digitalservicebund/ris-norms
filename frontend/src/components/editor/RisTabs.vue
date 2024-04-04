<script setup lang="ts">
import { onMounted } from "vue"

type Tab = {
  id: string
  label: string
}

const props = defineProps<{
  tabs: Tab[]
}>()

const activeTab = defineModel<string | undefined>("activeTab", {
  default: undefined,
})

onMounted(() => {
  if (props.tabs.length > 0) {
    activeTab.value = props.tabs[0].id
  }
})
function switchTab(tabId: string) {
  activeTab.value = tabId
}
</script>
<template>
  <div>
    <ul role="tablist" class="flex">
      <li v-for="tab in tabs" :key="tab.id" role="presentation">
        <button
          :role="'tab'"
          :aria-selected="tab.id === activeTab"
          :aria-label="tab.id"
          class="items-center px-8 text-blue-800 hover:underline focus:underline aria-selected:text-blue-800 aria-selected:underline"
          @click="switchTab(tab.id)"
        >
          {{ tab.label }}
        </button>
      </li>
    </ul>
  </div>
  <slot :name="activeTab" />
</template>
