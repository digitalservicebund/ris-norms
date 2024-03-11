<script setup lang="ts">
import { basicSetup, EditorView } from "codemirror"
import { xml } from "@codemirror/lang-xml"
import { ref, shallowRef, toRef, watch } from "vue"
import { useCodemirrorVueReadonlyExtension } from "@/components/editor/composables/useCodemirrorVueReadonlyExtension"
import { useCodemirrorVueEditableExtension } from "@/components/editor/composables/useCodemirrorVueEditableExtension"

const props = withDefaults(
  defineProps<{
    /**
     * The initial content of the editor. Changing this will recreate the editor.
     */
    initialContent?: string
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
    initialContent: "",
    readonly: false,
    editable: true,
  },
)

const emit = defineEmits<{
  /**
   * Triggers when a change to the text content of the editor happened.
   */
  change: [
    {
      /**
       *  the new text content
       */
      content: string
    },
  ]
}>()

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
  toRef(props.readonly),
)
const codemirrorVueEditableExtension = useCodemirrorVueEditableExtension(
  editorView,
  toRef(props.editable),
)

/**
 * Initialize codemirror when the editor element is available
 */
watch(
  [editorElement, () => props.initialContent],
  () => {
    if (editorElement.value == null) {
      return
    }

    if (props.initialContent === editorView.value?.state.doc.toString()) {
      return
    }

    if (editorView.value) {
      editorView.value.destroy()
    }

    editorView.value = new EditorView({
      doc: props.initialContent,
      extensions: [
        basicSetup,
        xml(),
        EditorView.updateListener.of((viewUpdate) => {
          if (viewUpdate.docChanged) {
            emit("change", { content: viewUpdate.state.doc.toString() })
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
  <div ref="editorElement" class="ds-textarea overflow-hidden p-2"></div>
</template>
