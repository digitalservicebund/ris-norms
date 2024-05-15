import { render, screen, waitFor } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import RisLawPreview from "./RisLawPreview.vue"
import { userEvent } from "@testing-library/user-event"

describe("RisLawPreview", () => {
  test("should render provided content", () => {
    render(RisLawPreview, {
      props: { content: "<span class='longTitle'>Test Title</span>" },
    })
    expect(screen.getByText("Test Title")).toBeInTheDocument()
    expect(screen.getByText("Test Title").className).contain("longTitle")
  })

  test("should emit click event", async () => {
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

    await waitFor(() => screen.getByRole("button"))
    await userEvent.click(screen.getByRole("button", { name: "MOD" }))

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  test("should emit click event when using keyboard navigation", async () => {
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

    await waitFor(() => screen.getByRole("button"))

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

  test("should have .selected class for selected elements", async () => {
    render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        selected: [
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
        ],
      },
    })

    await waitFor(() => screen.getByText("MOD"))
    expect(screen.getByText("MOD")).toHaveClass("selected")
  })

  test("should update selected elements", async () => {
    const renderResult = render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
        selected: [],
      },
    })

    await waitFor(() => screen.getByText("MOD"))
    expect(screen.getByText("MOD")).not.toHaveClass("selected")

    await renderResult.rerender({
      selected: [
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      ],
    })

    await waitFor(() => screen.getByText("MOD"))
    expect(screen.getByText("MOD")).toHaveClass("selected")
  })

  test("should support changing the content", async () => {
    const handler = vi.fn()

    const renderResult = render(RisLawPreview, {
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

    await waitFor(() => screen.getByRole("button"))
    await userEvent.click(screen.getByRole("button", { name: "MOD" }))
    expect(screen.getByText("MOD")).toHaveClass("selected")

    await renderResult.rerender({
      content:
        "<div><span class='longTitle'>Test Title 2</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>CHANGED MOD</div></div>",
    })

    expect(screen.getByText("Test Title 2")).toBeInTheDocument()
    await waitFor(() => screen.getByRole("button"))

    expect(screen.getByText("CHANGED MOD")).toHaveClass("selected")
    await userEvent.click(screen.getByRole("button", { name: "CHANGED MOD" }))

    expect(handler).toHaveBeenCalledTimes(2)
  })
})
