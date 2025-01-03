import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisInfoHeader from "./RisInfoHeader.vue"

describe("risInfoHeader", () => {
  it("renders heading and subtitle", () => {
    const heading = "Test Heading"
    const subtitle = "Test Title"

    render(RisInfoHeader, {
      props: { heading, subtitle },
    })

    const headingElement = screen.getByText(heading)
    expect(headingElement).toBeInTheDocument()

    const titleElement = screen.getByText(subtitle)
    expect(titleElement).toBeInTheDocument()

    expect(headingElement.compareDocumentPosition(titleElement)).toBe(
      Node.DOCUMENT_POSITION_FOLLOWING,
    )
  })
})
