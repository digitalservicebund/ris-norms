<script setup lang="ts">
import { nextTick, ref, watch } from "vue"

/**
 * Slots
 *
 * Slots with a name like "eid:part-1_part-2-..." are added into the rendering of the LDML.de preview to the end of the
 * element with the given eid. E.g. the content of a slot with the name "eid:hauptteil-1_art-1" is added to the end of
 * the rendering of the first article.
 */

const props = withDefaults(
  defineProps<{
    content: string
    highlightMods?: boolean
    highlightAffectedDocument?: boolean
    selectedEids?: string[]
  }>(),
  {
    highlightMods: false,
    highlightAffectedDocument: false,
    selectedEids: () => [],
  },
)

const emit = defineEmits<{
  "content:click": [
    {
      eid: string
      guid: string
      originalEvent: MouseEvent
    },
  ]
}>()

function handleClick(e: MouseEvent) {
  if (!(e.target instanceof HTMLElement)) {
    return
  }

  if (!e.target.dataset.eid || !e.target.dataset.guid) {
    return
  }

  emit("content:click", {
    eid: e.target.dataset.eid,
    guid: e.target.dataset.guid,
    originalEvent: e,
  })
}

function getElementByEid(eid: string) {
  return document.querySelector(`[data-eId="${eid}"]`)
}

function difference<a>(xs: a[], ys: a[]): a[] {
  return xs.filter((x) => ys.indexOf(x) === -1)
}

watch(
  () => [...props.selectedEids],
  (newElements: string[], oldElements: string[]) => {
    const addedElements = difference(newElements, oldElements)
    addedElements.forEach((element) => {
      getElementByEid(element)?.classList.add("selected")
    })

    const removedElements = difference(oldElements, newElements)
    removedElements.forEach((element) => {
      getElementByEid(element)?.classList.remove("selected")
    })
  },
  { deep: true },
)

function teleportEidSlotNameToEid(slotName: string) {
  return slotName.substring(4)
}

const contentHash = ref("")
watch(
  () => props.content,
  async () => {
    await nextTick()
    contentHash.value = props.content
    props.selectedEids.forEach((element) => {
      getElementByEid(element)?.classList.add("selected")
    })
  },
)
</script>

<template>
  <div class="overflow-hidden">
    <!-- eslint-disable vue/no-v-html -->
    <!-- eslint-disable vuejs-accessibility/click-events-have-key-events vuejs-accessibility/no-static-element-interactions -- think of something for this when moving this out of the prototype -->
    <div
      class="flex h-full overflow-auto bg-white p-8"
      :class="{
        'highlight-mods': highlightMods,
        'highlight-affected-document': highlightAffectedDocument,
      }"
      @click="handleClick"
      v-html="content"
    ></div>
    <!-- eslint-enable vuejs-accessibility/click-events-have-key-events vuejs-accessibility/no-static-element-interactions -->
    <!-- eslint-enable vue/no-v-html -->

    <template
      v-for="name in Object.keys($slots).filter((key) =>
        key.startsWith('eid:'),
      )"
      :key="`${contentHash}-${name}`"
    >
      <Teleport
        v-if="getElementByEid(teleportEidSlotNameToEid(name))"
        :to="getElementByEid(teleportEidSlotNameToEid(name))"
      >
        <slot :name="name"></slot>
      </Teleport>
    </template>
  </div>
</template>

<style scoped>
:deep([data-eId]) {
  @apply align-top;
}

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

:deep(.akn-paragraph .akn-num) {
  @apply float-left mr-8;
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
