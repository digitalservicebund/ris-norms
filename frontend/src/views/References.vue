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
import ModSelectionPanel from "@/components/references/ModSelectionPanel.vue"
import { useNormXml } from "@/composables/useNormXml"
import RefSelectionPanel from "@/components/references/RefSelectionPanel.vue"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"

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
  <div class="h-[calc(100dvh-5rem-1px)] bg-gray-100">
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
        class="grid h-[calc(100vh-5rem-5rem-1px)] grid-cols-3 grid-rows-1 gap-32 p-40"
      >
        <section aria-label="Mod Auswahl">
          <div>Mod Selection Panel</div>
          <ModSelectionPanel
            v-if="amendingNormXml"
            v-model="selectedModEId"
            class="h-full overflow-hidden"
            :norm-xml="amendingNormXml"
          />
        </section>
        <section aria-label="Ref Selektion">
          Ref Selektion
          <RefSelectionPanel
            v-if="selectedModQuotedContentXmlString"
            v-model:selected-ref="selectedRefEId"
            v-model:xml-snippet="selectedModQuotedContentXmlString"
            class="h-full overflow-hidden"
          />
        </section>
        <section aria-label="Ref Tabelle">Ref Tabelle</section>
      </div>

      <template #action>
        <div class="relative">
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
      </template>
    </RisHeader>
  </div>
</template>
