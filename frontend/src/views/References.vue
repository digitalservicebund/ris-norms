<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { ref, onMounted, watch } from "vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import ModSelectionPanel from "@/components/references/RisModSelectionPanel.vue"
import { useNormXml } from "@/composables/useNormXml"
import RisQuotedContentRefEditor from "@/components/references/RisModRefsEditor.vue"
import { useFetchReferences } from "@/composables/useFetchReferences"
import RisAlert from "@/components/controls/RisAlert.vue"

const amendingNormEli = useEliPathParameter()
const {
  data: amendingNorm,
  isFetching: amendingNormIsLoading,
  error: amendingNormError,
} = useGetNorm(amendingNormEli)
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

const affectedNormEli = useEliPathParameter("affectedDocument")
const {
  data: affectedNorm,
  isFetching: affectedNormIsLoading,
  error: affectedNormError,
} = useGetNorm(affectedNormEli)

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

const selectedModEId = ref<string | undefined>()

function handleSave(xml: string) {
  newNormXml.value = xml
  save()
}

const isFetchingReferences = ref()
const referencesError = ref()
const showErrorAlert = ref(false)

function fetchReferences() {
  const { isFetching, error } = useFetchReferences(amendingNormEli)

  watch(isFetching, (newIsFetching) => {
    isFetchingReferences.value = newIsFetching
  })

  watch(error, (newError) => {
    referencesError.value = newError
    showErrorAlert.value = true // Show the alert when there is an error
  })
}

interface ApiError {
  response?: {
    status?: number
    message?: string
  }
}

function isNotFoundError(error: ApiError): boolean {
  console.log(error?.response?.status)
  return error?.response?.status === 404
}

onMounted(() => {
  fetchReferences()
})
</script>

<template>
  <div class="flex h-[calc(100dvh-5rem-1px)] flex-col bg-gray-100">
    <RisAlert
      v-if="
        showErrorAlert && referencesError && !isNotFoundError(referencesError)
      "
      variant="error"
      @close="showErrorAlert = false"
    >
      <p>
        Die automatische Referenzierung konnte nicht durchgeführt werden. Sie
        können den Fehler schließen und die Referenzen manuell bearbeiten.
      </p>
    </RisAlert>

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
      <RisCallout
        title="Das Gesetz konnte nicht geladen werden."
        variant="error"
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
          <ModSelectionPanel
            v-if="amendingNormXml"
            v-model="selectedModEId"
            :norm-xml="amendingNormXml"
            :class="[
              'overflow-hidden',
              { 'pointer-events-none': isFetchingReferences },
            ]"
            data-testid="mod-selection-panel"
          />
        </section>

        <RisQuotedContentRefEditor
          v-if="selectedModEId && amendingNormXml"
          :norm-xml="amendingNormXml"
          :selected-mod-e-id="selectedModEId"
          :eli="amendingNormEli"
          class="col-span-2 grid grid-cols-subgrid"
          :has-saved="hasSaved"
          :is-saving="isSaving"
          :save-error="saveError"
          @save="handleSave"
        ></RisQuotedContentRefEditor>
      </div>
    </RisHeader>
  </div>
</template>
