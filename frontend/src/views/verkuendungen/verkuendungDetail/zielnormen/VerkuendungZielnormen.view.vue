<script setup lang="ts">
import RisDokumentExplorer from "@/components/RisDokumentExplorer.vue"
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import { type HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import RisPropertyValue from "@/components/RisPropertyValue.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useElementId } from "@/composables/useElementId"
import { useToast } from "@/composables/useToast"
import { useZeitgrenzenHighlightClasses } from "@/composables/useZeitgrenzenHighlightClasses"
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
import { ConfirmDialog, Splitter, SplitterPanel, useConfirm } from "primevue"
import { computed, ref, watch } from "vue"
import RisZielnormForm from "./RisZielnormForm.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

const { add: addToast, addError: addErrorToast } = useToast()

const eli = useNormExpressionEliPathParameter()

const confirm = useConfirm()

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(eli)

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

const { data: zeitgrenzen, error: zeitgrenzenError } = useGetZeitgrenzen(eli)

const eIdsToEdit = ref<string[]>([])

const editedZielnormReference = ref<EditableZielnormReference>()

const {
  deleteZielnormReferences,
  deleteZielnormReferencesError,
  isLoadingZielnormReferences,
  isDeletingZielnormReferences,
  isUpdatingZielnormReferences,
  loadZielnormReferencesError,
  updateZielnormReferences,
  updateZielnormReferencesError,
  zielnormReferences,
  zielnormReferencesForEid,
} = useZielnormReferences(eli)

const isSelected = (eid: string) => {
  return eIdsToEdit.value.includes(eid)
}

const highlightClasses = useZeitgrenzenHighlightClasses(
  () => [...(zielnormReferences.value ?? [])],
  isSelected,
  () => zeitgrenzen.value ?? [],
)

watch(eIdsToEdit, (val) => {
  if (!val?.length) editedZielnormReference.value = undefined
  else editedZielnormReference.value = zielnormReferencesForEid(...val)
})

async function onSaveZielnormReferences() {
  if (!eIdsToEdit.value.length || !editedZielnormReference.value) return

  await updateZielnormReferences(
    editedZielnormReference.value,
    ...eIdsToEdit.value,
  )

  if (!updateZielnormReferencesError.value) {
    addToast({ summary: "Gespeichert!", severity: "success" })
  } else {
    addErrorToast(updateZielnormReferencesError.value)
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
  if (!deleteZielnormReferencesError.value) {
    addToast({ summary: "Gelöscht!", severity: "success" })
  } else {
    addErrorToast(deleteZielnormReferencesError.value)
  }
}
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[
      verkuendungError,
      geltungszeitenHtmlError,
      zeitgrenzenError,
      loadZielnormReferencesError,
    ]"
    :loading="!verkuendungHasFinished || isLoadingZielnormReferences"
  >
    <Splitter class="h-full" layout="horizontal">
      <SplitterPanel
        :size="33"
        :min-size="33"
        class="h-full overflow-auto bg-gray-100"
      >
        <RisDokumentExplorer
          v-model:eids-to-edit="eIdsToEdit"
          :eli="
            DokumentExpressionEli.fromString(
              /* todo: replace by new method tarek introduced */
              eli + '/regelungstext-verkuendung-1',
            )
          "
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
          text-content="Bitte wählen Sie einen Artikel und eine Änderung aus."
        />

        <RisZielnormForm
          v-else
          v-model="editedZielnormReference"
          :zeitgrenzen="zeitgrenzen ?? []"
          :deleting="isDeletingZielnormReferences"
          :updating="isUpdatingZielnormReferences"
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
