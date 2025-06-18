<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import type { AknElementClickEvent } from "@/components/RisLawPreview.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useElementId } from "@/composables/useElementId"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useGetElementHtml } from "@/services/elementService"
import { useGetNormToc } from "@/services/tocService"
import { useMultiSelection } from "@/composables/useMultiSelection"
import Button from "primevue/button"
import Tree from "primevue/tree"
import type { TreeNode } from "primevue/treenode"
import { computed } from "vue"
import IcBaselineToc from "~icons/ic/baseline-toc"
import IcBaselineUnfoldLess from "~icons/ic/baseline-unfold-less"
import IcBaselineUnfoldMore from "~icons/ic/baseline-unfold-more"

const {
  eli,
  eIdClasses = {},
  disableSelection,
} = defineProps<{
  /** ELI of the document whose contents should be shown */
  eli: DokumentExpressionEli

  /** Highlighting that should be applied to elements in the explorer */
  eIdClasses?: { [eId: string]: string[] }

  /** Disable selection of elements */
  disableSelection?: boolean
}>()

/** eId of the selected element */
const eid = defineModel<string>("eid")

/** List of eIds that should be edited */
const eidsToEdit = defineModel<string[]>("eids-to-edit")

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
  error: artikelHtmlError,
  isFetching: artikelHtmlIsFetching,
} = useGetElementHtml(eli, eid)

const { values, toggle, clear } = useMultiSelection<string>()

function onSelect({ originalEvent, eid }: AknElementClickEvent) {
  if (disableSelection) return

  if (originalEvent.metaKey || originalEvent.ctrlKey) toggle(eid)
  else {
    clear()
    toggle(eid)
  }

  eidsToEdit.value = values.value
}
</script>

<template>
  <aside
    class="flex flex-col overflow-hidden border-gray-400 bg-white"
    :aria-labelledby="documentExplorerHeadingId"
  >
    <div class="flex items-center gap-4 border-b border-gray-400 px-4 py-4">
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

      <Button class="ml-auto" disabled text aria-label="Erweitern/verkleinern">
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
        v-if="artikelHtmlIsFetching"
        class="mt-24 flex items-center justify-center"
      >
        <RisLoadingSpinner />
      </div>

      <RisErrorCallout
        v-else-if="artikelHtmlError"
        :error="artikelHtmlError"
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
          :selected="eidsToEdit"
          :e-id-classes="eIdClasses"
          @click:akn:paragraph="onSelect"
          @click:akn:point="onSelect"
        />
      </div>
    </template>
  </aside>
</template>

<style scoped>
@layer components {
  :deep(.akn-paragraph),
  :deep(.akn-point) {
    background-color: var(--color-gray-100);
    outline-offset: calc(var(--spacing-2) * -1);
    outline: 2px dotted var(--color-gray-600);
    padding-inline: var(--spacing-2);

    &:hover,
    &:focus {
      background-color: var(--color-gray-200);
      outline-color: var(--color-blue-800);
    }

    &.selected {
      background-color: var(--color-gray-400);
      outline-color: var(--color-blue-800);
      outline-style: solid;
    }
  }
}
</style>
