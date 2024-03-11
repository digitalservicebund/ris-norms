<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { computed } from "vue"
import IcOutlineModeEdit from "~icons/ic/outline-mode-edit"

const props = defineProps<{
  /**
   * When set, the panel will render as a list item so it can be included in
   * ordered or unordered lists. By default, the panel will be rendered as an
   * unsemantic div element and you'll need to ensure correct semantics by
   * yourself.
   */
  asListItem?: boolean

  /** FNA number of the affected document. */
  fna?: string

  /** Abbreviated title of the affected document. */
  shortTitle?: string

  /** Full title of the affected document. */
  title: string

  /**
   * ELI of the affected document. This is needed both for display as well
   * as linking to the correct page for metadata editing.
   */
  eli: string
}>()

const tag = computed<"li" | "div">(() => (props.asListItem ? "li" : "div"))

const eliParam = useEliPathParameter()

const editorUrl = computed<string>(
  () => `/amending-laws/${eliParam.value}/affected-documents/${props.eli}/edit`,
)
</script>

<template>
  <component
    :is="tag"
    :class="{ 'list-none': tag === 'li' }"
    class="flex gap-24 bg-blue-200 p-24"
  >
    <div class="flex flex-1 flex-col gap-8">
      <div v-if="fna || shortTitle">
        <template v-if="fna">
          FNA <span class="font-bold">{{ fna }}</span>
        </template>
        <span v-if="fna && shortTitle" class="mx-4 font-bold">-</span>
        <span v-if="shortTitle" class="font-bold">{{ shortTitle }}</span>
      </div>
      <div v-if="title">{{ title }}</div>
      <div v-if="eli">{{ eli }}</div>
    </div>

    <div class="flex flex-none items-center">
      <RisTextButton
        label="Metadaten bearbeiten"
        :icon="IcOutlineModeEdit"
        variant="ghost"
        :to="editorUrl"
      />
    </div>
  </component>
</template>
