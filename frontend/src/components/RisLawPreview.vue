<script setup lang="ts">
defineProps<{
  content: string
}>()
</script>

<template>
  <div class="overflow-auto bg-white p-8">
    <!-- eslint-disable-next-line vue/no-v-html -->
    <div v-html="content"></div>
  </div>
</template>

<style scoped>
:deep(.act) {
  @apply flex flex-col;
}

:deep(.preface) {
  @apply mb-24;
}

:deep(.longTitle),
:deep(.docTitle),
:deep(.shortTitle) {
  @apply text-xl;
}

:deep(.body) {
  @apply my-24 flex flex-col gap-24;
}

:deep(.heading) {
  @apply text-lg;
}

:deep(.section) > .num {
  @apply text-lg;
}

:deep(.section) > .article {
  @apply mt-24;
}

:deep(.article) > .num {
  @apply text-lg;
}

:deep(.paragraph) {
  @apply flex flex-row;
}

:deep(.paragraph .num) {
  @apply mr-8;
}

:deep(.list .point) {
  @apply mb-4 flex flex-row;
}

:deep(.list .point .num) {
  @apply mr-8;
}

:deep(.conclusions) {
  @apply flex flex-col gap-24;
}

:deep(.signature) {
  @apply block;
}
</style>
