<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import { useZeitgrenzenHighlightClasses } from "@/composables/useZeitgrenzenHighlightClasses"
import {
  useZielnormReferences,
  type EditableZielnormReference,
} from "@/composables/useZielnormReferences"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import IcBaselineCheck from "~icons/ic/baseline-check"
import { useGetZeitgrenzen } from "@/services/zeitgrenzenService"
import { ConfirmDialog, Splitter, SplitterPanel } from "primevue"
import { computed, ref, watch } from "vue"
import RisDokumentExplorer from "@/components/RisDokumentExplorer.vue"
import Button from "primevue/button"
import Tree from "primevue/tree"
import { useGetNormToc } from "@/services/tocService"
import type { TreeNode } from "primevue/treenode"
import { useGetNorm } from "@/services/normService"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { useNormXml } from "@/composables/useNormXml"
import { useErrorToast } from "@/lib/errorToast"
import { useToast } from "primevue/usetoast"
import { useSentryTraceId } from "@/composables/useSentryTraceId"

const verkuendungEli = useDokumentExpressionEliPathParameter("verkuendung")
const expressionEli = useDokumentExpressionEliPathParameter("expression")

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const handleNodeSelect = (node: TreeNode) => {
  selectionKeys.value = { [node.key]: true }
  toggleNode(node)
  gotoEid(node.key)
}

function toggleNode(node: TreeNode) {
  expandedKeys.value[node.key] = !expandedKeys.value[node.key]
}

const codeEditorRef = ref<InstanceType<typeof RisCodeEditor> | null>(null)

const gotoEid = (eid: string) => {
  codeEditorRef.value?.scrollToText(`eId="${eid}"`)
}

// BREADCRUMBS
const sourceVerkuendungNormEli = computed(() => {
  return verkuendungEli.value.asNormEli()
})

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(sourceVerkuendungNormEli)

const {
  data: normExpression,
  error: normExpressionError,
  isFinished: normExpressionLoaded,
} = useGetNorm(expressionEli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${verkuendungEli.value}`,
  },
  {
    key: "norm",
    title: () =>
      normExpression.value
        ? `${normExpression.value.title} (${normExpression.value.shortTitle})`
        : "...",
  },
  { key: "textkonsolidierung", title: "Textkonsolidierung" },
])

// TABLE OF CONTENTS
const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(expressionEli)

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
const { tocHeadingId } = useElementId()

// EDITOR

const sentryTraceId = useSentryTraceId()
const { add: addToast } = useToast()
const { addErrorToast } = useErrorToast()

function showToast() {
  if (saveError.value) {
    addErrorToast(saveError, { traceId: sentryTraceId })
  } else {
    addToast({
      summary: "Gespeichert!",
      severity: "success",
    })
  }
}

const newExpressionXml = ref()
const {
  data: xml,
  isFetching: isFetchingXml,
  error: loadXmlError,
  update: {
    execute: save,
    isFetching: isSaving,
    isFinished: hasSaved,
    error: saveError,
  },
} = useNormXml(expressionEli, newExpressionXml)

const currentXml = ref("")
watch(xml, (xml) => {
  if (xml) {
    currentXml.value = xml
  }
})

function handleSave(xml: string) {
  newExpressionXml.value = xml
  save()
}

watch(hasSaved, (finished) => {
  if (finished) {
    showToast()
  }
})

// RIS DOCUMENT EXPLORER
const { data: zeitgrenzen, error: zeitgrenzenError } =
  useGetZeitgrenzen(expressionEli)

const eIdsToEdit = ref<string[]>([])

const editedZielnormReference = ref<EditableZielnormReference>()

const { zielnormReferences, zielnormReferencesForEid } = useZielnormReferences(
  () => verkuendungEli.value.asNormEli(),
)

const isSelected = (eid: string) => {
  return eIdsToEdit.value.includes(eid)
}

const highlightClasses = useZeitgrenzenHighlightClasses(
  () => [...(zielnormReferences.value ?? [])],
  isSelected,
  () => zeitgrenzen.value ?? [],
)

watch(eIdsToEdit, (val) => {
  if (!val?.length) editedZielnormReference.value = undefined
  else editedZielnormReference.value = zielnormReferencesForEid(...val)
})
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[
      loadXmlError,
      tocError,
      verkuendungError,
      normExpressionError,
      zeitgrenzenError,
    ]"
    :loading="!verkuendungHasFinished || !normExpressionLoaded"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full overflow-auto bg-white"
      >
        <aside :aria-labelledby="tocHeadingId">
          <div
            v-if="tocIsFetching"
            class="mt-24 flex items-center justify-center"
          >
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
          <div v-else class="flex-1 overflow-auto p-10">
            <h2 :id="tocHeadingId" class="ris-body1-bold mb-10 pt-10 pl-20">
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
          </div>
        </aside>
      </SplitterPanel>

      <SplitterPanel
        :size="40"
        :min-size="40"
        class="h-full overflow-hidden bg-gray-100 p-24"
      >
        <div
          v-if="isFetchingXml"
          class="mt-24 flex items-center justify-center"
        >
          <RisLoadingSpinner />
        </div>

        <RisEmptyState
          v-else-if="!currentXml"
          text-content="Keine Artikel gefunden."
          class="m-16 mt-8"
        />
        <RisCodeEditor
          ref="codeEditorRef"
          v-model="currentXml"
          class="h-full border-2 border-blue-800"
        />
      </SplitterPanel>

      <SplitterPanel :size="40" :min-size="40" class="h-full overflow-auto">
        <RisDokumentExplorer
          v-model:eids-to-edit="eIdsToEdit"
          v-model:eli="verkuendungEli"
          class="h-full"
          :e-id-classes="highlightClasses"
          :disable-selection="true"
        />
      </SplitterPanel>
    </Splitter>

    <template #headerAction>
      <Button
        label="Speichern"
        :disabled="isSaving || !currentXml"
        :loading="isSaving"
        @click="handleSave(currentXml)"
      >
        <template #icon><IcBaselineCheck /></template>
      </Button>
    </template>
  </RisViewLayout>

  <ConfirmDialog />
</template>
