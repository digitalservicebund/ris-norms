<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useToast } from "@/composables/useToast"
import type { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import {
  useCreateZielnormExpressions,
  useGetZielnormPreview,
} from "@/services/zielnormExpressionsService"
import { cloneDeep, isEqual } from "lodash"
import { ConfirmDialog, useConfirm } from "primevue"
import { ref, watch, watchEffect } from "vue"
import RisZielnormenPreviewList from "./RisZielnormenPreviewList.vue"

const eli = useDokumentExpressionEliPathParameter()

const confirm = useConfirm()
const traceId = useSentryTraceId()
const { add: addToast, addError: addErrorToast } = useToast()

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "expressionen-erzeugen", title: "Expressionen erzeugen" },
])

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungIsFinished,
} = useGetVerkuendungService(() => eli.value.asNormEli())

const {
  data: previewData,
  error: previewError,
  execute: fetchPreviewData,
  isFinished: previewIsFinished,
  isFetching: previewIsFetching,
} = useGetZielnormPreview(() => eli.value.asNormEli())

// Loading state for the view that is only true for the initial load.
// Prevents flickering when syncing data before submitting the create
// request.
const initialLoad = ref(true)

watchEffect(() => {
  if (verkuendungIsFinished.value && previewIsFinished.value) {
    initialLoad.value = false
  }
})

// Creating expressions -----------------------------------

const zielnormWorkEli = ref<NormWorkEli>()

const {
  data: createdExpressions,
  error: createExpressionsError,
  execute: createExpressions,
  isFetching: isCreatingExpressions,
  isFinished: finishedCreatingExpressions,
} = useCreateZielnormExpressions(() => eli.value.asNormEli(), zielnormWorkEli)

async function beginCreateExpression(eli: NormWorkEli) {
  if (!previewData.value) return

  const dataIndex =
    previewData.value.findIndex((i) => i.normWorkEli.equals(eli)) ?? -1

  if (dataIndex < 0) return

  const previewDataForEli = cloneDeep(previewData.value[dataIndex])
  try {
    await fetchPreviewData(true)
  } catch {
    // Error handling will be managed automatically by the existing previewError
    // handling
    return
  }

  if (!isEqual(previewData.value[dataIndex], previewDataForEli)) {
    addToast({
      summary: "Die Daten haben sich geändert",
      detail:
        "Bitte prüfen Sie die neuen Daten auf Korrektheit und versuchen Sie es dann erneut.",
      severity: "warn",
    })

    return
  }

  const needsConfirmOverride = previewDataForEli.expressions.some(
    (i) => i.isCreated && !i.isGegenstandslos,
  )

  if (needsConfirmOverride) {
    confirm.require({
      header: "Expressionen erneut erzeugen",
      message:
        "Sind Sie sicher, dass Sie die Expressionen erneut erzeugen und damit bereits erzeugte Expressionen überschrieben möchten?",
      acceptLabel: "Erneut erzeugen",
      acceptProps: { text: true },
      rejectLabel: "Abbrechen",
      defaultFocus: "reject",
      accept: () => {
        zielnormWorkEli.value = eli
        createExpressions()
      },
    })
  } else {
    confirm.require({
      header: "Expressionen erzeugen",
      message: "Sind Sie sicher, dass Sie die Expressionen erzeugen möchten?",
      acceptLabel: "Erzeugen",
      rejectLabel: "Abbrechen",
      rejectProps: { text: true },
      defaultFocus: "accept",
      accept: () => {
        zielnormWorkEli.value = eli
        createExpressions()
      },
    })
  }
}

watch(createdExpressions, (newVal) => {
  if (!zielnormWorkEli.value || !previewData.value?.length || !newVal) return
  const eli = zielnormWorkEli.value

  const createdExpressionsIndex = previewData.value?.findIndex((i) =>
    i.normWorkEli.equals(eli),
  )

  if (createdExpressionsIndex >= 0) {
    previewData.value = previewData.value.with(createdExpressionsIndex, newVal)
  }
})

watch(createExpressionsError, (newVal) => {
  if (newVal) addErrorToast(createExpressionsError, { traceId })
})

watch(finishedCreatingExpressions, (newVal) => {
  if (newVal && !createExpressionsError.value) {
    addToast({
      summary: "Expressionen erfolgreich erzeugt",
      detail:
        "Die neuen Expressionen für diese Zielnorm wurden erfolgreich erzeugt.",
      severity: "success",
    })
  }
})
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, previewError]"
    :loading="initialLoad"
  >
    <h1 class="sr-only">Expressionen erzeugen</h1>

    <RisZielnormenPreviewList
      v-if="previewData?.length"
      :items="previewData"
      :loading="isCreatingExpressions || previewIsFetching"
      @create-expression="beginCreateExpression"
    ></RisZielnormenPreviewList>

    <RisEmptyState
      v-else
      text-content="Bisher werden keine Expressionen erzeugt. Verknüpfen Sie Zielnormen, um neue Expressionen für diese Normen zu erzeugen."
    />
  </RisViewLayout>

  <ConfirmDialog />
</template>
