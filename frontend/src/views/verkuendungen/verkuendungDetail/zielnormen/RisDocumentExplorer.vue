<script setup lang="ts">
import { computed, ref, watch, nextTick, defineProps } from "vue"
import Tree from "primevue/tree"
import type { TreeNode } from "primevue/treenode"
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import ChevronUpIcon from "~icons/ic/baseline-keyboard-arrow-up"
import ChevronDownIcon from "~icons/ic/baseline-keyboard-arrow-down"
import IcBaselineToc from "~icons/ic/baseline-toc"
import IcBaselineUnfoldLess from "~icons/ic/baseline-unfold-less"
import IcBaselineUnfoldMore from "~icons/ic/baseline-unfold-more"
import Button from "primevue/button"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import type { TableOfContentsItem } from "@/types/tableOfContents"
import { useElementId } from "@/composables/useElementId"

const { documentExplorerHeadingId } = useElementId()

const props = defineProps<{
  items: TableOfContentsItem[] | null
  tocIsLoading: boolean
  tocError: unknown
}>()

const selectedEid = useEidPathParameter()
const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const mapElement = (el: TableOfContentsItem): TreeNode => ({
  key: el.id,
  label: el.marker || "",
  data: {
    primaryLabel: el.marker || "",
    secondaryLabel: el.heading || null,
  },
  children: el.children?.map(mapElement) ?? [],
})

const treeNodes = computed(() => props.items?.map(mapElement) ?? [])

// flattens every node
function flattenKeys(nodes: TreeNode[]): string[] {
  return nodes.reduce<string[]>((all, n) => {
    all.push(n.key as string)
    if (n.children?.length) {
      all.push(...flattenKeys(n.children))
    }
    return all
  }, [])
}
const allKeys = computed(() => flattenKeys(treeNodes.value))

const isAnyExpanded = computed(() =>
  Object.values(expandedKeys.value).some((v) => v),
)

const toggleAll = () => {
  if (isAnyExpanded.value) {
    expandedKeys.value = {}
  } else {
    expandedKeys.value = allKeys.value.reduce(
      (acc, k) => {
        acc[k] = true
        return acc
      },
      {} as Record<string, boolean>,
    )
  }
}

const toggleNode = (node: TreeNode) => {
  const key = node.key as string
  expandedKeys.value = {
    ...expandedKeys.value,
    [key]: !expandedKeys.value[key],
  }
}

const findParentIds = (
  eid: string,
  nodes: TreeNode[],
  parents: string[] = [],
): string[] => {
  for (const node of nodes) {
    if (node.key === eid) return parents
    const found = findParentIds(eid, node.children ?? [], [
      ...parents,
      node.key as string,
    ])
    if (found.length) return found
  }
  return []
}

watch(
  () => [selectedEid.value, treeNodes.value.length],
  ([eid]) => {
    if (!eid || treeNodes.value.length === 0) return

    const stringEid = String(eid)
    const parentIds = findParentIds(stringEid, treeNodes.value)
    parentIds.forEach((id) => (expandedKeys.value[id] = true))
    expandedKeys.value[stringEid] = true

    if (!Object.keys(selectionKeys.value).length) {
      selectionKeys.value = { [stringEid]: true }
    }
  },
  { immediate: true },
)

const handleNodeSelect = (node: TreeNode) => {
  selectionKeys.value = { [node.key as string]: true }
}

const handleNodeUnselect = (node: TreeNode) => {
  selectionKeys.value = {}
  nextTick(() => {
    selectionKeys.value = { [node.key as string]: true }
  })
}

const resetSelectionKeys = () => {
  selectionKeys.value = {}
}

const hasAnyChildren = computed(() =>
  treeNodes.value.some((node) => (node.children?.length ?? 0) > 0),
)
</script>

<template>
  <aside
    class="flex h-full flex-col overflow-auto border-r border-gray-400 bg-white px-8 pt-8"
    aria-labelledby="sidebarNavigation"
  >
    <span id="sidebarNavigation" class="sr-only">Inhaltsverzeichnis</span>
    <div class="flex items-center">
      <Button
        severity="text"
        disabled
        class="text-gray-900"
        @click="resetSelectionKeys"
      >
        <template #icon>
          <IcBaselineToc />
        </template>
      </Button>
      <h1 :id="documentExplorerHeadingId" class="ris-subhead-bold mb-0">
        Änderungsgesetz
      </h1>
      <Button
        severity="text"
        class="ml-auto"
        :disabled="!hasAnyChildren"
        @click="toggleAll"
      >
        <template #icon>
          <IcBaselineUnfoldLess v-if="hasAnyChildren && isAnyExpanded" />
          <IcBaselineUnfoldMore
            v-else
            :class="{ 'text-gray-400': !hasAnyChildren }"
          />
        </template>
      </Button>
    </div>
    <hr class="my-8 border-t border-gray-400" />
    <div v-if="tocIsLoading" class="m-16 flex items-center justify-center">
      <RisLoadingSpinner />
    </div>
    <RisErrorCallout v-else-if="tocError" :error="tocError" class="mx-16" />
    <RisEmptyState
      v-else-if="!items || !items.length"
      text-content="Keine Artikel gefunden."
      class="mx-16"
      variant="simple"
    />
    <Tree
      v-model:expanded-keys="expandedKeys"
      v-model:selection-keys="selectionKeys"
      :value="treeNodes"
      selection-mode="single"
      @node-select="handleNodeSelect"
      @node-unselect="handleNodeUnselect"
    >
      <template #default="{ node }">
        <!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
        <!-- eslint-disable vuejs-accessibility/no-static-element-interactions -->
        <span
          class="w-full truncate"
          :title="node.data.primaryLabel"
          tabindex="-1"
          @click="toggleNode(node)"
        >
          {{ node.data.primaryLabel }}
        </span>
        <span
          v-if="node.data.secondaryLabel"
          class="ris-label2-regular w-full truncate"
          :title="node.data.secondaryLabel"
          tabindex="-1"
          @click="toggleNode(node)"
        >
          {{ node.data.secondaryLabel }}
        </span>
      </template>
      <template #nodetoggleicon="{ expanded }">
        <ChevronDownIcon v-if="!expanded" />
        <ChevronUpIcon v-else />
      </template>
    </Tree>
  </aside>
</template>
