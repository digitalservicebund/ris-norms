<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { useElementId } from "@/composables/useElementId"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import {
  DocumentTypeValue,
  DocumentTypeValues,
  getDocumentTypeFromMetadata,
  isMetaArtValue,
  isMetaSubtypValue,
  isMetaTypValue,
} from "@/lib/proprietary"
import { useGetNormHtml } from "@/services/normService"
import {
  useGetProprietary,
  usePutProprietary,
} from "@/services/proprietaryService"
import { Proprietary } from "@/types/proprietary"
import { produce } from "immer"
import { computed, ref, watch } from "vue"
import RisCheckboxInput from "@/components/controls/RisCheckboxInput.vue"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<Proprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetProprietary(
  affectedDocumentEli,
  { atDate: timeBoundaryAsDate },
  { refetch: true },
)

watch(data, (newData) => {
  localData.value = newData
})

const {
  data: savedData,
  isFetching: isSaving,
  error: saveError,
  execute: save,
} = usePutProprietary(
  localData,
  affectedDocumentEli,
  { atDate: timeBoundaryAsDate },
  {
    refetch: false,
    immediate: false,
    afterFetch(c) {
      // Whenever the metadata has been saved successfully, reload the
      // XML to keep it in sync
      reloadXml()
      return c
    },
  },
).put(localData)

watch(savedData, (newData) => {
  localData.value = newData
})

/* -------------------------------------------------- *
 * Metadata form                                      *
 * -------------------------------------------------- */

const [
  documentTypeId,
  fnaId,
  bezeichnungInVorlageId,
  artNormSNid,
  artNormANid,
  artNormUNid,
] = Array(6)
  .fill(null)
  .map(() => useElementId())

const fna = computed<string | undefined>({
  get() {
    return localData.value?.fna
  },
  set(value?: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.fna = value
    })
  },
})

const documentType = computed<DocumentTypeValue | undefined>({
  get() {
    return isMetaArtValue(localData.value?.art) &&
      isMetaTypValue(localData.value?.typ) &&
      isMetaSubtypValue(localData.value?.subtyp)
      ? getDocumentTypeFromMetadata(
          localData.value.art,
          localData.value.typ,
          localData.value.subtyp,
        )
      : undefined
  },

  set(value) {
    const {
      art = undefined,
      typ = undefined,
      subtyp = undefined,
    } = value ? DocumentTypeValues[value] : {}

    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.art = art
      draft.typ = typ
      draft.subtyp = subtyp ?? undefined
    })
  },
})

const documentTypeItems: DropdownItem[] = [
  { label: "Unbekannt", value: "" },
  ...Object.keys(DocumentTypeValues).map((value) => ({ label: value, value })),
]

function createComputedArtNorm(value: string) {
  return computed<boolean | undefined>({
    get() {
      return localData.value?.artNorm?.includes(value)
    },
    set(newValue?: boolean) {
      localData.value = produce(localData.value, (draft) => {
        if (!draft) return
        if (newValue) {
          if (!draft.artNorm || !draft.artNorm.includes(value)) {
            draft.artNorm = draft.artNorm ? `${draft.artNorm},${value}` : value
          }
        } else if (draft.artNorm && draft.artNorm.includes(value)) {
          draft.artNorm = draft.artNorm
            .replace(new RegExp(`(^|,)${value}(,|$)`, "g"), "") // Remove "value" from start, middle, or end
            .replace(new RegExp(`^${value}$`), "") // Remove "value" if it's the only content
        }
      })
    },
  })
}

const artNormSN = createComputedArtNorm("SN")
const artNormAN = createComputedArtNorm("ÄN")
const artNormUN = createComputedArtNorm("ÜN")

const bezeichnungInVorlage = computed<string | undefined>({
  get() {
    return localData.value?.bezeichnungInVorlage
  },
  set(value?: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.bezeichnungInVorlage = value
    })
  },
})

/* -------------------------------------------------- *
 * XML + HTML preview                                 *
 * -------------------------------------------------- */

const {
  data: xml,
  isFetching: xmlIsLoading,
  error: xmlError,
  execute: reloadXml,
} = useNormXml(affectedDocumentEli)

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetNormHtml(
  affectedDocumentEli,
  { at: timeBoundaryAsDate },
  { immediate: true, refetch: true },
)
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <div class="flex flex-col overflow-hidden p-40">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-heading-03-reg">Rahmen</h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-32">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisCallout
          v-else-if="renderError"
          variant="error"
          title="Die Vorschau konnte nicht geladen werden."
        />

        <RisLawPreview
          v-else
          class="ds-textarea flex-grow p-2"
          :content="render ?? ''"
        />
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten bearbeiten">
        <RisTabs
          align="right"
          :tabs="[
            { id: 'editor', label: 'Rubriken' },
            { id: 'xml', label: 'XML' },
          ]"
        >
          <template #editor>
            <div v-if="isFetching" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="fetchError"
              variant="error"
              title="Die Metadaten konnten nicht geladen werden."
            />

            <form
              v-else
              class="grid grid-cols-[max-content,1fr] items-center gap-x-16 gap-y-14"
              @submit.prevent
            >
              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Sachgebiet</legend>
                <label :for="fnaId">Sachgebiet</label>
                <RisTextInput :id="fnaId" v-model="fna" size="small" />
              </fieldset>

              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Dokumenttyp</legend>

                <label :for="documentTypeId">Dokumenttyp</label>
                <RisDropdownInput
                  :id="documentTypeId"
                  v-model="documentType"
                  :items="documentTypeItems"
                />

                <label :for="artNormSNid" class="self-start"
                  >Art der Norm</label
                >
                <div class="space-y-10">
                  <RisCheckboxInput
                    :id="artNormSNid"
                    v-model="artNormSN"
                    label="SN - Stammnorm"
                    size="mini"
                  />
                  <RisCheckboxInput
                    :id="artNormANid"
                    v-model="artNormAN"
                    label="ÄN - Änderungsnorm"
                    size="mini"
                  />
                  <RisCheckboxInput
                    :id="artNormUNid"
                    v-model="artNormUN"
                    label="ÜN - Übergangsnorm"
                    size="mini"
                  />
                </div>

                <label :for="bezeichnungInVorlageId"
                  >Bezeichnung gemäß Vorlage</label
                >
                <RisTextInput
                  :id="bezeichnungInVorlageId"
                  v-model="bezeichnungInVorlage"
                  size="small"
                />
              </fieldset>

              <footer class="relative col-span-2 mt-32">
                <RisTooltip
                  v-slot="{ ariaDescribedby }"
                  title="Speichern fehlgeschlagen"
                  variant="error"
                  :visible="!!saveError"
                  allow-dismiss
                >
                  <RisTextButton
                    :aria-describedby
                    :loading="isSaving"
                    label="Metadaten speichern"
                    @click="save()"
                  />
                </RisTooltip>
              </footer>
            </form>
          </template>

          <template #xml>
            <div v-if="xmlIsLoading" class="my-16 flex justify-center">
              <RisLoadingSpinner />
            </div>

            <RisCallout
              v-else-if="xmlError"
              variant="error"
              title="Die XML-Ansicht konnte nicht geladen werden."
            />

            <RisCodeEditor
              v-else
              :model-value="xml ?? ''"
              :editable="false"
              class="flex-grow"
            />
          </template>
        </RisTabs>
      </section>
    </div>
  </div>
</template>
