<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useElementId } from "@/composables/useElementId"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useGetElementHtml } from "@/services/elementService"
import { useGetNormToc } from "@/services/tocService"
import { useAknElementEidSelection } from "@/views/amending-law/articles/editor/useAknElementEidSelection"
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

const { documentExplorerHeadingId, tocHeadingId } = useElementId()

// Table of contents --------------------------------------

const showToc = computed(() => !eid.value)

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

// Detail view --------------------------------------------

const {
  data: artikelHtml,
  error: artikelError,
  isFetching: artikelIsFetching,
} = useGetElementHtml(eli, eid)

// const artikelDocument = computed(() =>
//   artikelHtml.value ? xmlStringToDocument(artikelHtml.value) : undefined,
// )

// const elementEids = computed(() =>
//   artikelDocument.value ? getEidsOfElementType(artikelDocument.value, "p") : [],
// )

const elementEids = computed(() => {
  if (!artikelHtml.value) return []

  const eIds: string[] = []
  const dom = new DOMParser().parseFromString(artikelHtml.value, "text/html")

  dom.querySelectorAll(".akn-p").forEach((el) => {
    if (!(el instanceof HTMLElement) || !el.dataset.eid) return
    eIds.push(el.dataset.eid)
  })

  console.log(eIds)

  return eIds
})

const { values: selectedValues, handleAknElementClick } =
  useAknElementEidSelection(elementEids)
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
        <Button
          :disabled="showToc"
          aria-label="Inhaltsverzeichnis anzeigen"
          text
          title="Inhaltsverzeichnis anzeigen"
          @click="eid = undefined"
        >
          <template #icon>
            <IcBaselineToc />
          </template>
        </Button>
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

    <!-- Table of contents -->
    <template v-if="showToc">
      <div v-if="tocIsFetching" class="mt-24 flex items-center justify-center">
        <RisLoadingSpinner />
      </div>

      <RisErrorCallout
        v-else-if="tocError"
        :error="tocError"
        class="m-16 mt-8"
      />

      <RisEmptyState
        v-else-if="!toc || !toc.length"
        text-content="Keine Artikel gefunden."
        class="m-16 mt-8"
      />

      <div v-else class="flex-1 overflow-auto">
        <span :id="tocHeadingId" class="sr-only">Inhaltsverzeichnis</span>
        <Tree :aria-labelledby="tocHeadingId" :value="treeNodes">
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
    </template>

    <!-- Article detail -->
    <template v-else>
      <div
        v-if="artikelIsFetching"
        class="mt-24 flex items-center justify-center"
      >
        <RisLoadingSpinner />
      </div>

      <RisErrorCallout
        v-else-if="artikelError"
        :error="artikelError"
        class="m-16 mt-8"
      />

      <RisEmptyState
        v-else-if="!artikelHtml"
        text-content="Der Artikel hat keinen Inhalt."
        class="m-16 mt-8"
      />

      <div v-else class="flex-1 overflow-auto">
        <RisLawPreview
          :content="artikelHtml"
          :selected="selectedValues"
          @click:akn:p="handleAknElementClick"
        />
      </div>
    </template>
  </aside>
</template>

<style scoped>
:deep(.akn-p) {
  background-color: var(--color-gray-100);
  border: 1px dotted var(--color-gray-600);
  outline-offset: calc(var(--spacing-2) * -1);
  outline: 2px dotted transparent;
  padding-inline: var(--spacing-2);

  &:hover {
    background-color: var(--color-gray-200);
    outline-color: var(--color-blue-800);
  }
}

:deep(.akn-p.selected) {
  outline-color: var(--color-blue-800);
  outline-style: solid;
}
</style>
