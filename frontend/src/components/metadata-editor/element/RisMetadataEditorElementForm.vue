<script setup lang="ts">
import { computed } from "vue"
import type { RahmenProprietary } from "@/types/proprietary"
import { useElementId } from "@/composables/useElementId"
import { produce } from "immer"
import RadioButton from "primevue/radiobutton"

const localData = defineModel<RahmenProprietary | null>()

const { artNormSnId, artNormAnId, artNormUnId } = useElementId()

const artNorm = computed<string | undefined>({
  get() {
    return localData.value?.artDerNorm
  },
  set(value) {
    localData.value = produce(localData.value, (draft) => {
      if (!draft) return
      draft.artDerNorm = value
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
      <legend class="ris-label2-bold col-span-2">Dokumenttyp</legend>

      <fieldset class="col-span-2 contents">
        <legend class="self-start">Art der Norm</legend>

        <div class="space-y-10">
          <div class="flex items-center">
            <RadioButton
              v-model="artNorm"
              :input-id="artNormSnId"
              value="SN"
              name="artNorm"
            />
            <label :for="artNormSnId">SN - Stammnorm</label>
          </div>
          <div class="flex items-center">
            <RadioButton
              v-model="artNorm"
              :input-id="artNormAnId"
              value="ÄN"
              name="artNorm"
            />
            <label :for="artNormAnId">ÄN - Änderungsnorm</label>
          </div>
          <div class="flex items-center">
            <RadioButton
              v-model="artNorm"
              :input-id="artNormUnId"
              value="ÜN"
              name="artNorm"
            />
            <label :for="artNormUnId">ÜN - Übergangsnorm</label>
          </div>
        </div>
      </fieldset>
    </fieldset>
  </form>
</template>
