import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisLawPreview from "./RisLawPreview.vue"

describe("RisLawPreview", () => {
  test("should render provided content", () => {
    render(RisLawPreview, {
      props: { content: "<span class='longTitle'>Test Title</span>" },
    })
    expect(screen.getByText("Test Title")).toBeInTheDocument()
    expect(screen.getByText("Test Title").className).contain("longTitle")
  })
})
