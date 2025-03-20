<script setup lang="ts">
import type { DropdownItem } from "@/types/dropdownItem"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useElementId } from "@/composables/useElementId"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useErrorToast } from "@/lib/errorToast"
import Checkbox from "primevue/checkbox"
import type { DocumentTypeValue } from "@/lib/proprietary"
import {
  BeschliessendesOrganValues,
  DocumentTypeValues,
  getDocumentTypeFromMetadata,
  isArtNormTypePresent,
  RessortValues,
  StaatValues,
  udpateArtNorm,
  UNKNOWN_DOCUMENT_TYPE,
} from "@/lib/proprietary"
import { useGetNorm, useGetNormHtml } from "@/services/normService"
import {
  useGetRahmenProprietary,
  usePutRahmenProprietary,
} from "@/services/proprietaryService"
import type { RahmenProprietary } from "@/types/proprietary"
import { produce } from "immer"
import Button from "primevue/button"
import InputText from "primevue/inputtext"
import { useToast } from "primevue/usetoast"
import { computed, ref, watch } from "vue"
import Select from "primevue/select"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
const { actionTeleportTarget } = useHeaderContext()

/* -------------------------------------------------- *
 * API handling                                       *
 * -------------------------------------------------- */

const { data: normData } = useGetNorm(dokumentExpressionEli)

const localData = ref<RahmenProprietary | null>(null)

const {
  data,
  isFetching,
  error: fetchError,
} = useGetRahmenProprietary(dokumentExpressionEli)

watch(data, (newData) => {
  localData.value = newData
})

const {
  data: savedData,
  isFetching: isSaving,
  isFinished: hasSaved,
  error: saveError,
  execute: save,
} = usePutRahmenProprietary(localData, dokumentExpressionEli).put(localData)

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
 * HTML preview                                 *
 * -------------------------------------------------- */

const {
  data: render,
  isFetching: renderIsLoading,
  error: renderError,
} = useGetNormHtml(dokumentExpressionEli)

const sentryTraceId = useSentryTraceId()
const { add: addToast } = useToast()
const { addErrorToast } = useErrorToast()

function showToast() {
  if (saveError.value) {
    addErrorToast(saveError, sentryTraceId)
  } else {
    addToast({
      summary: "Gespeichert!",
      severity: "success",
    })
  }
}

watch(hasSaved, (finished) => {
  if (finished) {
    showToast()
  }
})
</script>

<template>
  <div class="flex flex-col overflow-hidden p-24">
    <div class="flex gap-16">
      <div class="grow">
        <h2 class="ris-label2-bold">{{ normData?.shortTitle ?? "Rahmen" }}</h2>
      </div>
    </div>
    <div class="gap grid min-h-0 grow grid-cols-2 grid-rows-1 gap-16">
      <section class="mt-16 flex flex-col gap-8" aria-label="Vorschau">
        <div v-if="renderIsLoading" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout v-else-if="renderError" :error="renderError" />

        <RisLawPreview v-else class="grow p-2" :content="render ?? ''" />
      </section>

      <section class="flex flex-col gap-8" aria-label="Metadaten dokumentieren">
        <div v-if="isFetching" class="my-16 flex justify-center">
          <RisLoadingSpinner />
        </div>

        <RisErrorCallout v-else-if="fetchError" :error="fetchError" />

        <form
          v-else
          class="grid grid-cols-[max-content_1fr] items-center gap-x-16 gap-y-14 overflow-auto"
          @submit.prevent
        >
          <fieldset class="contents">
            <legend class="ris-label2-bold col-span-2">Sachgebiet</legend>
            <label :for="fnaId">Sachgebiet</label>
            <InputText :id="fnaId" v-model="fna" />
          </fieldset>

          <fieldset class="contents">
            <legend class="ris-label2-bold col-span-2">Dokumenttyp</legend>

            <label :for="documentTypeId" class="ris-label2-regular">
              Dokumenttyp
            </label>
            <Select
              v-model="documentType"
              :label-id="documentTypeId"
              :options="documentTypeItems"
              aria-label="Dokumenttyp"
              option-label="label"
              option-value="value"
            />

            <label :for="artNormSnId" class="self-start">Art der Norm</label>
            <div class="space-y-10">
              <div class="flex items-center">
                <Checkbox v-model="artNormSN" :input-id="artNormSnId" binary />
                <label :for="artNormSnId">SN - Stammnorm</label>
              </div>
              <div class="flex items-center">
                <Checkbox v-model="artNormAN" :input-id="artNormAnId" binary />
                <label :for="artNormAnId">ÄN - Änderungsnorm</label>
              </div>
              <div class="flex items-center">
                <Checkbox v-model="artNormUN" :input-id="artNormUnId" binary />
                <label :for="artNormUnId">ÜN - Übergangsnorm</label>
              </div>
            </div>

            <label :for="bezeichnungInVorlageId">
              Bezeichnung gemäß Vorlage
            </label>
            <InputText
              :id="bezeichnungInVorlageId"
              v-model="bezeichnungInVorlage"
            />
          </fieldset>

          <fieldset class="contents">
            <legend class="ris-label2-bold col-span-2">Normgeber</legend>

            <label :for="staatId"
              ><abbr
                title="Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder Rechtsmacht die Norm trägt"
                >Staat</abbr
              ></label
            >
            <Select
              v-model="staat"
              :label-id="staatId"
              :options="staatItems"
              aria-label="Staat"
              option-label="label"
              option-value="value"
            />

            <label :for="beschliessendesOrganId">beschließendes Organ</label>
            <Select
              v-model="beschliessendesOrgan"
              :label-id="beschliessendesOrganId"
              :options="beschliessendesOrganItems"
              aria-label="beschließendes Organ"
              option-label="label"
              option-value="value"
              :aria-labelledby="beschliessendesOrganId"
            />

            <label :for="qualifizierteMehrheitId">
              Beschlussf. qual. Mehrheit
            </label>
            <Checkbox
              v-model="qualifizierteMehrheit"
              :input-id="qualifizierteMehrheitId"
              binary
            />
          </fieldset>

          <fieldset class="contents">
            <legend class="ris-label2-bold col-span-2">Federführung</legend>

            <label :for="ressortId">Ressort</label>
            <Select
              v-model="ressort"
              :label-id="ressortId"
              :options="ressortItems"
              aria-label="Ressort"
              option-label="label"
              option-value="value"
            />

            <label :for="organisationsEinheitId"> Organisationseinheit </label>
            <InputText
              :id="organisationsEinheitId"
              v-model="organisationsEinheit"
            />
          </fieldset>
        </form>

        <!-- Save button -->
        <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
          <div class="relative">
            <Button
              :disabled="isFetching || !!fetchError"
              :loading="isSaving"
              severity="primary"
              label="Speichern"
              @click="save()"
            />
          </div>
        </Teleport>
      </section>
    </div>
  </div>
</template>
