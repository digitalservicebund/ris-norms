<script setup lang="ts">
import { useElementId } from "@/composables/useElementId"
import { ref, useTemplateRef, watchEffect } from "vue"

const { length = 3 } = defineProps<{
  /**
   * Specifies the maximum number of visible lines.
   * @default 3
   */
  length?: number
}>()

const expanded = defineModel<boolean>("expanded", { default: false })

const canExpand = ref(false)

const textContent = useTemplateRef("textContent")

const { textId } = useElementId("expandable-text")

watchEffect(() => {
  if (textContent.value) {
    canExpand.value =
      textContent.value.scrollHeight > textContent.value.clientHeight
  }
})
</script>

<template>
  <div>
    <p :id="textId" ref="textContent" :class="{ [$style.truncate]: !expanded }">
      <slot />
    </p>

    <button
      v-if="canExpand"
      class="ris-link1-regular"
      :aria-controls="textId"
      :aria-expanded="expanded"
      @click="expanded = !expanded"
    >
      <template v-if="expanded">Weniger anzeigen</template>
      <template v-if="!expanded">Mehr anzeigen</template>
    </button>
  </div>
</template>

<style module>
.truncate {
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: v-bind(length);
}
</style>
