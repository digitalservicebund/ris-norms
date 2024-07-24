<script setup lang="ts">
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { ref } from "vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"

const amendingLawEli = useEliPathParameter()
const {
  data: amendingLaw,
  isFetching: amendingLawIsLoading,
  error: amendingLawError,
} = useGetNorm(amendingLawEli)

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const {
  data: affectedDocument,
  isFetching: affectedDocumentIsLoading,
  error: affectedDocumentError,
} = useGetNorm(affectedDocumentEli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? (getFrbrDisplayText(amendingLaw.value) ?? "...")
        : "...",
    to: `/amending-laws/${amendingLawEli.value}/affected-documents`,
  },
  {
    key: "affectedDocument",
    title: () => affectedDocument.value?.shortTitle ?? "...",
  },
  { key: "textMetadataEditor", title: "Textbasierte Metadaten" },
])

const isSaving = ref(false)
const hasSaved = ref(false)
const saveError = ref("")
</script>

<template>
  <div class="h-[calc(100dvh-5rem-1px)] bg-gray-100">
    <div
      v-if="amendingLawIsLoading || affectedDocumentIsLoading"
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="amendingLawError || affectedDocumentError" class="p-24">
      <RisCallout
        title="Das Gesetz konnte nicht geladen werden."
        variant="error"
      />
    </div>

    <RisHeader v-else :breadcrumbs>
      <div
        class="grid h-[calc(100vh-5rem-5rem-1px)] grid-cols-3 grid-rows-1 gap-32 p-40"
      >
        <section aria-label="Mod Auswahl">Mod Auswahl</section>
        <section aria-label="Ref Selektion">Ref Selektion</section>
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
