<script setup lang="ts">
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisHeader from "@/components/RisHeader.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisRefsEditor from "@/views/amending-law/affected-documents/references/RisRefsEditor.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useDokumentXml } from "@/composables/useDokumentXml"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { ref, watch } from "vue"
import { useRouter } from "vue-router"

/* -------------------------------------------------- *
 * Previews                                           *
 * -------------------------------------------------- */

const normEli = useDokumentExpressionEliPathParameter()
const {
  data: norm,
  isFetching: normIsLoading,
  error: normError,
} = useGetNorm(normEli)

/* -------------------------------------------------- *
 * Data and states for editing                        *
 * -------------------------------------------------- */

const newNormXml = ref()

const {
  data: normXml,
  isFetching: normXmlIsLoading,
  error: normXmlError,
  update: {
    execute: save,
    isFetching: isSaving,
    isFinished: hasSaved,
    error: saveError,
  },
} = useDokumentXml(normEli, newNormXml)

function handleSave(xml: string) {
  newNormXml.value = xml
  save()
}

const router = useRouter()

/* -------------------------------------------------- *
 * Header                                             *
 * -------------------------------------------------- */

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingNorm",
    title: () =>
      norm.value ? (getFrbrDisplayText(norm.value) ?? "...") : "...",
    to: `/amending-laws/${normEli.value}`,
  },
  { key: "textMetadataEditor", title: "Textbasierte Metadaten" },
])

watch(
  () => normXmlError?.value,
  (err) => {
    if (err?.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-1px)] flex-col bg-gray-100">
    <div
      v-if="normIsLoading || normXmlIsLoading"
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div
      v-else-if="normError || (normXmlError && normXmlError.status !== 404)"
      class="p-24"
    >
      <RisErrorCallout :error="normError ?? normXmlError" />
    </div>

    <RisHeader v-else :breadcrumbs>
      <div
        class="grid grow grid-cols-2 grid-rows-1 gap-32 overflow-hidden p-24"
      >
        <RisRefsEditor
          v-if="normXml"
          :norm-xml="normXml"
          e-id="hauptteil-n1"
          :eli="normEli"
          class="col-span-2 grid grid-cols-subgrid"
          :has-saved="hasSaved"
          :is-saving="isSaving"
          :save-error="saveError"
          @save="handleSave"
        />
      </div>
    </RisHeader>
  </div>
</template>
