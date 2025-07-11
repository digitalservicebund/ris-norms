<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useElementId } from "@/composables/useElementId"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useToast } from "@/composables/useToast"
import { formatDate } from "@/lib/dateTime"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { useGetNorm } from "@/services/normService"
import { useGetNormToc } from "@/services/tocService"
import {
  Button,
  ConfirmDialog,
  Message,
  Splitter,
  SplitterPanel,
  Tree,
} from "primevue"
import type { TreeNode } from "primevue/treenode"
import { computed, ref, watch } from "vue"
import IcBaselineCheck from "~icons/ic/baseline-check"
import { useDokumentXml } from "@/composables/useDokumentXml"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"

const expressionEli = useNormExpressionEliPathParameter()

// BREADCRUMBS

const {
  data: normExpression,
  error: normExpressionError,
  isFinished: normExpressionLoaded,
} = useGetNorm(expressionEli)

const formattedDate = computed(() =>
  formatDate(expressionEli.value.pointInTime),
)

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => [
  {
    key: "norm",
    title: () =>
      normExpression.value
        ? `${normExpression.value.title} (${normExpression.value.shortTitle})`
        : "...",
  },
  {
    key: "expressionDate",
    title: () => (formattedDate.value ? `${formattedDate.value}` : "..."),
  },
  { key: "textbearbeitung", title: "Textbearbeitung" },
])

// TABLE OF CONTENTS
const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(() =>
  DokumentExpressionEli.fromNormExpressionEli(expressionEli.value),
)

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
const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

watch(expressionEli, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    expandedKeys.value = {}
    selectionKeys.value = {}
  }
})

const handleNodeSelect = (node: TreeNode) => {
  selectionKeys.value = { [node.key]: true }
  toggleNode(node)
  gotoEid(node.key)
}

function toggleNode(node: TreeNode) {
  expandedKeys.value[node.key] = !expandedKeys.value[node.key]
}

// EDITOR
const codeEditorRef = ref<InstanceType<typeof RisCodeEditor> | null>(null)
const sentryTraceId = useSentryTraceId()
const { add: addToast, addError: addErrorToast } = useToast()

const gotoEid = (eid: string) => {
  codeEditorRef.value?.scrollToText(`eId="${eid}"`)
}

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
} = useDokumentXml(
  () => DokumentExpressionEli.fromNormExpressionEli(expressionEli.value),
  newExpressionXml,
)

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

const isGegenstandslosExpression = computed(
  () => normExpression.value?.gegenstandslos,
)
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[loadXmlError, tocError, normExpressionError]"
    :loading="!normExpressionLoaded"
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
          <div v-else class="flex-1 overflow-auto">
            <h2 :id="tocHeadingId" class="ris-body1-bold mx-20 mt-16 mb-10">
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
        :size="80"
        :min-size="80"
        class="h-full overflow-hidden bg-gray-100 p-24"
      >
        <div
          v-if="isFetchingXml"
          class="mt-24 flex items-center justify-center"
        >
          <RisLoadingSpinner />
        </div>
        <Message
          v-else-if="isGegenstandslosExpression"
          severity="warn"
          class="mb-16"
        >
          Diese Expression ist gegenstandslos und deshalb nicht bearbeitbar.
        </Message>
        <RisEmptyState
          v-else-if="!currentXml"
          text-content="Keine Artikel gefunden."
          class="m-16 mt-8"
        />
        <RisCodeEditor
          v-else
          ref="codeEditorRef"
          v-model="currentXml"
          class="h-full border-2 border-blue-800"
          :readonly="isGegenstandslosExpression"
        />
      </SplitterPanel>
    </Splitter>

    <template #headerAction>
      <Button
        label="Speichern"
        :disabled="isSaving || !currentXml || isGegenstandslosExpression"
        :loading="isSaving"
        @click="handleSave(currentXml)"
      >
        <template #icon>
          <IcBaselineCheck />
        </template>
      </Button>
    </template>
  </RisViewLayout>

  <ConfirmDialog />
</template>
