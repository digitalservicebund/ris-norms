<script setup lang="ts">
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { useGetZielnormPreview } from "@/services/zielnormPreviewService"
import { ref } from "vue"
import RisZielnormenPreviewList from "./RisZielnormenPreviewList.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"

const eli = useDokumentExpressionEliPathParameter()

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
    ></RisZielnormenPreviewList>

    <RisEmptyState
      v-else
      text-content="Bisher werden keine Expressionen erzeugt. Verknüpfen Sie Zielnormen, um neue Expressionen für diese Normen zu erzeugen."
    />
  </RisViewLayout>
</template>
