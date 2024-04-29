import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import { nextTick } from "vue"
import RisCodeEditor from "./RisCodeEditor.vue"
import { EditorView } from "codemirror"

describe("RisCodeEditor", () => {
  // We can't reliably test user interactions with the component in a unit test as parts of codemirror that get
  // called on interactions require a real browser. We therefore are creating some transactions on codemirror directly
  // to test the interactions between our component and codemirror.

  test("renders initial content", async () => {
    render(RisCodeEditor, {
      props: {
        modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })

    await nextTick()

    const editor = screen.getByRole("textbox")
    expect(editor).toBeInTheDocument()
    expect(editor.textContent).toBe(
      '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
    )
  })

  test("renders changed content when the initial content changes", async () => {
    const renderResult = render(RisCodeEditor, {
      props: {
        modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })
    await nextTick()

    expect(screen.getByRole("textbox").textContent).toBe(
      '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
    )

    await renderResult.rerender({
      modelValue: "<xml></xml>",
    })
    expect(screen.getByRole("textbox").textContent).toBe("<xml></xml>")
  })

  test("changing the content creates a change event", async () => {
    const renderResult = render(RisCodeEditor, {
      props: {
        modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })
    await nextTick()

    const editorView: EditorView = (
      screen.getByRole("textbox") as unknown as { cmView: { view: EditorView } }
    ).cmView.view
    editorView.dispatch({
      changes: { from: 20, to: 45, insert: "eli/bund/bgbl-1/1990/s2954" },
    })

    expect(screen.getByRole("textbox").textContent).toBe(
      '<akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954"/>',
    )

    expect(renderResult.emitted()["update:modelValue"].length).toBe(1)
    expect(renderResult.emitted()["update:modelValue"][0]).toEqual([
      '<akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954"/>',
    ])
  })

  test("changing the content and then updating the initial content to the same content does not cause a recreation of the editor", async () => {
    const renderResult = render(RisCodeEditor, {
      props: {
        modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })
    await nextTick()

    const editorView = (
      screen.getByRole("textbox") as unknown as { cmView: { view: EditorView } }
    ).cmView.view
    editorView.dispatch({
      changes: { from: 20, to: 45, insert: "eli/bund/bgbl-1/1990/s2954" },
    })
    await renderResult.rerender({
      modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954"/>',
    })

    expect(editorView).toBe(
      (
        screen.getByRole("textbox") as unknown as {
          cmView: { view: EditorView }
        }
      ).cmView.view,
    )
  })
})
