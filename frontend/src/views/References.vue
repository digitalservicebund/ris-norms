<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { computed, ref, triggerRef } from "vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import ModSelectionPanel from "@/components/references/RisModSelectionPanel.vue"
import { useNormXml } from "@/composables/useNormXml"
import RefSelectionPanel from "@/components/references/RisRefSelectionPanel.vue"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import RefEditorTable from "@/components/references/RisRefEditorTable.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"

const amendingNormEli = useEliPathParameter()
const {
  data: amendingNorm,
  isFetching: amendingNormIsLoading,
  error: amendingNormError,
} = useGetNorm(amendingNormEli)
const {
  data: amendingNormXml,
  isFetching: amendingNormXmlIsLoading,
  error: amendingNormXmlError,
} = useNormXml(amendingNormEli)

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
const selectedRefEId = ref<string | undefined>()

const amendingNormDocument = computed(() =>
  xmlStringToDocument(amendingNormXml.value ?? ""),
)

const selectedModQuotedContent = computed(() => {
  if (selectedModEId.value) {
    // TODO: (Malte Laukötter, 2024-07-25) get the quoted text / quoted structure instead of the whole node
    return getNodeByEid(amendingNormDocument.value, selectedModEId.value)
  }
  return null
})
const selectedModQuotedContentXmlString = computed({
  get() {
    if (selectedModQuotedContent.value) {
      return xmlNodeToString(selectedModQuotedContent.value)
    }

    return ""
  },
  set(newValue) {
    const parentNode = selectedModQuotedContent.value?.parentNode
    if (!parentNode) {
      return
    }

    const newNode = xmlStringToDocument(newValue).firstChild
    if (!newNode) {
      return
    }

    parentNode.replaceChild(newNode, selectedModQuotedContent.value)
    // we mutate the norm Document (as a side effect), so we need to trigger a recompute of all things that use it
    triggerRef(amendingNormDocument)
  },
})

const isSaving = ref(false)
const hasSaved = ref(false)
const saveError = ref("")
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
            class="overflow-hidden"
            :norm-xml="amendingNormXml"
          />
        </section>

        <div
          class="col-span-2 grid grid-cols-subgrid grid-rows-[minmax(0,max-content),max-content,max-content]"
        >
          <section
            aria-labelledby="textBasedMetadataHeading"
            class="flex flex-col"
          >
            <h3
              id="textBasedMetadataHeading"
              class="ds-label-02-bold mb-12 block"
            >
              Textbasierte Metadaten
            </h3>
            <RefSelectionPanel
              v-if="selectedModEId && selectedModQuotedContentXmlString"
              v-model:selected-ref="selectedRefEId"
              v-model:xml-snippet="selectedModQuotedContentXmlString"
              class="overflow-hidden"
            />
            <RisEmptyState
              v-else
              text-content="Wählen sie einen Änderungsbefehl zur Bearbeitung aus."
            />
          </section>
          <section
            v-if="selectedModEId"
            aria-labelledby="referencesHeading"
            class="flex flex-col"
          >
            <h3 id="referencesHeading" class="ds-label-02-bold mb-12 block">
              Verweise
            </h3>
            <RefEditorTable
              v-if="selectedModQuotedContentXmlString"
              v-model:selected-ref="selectedRefEId"
              v-model:xml-snippet="selectedModQuotedContentXmlString"
              class="overflow-hidden"
            />
          </section>

          <hr
            v-if="selectedModEId"
            class="col-span-2 mb-16 mt-32 border border-solid border-gray-400"
          />

          <div v-if="selectedModEId" class="col-span-2 flex flex-row-reverse">
            <RisTooltip
              v-slot="{ ariaDescribedby }"
              :title="
                hasSaved && saveError
                  ? 'Speichern fehlgeschlagen'
                  : 'Gespeichert!'
              "
              :variant="hasSaved && saveError ? 'error' : 'success'"
              :visible="hasSaved"
              allow-dismiss
              alignment="right"
              attachment="bottom"
            >
              <RisTextButton
                :aria-describedby
                :disabled="isSaving"
                :loading="isSaving"
                label="Speichern"
              />
            </RisTooltip>
          </div>
        </div>
      </div>
    </RisHeader>
  </div>
</template>
