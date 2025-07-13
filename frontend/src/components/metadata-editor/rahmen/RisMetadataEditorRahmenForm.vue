<script setup lang="ts">
import type { DropdownItem } from "@/types/dropdownItem"
import { useElementId } from "@/composables/useElementId"
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
import type { RahmenProprietary } from "@/types/proprietary"
import { produce } from "immer"
import InputText from "primevue/inputtext"
import { computed } from "vue"
import Select from "primevue/select"

const localData = defineModel<RahmenProprietary | null>()

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
    if (!localData.value?.subtyp) {
      // None of the relevant values are set means that the document type
      // intentionally has no value
      return ""
    } else {
      // If any value is set, we'll check if the combination of values
      // corresponds to a known type, otherwise the type will be unknown
      return getDocumentTypeFromMetadata(localData.value?.subtyp ?? "")
    }
  },

  set(value) {
    if (value === UNKNOWN_DOCUMENT_TYPE) {
      // This is disabled in the UI and should never happen. We still need to
      // check for it to make TypeScript happy, but we'll simply ignore it
      // and keep the value as it was.
      return
    }

    const { subtyp = "" } = value ? DocumentTypeValues[value] : {}

    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
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
</script>

<template>
  <form
    class="grid grid-cols-[max-content_1fr] items-center gap-x-16 gap-y-14 overflow-auto"
    @submit.prevent
  >
    <fieldset class="contents">
      <legend class="ris-label2-bold col-span-2">Sachgebiet</legend>
      <div><label :for="fnaId">Sachgebiet</label></div>
      <InputText :id="fnaId" v-model="fna" />
    </fieldset>

    <fieldset class="contents">
      <legend class="ris-label2-bold col-span-2">Dokumenttyp</legend>

      <div>
        <label :for="documentTypeId" class="ris-label2-regular">
          Dokumenttyp
        </label>
      </div>
      <Select
        v-model="documentType"
        :label-id="documentTypeId"
        :options="documentTypeItems"
        aria-label="Dokumenttyp"
        option-label="label"
        option-value="value"
      />

      <fieldset class="contents">
        <legend class="h-full">Art der Norm</legend>
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
      </fieldset>

      <div>
        <label :for="bezeichnungInVorlageId"> Bezeichnung gemäß Vorlage </label>
      </div>
      <InputText :id="bezeichnungInVorlageId" v-model="bezeichnungInVorlage" />
    </fieldset>

    <fieldset class="contents">
      <legend class="ris-label2-bold col-span-2">Normgeber</legend>

      <div>
        <label :for="staatId">
          <abbr
            title="Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder Rechtsmacht die Norm trägt"
          >
            Staat
          </abbr>
        </label>
      </div>
      <Select
        v-model="staat"
        :label-id="staatId"
        :options="staatItems"
        aria-label="Staat"
        option-label="label"
        option-value="value"
      />

      <div>
        <label :for="beschliessendesOrganId">beschließendes Organ</label>
      </div>
      <Select
        v-model="beschliessendesOrgan"
        :label-id="beschliessendesOrganId"
        :options="beschliessendesOrganItems"
        aria-label="beschließendes Organ"
        option-label="label"
        option-value="value"
        :aria-labelledby="beschliessendesOrganId"
      />

      <div>
        <label :for="qualifizierteMehrheitId">
          Beschlussf. qual. Mehrheit
        </label>
      </div>
      <Checkbox
        v-model="qualifizierteMehrheit"
        :input-id="qualifizierteMehrheitId"
        binary
      />
    </fieldset>

    <fieldset class="contents">
      <legend class="ris-label2-bold col-span-2">Federführung</legend>

      <div><label :for="ressortId">Ressort</label></div>
      <Select
        v-model="ressort"
        :label-id="ressortId"
        :options="ressortItems"
        aria-label="Ressort"
        option-label="label"
        option-value="value"
      />

      <div>
        <label :for="organisationsEinheitId"> Organisationseinheit </label>
      </div>
      <InputText :id="organisationsEinheitId" v-model="organisationsEinheit" />
    </fieldset>
  </form>
</template>
