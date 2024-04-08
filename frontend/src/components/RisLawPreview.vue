<script setup lang="ts">
withDefaults(
  defineProps<{
    content: string
    highlightMods?: boolean
    highlightAffectedDocument?: boolean
  }>(),
  {
    highlightMods: false,
    highlightAffectedDocument: false,
  },
)
</script>

<template>
  <div class="overflow-hidden">
    <!-- eslint-disable vue/no-v-html -->
    <div
      class="flex h-full overflow-auto bg-white p-8"
      :class="{
        'highlight-mods': highlightMods,
        'highlight-affected-document': highlightAffectedDocument,
      }"
      v-html="content"
    ></div>
    <!-- eslint-enable vue/no-v-html -->
  </div>
</template>

<style scoped>
:deep(:is(table, thead, td)) {
  @apply border border-blue-400;
}

:deep(:is(thead, td:first-child)) {
  @apply font-bold;
}

:deep(thead) {
  @apply bg-blue-100;
}

:deep(td) {
  @apply p-8;
}

:deep(td:empty::before) {
  content: "-";
}

:deep(.metadata) {
  @apply mb-24;
}

:deep(.akn-act) {
  @apply flex flex-col;
}

:deep(.akn-preface) {
  @apply mb-24;
}

:deep(h1) {
  @apply text-2xl;
}

:deep(.akn-body) {
  @apply my-24 flex flex-col gap-24;
}

:deep(.akn-section) {
  @apply flex flex-col gap-24;
}

:deep(.akn-section h2) {
  @apply text-xl;
}

:deep(.akn-article h3) {
  @apply text-lg;
}

:deep(.akn-paragraph) {
  @apply flex flex-row;
}

:deep(.akn-paragraph .akn-num) {
  @apply mr-8;
}

:deep(.akn-point) {
  @apply mb-4 flex flex-row;
}

:deep(.akn-point .akn-num) {
  @apply mr-8;
}

:deep(.akn-conclusions) {
  @apply flex flex-col gap-24;
}

:deep(.akn-signature) {
  @apply block;
}

:deep(.akn-intro) {
  @apply mb-4 block;
}

:deep(.akn-a) {
  @apply underline;
}

:deep(.akn-num + .akn-heading) {
  @apply pl-4;
}

:deep(.akn-longTitle) {
  @apply font-bold;
}

:deep(.akn-shortTitle) {
  @apply block font-normal;
}

.highlight-mods :deep(.akn-mod) {
  @apply border border-dotted border-gray-900 bg-highlight-mod-default px-2;
}

.highlight-mods :deep(.akn-mod):hover {
  @apply border border-dotted border-highlight-mod-border bg-highlight-mod-hover px-2;
}

/* This is currently unused as the .selected class is never applied to elements */
.highlight-mods :deep(.akn-mod.selected) {
  @apply border border-solid border-highlight-mod-border bg-highlight-mod-selected px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument) {
  @apply border border-dotted border-gray-900 bg-highlight-affectedDocument-default px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument):hover {
  @apply border border-dotted border-highlight-affectedDocument-border bg-highlight-affectedDocument-hover px-2;
}

/* This is currently unused as the .selected class is never applied to elements */
.highlight-affected-document :deep(.akn-affectedDocument.selected) {
  @apply border border-solid border-highlight-affectedDocument-border bg-highlight-affectedDocument-selected px-2;
}
</style>
