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

function autoDetectBezugsnormTest(bezugsnorm: string) {
  return bezugsnorm
    .replaceAll(/(?<=\s)(a|A)(?<absatz>[\d-]+)(?=\s)/g, "Abs. $<absatz>")
    .replaceAll(/(?<=\s)(s|S)(?<satz>[\d-]+)(?=\s)/g, "Satz $<satz>")
    .replaceAll(/(?<=\s)(n|N)(?<nummer>[\d-]+)(?=\s)/g, "Nr. $<nummer>")
    .replaceAll(/(?<=\s)(b|B)(?<buchstabe>\w)(?=\s)/g, "Buchstabe $<buchstabe>")
}

const bezugsnorm = ref("")
watch(bezugsnorm, () => {
  bezugsnorm.value = autoDetectBezugsnormTest(bezugsnorm.value)

  aknRef.value.setAttribute("bezugsnorm", bezugsnorm.value)

  if (bezugsnorm.value.length > 3) {
    href.value = "eli/bund/bgbl-1/0001/1/0001-01-01/1/deu/regelungstext-1"
  }

  emit("change")
})

const bezugsnormExtension = ref("")
watch(bezugsnormExtension, () => {
  bezugsnormExtension.value = autoDetectBezugsnormTest(
    bezugsnormExtension.value,
  )

  aknRef.value.setAttribute("bezugsnormExtension", bezugsnormExtension.value)

  emit("change")
})

const bezugsnormAutomated = ref(false)
watch(bezugsnormAutomated, () => {
  if (bezugsnormAutomated.value) {
    aknRef.value.setAttribute("bezugsnorm-automated", "true")
  } else {
    aknRef.value.removeAttribute("bezugsnorm-automated")
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
    bezugsnormExtension.value =
      aknRef.value.getAttribute("bezugsnormExtension") ?? ""
    fassung.value = aknRef.value.getAttribute("fassung") ?? ""
    href.value = aknRef.value.getAttribute("href") ?? ""
    bezugsnormAutomated.value = aknRef.value.hasAttribute(
      "bezugsnorm-automated",
    )
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
            bezugsnormExtension: bezugsnormExtension.value,
            fassung: fassung.value,
          }),
        ],
        { type: "text/plain" },
      ),
    }),
  ])
}

async function handlePaste(e: ClipboardEvent) {
  const clipboardData = e.clipboardData?.getData("text/plain")

  if (!clipboardData || !clipboardData.startsWith("{")) {
    return
  }

  e.preventDefault()

  const data = JSON.parse(clipboardData)

  type.value = data.type
  bezugsnorm.value = data.bezugsnorm
  bezugsnormExtension.value = data.bezugsnormExtension
  fassung.value = data.fassung
  bezugsnormAutomated.value = data.bezugsnormAutomated || false
}

function handleDeleteClick() {
  emit("delete")
}

function handleBezugsnormKeydownDown(e: KeyboardEvent) {
  const bezugsnormInputs = document.querySelectorAll<HTMLElement>("#bezugsnorm")

  const currentInputIndex = Array.from(bezugsnormInputs).findIndex(
    (input) => input == e.target,
  )

  if (bezugsnormInputs[currentInputIndex + 1]) {
    bezugsnormInputs[currentInputIndex + 1].focus()
  } else {
    bezugsnormInputs[0].focus()
  }
}

function handleBezugsnormKeydownUp(e: KeyboardEvent) {
  const bezugsnormInputs = document.querySelectorAll<HTMLElement>("#bezugsnorm")

  const currentInputIndex = Array.from(bezugsnormInputs).findIndex(
    (input) => input == e.target,
  )

  if (currentInputIndex === 0) {
    bezugsnormInputs[bezugsnormInputs.length - 1].focus()
  } else {
    bezugsnormInputs[currentInputIndex - 1].focus()
  }
}

function handleBezugsnormExtensionKeydownDown(e: KeyboardEvent) {
  const bezugsnormInputs = document.querySelectorAll<HTMLElement>(
    "#bezugsnormExtension",
  )

  const currentInputIndex = Array.from(bezugsnormInputs).findIndex(
    (input) => input == e.target,
  )

  if (bezugsnormInputs[currentInputIndex + 1]) {
    bezugsnormInputs[currentInputIndex + 1].focus()
  } else {
    bezugsnormInputs[0].focus()
  }
}

function handleBezugsnormExtensionKeydownUp(e: KeyboardEvent) {
  const bezugsnormInputs = document.querySelectorAll<HTMLElement>(
    "#bezugsnormExtension",
  )

  const currentInputIndex = Array.from(bezugsnormInputs).findIndex(
    (input) => input == e.target,
  )

  if (currentInputIndex === 0) {
    bezugsnormInputs[bezugsnormInputs.length - 1].focus()
  } else {
    bezugsnormInputs[currentInputIndex - 1].focus()
  }
}

function handleFassungKeydownDown(e: KeyboardEvent) {
  const fassungInputs = document.querySelectorAll<HTMLElement>("#fassung")

  const currentInputIndex = Array.from(fassungInputs).findIndex(
    (input) => input == e.target,
  )

  if (fassungInputs[currentInputIndex + 1]) {
    fassungInputs[currentInputIndex + 1].focus()
  } else {
    fassungInputs[0].focus()
  }
}

function handleFassungKeydownUp(e: KeyboardEvent) {
  const fassungInputs = document.querySelectorAll<HTMLElement>("#fassung")

  const currentInputIndex = Array.from(fassungInputs).findIndex(
    (input) => input == e.target,
  )

  if (currentInputIndex === 0) {
    fassungInputs[fassungInputs.length - 1].focus()
  } else {
    fassungInputs[currentInputIndex - 1].focus()
  }
}

function handleBezugsnormChange() {
  bezugsnormAutomated.value = false
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
      @keydown.down="handleBezugsnormKeydownDown"
      @keydown.up="handleBezugsnormKeydownUp"
      @change="handleBezugsnormChange"
    >
    </RisTextInput>
    <RisTextInput
      id="bezugsnormExtension"
      v-model="bezugsnormExtension"
      placeholder="Ergänzung zur Bezugsnorm"
      size="small"
      @keydown.down="handleBezugsnormExtensionKeydownDown"
      @keydown.up="handleBezugsnormExtensionKeydownUp"
      @change="handleBezugsnormChange"
    ></RisTextInput>
    <RisTextInput
      id="fassung"
      v-model="fassung"
      placeholder="Fassung"
      size="small"
      @keydown.down="handleFassungKeydownDown"
      @keydown.up="handleFassungKeydownUp"
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

    <div v-if="href" class="col-span-5 p-4">
      {{ href }}
    </div>
  </div>
</template>
