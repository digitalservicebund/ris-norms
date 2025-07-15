<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { Splitter, SplitterPanel } from "primevue"
import RisMetadataEditorNavigation from "@/components/metadata-editor/RisMetadataEditorNavigation.vue"
import { computed } from "vue"
import { useGetNorm } from "@/services/normService"
import { formatDate } from "@/lib/dateTime"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"

const expressionEli = useNormExpressionEliPathParameter()
const selectedEid = useEidPathParameter()

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
    to: { name: "DatenbankWorkDetail" },
  },
  {
    key: "expressionDate",
    title: () => (formattedDate.value ? `${formattedDate.value}` : "..."),
    to: { name: "DatenbankWorkExpressionDetail" },
  },
  { key: "metadaten", title: "Metadaten" },
])
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[normExpressionError]"
    :loading="!normExpressionLoaded"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full overflow-auto bg-white"
      >
        <RisMetadataEditorNavigation
          route-name-editor-element="DatenbankMetadatenEditorElement"
          route-name-editor-outline-element="DatenbankMetadatenEditorOutlineElement"
          route-name-editor-rahmen="DatenbankMetadatenEditorRahmen"
          :norm-expression-eli="expressionEli"
          :selected-e-id="selectedEid"
        />
      </SplitterPanel>
      <SplitterPanel
        :size="80"
        :min-size="20"
        class="h-full overflow-hidden bg-gray-100"
      >
        <RouterView />
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>
</template>
