import { nextTick, ref, shallowRef } from "vue"
import { EditorView } from "@codemirror/view"
import { useCodemirrorVueEditableExtension } from "@/components/editor/composables/useCodemirrorVueEditableExtension"
import { describe, test, expect } from "vitest"

describe("useCodemirrorVueEditableExtension", () => {
  test("Sets initial state", async () => {
    const editable = ref(true)
    const editorView = shallowRef<EditorView | null>(null)
    const extension = useCodemirrorVueEditableExtension(editorView, editable)
    editorView.value = new EditorView({
      extensions: extension,
    })

    expect(editorView.value?.contentDOM?.getAttribute("contenteditable")).toBe(
      "true",
    )
  })

  test("Updates state", async () => {
    const editable = ref(false)
    const editorView = shallowRef<EditorView | null>(null)
    const extension = useCodemirrorVueEditableExtension(editorView, editable)
    editorView.value = new EditorView({ extensions: extension })

    expect(editorView.value?.contentDOM?.getAttribute("contenteditable")).toBe(
      "false",
    )

    editable.value = true
    await nextTick()
    expect(editorView.value?.contentDOM?.getAttribute("contenteditable")).toBe(
      "true",
    )
  })
})
