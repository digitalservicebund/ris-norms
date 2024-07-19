<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisCheckboxInput from "@/components/controls/RisCheckboxInput.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import RisTooltip from "@/components/controls/RisTooltip.vue"
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useElementId } from "@/composables/useElementId"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useNormXml } from "@/composables/useNormXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import {
  BeschliessendesOrganValues,
  DocumentTypeValue,
  DocumentTypeValues,
  getDocumentTypeFromMetadata,
  isArtNormTypePresent,
  RessortValues,
  StaatValues,
  udpateArtNorm,
  UNKNOWN_DOCUMENT_TYPE,
} from "@/lib/proprietary"
import { useGetNormHtml } from "@/services/normService"
import {
  useGetRahmenProprietary,
  usePutRahmenProprietary,
} from "@/services/proprietaryService"
import { RahmenProprietary } from "@/types/proprietary"
import { produce } from "immer"
import { computed, ref, watch } from "vue"

const affectedDocumentEli = useEliPathParameter("affectedDocument")
const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()
const { actionTeleportTarget } = useHeaderContext()

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const localData = ref<RahmenProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetRahmenProprietary(affectedDocumentEli, {
  atDate: timeBoundaryAsDate,
})

watch(data, (newData) => {
  localData.value = newData
})

const {
  data: savedData,
  isFetching: isSaving,
  isFinished: hasSaved,
  error: saveError,
  execute: save,
} = usePutRahmenProprietary(
  localData,
  affectedDocumentEli,
  { atDate: timeBoundaryAsDate },
  {
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

const {
  documentTypeId,
  fnaId,
  bezeichnungInVorlageId,
  artNormSnId,
  artNormAnId,
  artNormUnId,
  staatId,
  beschliessendesOrganId,
  qualifizierteMehrheitId,
  ressortId,
  organisationsEinheitId,
} = useElementId()

const fna = computed<string>({
  get() {
    return localData.value?.fna ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.fna = value
    })
  },
})

const documentType = computed<
  DocumentTypeValue | typeof UNKNOWN_DOCUMENT_TYPE | ""
>({
  get() {
    if (
      [
        localData.value?.art,
        localData.value?.typ,
        localData.value?.subtyp,
      ].every((i) => !i)
    ) {
      // None of the relevant values are set means that the document type
      // intentionally has no value
      return ""
    } else {
      // If any value is set, we'll check if the combination of values
      // corresponds to a known type, otherwise the type will be unknown
      return getDocumentTypeFromMetadata(
        localData.value?.art ?? "",
        localData.value?.typ ?? "",
        localData.value?.subtyp ?? "",
      )
    }
  },

  set(value) {
    if (value === UNKNOWN_DOCUMENT_TYPE) {
      // This is disabled in the UI and should never happen. We still need to
      // check for it to make TypeScript happy, but we'll simply ignore it
      // and keep the value as it was.
      return
    }

    const {
      art = "",
      typ = "",
      subtyp = "",
    } = value ? DocumentTypeValues[value] : {}

    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.art = art
      draft.typ = typ
      draft.subtyp = subtyp
    })
  },
})

const documentTypeItems: DropdownItem[] = [
  { label: "", value: "" },
  { label: "Unbekannt", value: UNKNOWN_DOCUMENT_TYPE, disabled: true },
  ...Object.keys(DocumentTypeValues).map((value) => ({ label: value, value })),
]

const artNormSN = computed<boolean>({
  get() {
    return isArtNormTypePresent(localData.value?.artDerNorm, "SN")
  },
  set(value: boolean) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.artDerNorm = udpateArtNorm(localData.value?.artDerNorm, "SN", value)
    })
  },
})

const artNormAN = computed<boolean>({
  get() {
    return isArtNormTypePresent(localData.value?.artDerNorm, "ÄN")
  },
  set(value: boolean) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.artDerNorm = udpateArtNorm(localData.value?.artDerNorm, "ÄN", value)
    })
  },
})

const artNormUN = computed<boolean>({
  get() {
    return isArtNormTypePresent(localData.value?.artDerNorm, "ÜN")
  },
  set(value: boolean) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.artDerNorm = udpateArtNorm(localData.value?.artDerNorm, "ÜN", value)
    })
  },
})

const bezeichnungInVorlage = computed<string>({
  get() {
    return localData.value?.bezeichnungInVorlage ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.bezeichnungInVorlage = value
    })
  },
})

const staat = computed<string>({
  get() {
    return localData.value?.staat ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.staat = value
    })
  },
})

const staatItems: DropdownItem[] = [
  { label: "", value: "" },
  ...StaatValues.map((value) => ({ label: value, value })),
]

const beschliessendesOrgan = computed<string>({
  get() {
    return localData.value?.beschliessendesOrgan ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.beschliessendesOrgan = value
    })
  },
})

const beschliessendesOrganItems: DropdownItem[] = [
  { label: "", value: "" },
  ...BeschliessendesOrganValues.map((value) => ({ label: value, value })),
]

const qualifizierteMehrheit = computed<boolean>({
  get() {
    return localData.value?.qualifizierteMehrheit ?? false
  },
  set(value: boolean) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.qualifizierteMehrheit = value
    })
  },
})

const ressort = computed<string>({
  get() {
    return localData.value?.ressort ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.ressort = value
    })
  },
})

const ressortItems: DropdownItem[] = [
  { label: "", value: "" },
  ...RessortValues.map<DropdownItem>((name) => ({
    label: name,
    value: name,
  })),
]

const organisationsEinheit = computed<string>({
  get() {
    return localData.value?.organisationsEinheit ?? ""
  },
  set(value: string) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.organisationsEinheit = value
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
} = useGetNormHtml(affectedDocumentEli, { at: timeBoundaryAsDate })
</script>

<template>
  <!-- eslint-disable vuejs-accessibility/label-has-for -->
  <div class="flex flex-col overflow-hidden p-24">
    <div class="flex gap-16">
      <div class="flex-grow">
        <h2 class="ds-label-02-bold">Rahmen</h2>
      </div>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-1 gap-16">
      <section class="mt-32 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisCallout
          v-else-if="renderError"
          variant="error"
          title="Die Vorschau konnte nicht geladen werden."
        >
          <p v-if="renderError.sentryEventId">
            Fehler-ID:
            <RisCopyableLabel
              :text="renderError.sentryEventId"
              name="Fehler-ID"
            />
          </p>
        </RisCallout>

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
            >
              <p v-if="fetchError.sentryEventId">
                Fehler-ID:
                <RisCopyableLabel
                  :text="fetchError.sentryEventId"
                  name="Fehler-ID"
                />
              </p>
            </RisCallout>

            <form
              v-else
              class="grid grid-cols-[max-content,1fr] items-center gap-x-16 gap-y-14 overflow-auto"
              @submit.prevent
            >
              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Sachgebiet</legend>
                <label :for="fnaId">Sachgebiet</label>
                <RisTextInput :id="fnaId" v-model="fna" />
              </fieldset>

              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Dokumenttyp</legend>

                <label :for="documentTypeId">Dokumenttyp</label>
                <RisDropdownInput
                  :id="documentTypeId"
                  v-model="documentType"
                  :items="documentTypeItems"
                />

                <label :for="artNormSnId" class="self-start">
                  Art der Norm
                </label>
                <div class="space-y-10">
                  <RisCheckboxInput
                    :id="artNormSnId"
                    v-model="artNormSN"
                    label="SN - Stammnorm"
                  />
                  <RisCheckboxInput
                    :id="artNormAnId"
                    v-model="artNormAN"
                    label="ÄN - Änderungsnorm"
                  />
                  <RisCheckboxInput
                    :id="artNormUnId"
                    v-model="artNormUN"
                    label="ÜN - Übergangsnorm"
                  />
                </div>

                <label :for="bezeichnungInVorlageId">
                  Bezeichnung gemäß Vorlage
                </label>
                <RisTextInput
                  :id="bezeichnungInVorlageId"
                  v-model="bezeichnungInVorlage"
                />
              </fieldset>

              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">Normgeber</legend>

                <label :for="staatId"
                  ><abbr
                    title="Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder Rechtsmacht die Norm trägt"
                    >Staat</abbr
                  ></label
                >
                <RisDropdownInput
                  :id="staatId"
                  v-model="staat"
                  :items="staatItems"
                />

                <label :for="beschliessendesOrganId">
                  beschließendes Organ
                </label>
                <RisDropdownInput
                  :id="beschliessendesOrganId"
                  v-model="beschliessendesOrgan"
                  :items="beschliessendesOrganItems"
                />

                <label :for="qualifizierteMehrheitId">
                  Beschlussf. qual. Mehrheit
                </label>
                <RisCheckboxInput
                  :id="qualifizierteMehrheitId"
                  v-model="qualifizierteMehrheit"
                />
              </fieldset>

              <fieldset class="contents">
                <legend class="ds-label-02-bold col-span-2">
                  Federführung
                </legend>

                <label :for="ressortId">Ressort</label>
                <RisDropdownInput
                  :id="ressortId"
                  v-model="ressort"
                  :items="ressortItems"
                />

                <label :for="organisationsEinheitId">
                  Organisationseinheit
                </label>
                <RisTextInput
                  :id="organisationsEinheitId"
                  v-model="organisationsEinheit"
                />
              </fieldset>
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
            >
              <p v-if="xmlError.sentryEventId">
                Fehler-ID:
                <RisCopyableLabel
                  :text="xmlError.sentryEventId"
                  name="Fehler-ID"
                />
              </p>
            </RisCallout>

            <RisCodeEditor
              v-else
              :model-value="xml ?? ''"
              :readonly="true"
              class="flex-grow"
            />
          </template>
        </RisTabs>

        <!-- Save button -->
        <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
          <div class="relative">
            <RisTooltip
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
              <template #default="{ ariaDescribedby }">
                <RisTextButton
                  :aria-describedby
                  :disabled="isFetching || !!fetchError"
                  :loading="isSaving"
                  label="Speichern"
                  @click="save()"
                />
              </template>

              <template #message>
                <RisCopyableLabel
                  v-if="saveError?.sentryEventId"
                  name="Fehler-ID"
                  text="Fehler-ID kopieren"
                  :value="saveError?.sentryEventId"
                />
              </template>
            </RisTooltip>
          </div>
        </Teleport>
      </section>
    </div>
  </div>
</template>
