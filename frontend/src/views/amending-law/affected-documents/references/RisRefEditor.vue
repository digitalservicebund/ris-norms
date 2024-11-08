<script setup lang="ts">
import { DropdownItem } from "@/types/dropdownItem"
import Select from "primevue/select"
import { useRef } from "@/views/amending-law/affected-documents/references/useRef"
import { useDebounceFn } from "@vueuse/core"
import Button from "primevue/button"
import { ref, watch } from "vue"
import CloseIcon from "~icons/ic/close"

/**
 * The XML-String (LDML.de) of the akn:ref element.
 */
const xmlSnippet = defineModel<string>("xmlSnippet")

const props = defineProps<{
  /**
   * When this is set to true, the input field will receive keyboard
   * focus *once* (keeping this set to true will not keep the keyboard
   * focus on the input field). Set this prop from `false` to `true`
   * if you want to automatically focus the input field when the
   * selected ref changes.
   */
  grabFocus?: boolean
}>()

defineEmits<{
  /**
   * The akn:ref element should be removed.
   */
  delete: []

  /**
   * Emitted when the user presses arrow up. The parent view can then
   * move the focus from the current editor line to the previous line.
   */
  selectPrevious: []

  /**
   * Emitted when the user presses arrow down. The parent view can then
   * move the focus from the current editor line to the next line.
   */
  selectNext: []
}>()

const refersToOptions: DropdownItem[] = [
  {
    label: "Zitierung",
    value: "zitierung",
  },
]

const updateXmlSnippet = useDebounceFn((newXmlSnippet) => {
  xmlSnippet.value = newXmlSnippet
}, 250)

const { refersTo, href } = useRef(xmlSnippet, updateXmlSnippet)

const inputEl = ref<HTMLInputElement | null>(null)

watch(
  () => props.grabFocus,
  (val) => {
    if (val) inputEl.value?.focus()
  },
  { immediate: true },
)
</script>

<template>
  <Select
    v-model="refersTo"
    :options="refersToOptions"
    option-label="label"
    option-value="value"
    aria-label="Typ"
    class="-mr-1"
  />

  <input
    ref="inputEl"
    v-model="href"
    aria-label="ELI mit Zielstelle"
    class="ds-input ds-input-small -ml-1"
    placeholder="ELI mit Zielstelle"
    type="text"
    @keydown.up.prevent="$emit('selectPrevious')"
    @keydown.down.prevent="$emit('selectNext')"
  />

  <Button
    aria-label="Löschen"
    class="!h-40 !w-40 focus:-outline-offset-4"
    text
    @click="$emit('delete')"
  >
    <template #icon>
      <CloseIcon />
    </template>
  </Button>
</template>
