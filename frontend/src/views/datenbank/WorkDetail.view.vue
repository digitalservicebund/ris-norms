<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { ref, computed } from "vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import { useGetNormWork, useGetNormExpressions } from "@/services/normService"
import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { formatDate } from "@/lib/dateTime"
import { useElementId } from "@/composables/useElementId"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import type { TreeNode } from "primevue/treenode"
import { Tree } from "primevue"
import { RouterLink, useRoute } from "vue-router"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"

const workEli = useNormWorkEliPathParameter()
const { expressionsHeadingId } = useElementId()
const route = useRoute()

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
    const expressionEli = useDokumentExpressionEliPathParameter("expression")
    console.log(expressionEli)
    return [
      ...base,
      {
        key: "expressionInforceDate",
        title: () =>
          formatDate(
            DokumentExpressionEli.fromString(
              expressionEli.value?.toString() ?? "",
            ).pointInTime,
          ),
      },
    ]
  }
  return base
})

function getInForceDateFromEli(eli: string) {
  try {
    return formatDate(NormExpressionEli.fromString(eli).pointInTime)
  } catch {
    return eli
  }
}

const selectionKeysExpressions = ref<Record<string, boolean>>({})

const treeNodesExpressions = computed<TreeNode[]>(() =>
  normExpressions.value?.length
    ? normExpressions.value.map<TreeNode>((expr) => ({
        key: expr.eli,
        label:
          getInForceDateFromEli(expr.eli) +
          (expr.gegenstandslos ? " (gegenstandslos)" : ""),
        data: {
          route: `/datenbank/${workEli.value}/expression/${DokumentExpressionEli.fromNormExpressionEli(expr.eli).toString()}`,
        },
      }))
    : [],
)

const handleExpressionNodeSelect = (node: TreeNode) => {
  selectionKeysExpressions.value = { [node.key]: true }
}
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :header-back-destination="{ name: 'Datenbank' }"
    :errors="[normWorkError, normExpressionsError]"
    :loading="isLoading"
  >
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
            @node-select="handleExpressionNodeSelect"
          >
            <template #default="{ node }">
              <RouterLink
                :to="node.data.route"
                class="ris-label2-regular w-full text-black"
              >
                {{ node.label }}
              </RouterLink>
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

<style scoped>
:deep(.selected) {
  background-color: var(--color-element-select-selected-background);
  outline: 2px solid var(--color-element-select-selected-border);
  outline-offset: 2px;
}
</style>
