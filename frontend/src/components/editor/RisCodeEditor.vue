<script setup lang="ts">
import { EditorView, basicSetup } from "codemirror"
import { xml } from "@codemirror/lang-xml"
import { ref, watch } from "vue"

const props = withDefaults(
  defineProps<{
    /**
     * The initial content of the editor. Changing this after the editor is created has no effect.
     */
    initialContent?: string
  }>(),
  {
    initialContent: "",
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
 * Initialize codemirror when the editor element is available
 */
watch(
  editorElement,
  () => {
    if (editorElement.value == null) {
      return () => {}
    }

    const editorView = new EditorView({
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
      ],
      parent: editorElement.value,
    })

    return () => {
      editorView.destroy()
    }
  },
  { immediate: true },
)
</script>

<template>
  <div ref="editorElement" class="overflow-hidden"></div>
</template>
