<script setup lang="ts">
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useGetNorm } from "@/services/normService"
import { computed } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import IcOutlineBorderColor from "~icons/ic/outline-border-color"
import IcOutlinePostAdd from "~icons/ic/outline-post-add"

const props = defineProps<{
  /**
   * When set, the panel will render as a list item so it can be included in
   * ordered or unordered lists. By default, the panel will be rendered as an
   * unsemantic div element and you'll need to ensure correct semantics by
   * yourself.
   */
  asListItem?: boolean

  /**
   * ELI of the affected document. This is needed both for display.
   */
  eli: string

  /**
   * ELI of the first future version of the affected document.
   * This is needed for linking to the correct page for metadata editing.
   */
  zf0Eli: string
}>()

const { data: norm, isFetching, error } = useGetNorm(props.eli)

const tag = computed<"li" | "div">(() => (props.asListItem ? "li" : "div"))

const eliParam = useEliPathParameter()

const editorUrl = computed<string>(
  () =>
    `/amending-laws/${eliParam.value}/affected-documents/${props.zf0Eli}/edit`,
)

const referenceEditorUrl = computed<string>(
  () =>
    `/amending-laws/${eliParam.value}/affected-documents/${props.zf0Eli}/references`,
)
</script>

<template>
  <component
    :is="tag"
    :class="{ 'list-none': tag === 'li' }"
    class="flex gap-24 bg-blue-200 p-24"
  >
    <div v-if="isFetching" class="flex flex-1 items-center justify-center">
      <RisLoadingSpinner></RisLoadingSpinner>
    </div>

    <div v-else-if="error" class="flex-1">
      <RisErrorCallout :error />
    </div>

    <div v-else class="flex flex-1 flex-col gap-8">
      <div v-if="norm?.fna || norm?.shortTitle">
        <template v-if="norm?.fna">
          FNA <span class="font-bold">{{ norm.fna }}</span>
        </template>
        <span v-if="norm.fna && norm.shortTitle" class="mx-4 font-bold">-</span>
        <span v-if="norm.shortTitle" class="font-bold">{{
          norm.shortTitle
        }}</span>
      </div>
      <div v-if="norm?.title">{{ norm.title }}</div>
      <div v-if="norm?.eli">{{ norm.eli }}</div>
    </div>

    <div class="flex flex-none flex-col items-start justify-center">
      <RisTextButton
        label="Inhaltliche Auszeichnungen"
        :icon="IcOutlineBorderColor"
        variant="ghost"
        :to="referenceEditorUrl"
      />
      <RisTextButton
        label="Metadaten dokumentieren"
        :icon="IcOutlinePostAdd"
        variant="ghost"
        :to="editorUrl"
      />
    </div>
  </component>
</template>
