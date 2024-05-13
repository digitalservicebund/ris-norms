<script setup lang="ts">
import { nextTick, ref, useAttrs, watch } from "vue"

const props = withDefaults(
  defineProps<{
    /**
     * The HTML content of the norm that should be shown in the preview.
     *
     * **Important:** This is going to be rendered as-is as HTML. Make sure
     * the content is trustworthy and sanitized in order to prevent security
     * vulnerabilities from rendering manipulated HTML.
     */
    content: string

    /**
     * Enable or disable highlighting of mod elements (these used to be amending
     * commands in the source XML).
     *
     * @default false
     */
    highlightMods?: boolean

    /**
     * Enable or disable highlighting of the affected document elements.
     *
     * @default false
     */
    highlightAffectedDocument?: boolean
  }>(),
  {
    highlightMods: false,
    highlightAffectedDocument: false,
  },
)

const emit = defineEmits<{
  /**
   * Event fired when clicking on an HTML-element for the specified LDML.de element.
   *
   * Event handlers, a11y properties and so on are only created for elements for which an event handler is registered.
   *
   * E.g. `@click:akn:article` will fire for every click on a part of the preview that is showing an article.
   */
  [key: `click:akn:${string}`]: [
    {
      /**
       * Eid of the element that was clicked.
       */
      eid: string
      /**
       * GUID of the element that was clicked.
       */
      guid?: string
      /**
       * The original event.
       */
      originalEvent: MouseEvent | KeyboardEvent
    },
  ]
}>()

const attrs = useAttrs()

/**
 * The element containing the provided content html.
 */
const container = ref<HTMLElement | null>()

/**
 * Makes the given HTMLElement clickable.
 *
 * Handles adding the event listeners (for both mouse and keyboard interactions), a11y role and tab index to the element.
 *
 * @param element the HTMLElement to make clickable.
 * @param eventHandler the handler to call when the element is clicked
 * @param eventListenerOptions additional options for the event listeners.
 */
function makeElementClickable(
  element: HTMLElement,
  eventHandler: (event: KeyboardEvent | MouseEvent) => void,
  eventListenerOptions: AddEventListenerOptions,
) {
  element.addEventListener(
    "click",
    (event: Event) => {
      if (!(event instanceof MouseEvent)) return
      eventHandler(event)
    },
    eventListenerOptions,
  )

  element.addEventListener(
    "keydown",
    (event: Event) => {
      if (
        !(event instanceof KeyboardEvent) ||
        !(event.key === "Enter" || event.key === " ")
      )
        return
      eventHandler(event)
    },
    eventListenerOptions,
  )

  element.tabIndex = 0
  element.role = "button"
}

/**
 * Wire up event handling for click events whenever the document changes.
 */
watch(
  () => props.content,
  async (value, oldValue, onCleanup) => {
    // Need to tick in order to give Vue some time to render the HTML first
    await nextTick()

    const abortController = new AbortController()
    onCleanup(() => abortController.abort())

    Object.keys(attrs)
      .filter((key) => key.startsWith("onClick:akn:"))
      .map((key) => key.replace("onClick:akn:", ""))
      .forEach((aknElement) => {
        container.value
          ?.querySelectorAll(`.akn-${aknElement}`)
          ?.forEach((htmlElement) => {
            if (!(htmlElement instanceof HTMLElement)) return

            const eid = htmlElement.dataset.eid
            const guid = htmlElement.dataset.guid

            if (!eid) return

            makeElementClickable(
              htmlElement,
              (event) => {
                emit(`click:akn:${aknElement}`, {
                  eid,
                  guid,
                  originalEvent: event,
                })
              },
              {
                signal: abortController.signal,
              },
            )
          })
      })
  },
  { immediate: true },
)
</script>

<template>
  <div class="overflow-hidden">
    <!-- eslint-disable vue/no-v-html -->
    <div
      ref="container"
      tabindex="0"
      class="flex h-full overflow-auto bg-white p-20"
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

.highlight-mods :deep(.akn-mod):hover,
.highlight-mods :deep(.akn-mod):focus {
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

:deep([role="button"]) {
  @apply cursor-pointer;
}
</style>
