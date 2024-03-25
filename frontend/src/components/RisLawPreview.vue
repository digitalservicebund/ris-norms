<script setup lang="ts">
defineProps<{
  content: string
}>()
</script>

<template>
  <div class="ds-textarea overflow-hidden p-2">
    <!-- eslint-disable-next-line vue/no-v-html -->
    <div class="flex h-full overflow-auto bg-white p-8" v-html="content"></div>
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
</style>
