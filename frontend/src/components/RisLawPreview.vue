<script setup lang="ts">
import { nextTick, reactive, ref, watch } from "vue"

const props = withDefaults(
  defineProps<{
    content: string
    highlightMods: boolean
    highlightAffectedDocument: boolean
  }>(),
  {
    highlightMods: false,
    highlightAffectedDocument: false,
  },
)

type SavePayload = {
  modifiedElement: string
  quotedTextFrom: string
  quotedTextTo: string
  targetLawEli: string
}

const emit = defineEmits<{
  save: [value: SavePayload]
}>()

/* -------------------------------------------------- *
 * Data                                               *
 * -------------------------------------------------- */

// The following are used to de-serialize structured data like change commands,
// refs, and quoted texts from the attributes in the corresponding HTML
// elements.

type QuotedText = {
  eId?: string
  guid?: string
  text?: string
}

type Ref = {
  eId?: string
  guid?: string
  href?: string
}

type ChangeCommand = {
  eId?: string
  refersTo?: string
  guid?: string
  ref?: Ref
  quotedText?: [source: QuotedText | null, target: QuotedText | null]
}

function getQuotedText(el: HTMLElement): QuotedText {
  return {
    eId: el.dataset["eid"],
    guid: el.dataset["guid"],
    text: el.innerText,
  }
}

function getRef(el: HTMLElement): Ref {
  return {
    eId: el.dataset["eid"],
    guid: el.dataset["guid"],
    href: el.dataset["href"],
  }
}

function getChangeCommand(el: HTMLElement): ChangeCommand {
  const quotedTextEls = el.querySelectorAll(".akn-quotedText")

  const quotedTextSrc =
    quotedTextEls[0] instanceof HTMLElement
      ? getQuotedText(quotedTextEls[0])
      : null

  const quotedTextTarget =
    quotedTextEls[1] instanceof HTMLElement
      ? getQuotedText(quotedTextEls[1])
      : null

  const ref = el.querySelector(".akn-ref")

  return {
    eId: el.dataset["eid"],
    refersTo: el.dataset["refersto"],
    guid: el.dataset["guid"],
    ref: ref instanceof HTMLElement ? getRef(ref) : undefined,
    quotedText: [quotedTextSrc, quotedTextTarget],
  }
}

/* -------------------------------------------------- *
 * Wiring up the DOM                                  *
 * -------------------------------------------------- */

// Keeps track of event handlers so we can clean them up when we need to.
const handlers: Array<{
  el: HTMLElement
  event: keyof HTMLElementEventMap
  handler: Parameters<HTMLElement["addEventListener"]>[1]
}> = []

// This is the HTML element where the preview will appear.
const container = ref<HTMLElement | null>()

// This connects the 3rd-party HTML with Vue, mostly by manually wiring up
// event handlers and setting some additional supporting properties on the
// relevant elements. If we were to actually use this I would build this in
// a more modular way as we will likely need more things than just mods.
async function connect() {
  // Need to tick in order to give Vue some time to render the HTML first
  await nextTick()

  // Clean up existing handlers first to avoid memory leaks. To keep this
  // simple, we just recreate everything whenever the content changes.
  handlers.forEach(({ el, event, handler }) => {
    el.removeEventListener(event, handler)
  })

  const modElements = container.value?.querySelectorAll(".akn-mod")

  modElements?.forEach((modElement) => {
    // Nothing to do if the element has an unexpected type
    if (!(modElement instanceof HTMLElement)) return

    // De-serialize data from the attributes on the element
    const changeCommand = getChangeCommand(modElement)

    // Create and register event handlers
    const onClick = (event: Event) => {
      if (!(event instanceof PointerEvent)) return

      toggleSelectedChangeCommand(changeCommand, modElement, {
        selectMultiple: event.metaKey,
      })
    }

    modElement.addEventListener("click", onClick)
    handlers.push({ el: modElement, event: "click", handler: onClick })

    const onKeyPress = (event: Event) => {
      if (event instanceof KeyboardEvent && event.key === "Enter") {
        beginEditChangeCommand(changeCommand)
        event.stopPropagation()
      }
    }
    modElement.addEventListener("keypress", onKeyPress)
    handlers.push({ el: modElement, event: "keypress", handler: onKeyPress })

    // Make additional changes to the element, e.g. for keyboard naviation.
    // This could potentially also be accessibility stuff.
    modElement.tabIndex = 1
  })
}

// Wire up event handling etc. whenever the document changes
watch(
  () => props.content,
  () => {
    connect()
  },
  { immediate: true },
)

/* -------------------------------------------------- *
 * Selection management                               *
 * -------------------------------------------------- */

const selectedChangeCommands = reactive<ChangeCommand[]>([])

function toggleSelectedChangeCommand(
  changeCommand: ChangeCommand,
  el: HTMLElement,
  opts = { selectMultiple: false },
) {
  if (!opts.selectMultiple) {
    selectedChangeCommands.splice(0, selectedChangeCommands.length)
    container.value
      ?.querySelectorAll(".akn-mod.selected")
      ?.forEach((el) => el.classList.remove("selected"))
  }

  const inList = selectedChangeCommands.findIndex(
    (i) => i.eId === changeCommand.eId,
  )

  if (inList >= 0) {
    el.classList.remove("selected")
    selectedChangeCommands.splice(inList, 1)
  } else {
    el.classList.add("selected")
    selectedChangeCommands.push(changeCommand)
  }
}

/* -------------------------------------------------- *
 * Editing form                                       *
 * -------------------------------------------------- */

const dialogEl = ref<HTMLDialogElement | null>()

const changeCommandEid = ref("")
const changeCommandTargetEli = ref("")
const changeCommandReplaceFrom = ref("")
const changeCommandReplaceTo = ref("")

function beginEditChangeCommand(changeCommand: ChangeCommand) {
  changeCommandEid.value = changeCommand.eId ?? ""
  changeCommandTargetEli.value = changeCommand.ref?.href ?? ""
  changeCommandReplaceFrom.value = changeCommand.quotedText?.[0]?.text ?? ""
  changeCommandReplaceTo.value = changeCommand.quotedText?.[1]?.text ?? ""

  dialogEl.value?.show()
}

function saveEditChangeCommand() {
  emit("save", {
    modifiedElement: changeCommandEid.value,
    targetLawEli: changeCommandTargetEli.value,
    quotedTextFrom: changeCommandReplaceFrom.value,
    quotedTextTo: changeCommandReplaceTo.value,
  })

  closeEditChangeCommand()
}

function closeEditChangeCommand() {
  changeCommandEid.value = ""
  changeCommandTargetEli.value = ""
  changeCommandReplaceFrom.value = ""
  changeCommandReplaceTo.value = ""

  dialogEl.value?.close()
}
</script>

<template>
  <div class="overflow-hidden">
    <dialog
      ref="dialogEl"
      class="absolute left-auto right-32 top-32 w-[600px] rounded-md border-2 border-blue-900 bg-white p-16"
    >
      <form class="flex flex-col gap-16">
        <fieldset class="flex gap-16">
          <label class="flex-1" for="aenderungstyp">
            Änderungstyp
            <select
              id="aenderungstyp"
              class="ds-select ds-select-medium"
              disabled
            ></select
          ></label>

          <label class="flex-1" for="zeitgrenze">
            Zeitgrenze
            <select
              id="zeitgrenze"
              class="ds-select ds-select-medium"
              disabled
            ></select
          ></label>
        </fieldset>

        <label for="eli-zielgesetz">
          ELI Zielgesetz
          <input
            id="eli-zielgesetz"
            v-model="changeCommandTargetEli"
            type="text"
            class="ds-input ds-input-medium"
          />
        </label>

        <label for="textstelle">
          zu ersetzende Textstelle
          <input
            id="textstelle"
            v-model="changeCommandReplaceFrom"
            type="text"
            class="ds-input ds-input-medium"
          />
        </label>

        <label for="textinhalt">
          neuer Textinhalt
          <textarea
            id="textinhalt"
            v-model="changeCommandReplaceTo"
            class="ds-textarea"
          />
        </label>
      </form>

      <footer class="mt-16 flex justify-end gap-14 border-t pt-16">
        <button
          class="ds-button ds-button-secondary"
          @click="closeEditChangeCommand()"
        >
          Abbrechen
        </button>
        <button class="ds-button" @click="saveEditChangeCommand()">
          Speichern
        </button>
      </footer>
    </dialog>

    <!-- eslint-disable vue/no-v-html -->
    <div
      ref="container"
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

.highlight-mods :deep(.akn-mod) {
  @apply -mx-2 inline-block border border-dotted border-gray-900 bg-highlight-mod-default px-2;
}

.highlight-mods :deep(.akn-mod):hover {
  @apply -mx-[3px] -my-1 inline-block cursor-pointer border-2 border-dotted border-highlight-mod-border bg-highlight-mod-hover px-2;
}

/* This is currently unused as the .selected class is never applied to elements */
.highlight-mods :deep(.akn-mod.selected) {
  @apply -mx-[3px] -my-1 inline-block border-2 border-solid border-highlight-mod-border bg-highlight-mod-selected px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument) {
  @apply -mx-2 inline-block border border-dotted border-gray-900 bg-highlight-affectedDocument-default px-2;
}

.highlight-affected-document :deep(.akn-affectedDocument):hover {
  @apply -mx-[3px] -my-1 inline-block border-2 border-dotted border-highlight-affectedDocument-border bg-highlight-affectedDocument-hover px-2;
}

/* This is currently unused as the .selected class is never applied to elements */
.highlight-affected-document :deep(.akn-affectedDocument.selected) {
  @apply -mx-[3px] -my-1 inline-block border-2 border-solid border-highlight-affectedDocument-border bg-highlight-affectedDocument-selected px-2;
}
</style>
