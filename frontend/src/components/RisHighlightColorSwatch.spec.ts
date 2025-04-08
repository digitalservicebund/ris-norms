import { describe, expect, it } from "vitest"
import RisHighlightColorSwatch from "./RisHighlightColorSwatch.vue"
import { render } from "@testing-library/vue"

describe("risHighlightColorSwatch", () => {
  it("should render", () => {
    const { container } = render(RisHighlightColorSwatch, {
      props: {
        colorIndex: 1,
      },
    })

    // eslint-disable-next-line -- Purely visual component, no better selectors available
    expect(container.querySelector(".bg-highlight-2-default")).toBeVisible()
  })

  it("should apply different sizes", () => {
    const { container } = render(RisHighlightColorSwatch, {
      props: {
        colorIndex: 1,
        size: ["h-28", "w-28"],
      },
    })

    // eslint-disable-next-line -- Purely visual component, no better selectors available
    expect(container.querySelector(".h-28.w-28")).toBeVisible()
  })
})
