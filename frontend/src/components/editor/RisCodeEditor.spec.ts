import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisCodeEditor from "./RisCodeEditor.vue"
import { nextTick } from "vue"

describe("RisCodeEditor", () => {
  test("renders initial content", async () => {
    render(RisCodeEditor, {
      props: {
        initialContent: '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
      },
    })

    await nextTick()

    const editor = screen.getByRole("textbox")
    expect(editor).toBeInTheDocument()
    expect(editor.textContent).toBe(
      '<akn:FRBRuri value="eli/bund/bgbl-1/1964/s593"/>',
    )
  })

  // we can't reliably test the other functionality of the component in a unit test as parts of codemirror that get
  // called on interactions require a real browser
})
