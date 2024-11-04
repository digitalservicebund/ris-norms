import { nextTick, ref, shallowRef } from "vue"
import { EditorView } from "@codemirror/view"
import { useCodemirrorVueEditableExtension } from "@/components/editor/composables/useCodemirrorVueEditableExtension"
import { describe, it, expect } from "vitest"

describe("useCodemirrorVueEditableExtension", () => {
  it("sets initial state", async () => {
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

  it("updates state", async () => {
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
