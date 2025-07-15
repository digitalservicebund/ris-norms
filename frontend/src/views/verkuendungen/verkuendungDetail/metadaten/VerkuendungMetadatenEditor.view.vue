<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { Splitter, SplitterPanel } from "primevue"
import RisMetadataEditorNavigation from "@/components/metadata-editor/RisMetadataEditorNavigation.vue"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { useGetNorm } from "@/services/normService"
import { computed } from "vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"

const verkuendungEli = useNormExpressionEliPathParameter("verkuendung")
const expressionEli = useNormExpressionEliPathParameter()
const selectedEid = useEidPathParameter()

// BREADCRUMBS
const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(verkuendungEli)

const {
  data: normExpression,
  error: normExpressionError,
  isFinished: normExpressionLoaded,
} = useGetNorm(expressionEli)

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => [
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${verkuendungEli.value.toString()}`,
  },
  {
    key: "norm",
    title: () =>
      normExpression.value
        ? `${normExpression.value.title} (${normExpression.value.shortTitle})`
        : "...",
  },
  { key: "metadaten", title: "Metadaten" },
])
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, normExpressionError]"
    :loading="!verkuendungHasFinished || !normExpressionLoaded"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="20"
        :min-size="20"
        class="h-full overflow-auto bg-white"
      >
        <RisMetadataEditorNavigation
          route-name-editor-element="VerkuendungMetadatenEditorElement"
          route-name-editor-outline-element="VerkuendungMetadatenEditorOutlineElement"
          route-name-editor-rahmen="VerkuendungMetadatenEditorRahmen"
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
