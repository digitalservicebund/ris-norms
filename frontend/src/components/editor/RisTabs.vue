<script setup lang="ts">
import { onMounted } from "vue"

type Tab = {
  /**
   * ID of the tab. This is used for internal state tracking and for associating
   * the tab label with the contents.
   */
  id: string

  /** Label of the tab. */
  label: string
}

const props = withDefaults(
  defineProps<{
    /** List of tabs. */
    tabs: Tab[]

    /** Horizontal alignment of the text in the tab list. */
    align?: "right" | "left"
  }>(),
  {
    align: "left",
  },
)

/** Currently selected tab. */
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
    <ul
      role="tablist"
      class="flex gap-16"
      :class="{ 'float-right': props.align === 'right' }"
    >
      <li v-for="tab in tabs" :key="tab.id" class="contents">
        <button
          role="tab"
          :aria-selected="tab.id === activeTab"
          :aria-label="tab.id"
          class="items-center text-blue-800 hover:underline focus:underline aria-selected:font-bold aria-selected:text-blue-800 aria-selected:underline"
          @click="switchTab(tab.id)"
        >
          {{ tab.label }}
        </button>
      </li>
    </ul>
  </div>
  <slot :name="activeTab" />
</template>
