<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import CloseIcon from "~icons/ic/close"
import RisDropdownInput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"
import { useRef } from "@/composables/useRef"
import { useDebounceFn } from "@vueuse/core"

/**
 * The XML-String (LDML.de) of the akn:ref element.
 */
const xmlSnippet = defineModel<string>("xmlSnippet")

defineEmits<{
  /**
   * The akn:ref element should be removed.
   */
  delete: []
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
    v-model="href"
    aria-label="ELI mit Zielstelle"
    class="ds-input ds-input-small -ml-1"
    placeholder="ELI mit Zielstelle"
    type="text"
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
