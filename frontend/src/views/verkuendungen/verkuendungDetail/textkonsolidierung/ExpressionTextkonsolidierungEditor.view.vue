<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisDokumentExplorer from "@/components/RisDokumentExplorer.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import { useDokumentXml } from "@/composables/useDokumentXml"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useToast } from "@/composables/useToast"
import { useZeitgrenzenHighlightClasses } from "@/composables/useZeitgrenzenHighlightClasses"
import {
  useZielnormReferences,
  type EditableZielnormReference,
} from "@/composables/useZielnormReferences"
import { formatDate } from "@/lib/dateTime"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { useGetNormToc } from "@/services/tocService"
import {
  useGetVerkuendungService,
  useGetZielnormReferences,
} from "@/services/verkuendungService"
import { useGetZeitgrenzen } from "@/services/zeitgrenzenService"
import { useGetZielnormPreview } from "@/services/zielnormExpressionsService"
import { useGroupedZielnormen } from "@/views/verkuendungen/verkuendungDetail/useGroupedZielnormen"
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
import { RouterLink } from "vue-router"
import IcBaselineArrowBack from "~icons/ic/baseline-arrow-back"
import IcBaselineArrowForward from "~icons/ic/baseline-arrow-forward"
import IcBaselineCheck from "~icons/ic/baseline-check"

const verkuendungEli = useDokumentExpressionEliPathParameter("verkuendung")
const expressionEli = useDokumentExpressionEliPathParameter("expression")

const announcementNormExpressionEli = computed(() =>
  verkuendungEli.value.asNormEli(),
)

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
} = useGetNorm(() => expressionEli.value.asNormEli())

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => [
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

const { tocHeadingId, expressionPointInTimeLabelId } = useElementId()
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

const pointInTime = computed(() => {
  return NormExpressionEli.fromString(expressionEli.value.toString())
    .pointInTime
})

const formattedDate = computed(() => formatDate(pointInTime.value))

const { data: zielnormen } = useGetZielnormReferences(
  announcementNormExpressionEli,
)
const groupedZielnormen = useGroupedZielnormen(zielnormen)

// NAVIGATION
const currentEli = computed(() => expressionEli.value.toString())

const currentZielnormGroup = computed(() => {
  return groupedZielnormen.value?.find((group) =>
    group.expressions.some((expr) => expr.eli === currentEli.value),
  )
})

const sequence = computed(() => {
  if (!currentZielnormGroup.value) return []

  return currentZielnormGroup.value.expressions
    .filter((expr) => {
      const match = previewData.value
        ?.flatMap((d) => d.expressions)
        .find((e) => e.normExpressionEli.toString() === expr.eli)
      return !match?.isGegenstandslos
    })
    .map((expr) => expr.eli)
})

const currentIndex = computed(() => sequence.value.indexOf(currentEli.value))

const hasPrev = computed(() => currentIndex.value > 0)

const hasNext = computed(
  () =>
    currentIndex.value >= 0 && currentIndex.value < sequence.value.length - 1,
)

const previousGuid = computed(() => normExpression.value?.vorherigeVersionId)
const nextGuid = computed(() => normExpression.value?.nachfolgendeVersionId)

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
} = useDokumentXml(expressionEli, newExpressionXml)

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
const { data: zeitgrenzen, error: zeitgrenzenError } = useGetZeitgrenzen(
  computed(() => expressionEli.value.asNormEli()),
)

const eIdsToEdit = ref<string[]>([])

const editedZielnormReference = ref<EditableZielnormReference>()

const { zielnormReferences, zielnormReferencesForEid } = useZielnormReferences(
  () => verkuendungEli.value.asNormEli(),
)

const colorIndex = computed(() => {
  const expressionEliStr = expressionEli.value.toString()

  for (const zielnorm of groupedZielnormen.value) {
    const index = zielnorm.expressions.findIndex(
      (expr) => expr.eli === expressionEliStr,
    )
    if (index !== -1) return index
  }

  return undefined
})

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

const { data: previewData } = useGetZielnormPreview(() =>
  verkuendungEli.value.asNormEli(),
)

const isGegenstandslosExpression = computed(() => {
  if (!previewData.value || !Array.isArray(previewData.value)) return false

  for (const item of previewData.value) {
    const found = item.expressions.find(
      (expr) =>
        expr.normExpressionEli.toString() === expressionEli.value.toString(),
    )
    if (found?.isGegenstandslos) return true
  }

  return false
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
          <div v-else class="flex-1 overflow-auto">
            <div
              class="sticky top-0 z-10 flex items-center justify-between gap-8 border-b border-gray-400 p-16"
            >
              <RouterLink
                :to="`/verkuendungen/${verkuendungEli}/textkonsolidierung/${previousGuid}`"
                :class="[
                  'focus:outline-none',
                  hasPrev
                    ? 'text-blue-800 hover:text-blue-900 focus:ring-2 focus:ring-blue-800'
                    : 'pointer-events-none cursor-not-allowed text-gray-800 opacity-50',
                ]"
                :aria-disabled="!hasPrev"
              >
                <IcBaselineArrowBack />
                <span class="sr-only">Vorherige Version</span>
              </RouterLink>

              <span :id="expressionPointInTimeLabelId" class="sr-only">
                Zeitpunkt der Gültigkeit dieser Fassung
              </span>

              <div
                class="flex items-center gap-6"
                :aria-labelledby="expressionPointInTimeLabelId"
              >
                <span
                  >Gültig ab:
                  <span class="ris-body2-bold">{{ formattedDate }}</span></span
                >
                <RisHighlightColorSwatch :color-index="colorIndex" />
              </div>

              <RouterLink
                :to="`/verkuendungen/${verkuendungEli}/textkonsolidierung/${nextGuid}`"
                :class="[
                  'focus:outline-none',
                  hasNext
                    ? 'text-blue-800 hover:text-blue-900 focus:ring-2 focus:ring-blue-800'
                    : 'pointer-events-none cursor-not-allowed text-gray-800 opacity-50',
                ]"
                :aria-disabled="!hasNext"
              >
                <IcBaselineArrowForward />
                <span class="sr-only">Nächste Version</span>
              </RouterLink>
            </div>
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
        :disabled="isSaving || !currentXml || isGegenstandslosExpression"
        :loading="isSaving"
        @click="handleSave(currentXml)"
      >
        <template #icon><IcBaselineCheck /></template>
      </Button>
    </template>
  </RisViewLayout>

  <ConfirmDialog />
</template>
