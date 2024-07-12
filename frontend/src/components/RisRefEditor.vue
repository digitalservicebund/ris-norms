<script setup lang="ts">
import { ref, watch } from "vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import CopyIcon from "~icons/ic/outline-content-copy"
import CloseIcon from "~icons/ic/close"
const aknRef = defineModel<Element>({ required: true })
const emit = defineEmits(["change", "delete"])

const type = ref("Typ")
watch(type, () => {
  aknRef.value.setAttribute("type", type.value)
  emit("change")
})

const bezugsnorm = ref("")
watch(bezugsnorm, () => {
  aknRef.value.setAttribute("bezugsnorm", bezugsnorm.value)

  if (bezugsnorm.value.length > 10) {
    href.value = "eli/bund/bgbl-1/0001/1/0001-01-01/1/deu/regelungstext-1"
  }

  emit("change")
})

const fassung = ref("")
watch(fassung, () => {
  aknRef.value.setAttribute("fassung", fassung.value)
  emit("change")
})

const href = ref("")
watch(href, () => {
  aknRef.value.setAttribute("href", href.value)
  emit("change")
})

watch(
  aknRef,
  () => {
    type.value = aknRef.value.getAttribute("type") ?? "Typ"
    bezugsnorm.value = aknRef.value.getAttribute("bezugsnorm") ?? ""
    fassung.value = aknRef.value.getAttribute("fassung") ?? ""
    href.value = aknRef.value.getAttribute("href") ?? ""
  },
  { immediate: true },
)

async function handleCopyClick() {
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

function handleDeleteClick() {
  emit("delete")
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

    <div>
      <RisTextButton
        size="small"
        variant="ghost"
        label="Kopieren"
        icon-only
        :icon="CopyIcon"
        @click="handleCopyClick"
      ></RisTextButton>

      <RisTextButton
        size="small"
        variant="ghost"
        label="Löschen"
        icon-only
        :icon="CloseIcon"
        @click="handleDeleteClick"
      ></RisTextButton>
    </div>

    <div v-if="href" class="col-span-4 p-4">
      {{ href }}
    </div>
  </div>
</template>
