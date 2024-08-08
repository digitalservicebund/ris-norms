<script setup lang="ts">
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useRef } from "@/composables/useRef"
import { useDebounceFn } from "@vueuse/core"
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
  <RisDropdownInput
    id="ref-refersTo-select"
    v-model="refersTo"
    :items="refersToOptions"
    placeholder=" "
    select-classes="bg-blue-300"
    class="-mr-1"
    aria-label="Typ"
  ></RisDropdownInput>

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

  <RisTextButton
    size="small"
    variant="ghost"
    label="Löschen"
    icon-only
    :icon="CloseIcon"
    @click="$emit('delete')"
  />
</template>
