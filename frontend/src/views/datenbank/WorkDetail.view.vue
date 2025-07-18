<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { ref, computed, watch, nextTick } from "vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import { useGetNormWork, useGetNormExpressions } from "@/services/normService"
import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { formatDate } from "@/lib/dateTime"
import { useElementId } from "@/composables/useElementId"
import type { TreeNode } from "primevue/treenode"
import { Tree } from "primevue"
import { RouterLink, useRoute, useRouter } from "vue-router"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import Button from "primevue/button"

const workEli = useNormWorkEliPathParameter()
const { expressionsHeadingId } = useElementId()
const route = useRoute()
const router = useRouter()
const {
  data: normWork,
  isFetching: isFetchingNormWork,
  error: normWorkError,
} = useGetNormWork(workEli)

const {
  data: normExpressions,
  isFetching: isFetchingNormExpressions,
  error: normExpressionsError,
} = useGetNormExpressions(workEli)

const isLoading = computed(() => {
  return isFetchingNormWork.value || isFetchingNormExpressions.value
})

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => {
  const base = [
    {
      key: "normWorkTitle",
      title: () => normWork.value?.title ?? "...",
    },
  ]

  if (route.name === "DatenbankWorkExpressionDetail") {
    const expressionEli = useNormExpressionEliPathParameter()
    return [
      ...base,
      {
        key: "expressionInforceDate",
        title: () => formatDate(expressionEli.value.pointInTime),
      },
    ]
  }
  return base
})

const selectionKeysExpressions = ref<Record<string, boolean>>({})

const treeNodesExpressions = computed<TreeNode[]>(() =>
  normExpressions.value?.length
    ? normExpressions.value.map<TreeNode>((expr) => {
        const basePath = `/datenbank/${NormExpressionEli.fromString(expr.eli).toString()}`
        const eidPath = route.params.eid ? `/${route.params.eid}` : ""

        return {
          key: expr.eli,
          label:
            formatDate(NormExpressionEli.fromString(expr.eli).pointInTime) +
            (expr.gegenstandslos ? " (gegenstandslos)" : ""),
          data: {
            route: basePath + eidPath,
          },
        }
      })
    : [],
)

const handleExpressionNodeUnselect = (node: TreeNode) => {
  nextTick(() => {
    selectionKeysExpressions.value = { [node.key]: true }
  })
}

const handleExpressionSelect = (node: TreeNode) => {
  selectionKeysExpressions.value = { [node.key]: true }
  router.push(node.data.route)
}

watch(
  [() => route.path, treeNodesExpressions],
  ([currentPath, nodes]) => {
    if (nodes.length) {
      const activeNode = nodes.find((node) => {
        return (
          currentPath.includes(node.data.route) ||
          currentPath === node.data.route
        )
      })

      if (activeNode) {
        selectionKeysExpressions.value = { [activeNode.key]: true }
      } else {
        selectionKeysExpressions.value = {}
      }
    }
  },
  { immediate: true },
)
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :header-back-destination="{ name: 'Datenbank' }"
    :errors="[normWorkError, normExpressionsError]"
    :loading="isLoading"
  >
    <template #headerAction>
      <RouterLink :to="{ name: 'DatenbankAbgabe' }">
        <Button severity="primary" label="Abgeben" />
      </RouterLink>
    </template>
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full w-full overflow-auto"
      >
        <aside class="px-8">
          <h3
            :id="expressionsHeadingId"
            class="ris-subhead-bold border-b border-blue-300 px-16 py-12"
          >
            Expressionen
          </h3>

          <Tree
            v-if="treeNodesExpressions.length"
            v-model:selection-keys="selectionKeysExpressions"
            :value="treeNodesExpressions"
            selection-mode="single"
            :aria-labelledby="expressionsHeadingId"
            @node-select="handleExpressionSelect"
            @node-unselect="handleExpressionNodeUnselect"
          >
            <template #default="{ node }">
              <button
                class="ris-label2-regular w-full cursor-pointer text-left text-black"
                @click="() => handleExpressionSelect(node)"
              >
                {{ node.label }}
              </button>
            </template>
          </Tree>

          <RisEmptyState v-else text-content="Keine Expressionen gefunden" />
        </aside>
      </SplitterPanel>

      <SplitterPanel
        :size="80"
        :min-size="33"
        class="h-full w-full overflow-auto"
      >
        <div
          v-if="$route.name === 'DatenbankWorkDetail'"
          class="flex h-full w-full items-center justify-center bg-gray-100 p-24"
        >
          <RisEmptyState text-content="Keine Expression ausgewählt." />
        </div>
        <RouterView v-else />
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
