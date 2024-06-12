<script setup lang="ts">
import { useCodemirrorVueEditableExtension } from "@/components/editor/composables/useCodemirrorVueEditableExtension"
import { useCodemirrorVueReadonlyExtension } from "@/components/editor/composables/useCodemirrorVueReadonlyExtension"
import { xml } from "@codemirror/lang-xml"
import { basicSetup, EditorView } from "codemirror"
import { computed, ref, shallowRef, watch } from "vue"

/**
 * The xml content of the editor. Triggers an update event when the content changes.
 */
const model = defineModel<string>()

const props = withDefaults(
  defineProps<{
    /**
     * The readonly state of the editor.
     *
     * @see {@link https://codemirror.net/docs/ref/#state.EditorState^readOnly|CodeMirror Documentation}
     */
    readonly?: boolean
    /**
     * The editable state of the editor.
     *
     * @see {@link https://codemirror.net/docs/ref/#view.EditorView^editable|CodeMirror Documentation}
     */
    editable?: boolean
  }>(),
  {
    readonly: false,
    editable: true,
  },
)

/**
 * The element that is used for holding the codemirror editor
 */
const editorElement = ref<HTMLDivElement | null>(null)

/**
 * The current codemirror editor
 */
const editorView = shallowRef<EditorView | null>(null)

const codemirrorVueReadonlyExtension = useCodemirrorVueReadonlyExtension(
  editorView,
  computed(() => props.readonly),
)
const codemirrorVueEditableExtension = useCodemirrorVueEditableExtension(
  editorView,
  computed(() => props.editable),
)

/**
 * Initialize codemirror when the editor element is available
 */
watch(
  [editorElement, model],
  () => {
    if (editorElement.value == null) {
      return
    }

    // do not destroy and recreate the editor if it already has the correct content (when e.g. the change of the model originated from the editor itself)
    if (model.value === editorView.value?.state.doc.toString()) {
      return
    }

    if (editorView.value) {
      editorView.value.destroy()
    }

    editorView.value = new EditorView({
      doc: model.value,
      extensions: [
        basicSetup,
        xml(),
        EditorView.updateListener.of((viewUpdate) => {
          if (viewUpdate.docChanged) {
            model.value = viewUpdate.state.doc.toString()
          }
        }),
        // Ensure that the editor shows a scrollbar instead of overflowing
        EditorView.theme({
          "&": { height: "100%" },
          ".cm-content, .cm-gutter": { minHeight: "100%" },
          ".cm-scroller": { overflow: "auto" },
        }),
        EditorView.theme({
          "&": { backgroundColor: "#ffffff" },
        }),
        codemirrorVueReadonlyExtension,
        codemirrorVueEditableExtension,
      ],
      parent: editorElement.value,
    })
  },
  { immediate: true },
)
</script>

<template>
  <div
    ref="editorElement"
    class="ds-textarea overflow-hidden p-2 focus:outline focus:outline-2 focus:outline-blue-500"
  ></div>
</template>
