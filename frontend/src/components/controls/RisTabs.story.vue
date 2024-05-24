<script setup lang="ts">
import RisTabs from "@/components/editor/RisTabs.vue"

import { ref } from "vue"

const tabs = [
  { id: "tab1", label: "Tab 1" },
  { id: "tab2", label: "Tab 2" },
  { id: "tab3", label: "Tab 3" },
]

const activeTab = ref(tabs[0].id)
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Default">
      <RisTabs v-model:active-tab="activeTab" :tabs="tabs">
        <template #tab1>
          <div>Content for Tab 1</div>
        </template>
        <template #tab2>
          <div>Content for Tab 2</div>
        </template>
        <template #tab3>
          <div>Content for Tab 3</div>
        </template>
      </RisTabs>
    </Variant>
  </Story>
</template>
