<script setup lang="ts">
import { ref, watch, nextTick } from "vue"
import { useGetNormHtml } from "@/services/normService"
import Splitter from "primevue/splitter"
import SplitterPanel from "primevue/splitterpanel"
import { useElementId } from "@/composables/useElementId"
import RisLawPreview from "@/components/RisLawPreview.vue"
import Button from "primevue/button"
import { useGetNormToc } from "@/services/tocService"
import { RouterLink, useRoute, useRouter } from "vue-router"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import RisTableOfContents from "@/components/RisTableOfContents.vue"

const expressionEli = useNormExpressionEliPathParameter()
const { expressionHtmlHeadingId } = useElementId()
const route = useRoute()
const router = useRouter()
const {
  data: normExpressionHtml,
  isFetching: isFetchingNormExpressionHtml,
  error: normExpressionHtmlError,
} = useGetNormHtml(expressionEli)

const {
  data: toc,
  error: tocError,
  isFetching: tocIsFetching,
} = useGetNormToc(() =>
  DokumentExpressionEli.fromNormExpressionEli(expressionEli.value),
)

const handleNodeSelect = ({ eId }: { eId: string }): void => {
  router.push({
    name: "DatenbankWorkExpressionDetail",
    params: {
      ...route.params,
      eid: eId,
    },
  })
}

const gotoEid = (eid: string) => {
  previewRef.value?.scrollToText(eid)
}

const previewRef = ref<InstanceType<typeof RisLawPreview> | null>(null)

watch(
  [() => route.params.eid],
  () => {
    nextTick(() => {
      gotoEid(route.params.eid as string)
    })
  },
  { immediate: true },
)

const handlePreviewRendered = () => {
  gotoEid(route.params.eid as string)
}
</script>

<template>
  <Splitter layout="horizontal" class="h-full w-full">
    <SplitterPanel
      :size="30"
      :min-size="20"
      class="h-full w-full overflow-auto px-8 py-12"
    >
      <RisTableOfContents
        :toc="toc"
        :is-fetching="tocIsFetching"
        :fetch-error="tocError"
        :selected-e-id="route.params.eid as string"
        @select="handleNodeSelect"
      />
    </SplitterPanel>

    <SplitterPanel
      :size="70"
      :min-size="33"
      class="h-full w-full overflow-auto p-24"
    >
      <section :aria-labelledby="expressionHtmlHeadingId">
        <div class="flex flex-row items-center justify-between">
          <h2 :id="expressionHtmlHeadingId" class="ris-subhead-bold">
            Vorschau
          </h2>
          <div class="flex flex-row gap-8">
            <RouterLink :to="`/datenbank/${expressionEli}/metadaten`">
              <Button severity="primary" label="Metadaten bearbeiten" />
            </RouterLink>
            <RouterLink :to="`/datenbank/${expressionEli}/textbearbeitung`">
              <Button severity="primary" label="Text bearbeiten" />
            </RouterLink>
          </div>
        </div>
        <hr class="mb-0" />
        <div
          v-if="isFetchingNormExpressionHtml"
          class="my-16 flex justify-center"
        >
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout
          v-else-if="normExpressionHtmlError"
          :error="normExpressionHtmlError"
        />

        <RisLawPreview
          v-else
          ref="previewRef"
          :arrow-focus="false"
          :content="normExpressionHtml ?? ''"
          :selected="[route.params.eid as string]"
          class="-mx-24"
          @rendered="handlePreviewRendered"
        />
      </section>
    </SplitterPanel>
  </Splitter>
</template>
