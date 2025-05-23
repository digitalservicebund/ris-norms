<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import type { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { useGetZielnormPreview } from "@/services/zielnormExpressionsService"
import { ConfirmDialog, useConfirm } from "primevue"
import { ref } from "vue"
import RisZielnormenPreviewList from "./RisZielnormenPreviewList.vue"

const eli = useDokumentExpressionEliPathParameter()

const confirm = useConfirm()

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(() => eli.value.asNormEli())

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "expressionen-erzeugen", title: "Expressionen erzeugen" },
])

const {
  data: previewData,
  error: previewError,
  isFinished: previewIsFinished,
} = useGetZielnormPreview(() => eli.value.asNormEli())

function beginCreateExpression(eli: NormWorkEli) {
  const previewDataForEli = previewData.value?.find((i) =>
    i.normWorkEli.equals(eli),
  )

  if (!previewDataForEli) return

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
    })
  } else {
    confirm.require({
      header: "Expressionen erzeugen",
      message: "Sind Sie sicher, dass Sie die Expressionen erzeugen möchten?",
      acceptLabel: "Erzeugen",
      rejectLabel: "Abbrechen",
      rejectProps: { text: true },
      defaultFocus: "accept",
    })
  }
}
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, previewError]"
    :loading="!verkuendungHasFinished || !previewIsFinished"
  >
    <h1 class="sr-only">Expressionen erzeugen</h1>

    <RisZielnormenPreviewList
      v-if="previewData?.length"
      :items="previewData"
      @create-expression="beginCreateExpression"
    ></RisZielnormenPreviewList>

    <RisEmptyState
      v-else
      text-content="Bisher werden keine Expressionen erzeugt. Verknüpfen Sie Zielnormen, um neue Expressionen für diese Normen zu erzeugen."
    />
  </RisViewLayout>

  <ConfirmDialog />
</template>
