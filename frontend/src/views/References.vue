<script setup lang="ts">
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisModRefsEditor from "@/components/references/RisModRefsEditor.vue"
import RisModSelectionPanel from "@/components/references/RisModSelectionPanel.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { computed, ref } from "vue"
import { useRoute, useRouter } from "vue-router"

/* -------------------------------------------------- *
 * Previews                                           *
 * -------------------------------------------------- */

const amendingNormEli = useEliPathParameter()
const {
  data: amendingNorm,
  isFetching: amendingNormIsLoading,
  error: amendingNormError,
} = useGetNorm(amendingNormEli)

const affectedNormEli = useEliPathParameter("affectedDocument")
const {
  data: affectedNorm,
  isFetching: affectedNormIsLoading,
  error: affectedNormError,
} = useGetNorm(affectedNormEli)

/* -------------------------------------------------- *
 * Data and states for editing                        *
 * -------------------------------------------------- */

const newNormXml = ref()

const {
  data: amendingNormXml,
  isFetching: amendingNormXmlIsLoading,
  error: amendingNormXmlError,
  update: {
    execute: save,
    isFetching: isSaving,
    isFinished: hasSaved,
    error: saveError,
  },
} = useNormXml(amendingNormEli, newNormXml)

function handleSave(xml: string) {
  newNormXml.value = xml
  save()
}

const router = useRouter()
const route = useRoute()

const selectedModEId = computed({
  get() {
    return route.params.modEid?.toString()
  },
  set(newEid) {
    router.replace({
      params: {
        modEid: newEid ?? "",
        refEid: "",
      },
    })
  },
})

/* -------------------------------------------------- *
 * Header                                             *
 * -------------------------------------------------- */

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingNorm",
    title: () =>
      amendingNorm.value
        ? (getFrbrDisplayText(amendingNorm.value) ?? "...")
        : "...",
    to: `/amending-laws/${amendingNormEli.value}/affected-documents`,
  },
  {
    key: "affectedNorm",
    title: () => affectedNorm.value?.shortTitle ?? "...",
  },
  { key: "textMetadataEditor", title: "Textbasierte Metadaten" },
])
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-1px)] flex-col bg-gray-100">
    <div
      v-if="
        amendingNormIsLoading ||
        affectedNormIsLoading ||
        amendingNormXmlIsLoading
      "
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div
      v-else-if="amendingNormError || affectedNormError || amendingNormXmlError"
      class="p-24"
    >
      <RisErrorCallout
        :error="amendingNormError ?? affectedNormError ?? amendingNormXmlError"
      />
    </div>

    <RisHeader v-else :breadcrumbs>
      <div
        class="grid flex-grow grid-cols-3 grid-rows-1 gap-32 overflow-hidden p-24"
      >
        <section aria-labelledby="changeCommandsHeading" class="flex flex-col">
          <h3 id="changeCommandsHeading" class="ds-label-02-bold mb-12 block">
            Änderungsbefehle
          </h3>
          <RisModSelectionPanel
            v-if="amendingNormXml"
            v-model="selectedModEId"
            :norm-xml="amendingNormXml"
            :class="['overflow-hidden']"
            data-testid="mod-selection-panel"
          />
        </section>

        <RisModRefsEditor
          v-if="amendingNormXml"
          :norm-xml="amendingNormXml"
          :selected-mod-e-id="selectedModEId"
          :eli="amendingNormEli"
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
