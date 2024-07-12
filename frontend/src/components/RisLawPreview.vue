<script setup lang="ts">
import { nextTick, ref, useAttrs, watch } from "vue"
import { refDebounced, useEventListener } from "@vueuse/core"

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
     * Enable or disable highlighting of the affected document elements.
     *
     * @default false
     */
    highlightAffectedDocument?: boolean

    /**
     * EIds of the currently selected elements.
     */
    selected?: string[]

    /**
     * Classes that should be assigned to the elements with the given eIds.
     */
    eIdClasses?: { [eId: string]: string[] }
  }>(),
  {
    highlightAffectedDocument: false,
    selected: () => [],
    eIdClasses: () => ({}),
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
  select: [
    {
      eid: string
      start: number
      end: number
    } | null,
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
                event.stopPropagation()
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

/**
 * Setup and update the .selected class on selected elements.
 */
watch(
  [() => props.selected, () => props.content],
  async (value, oldValue, onCleanup) => {
    // Need to tick in order to give Vue some time to render the HTML first
    await nextTick()

    const elements = props.selected.map((eid) =>
      container.value?.querySelector(`[data-eId="${eid}"]`),
    )

    onCleanup(() => {
      elements.forEach((element) => {
        if (!element) {
          return
        }

        element.classList.remove("selected")
      })
    })

    elements.forEach((element) => {
      if (!element) {
        return
      }

      element.classList.add("selected")
    })
  },
  { immediate: true },
)

function hasAsParentElement(element: Node, parentElement: Node): boolean {
  const elementParent = element.parentElement
  if (!elementParent) {
    return false
  }

  if (elementParent === parentElement) {
    return true
  }

  return hasAsParentElement(elementParent, parentElement)
}

const selection = ref<Range | null>()
const debouncedSelection = refDebounced(selection, 500)

watch(debouncedSelection, () => {
  const parentElement = debouncedSelection.value?.startContainer.parentElement
  const eid = parentElement?.dataset.eid
  if (!parentElement || !eid) {
    emit("select", null)
    return
  }

  let previousSibling = debouncedSelection.value?.startContainer.previousSibling
  let previousSiblingsTextLength = 0
  while (previousSibling) {
    if (
      previousSibling.nodeType !== Node.ELEMENT_NODE ||
      !(
        (previousSibling as Element).classList.contains("akn-quote-endQuote") ||
        (previousSibling as Element).classList.contains("akn-quote-startQuote")
      )
    ) {
      previousSiblingsTextLength +=
        previousSibling?.textContent?.replaceAll(/\s/g, "").length ?? 0
    }
    previousSibling = previousSibling.previousSibling
  }

  const textContent = debouncedSelection.value.startContainer.textContent ?? ""

  const startWithoutSpaces = textContent
    .slice(0, debouncedSelection.value.startOffset)
    .replaceAll(/\s/g, "").length
  const endWithoutSpaces = textContent
    .slice(0, debouncedSelection.value.endOffset)
    .replaceAll(/\s/g, "").length

  emit("select", {
    eid,
    start: previousSiblingsTextLength + startWithoutSpaces,
    end: previousSiblingsTextLength + endWithoutSpaces,
  })
})

function limitSelectionToSelectableRanges() {
  const selection = getSelection()

  if (
    selection?.anchorNode &&
    selection.anchorNode.nodeType === 3 &&
    selection.anchorNode.parentNode !== selection.focusNode?.parentNode
  ) {
    if (selection.anchorNode === selection.focusNode) {
      selection.extend(
        selection.anchorNode,
        selection.anchorNode.textContent?.length,
      )
    } else if (
      selection.anchorNode.parentNode &&
      selection.anchorNode.parentNode.lastChild
    ) {
      selection.extend(
        selection.anchorNode.parentNode.lastChild,
        selection.anchorNode.parentNode.lastChild.textContent?.length,
      )
    }
  }
}

useEventListener(document, "selectionchange", () => {
  const currentSelection = getSelection()
  if (
    !container.value ||
    !currentSelection?.anchorNode ||
    !hasAsParentElement(currentSelection.anchorNode, container.value)
  ) {
    selection.value = null
    return
  }

  limitSelectionToSelectableRanges()

  const range = getSelection()?.getRangeAt(0)

  if (range) {
    selection.value = range
  }
})

const uniqueId = Math.random().toString(16).substring(2)

function getElementByEid(eid: string) {
  return document.querySelector(`#preview-${uniqueId} [data-eId="${eid}"]`)
}

function teleportEidSlotNameToEid(slotName: string) {
  return slotName.substring(4)
}

const contentHash = ref("")
watch(
  () => props.content,
  async () => {
    await nextTick()
    contentHash.value = props.content
  },
)

/**
 * Setup and update the custom classes on elements.
 */
watch(
  [() => props.eIdClasses, () => props.content],
  async (value, oldValue, onCleanup) => {
    // Need to wait for a tick in order to give Vue some time to render the HTML first
    await nextTick()

    const cleanupFunctions: (() => void)[] = []

    Object.entries(props.eIdClasses).forEach(([eId, classNames]) => {
      const element = container.value?.querySelector(`[data-eId="${eId}"]`)

      if (!element) return

      cleanupFunctions.push(() => {
        element.classList.remove(...classNames)
      })

      element.classList.add(...classNames)
    })

    onCleanup(() => {
      cleanupFunctions.forEach((cleanup) => cleanup())
    })
  },
  { immediate: true },
)
</script>

<template>
  <div class="overflow-hidden">
    <!-- eslint-disable vue/no-v-html -->
    <div
      :id="`preview-${uniqueId}`"
      ref="container"
      tabindex="0"
      class="flex h-full overflow-auto bg-white p-20"
      :class="{
        'highlight-affected-document': highlightAffectedDocument,
      }"
      v-html="content"
    ></div>
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

.highlight-affected-document :deep(.akn-affectedDocument) {
  @apply border border-dotted border-gray-900 bg-highlight-affectedDocument-default px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument):hover {
  @apply border border-dotted border-highlight-affectedDocument-border bg-highlight-affectedDocument-hover px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument.selected) {
  @apply border border-solid border-highlight-affectedDocument-border bg-highlight-affectedDocument-selected px-2;
}

:deep([role="button"]) {
  @apply cursor-pointer;
}
</style>
