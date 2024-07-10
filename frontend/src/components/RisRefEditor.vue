<script setup lang="ts">
import { computed } from "vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"

const aknRef = defineModel<Element>({ required: true })

const type = computed({
  get: () => aknRef.value.getAttribute("type") ?? "Typ",
  set: (newValue) => aknRef.value.setAttribute("type", newValue ?? ""),
})

const bezugsnorm = computed({
  get: () => aknRef.value.getAttribute("bezugsnorm"),
  set: (newValue) => aknRef.value.setAttribute("bezugsnorm", newValue ?? ""),
})

const fassung = computed({
  get: () => aknRef.value.getAttribute("fassung"),
  set: (newValue) => aknRef.value.setAttribute("fassung", newValue ?? ""),
})
</script>

<template>
  <div>
    <!-- eslint-disable vuejs-accessibility/form-control-has-label -->
    <select v-model="type" class="ds-select ds-select-small">
      <option>Zitierung</option>
      <option>Einschränkung von Grundrechten</option>
      <option>Überleitungsvorschrift</option>
    </select>
    <RisTextInput
      id="bezugsnorm"
      v-model="bezugsnorm"
      placeholder="Bezugsnorm"
      size="small"
    ></RisTextInput>
    <RisTextInput
      id="fassung"
      v-model="fassung"
      placeholder="Fassung"
      size="small"
    ></RisTextInput>
  </div>
</template>

<style scoped></style>
