<script lang="ts" setup>
import RisDateInput from "@/components/controls/RisDateInput.vue"
import { ref } from "vue"

const dateEntry = ref({
  date: "2022-01-01",
})

const invalidDateEntry = ref({
  date: "0099-01-01",
})
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Valid Date">
      <RisDateInput
        id="test-id"
        v-model="dateEntry.date"
        size="small"
      ></RisDateInput>
    </Variant>

    <Variant title="Invalid Date">
      <RisDateInput
        id="test-id"
        v-model="invalidDateEntry.date"
        size="small"
      ></RisDateInput>
    </Variant>
  </Story>
</template>
