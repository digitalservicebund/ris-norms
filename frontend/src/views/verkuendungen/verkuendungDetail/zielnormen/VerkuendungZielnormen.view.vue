<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useElementId } from "@/composables/useElementId"
import {
  useZielnormReferences,
  type EditableZielnormReference,
} from "@/composables/useZielnormReferences"
import { formatDate } from "@/lib/dateTime"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import {
  useGeltungszeitenHtml,
  useGetZeitgrenzen,
} from "@/services/zeitgrenzenService"
import {
  ConfirmDialog,
  Splitter,
  SplitterPanel,
  useConfirm,
  useToast,
} from "primevue"
import { computed, ref, watch } from "vue"
import RisDokumentExplorer from "./RisDokumentExplorer.vue"
import RisZielnormForm from "./RisZielnormForm.vue"
import { useErrorToast } from "@/lib/errorToast"
import { useZeitgrenzenHighlightClasses } from "@/composables/useZeitgrenzenHighlightClasses"

const { addErrorToast } = useErrorToast()

const { add: addToast } = useToast()

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
  { key: "zielnormen", title: "Zielnormen verknüpfen" },
])

// Geltungszeiten HTML ------------------------------------

const { geltungszeitenHtmlHeadingId } = useElementId()

const {
  data: geltungszeitenHtml,
  isFetching: isFetchingGeltungszeitenHtml,
  error: geltungszeitenHtmlError,
} = useGeltungszeitenHtml(eli)

const formattedVerkuendungsdatum = computed(() =>
  verkuendung.value?.frbrDateVerkuendung
    ? formatDate(verkuendung.value.frbrDateVerkuendung)
    : undefined,
)

// Editing ------------------------------------------------

const eIdsToEdit = ref<string[]>([])

const editedZielnormReference = ref<EditableZielnormReference>()

const {
  zielnormReferences,
  zielnormReferencesForEid,
  updateZielnormReferences,
  deleteZielnormReferences,
  error: zielnormReferencesError,
  isFetching: isFetchingZielnormReferences,
} = useZielnormReferences(() => eli.value.asNormEli())

const isSelected = (eid: string) => {
  return eIdsToEdit.value.includes(eid)
}

const highlightClasses = useZeitgrenzenHighlightClasses(
  () => [...(zielnormReferences.value ?? [])],
  isSelected,
)

watch(eIdsToEdit, (val) => {
  if (!val?.length) editedZielnormReference.value = undefined
  else editedZielnormReference.value = zielnormReferencesForEid(...val)
})

const { data: zeitgrenzen, error: zeitgrenzenError } = useGetZeitgrenzen(eli)

async function onSaveZielnormReferences() {
  if (!eIdsToEdit.value.length || !editedZielnormReference.value) return

  await updateZielnormReferences(
    editedZielnormReference.value,
    ...eIdsToEdit.value,
  )

  if (!zielnormReferencesError.value) {
    addToast({ summary: "Gespeichert!", severity: "success" })
  }
}

function confirmDeleteZielnormReferences() {
  confirm.require({
    header: "Einträge entfernen",
    message: "Sollen die gewählten Einträge wirklich entfernt werden?",
    acceptProps: {
      label: "Entfernen",
      severity: "danger",
      autofocus: false,
    },
    rejectProps: {
      label: "Abbrechen",
      severity: "primary",
      autofocus: true,
    },
    acceptClass: "w-full",
    rejectClass: "w-full",
    accept: onDeleteZielnormReferences,
  })
}

async function onDeleteZielnormReferences() {
  if (!eIdsToEdit.value.length) return
  await deleteZielnormReferences(...eIdsToEdit.value)
  eIdsToEdit.value = []
  if (!zielnormReferencesError.value) {
    addToast({ summary: "Gelöscht!", severity: "success" })
  }
}

watch(zielnormReferencesError, (val) => {
  if (val) addErrorToast(val)
})
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError, geltungszeitenHtmlError, zeitgrenzenError]"
    :loading="!verkuendungHasFinished"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100"
      >
        <RisDokumentExplorer
          v-model:eids-to-edit="eIdsToEdit"
          v-model:eli="eli"
          class="h-full"
          :e-id-classes="highlightClasses"
        />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100 p-24"
      >
        <RisEmptyState
          v-if="!editedZielnormReference"
          text-content="Bitte wählen Sie einen Änderungsbefehl aus."
        />

        <RisZielnormForm
          v-else
          v-model="editedZielnormReference"
          :zeitgrenzen="zeitgrenzen ?? []"
          :loading="isFetchingZielnormReferences"
          @save="onSaveZielnormReferences()"
          @delete="confirmDeleteZielnormReferences()"
        />
      </SplitterPanel>

      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto p-24"
      >
        <section :aria-labelledby="geltungszeitenHtmlHeadingId">
          <h2 :id="geltungszeitenHtmlHeadingId" class="ris-subhead-bold mb-10">
            Verkündungsdaten
          </h2>
          <RisPropertyValue
            property="Verkündungsdatum"
            :value="formattedVerkuendungsdatum"
          />
          <hr class="mb-0" />

          <div
            v-if="isFetchingGeltungszeitenHtml"
            class="mt-24 flex items-center justify-center"
          >
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout
            v-else-if="geltungszeitenHtmlError"
            :error="geltungszeitenHtmlError"
          />
          <RisLawPreview
            v-else-if="geltungszeitenHtml"
            :arrow-focus="false"
            :content="geltungszeitenHtml"
            class="-mx-24"
          />
        </section>
      </SplitterPanel>
    </Splitter>
  </RisViewLayout>

  <ConfirmDialog />
</template>
