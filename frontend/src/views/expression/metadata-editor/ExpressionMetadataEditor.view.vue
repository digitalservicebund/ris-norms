<script setup lang="ts">
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisHeader from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { ComputedRef, computed, ref, watch, nextTick } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import Tree from "primevue/tree"
import ChevronUpIcon from "~icons/ic/baseline-keyboard-arrow-up"
import ChevronDownIcon from "~icons/ic/baseline-keyboard-arrow-down"
import { useGetNormTableOfContents } from "@/services/normService"
import { TabelOfContentsItem } from "@/types/tableOfContents"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useRouter } from "vue-router"

const router = useRouter()
const expressionEli = useEliPathParameter()
const selectedEid = useEidPathParameter()

const {
  data: tocItems,
  isFetching: tocIsLoading,
  error: tocError,
} = useGetNormTableOfContents(expressionEli)

interface TreeNode {
  key: string
  label?: string
  data?: {
    primaryLabel: string
    secondaryLabel: string | null
    route: {
      name: string
      params: { eid: string }
    }
  }
  children?: TreeNode[]
}

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const elementLinks = computed<TreeNode[]>(
  () => tocItems.value?.map(mapElement) ?? [],
)

const mapElement = (el: TabelOfContentsItem): TreeNode => ({
  key: el.id,
  label: el.marker || "",
  data: {
    primaryLabel: el.marker || "",
    secondaryLabel: el.heading || null,
    route: {
      name: ["article", "conclusions", "preamble", "preface"].includes(el.type)
        ? "ExpressionMetadataEditorElement"
        : "ExpressionMetadataEditorOutlineElement",
      params: { eid: el.id },
    },
  },
  children: el.children?.map(mapElement) ?? [],
})

const treeNodes: ComputedRef<TreeNode[]> = elementLinks

const collapseAllNodes = async () => {
  expandedKeys.value = { ...{} }
  await nextTick()
}

const handleRahmenClick = async () => {
  await collapseAllNodes()
  selectionKeys.value = {}
}

const toggleNode = (node: TreeNode) => {
  if (expandedKeys.value[node.key]) {
    const newExpandedKeys = { ...expandedKeys.value }
    newExpandedKeys[node.key] = false
    expandedKeys.value = newExpandedKeys
  } else {
    expandedKeys.value = { ...expandedKeys.value, [node.key]: true }
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
      node.key,
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
  if (node.data?.route) {
    router.push(node.data.route)
  }
}
</script>

<template>
  <div class="h-[calc(100dvh-5rem)] bg-gray-100">
    <div
      class="grid h-full grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
    >
      <RisHeader class="col-span-2">
        <aside
          class="col-span-1 flex h-[calc(100dvh-5rem-5rem)] w-full flex-col overflow-auto border-r border-gray-400 bg-white px-8 pt-16"
        >
          <span id="sidebarNavigation" class="sr-only">Inhaltsverzeichnis</span>

          <!-- Frame link -->
          <router-link
            :to="{ name: 'ExpressionMetadataEditorRahmen' }"
            class="px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
            exact-active-class="font-bold underline bg-blue-200"
            @click="handleRahmenClick"
          >
            Rahmen
          </router-link>
          <hr class="mx-16 my-8 border-t border-gray-400" />
          <!-- Content links -->
          <div
            v-if="tocIsLoading"
            class="m-16 flex items-center justify-center"
          >
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout
            v-else-if="tocError"
            :error="tocError"
            class="mx-16"
          />

          <RisEmptyState
            v-else-if="!tocItems || !tocItems.length"
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
          >
            <template #default="{ node }">
              <router-link
                v-if="node.data.route"
                :to="node.data.route"
                class="w-full overflow-hidden truncate text-ellipsis"
                :title="node.data.primaryLabel"
                tabindex="-1"
                @click="toggleNode(node)"
              >
                {{ node.data.primaryLabel }}
              </router-link>

              <button
                v-else
                class="w-full overflow-hidden truncate text-ellipsis"
                :title="node.data.primaryLabel"
                tabindex="-1"
                @click="toggleNode(node)"
              >
                {{ node.data.primaryLabel }}
              </button>

              <router-link
                v-if="node.data.secondaryLabel"
                :to="node.data.route"
                class="ris-label2-regular w-full overflow-hidden truncate text-ellipsis"
                :title="node.data.secondaryLabel"
                tabindex="-1"
                @click="toggleNode(node)"
              >
                {{ node.data.secondaryLabel }}
              </router-link>
            </template>

            <template #nodetoggleicon="{ expanded }">
              <ChevronDownIcon v-if="!expanded" />
              <ChevronUpIcon v-else />
            </template>
          </Tree>
        </aside>

        <RouterView />
      </RisHeader>
    </div>
  </div>
</template>
