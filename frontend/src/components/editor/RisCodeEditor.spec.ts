import { render, screen } from "@testing-library/vue"
import { beforeAll, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"
import RisCodeEditor from "./RisCodeEditor.vue"
import type { EditorView } from "codemirror"

describe("risCodeEditor", () => {
  // We can't reliably test user interactions with the component in a unit test as parts of codemirror that get
  // called on interactions require a real browser. We therefore are creating some transactions on codemirror directly
  // to test the interactions between our component and codemirror.

  beforeAll(() => {
    const consoleError = console.error
    vi.spyOn(console, "error").mockImplementation(
      (...args: Parameters<typeof consoleError>) => {
        // Codemirror does not fully work in our unit test environment (as it's not a real browser) and therefore throws
        // an error. We want to hide this error to not cause confusion. This error does not impact the unit tests
        // written in this file.
        if (
          args.length > 0 &&
          args[0] instanceof Error &&
          args[0].stack?.includes("textRange(...).getClientRects")
        ) {
          return
        }

        consoleError(...args)
      },
    )
  })

  it("renders initial content", async () => {
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

  it("renders changed content when the initial content changes", async () => {
    const { rerender } = render(RisCodeEditor, {
      props: {
        modelValue: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })
    await nextTick()

    expect(screen.getByRole("textbox").textContent).toBe(
      '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
    )

    await rerender({
      modelValue: "<xml></xml>",
    })
    expect(screen.getByRole("textbox").textContent).toBe("<xml></xml>")
  })

  it("changing the content creates a change event", async () => {
    const { emitted } = render(RisCodeEditor, {
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

    expect(emitted()["update:modelValue"].length).toBe(1)
    expect(emitted()["update:modelValue"][0]).toEqual([
      '<akn:FRBRuri value="eli/bund/bgbl-1/1990/s2954"/>',
    ])
  })

  it("changing the content and then updating the initial content to the same content does not cause a recreation of the editor", async () => {
    const { rerender } = render(RisCodeEditor, {
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
    await rerender({
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
