<script setup lang="ts">
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { ref, computed } from "vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import {
  useGetNormWork,
  useGetNormExpressions,
  useGetNormHtml,
} from "@/services/normService"
import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { formatDate } from "@/lib/dateTime"
import { useElementId } from "@/composables/useElementId"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import RisLawPreview from "@/components/RisLawPreview.vue"
import Button from "primevue/button"
import { useGetNormToc } from "@/services/tocService"
import type { TreeNode } from "primevue/treenode"
import { Tree } from "primevue"
import { RouterLink } from "vue-router"

const workEli = useNormWorkEliPathParameter()
const selectedExpression = ref<string | null>(null)
const { expressionsHeadingId, tocHeadingId, expressionHtmlHeadingId } =
  useElementId()

const selectedExpressionEli = computed(() => {
  if (!selectedExpression.value) return undefined
  return DokumentExpressionEli.fromNormExpressionEli(selectedExpression.value)
})

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

const {
  data: normExpressionHtml,
  isFetching: isFetchingNormExpressionHtml,
  error: normExpressionHtmlError,
} = useGetNormHtml(selectedExpressionEli)

const isLoading = computed(() => {
  return (
    isFetchingNormWork.value ||
    isFetchingNormExpressions.value ||
    isFetchingNormExpressionHtml.value ||
    tocIsFetching.value
  )
})

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => {
  const base = [
    {
      key: "normWorkTitle",
      title: () => normWork.value?.title ?? "...",
    },
  ]
  if (selectedExpression.value) {
    return [
      ...base,
      {
        key: "expressionInforceDate",
        title: () => getInForceDateFromEli(selectedExpression.value ?? ""),
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

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(selectedExpressionEli)

const treeNodes = computed<TreeNode[]>(() =>
  toc.value?.length
    ? toc.value.map<TreeNode>((i) => ({
        key: i.id,
        label: i.marker || "Unbenanntes Element",
        data: { sublabel: i.heading || null },
        children: [],
      }))
    : [],
)
const handleNodeSelect = (node: TreeNode) => {
  selectionKeys.value = { [node.key]: true }
  toggleNode(node)
  gotoEid(node.key)
}

const selectedEids = ref<string[]>([])

const gotoEid = (eid: string) => {
  previewRef.value?.scrollToText(eid)
  selectedEids.value = [eid]
}
const previewRef = ref<InstanceType<typeof RisLawPreview> | null>(null)

function toggleNode(node: TreeNode) {
  expandedKeys.value[node.key] = !expandedKeys.value[node.key]
}

const selectionKeysExpressions = ref<Record<string, boolean>>({})

const treeNodesExpressions = computed<TreeNode[]>(() =>
  normExpressions.value?.length
    ? normExpressions.value.map<TreeNode>((expr) => ({
        key: expr.eli,
        label:
          getInForceDateFromEli(expr.eli) +
          (expr.gegenstandslos ? " (gegenstandslos)" : ""),
      }))
    : [],
)

const handleExpressionNodeSelect = (node: TreeNode) => {
  selectionKeysExpressions.value = { [node.key]: true }
  selectedExpression.value = node.key
}
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :header-back-destination="{ name: 'Datenbank' }"
    :errors="[
      normWorkError,
      normExpressionsError,
      normExpressionHtmlError,
      tocError,
    ]"
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
              <span>{{ node.label }}</span>
            </template>
          </Tree>

          <RisEmptyState v-else text-content="Keine Expressionen gefunden" />
        </aside>
      </SplitterPanel>

      <template v-if="!selectedExpression">
        <SplitterPanel
          :size="80"
          :min-size="33"
          class="flex w-full items-center justify-center bg-gray-100 p-24"
        >
          <RisEmptyState text-content="Keine Expression ausgewählt." />
        </SplitterPanel>
      </template>
      <template v-else>
        <SplitterPanel :size="80" class="h-full w-full overflow-auto">
          <Splitter layout="horizontal" class="h-full w-full">
            <SplitterPanel
              :size="30"
              :min-size="20"
              class="h-full w-full overflow-auto px-8 py-16"
            >
              <h2 :id="tocHeadingId" class="ris-subhead-bold mx-20 mb-10">
                Inhaltsübersicht
              </h2>
              <Tree
                v-model:expanded-keys="expandedKeys"
                v-model:selection-keys="selectionKeys"
                :aria-labelledby="tocHeadingId"
                :value="treeNodes"
                selection-mode="single"
                @node-select="handleNodeSelect"
              >
                <template #default="{ node }">
                  <button
                    class="cursor-pointer pl-4 text-left group-hover:underline!"
                    @click="() => toggleNode(node)"
                  >
                    <span class="block">{{ node.label }}</span>
                    <span class="block font-normal">{{
                      node.data.sublabel
                    }}</span>
                  </button>
                </template>
              </Tree>
            </SplitterPanel>
            <SplitterPanel
              :size="70"
              :min-size="33"
              class="h-full w-full overflow-auto p-24"
            >
              <section :aria-labelledby="expressionHtmlHeadingId">
                <div class="flex flex-row items-center justify-between">
                  <h2 :id="expressionHtmlHeadingId" class="ris-subhead-bold">
                    Vorschau
                  </h2>
                  <div class="flex flex-row gap-8">
                    <RouterLink :to="`/${selectedExpressionEli}/metadata`">
                      <Button severity="primary" label="Metadaten bearbeiten" />
                    </RouterLink>

                    <Button
                      severity="primary"
                      label="Text bearbeiten"
                      disabled
                    />
                  </div>
                </div>
                <hr class="mb-0" />
                <RisLawPreview
                  ref="previewRef"
                  :arrow-focus="false"
                  :content="normExpressionHtml ?? ''"
                  :selected="selectedEids"
                  class="-mx-24"
                />
              </section>
            </SplitterPanel>
          </Splitter>
        </SplitterPanel>
      </template>
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
