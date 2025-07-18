<script setup lang="ts">
import { Tree } from "primevue"
import { computed, ref, watch } from "vue"
import type { TreeNode } from "primevue/treenode"
import type { TocItem } from "@/types/toc"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"

const props = defineProps<{
  toc: TocItem[] | null
  isFetching: boolean
  fetchError: unknown
  selectedEId?: string | null | undefined
  ariaLabelledby?: string | undefined
}>()

const emit = defineEmits<{
  select: [{ eId: string }]
}>()

function tocToTreeNodes(i: TocItem): TreeNode {
  return {
    key: i.id,
    label: i.marker,
    data: { sublabel: i.heading || null },
    children: i.children?.map(tocToTreeNodes),
  }
}

const treeNodes = computed<TreeNode[]>(() => {
  if (props.toc?.length == undefined) {
    return []
  }

  return props.toc.map(tocToTreeNodes)
})

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = computed(() => {
  if (props.selectedEId) {
    return { [props.selectedEId]: true }
  }

  return {}
})

const handleNodeSelect = (node: TreeNode) => {
  expandedKeys.value[node.key] = true
  emit("select", { eId: node.key })
}

watch(
  () => props.selectedEId,
  () => {
    if (props.selectedEId) {
      expandedKeys.value[props.selectedEId] = true
    }
  },
  { immediate: true },
)
</script>

<template>
  <div v-if="isFetching" class="mt-24 flex items-center justify-center">
    <RisLoadingSpinner />
  </div>

  <RisErrorCallout
    v-else-if="fetchError"
    :error="fetchError"
    class="m-16 mt-8"
  />

  <RisEmptyState
    v-else-if="!toc || !toc.length"
    text-content="Keine Artikel gefunden."
    class="m-16 mt-8"
  />

  <Tree
    v-else
    v-model:expanded-keys="expandedKeys"
    :selection-keys="selectionKeys"
    :aria-labelledby="ariaLabelledby"
    :value="treeNodes"
    selection-mode="single"
    @node-select="handleNodeSelect"
  >
    <template #default="{ node }">
      <button class="cursor-pointer pl-4 text-left group-hover:underline!">
        <span v-if="!node.label && !node.data.sublabel" class="block"
          >Unbekanntes Element</span
        >
        <span v-if="node.label" class="block">{{ node.label }}</span>
        <span class="block font-normal">{{ node.data.sublabel }}</span>
      </button>
    </template>
  </Tree>
</template>
