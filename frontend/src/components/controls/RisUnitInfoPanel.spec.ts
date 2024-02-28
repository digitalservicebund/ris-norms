import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisUnitInfoPanel from "./RisUnitInfoPanel.vue"

describe("RisInfoModal", () => {
  test("renders heading and title", () => {
    const heading = "Test Heading"
    const title = "Test Title"

    render(RisUnitInfoPanel, {
      props: { heading, title },
    })

    const headingElement = screen.getByText(heading)
    expect(headingElement).toBeInTheDocument()

    const titleElement = screen.getByText(title)
    expect(titleElement).toBeInTheDocument()

    expect(headingElement.compareDocumentPosition(titleElement)).toBe(
      Node.DOCUMENT_POSITION_FOLLOWING,
    )
  })
})
