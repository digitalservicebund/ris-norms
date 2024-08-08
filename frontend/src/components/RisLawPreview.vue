<script setup lang="ts">
import { useElementId } from "@/composables/useElementId"
import { v4 as uuidV4 } from "uuid"
import { nextTick, ref, SetupContext, useAttrs, watch } from "vue"

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
     * EIds of the currently selected elements.
     */
    selected?: string[]

    /**
     * Classes that should be assigned to the elements with the given eIds.
     */
    eIdClasses?: { [eId: string]: string[] }

    /**
     * How keyboard focus should be handled in the component. By default,
     * users can use tab to focus the preview as a whole, and the up and down
     * arrows to select elements within the preview.
     *
     * By setting this prop to false, this behavior can be disabled in favor
     * of the browsers' native focus handling. This will be less convenient to
     * use in most cases, but has better compatibility for very complex previews
     * (e.g. structural replacements).
     */
    arrowFocus?: boolean
  }>(),
  {
    highlightAffectedDocument: false,
    selected: () => [],
    eIdClasses: () => ({}),
    arrowFocus: true,
  },
)

/**
 * Event for a click event on a specific akn element.
 */
export type AknElementClickEvent = {
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
}

const emit = defineEmits<{
  /**
   * Event fired when clicking on an HTML-element for the specified LDML.de element.
   *
   * Event handlers, a11y properties and so on are only created for elements for
   * which an event handler is registered. By providing an additional attribute of
   * the form `akn:${string}-aria-label` a label for the created button can be provided.
   *
   * E.g. `@click:akn:article` will fire for every click on a part of the preview that
   * is showing an article.
   */
  [key: `click:akn:${string}`]: [AknElementClickEvent]
}>()

const attrs = useAttrs()

/**
 * The element containing the provided content html.
 */
const container = ref<HTMLElement | null>()

/**
 * Makes the given HTMLElement clickable.
 *
 * Handles adding the event listeners for mouse interactions and a11y roles.
 * Note that this does not include keyboard interaction as we're implementing
 * that ourselves in order to support some non-standard focus behavior.
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
  element.role = "button"
  if (element.dataset.eid) element.id = ids[element.dataset.eid]

  element.addEventListener(
    "click",
    (event: Event) => {
      if (!(event instanceof MouseEvent)) return
      eventHandler(event)
    },
    eventListenerOptions,
  )

  // Browser-native focus handling (if enabled)
  if (!props.arrowFocus) {
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
  }
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

    onCleanup(() => {
      abortController.abort()
      interactiveEls = []
    })

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
              { signal: abortController.signal },
            )

            interactiveEls.push(htmlElement)

            const aknElementNameWithDashes = aknElement
              .replaceAll(/([A-Z])/g, "-$1")
              .toLowerCase()

            const label =
              attrs[`akn:${aknElementNameWithDashes}-aria-label`] ??
              htmlElement.innerText

            if (typeof label === "string") htmlElement.ariaLabel = label
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
        if (!element) return
        element.classList.remove("selected")
      })
    })

    elements.forEach((element) => {
      if (!element) return
      element.classList.add("selected")
    })
  },
  { immediate: true },
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

/**
 * We must pass $slots to this method in the render, and it is not possible to use
 * a computed value. `useSlots` is not reactive but $slots is so this is necessary
 * to be able to react to slots created after the initial
 * render of this component.
 *
 * @param slots $slots
 */
function getTeleportSlotEIds(slots: SetupContext["slots"]) {
  return Object.keys(slots)
    .filter((key) => key.startsWith("eid:"))
    .map((name) => name.substring(4))
}

function getTeleportTarget(eId: string) {
  if (!container.value) {
    return
  }

  return container.value.querySelector(`[data-eId='${eId}']`)
}

/**
 * We need to update this id whenever a new preview has been rendered to trick vue into
 * updating the teleport targets in case there is a new eId in the preview.
 */
const uniqueId = ref("")

watch(
  [() => props.content],
  async () => {
    await nextTick()
    uniqueId.value = uuidV4()
  },
  { deep: true },
)

/* -------------------------------------------------- *
 * Focus handling                                     *
 * -------------------------------------------------- */

// NB: If we need this in more places in the future, consider extracting
// this into a composable to reuse it in other places!

const ids = useElementId()

/**
 * Used for keeping track of the elements that have been made interactive. Needed
 * for focus handling.
 */
let interactiveEls: HTMLElement[] = []

/** Index of the currently focused element in the `interactiveEls` list. */
const focusedEl = ref(-1)

const activeDescendant = ref<string>()

watch(focusedEl, (next, prev) => {
  if (interactiveEls[prev]) {
    interactiveEls[prev].classList.remove("focused")
  }

  if (interactiveEls[next]) {
    interactiveEls[next].classList.add("focused")
    interactiveEls[next].scrollIntoView({ behavior: "smooth", block: "center" })
    activeDescendant.value = interactiveEls[next].id
  } else {
    activeDescendant.value = undefined
  }
})

function moveFocusDown(event: KeyboardEvent) {
  // If there are no elements that could be focused or custom focus handling is
  // disabled, we'll ignore the event and keep the browser default behavior
  if (!props.arrowFocus || interactiveEls.length === 0) return

  event.preventDefault()
  focusedEl.value = Math.min(focusedEl.value + 1, interactiveEls.length - 1)
}

function moveFocusUp(event: KeyboardEvent) {
  // If there are no elements that could be focused or custom focus handling is
  // disabled, we'll ignore the event and keep the browser default behavior
  if (!props.arrowFocus || interactiveEls.length === 0) return

  event.preventDefault()
  focusedEl.value = Math.max(focusedEl.value - 1, 0)
}

function triggerFocusedElement(event: KeyboardEvent) {
  // If there are no elements that could be focused or custom focus handling is
  // disabled, we'll ignore the event and keep the browser default behavior
  if (!props.arrowFocus || interactiveEls.length === 0) return

  event.preventDefault()
  interactiveEls[focusedEl.value]?.click()
}

// Keep the focus in sync with whatever is selected through some other interaction
// (e.g. clicking an element or selecting a row in the reference table). Because
// multiple elements can be selected at the same time, we will only do this if
// exactly one element is selected in order to not mess with the user's flow if
// they're trying to select multiple elements via keyboard.
watch(
  () => props.selected,
  (val) => {
    if (val.length !== 1) return
    focusedEl.value = interactiveEls.findIndex((i) => i.dataset.eid === val[0])
  },
)
</script>

<template>
  <div class="overflow-hidden">
    <!-- eslint-disable vue/no-v-html -->
    <!-- eslint-disable vuejs-accessibility/no-static-element-interactions -->
    <div
      ref="container"
      :aria-activedescendant="activeDescendant"
      :role="arrowFocus ? 'textbox' : undefined"
      class="preview-container flex h-full overflow-auto bg-white p-20 -outline-offset-2 focus:outline focus:outline-4 focus:outline-blue-800"
      data-testid="preview-container"
      tabindex="0"
      @keypress.enter="triggerFocusedElement"
      @keydown.up="moveFocusUp"
      @keydown.down="moveFocusDown"
      v-html="content"
    ></div>
    <!-- eslint-enable vue/no-v-html -->
    <!-- eslint-enable vuejs-accessibility/no-static-element-interactions -->

    <template
      v-for="eId in getTeleportSlotEIds($slots)"
      :key="`${uniqueId}-${eId}`"
    >
      <Teleport v-if="getTeleportTarget(eId)" :to="getTeleportTarget(eId)">
        <slot :name="`eid:${eId}`"></slot>
      </Teleport>
    </template>
  </div>
</template>

<style scoped>
.preview-container:focus :deep(.focused) {
  @apply relative z-10 outline outline-4 outline-offset-2 outline-blue-800;
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

:deep(.akn-quotedStructure .akn-longTitle) {
  @apply font-normal;
}

:deep(.akn-quotedText::before) {
  content: "„";
}

:deep(.akn-quotedText::after) {
  content: "“";
}

:deep(.akn-quotedStructure > :first-child::before) {
  content: "„";
}

:deep(.akn-quotedStructure > :last-child::after) {
  content: "“";
}

:deep(.akn-quotedStructure > :last-child),
:deep(.akn-quotedStructure > :last-child *) {
  @apply inline;
}

:deep(.akn-shortTitle) {
  @apply block font-normal;
}

:deep([role="button"]) {
  @apply cursor-pointer;
}
</style>
