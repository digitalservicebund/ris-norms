<script setup lang="ts">
import { ref, computed } from "vue"
import { useGetNormHtml } from "@/services/normService"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { useElementId } from "@/composables/useElementId"
import RisLawPreview from "@/components/RisLawPreview.vue"
import Button from "primevue/button"
import { useGetNormToc } from "@/services/tocService"
import type { TreeNode } from "primevue/treenode"
import { Tree } from "primevue"
import { RouterLink } from "vue-router"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"

const expressionEli = useDokumentExpressionEliPathParameter("expression")
const { tocHeadingId, expressionHtmlHeadingId } = useElementId()

const {
  data: normExpressionHtml,
  isFetching: isFetchingNormExpressionHtml,
  error: normExpressionHtmlError,
} = useGetNormHtml(() => expressionEli.value.asNormEli())

const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(expressionEli)

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

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
</script>

<template>
  <Splitter layout="horizontal" class="h-full w-full">
    <SplitterPanel
      :size="30"
      :min-size="20"
      class="h-full w-full overflow-auto px-8 py-12"
    >
      <h2 :id="tocHeadingId" class="ris-subhead-bold mx-20 mb-10">
        Inhaltsübersicht
      </h2>

      <div v-if="tocIsFetching" class="my-16 flex justify-center">
        <RisLoadingSpinner />
      </div>

      <RisErrorCallout v-else-if="tocError" :error="tocError" />

      <Tree
        v-else
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
            <span class="block font-normal">{{ node.data.sublabel }}</span>
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
            <RouterLink :to="`/${expressionEli}/metadata`">
              <Button severity="primary" label="Metadaten bearbeiten" />
            </RouterLink>
            <RouterLink :to="`/datenbank/textbearbeitung/${expressionEli}`">
              <Button severity="primary" label="Text bearbeiten" />
            </RouterLink>
          </div>
        </div>
        <hr class="mb-0" />
        <div
          v-if="isFetchingNormExpressionHtml"
          class="my-16 flex justify-center"
        >
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout
          v-else-if="normExpressionHtmlError"
          :error="normExpressionHtmlError"
        />

        <RisLawPreview
          v-else
          ref="previewRef"
          :arrow-focus="false"
          :content="normExpressionHtml ?? ''"
          :selected="selectedEids"
          class="-mx-24"
        />
      </section>
    </SplitterPanel>
  </Splitter>
</template>
