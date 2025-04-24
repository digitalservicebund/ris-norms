<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useElementId } from "@/composables/useElementId"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useGetNormToc } from "@/services/tocService"
import Button from "primevue/button"
import Tree from "primevue/tree"
import type { TreeNode } from "primevue/treenode"
import { computed } from "vue"
import IcBaselineToc from "~icons/ic/baseline-toc"
import IcBaselineUnfoldLess from "~icons/ic/baseline-unfold-less"
import IcBaselineUnfoldMore from "~icons/ic/baseline-unfold-more"

const { eli } = defineProps<{
  /** ELI of the document whose contents should be shown */
  eli: DokumentExpressionEli
}>()

/** eId of the selected element */
const eid = defineModel<string>("eid")

const { documentExplorerHeadingId } = useElementId()

const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(eli)

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
</script>

<template>
  <aside
    class="flex flex-col overflow-hidden border-gray-400 bg-white"
    :aria-labelledby="documentExplorerHeadingId"
  >
    <div
      class="mb-8 flex items-center gap-4 border-b border-gray-400 px-4 py-4"
    >
      <div class="flex h-48 w-48 items-center justify-center text-gray-600">
        <IcBaselineToc />
      </div>

      <h1 :id="documentExplorerHeadingId" class="ris-subhead-bold">
        Änderungsgesetz
      </h1>

      <Button class="ml-auto" disabled text>
        <template #icon>
          <IcBaselineUnfoldLess v-if="false" />
          <IcBaselineUnfoldMore v-else />
        </template>
      </Button>
    </div>

    <div v-if="tocIsFetching" class="mt-24 flex items-center justify-center">
      <RisLoadingSpinner />
    </div>

    <RisErrorCallout v-else-if="tocError" :error="tocError" class="m-16 mt-8" />

    <RisEmptyState
      v-else-if="!toc || !toc.length"
      text-content="Keine Artikel gefunden."
      class="m-16 mt-8"
    />

    <div v-else class="flex-1 overflow-auto">
      <Tree :value="treeNodes">
        <template #default="{ node }">
          <button
            class="cursor-pointer pl-4 text-left group-hover:underline!"
            @click="eid = node.key"
          >
            <span class="block">{{ node.label }}</span>
            <span class="block font-normal">{{ node.data.sublabel }}</span>
          </button>
        </template>
      </Tree>
    </div>
  </aside>
</template>
