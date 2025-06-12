<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import type { ComputedRef } from "vue"
import { computed, ref, watch, nextTick } from "vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import Tree from "primevue/tree"
import type { TreeNode } from "primevue/treenode"
import ChevronUpIcon from "~icons/ic/baseline-keyboard-arrow-up"
import ChevronDownIcon from "~icons/ic/baseline-keyboard-arrow-down"
import { useGetNormToc } from "@/services/tocService"
import type { TocItem } from "@/types/toc"
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useRouter } from "vue-router"
import { useElementId } from "@/composables/useElementId"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { Splitter, SplitterPanel } from "primevue"

const router = useRouter()
const expressionEli = useDokumentExpressionEliPathParameter()
const selectedEid = useEidPathParameter()
const { sidebarNavigationId } = useElementId()

const {
  data: tocItems,
  isFetching: tocIsLoading,
  error: tocError,
} = useGetNormToc(expressionEli)

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const elementLinks = computed(() => tocItems.value?.map(mapElement) ?? [])

const mapElement = (el: TocItem): TreeNode => ({
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
  selectionKeys.value = { [node.key]: true }
  if (node.data?.route) {
    router.push(node.data.route)
  }
}

const handleNodeUnselect = (node: TreeNode) => {
  selectionKeys.value = {}
  nextTick(() => {
    selectionKeys.value = { [node.key]: true }
  })
}

const resetSelectionKeys = () => {
  selectionKeys.value = {}
}
</script>

<template>
  <RisViewLayout header-back-destination="history-back" :errors="[tocError]">
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full overflow-auto bg-white"
      >
        <aside
          class="w-full flex-1 overflow-auto px-8 pt-16"
          :aria-labelledby="sidebarNavigationId"
        >
          <span :id="sidebarNavigationId" class="sr-only"
            >Inhaltsverzeichnis</span
          >

          <!-- Frame link -->
          <router-link
            :to="{ name: 'ExpressionMetadataEditorRahmen' }"
            class="flex w-full justify-start border-l-4 border-transparent px-20 py-10 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline focus-visible:outline focus-visible:outline-4 focus-visible:outline-offset-4 focus-visible:outline-blue-800"
            exact-active-class="font-bold underline bg-blue-200 border-l-blue-800"
            @click="resetSelectionKeys"
          >
            Rahmen
          </router-link>
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
          <div class="min-w-max">
            <hr class="mx-16 my-8 border-t border-gray-400" />
            <Tree
              v-model:expanded-keys="expandedKeys"
              v-model:selection-keys="selectionKeys"
              :value="treeNodes"
              selection-mode="single"
              @node-select="handleNodeSelect"
              @node-unselect="handleNodeUnselect"
            >
              <template #default="{ node }">
                <router-link
                  v-if="node.data.route"
                  :to="node.data.route"
                  class="inline-block max-w-192 truncate whitespace-nowrap"
                  :title="node.data.primaryLabel"
                  tabindex="-1"
                  @click="toggleNode(node)"
                >
                  {{ node.data.primaryLabel }}
                </router-link>
                <!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
                <!-- eslint-disable vuejs-accessibility/no-static-element-interactions -->
                <span
                  v-else
                  class="inline-block max-w-192 truncate whitespace-nowrap"
                  :title="node.data.primaryLabel"
                  tabindex="-1"
                  @click="toggleNode(node)"
                >
                  {{ node.data.primaryLabel }}
                </span>

                <router-link
                  v-if="node.data.secondaryLabel"
                  :to="node.data.route"
                  class="ris-label2-regular inline-block max-w-192 truncate whitespace-nowrap"
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
          </div>
        </aside>
      </SplitterPanel>
      <SplitterPanel
        :size="80"
        :min-size="20"
        class="h-full overflow-hidden bg-gray-100"
      >
        <RouterView />
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
