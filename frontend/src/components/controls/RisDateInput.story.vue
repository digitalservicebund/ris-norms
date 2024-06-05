<script lang="ts" setup>
import RisDateInput from "@/components/controls/RisDateInput.vue"
import { ref } from "vue"

const dateEntryOne = ref("2022-01-01")
const dateEntryTwo = ref("2022-01-01")
const dateEntryThree = ref("2022-01-01")

const externalValidationError = {
  message: "Extesrsnes Fehler",
  instance: "test-id",
}
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Valid Date">
      <RisDateInput
        id="test-id"
        v-model="dateEntryOne"
        size="small"
        label="Zeitgrenze 1"
      ></RisDateInput>
    </Variant>

    <Variant title="External Validation Error">
      <RisDateInput
        id="test-id"
        v-model="dateEntryTwo"
        size="small"
        label="Zeitgrenze 1"
        :validation-error="externalValidationError"
      ></RisDateInput>
    </Variant>

    <Variant title="External Validation Error">
      <RisDateInput
        id="test-id"
        v-model="dateEntryThree"
        size="small"
        label="Zeitgrenze 1"
        :validation-error="externalValidationError"
        label-position="left"
      ></RisDateInput>
    </Variant>
  </Story>
</template>
