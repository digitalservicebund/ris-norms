<script setup lang="ts">
import { ref, watch } from "vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"

const aknRef = defineModel<Element>({ required: true })
const emit = defineEmits(["change"])

const type = ref("Typ")
watch(type, () => {
  aknRef.value.setAttribute("type", type.value)
  emit("change")
})

const bezugsnorm = ref("")
watch(bezugsnorm, () => {
  aknRef.value.setAttribute("bezugsnorm", bezugsnorm.value)
  emit("change")
})

const fassung = ref("")
watch(fassung, () => {
  aknRef.value.setAttribute("fassung", fassung.value)
  emit("change")
})

watch(
  aknRef,
  () => {
    type.value = aknRef.value.getAttribute("type") ?? "Typ"
    bezugsnorm.value = aknRef.value.getAttribute("bezugsnorm") ?? ""
    fassung.value = aknRef.value.getAttribute("fassung") ?? ""
  },
  { immediate: true },
)

async function handleCopy(e: ClipboardEvent) {
  if (document.getSelection()?.type === "Range") {
    return
  }

  e.preventDefault()

  await navigator.clipboard.write([
    new ClipboardItem({
      "text/plain": new Blob(
        [
          JSON.stringify({
            type: type.value,
            bezugsnorm: bezugsnorm.value,
            fassung: fassung.value,
          }),
        ],
        { type: "text/plain" },
      ),
    }),
  ])
}

async function handlePaste(e: ClipboardEvent) {
  console.log(e)
  const clipboardData = e.clipboardData?.getData("text/plain")

  if (!clipboardData || !clipboardData.startsWith("{")) {
    return
  }

  e.preventDefault()

  const data = JSON.parse(clipboardData)

  type.value = data.type
  bezugsnorm.value = data.bezugsnorm
  fassung.value = data.fassung
}
</script>

<template>
  <div @copy="handleCopy" @paste="handlePaste">
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
