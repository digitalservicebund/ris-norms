import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"
import RisLawPreview from "./RisLawPreview.vue"

describe("risLawPreview", () => {
  it("should render provided content", () => {
    render(RisLawPreview, {
      props: { content: "<span class='longTitle'>Test Title</span>" },
    })
    expect(screen.getByText("Test Title")).toBeInTheDocument()
    expect(screen.getByText("Test Title").className).contain("longTitle")
  })

  it("should emit click event", async () => {
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findByRole("button")
    await userEvent.click(screen.getByRole("button", { name: "MOD" }))

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  it("should emit click event when using keyboard navigation without arrows", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        arrowFocus: false,
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findByRole("button")

    await user.tab()
    await user.tab()
    expect(screen.getByRole("button", { name: "MOD" })).toHaveFocus()

    await user.keyboard("{Enter}")

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  it("should emit click event when using keyboard navigation with arrows", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findByRole("button")

    await user.tab()
    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD" })).toHaveClass("focused")

    await user.keyboard("{Enter}")

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  it("should move the focus up and down then using keyboard navigation with arrows", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD2" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD3" })).toHaveClass("focused")

    await user.keyboard("{Enter}")
    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888513",
      originalEvent: expect.anything(),
    })

    await user.keyboard("{ArrowUp}")
    expect(screen.getByRole("button", { name: "MOD2" })).toHaveClass("focused")

    await user.keyboard("{ArrowUp}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")
  })

  it("should not move the focus before the first element", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")

    await user.keyboard("{ArrowUp}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")
  })

  it("should not move the focus past the last element", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD2" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD3" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD3" })).toHaveClass("focused")
  })

  it("should focus nested elements in order", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content: `
          <div>
            <div class="akn-foo" data-eid="eid-1">
              1
              <div class="akn-bar" data-eid="eid-2">2</div>
              <div class="akn-bar" data-eid="eid-3">3</div>
            </div>
            <div class="akn-foo" data-eid="eid-4">4</div>
          </div>
        `,
      },
      attrs: {
        "onClick:akn:foo": handler,
        "onClick:akn:bar": handler,
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: /1/ })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "2" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "3" })).toHaveClass("focused")

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "4" })).toHaveClass("focused")
  })

  it("should automatically focus the selected element", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    const { rerender } = render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")

    await rerender({
      selected: [
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3",
      ],
    })

    expect(screen.getByRole("button", { name: "MOD3" })).toHaveClass("focused")
  })

  it("should focus the clicked on element", async () => {
    const user = userEvent.setup()

    render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": vi.fn(),
      },
    })

    await screen.findAllByRole("button")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    expect(screen.getByRole("button", { name: "MOD1" })).toHaveClass("focused")

    await user.click(screen.getByRole("button", { name: "MOD3" }))
    expect(screen.getByRole("button", { name: "MOD3" })).toHaveClass("focused")
  })

  it("should set the active descendant", async () => {
    const user = userEvent.setup()
    const handler = vi.fn()

    render(RisLawPreview, {
      props: {
        content: `<div>
            <span class='longTitle'>Test Title</span>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888511'>MOD1</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-2' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888512'>MOD2</div>
            <div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-3' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888513'>MOD3</div>
          </div>`,
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findAllByRole("button")

    const textbox = screen.getByRole("textbox")
    expect(textbox).not.toHaveAttribute("aria-activedescendant")

    await user.tab()

    await user.keyboard("{ArrowDown}")
    const selected = screen.getByRole("button", { name: "MOD1" })

    expect(selected).toHaveClass("focused")
    expect(textbox).toHaveAttribute("aria-activedescendant", selected.id)
  })

  it("should have .selected class for selected elements", async () => {
    render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        selected: [
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
        ],
      },
    })

    await screen.findByText("MOD")
    expect(screen.getByText("MOD")).toHaveClass("selected")
  })

  it("should update selected elements", async () => {
    const { rerender } = render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        selected: [],
      },
    })

    await screen.findByText("MOD")
    expect(screen.getByText("MOD")).not.toHaveClass("selected")

    await rerender({
      selected: [
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      ],
    })

    await screen.findByText("MOD")
    expect(screen.getByText("MOD")).toHaveClass("selected")
  })

  it("should support changing the content", async () => {
    const handler = vi.fn()

    const { rerender } = render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        selected: [
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
        ],
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await screen.findByRole("button")
    await userEvent.click(screen.getByRole("button", { name: "MOD" }))
    expect(screen.getByText("MOD")).toHaveClass("selected")

    await rerender({
      content:
        "<div><span class='longTitle'>Test Title 2</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>CHANGED MOD</div></div>",
    })

    expect(screen.getByText("Test Title 2")).toBeInTheDocument()
    await screen.findByRole("button")

    expect(screen.getByText("CHANGED MOD")).toHaveClass("selected")
    await userEvent.click(screen.getByRole("button", { name: "CHANGED MOD" }))

    expect(handler).toHaveBeenCalledTimes(2)
  })

  it("should set and update classes of element with the specified eId", async () => {
    const { rerender } = render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        eIdClasses: {
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1":
            ["class-1", "class-2"],
        },
      },
    })

    await nextTick()
    expect(screen.getByText("MOD")).toHaveClass("class-1")
    expect(screen.getByText("MOD")).toHaveClass("class-2")

    await rerender({
      eIdClasses: {
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1":
          ["class-3", "class-2"],
      },
    })

    await nextTick()
    expect(screen.getByText("MOD")).not.toHaveClass("class-1")
    expect(screen.getByText("MOD")).toHaveClass("class-2")
    expect(screen.getByText("MOD")).toHaveClass("class-3")
  })
})
