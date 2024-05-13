import { fireEvent, render, screen, waitFor } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import RisLawPreview from "./RisLawPreview.vue"

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
    await fireEvent.click(screen.getByRole("button", { name: "MOD" }))

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  test("should emit click event when using keyboard navigation", async () => {
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
    await fireEvent.keyDown(screen.getByRole("button", { name: "MOD" }), {
      key: "Enter",
    })

    expect(handler).toHaveBeenCalledWith({
      eid: "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      guid: "148c2f06-6e33-4af8-9f4a-3da67c888510",
      originalEvent: expect.anything(),
    })
  })

  test("should support changing the content", async () => {
    const handler = vi.fn()

    const renderResult = render(RisLawPreview, {
      props: {
        content:
          "<div><span class='longTitle'>Test Title</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>MOD</div></div>",
      },
      attrs: {
        "onClick:akn:mod": handler,
      },
    })

    await waitFor(() => screen.getByRole("button"))
    await fireEvent.click(screen.getByRole("button", { name: "MOD" }))

    await renderResult.rerender({
      content:
        "<div><span class='longTitle'>Test Title 2</span><div class='akn-mod' data-eId='hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1' data-GUID='148c2f06-6e33-4af8-9f4a-3da67c888510'>CHANGED MOD</div></div>",
    })

    expect(screen.getByText("Test Title 2")).toBeInTheDocument()

    await waitFor(() => screen.getByRole("button"))
    await fireEvent.click(screen.getByRole("button", { name: "CHANGED MOD" }))

    expect(handler).toHaveBeenCalledTimes(2)
  })
})
