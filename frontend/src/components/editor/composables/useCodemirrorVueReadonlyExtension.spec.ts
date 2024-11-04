import { nextTick, ref, shallowRef } from "vue"
import { useCodemirrorVueReadonlyExtension } from "@/components/editor/composables/useCodemirrorVueReadonlyExtension"
import { EditorView } from "@codemirror/view"
import { describe, it, expect } from "vitest"

describe("useCodemirrorVueReadonlyExtension", () => {
  it("sets initial state", () => {
    const readOnly = ref(true)
    const editorView = shallowRef<EditorView | null>(null)
    const extension = useCodemirrorVueReadonlyExtension(editorView, readOnly)
    editorView.value = new EditorView({ extensions: extension })

    expect(editorView.value?.state.readOnly).toBe(true)
  })

  it("updates state", async () => {
    const readOnly = ref(false)
    const editorView = shallowRef<EditorView | null>(null)
    const extension = useCodemirrorVueReadonlyExtension(editorView, readOnly)
    editorView.value = new EditorView({ extensions: extension })

    expect(editorView.value?.state.readOnly).toBe(false)

    readOnly.value = true
    await nextTick()
    expect(editorView.value?.state.readOnly).toBe(true)
  })
})
