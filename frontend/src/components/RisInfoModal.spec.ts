import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisInfoModal from "./RisInfoModal.vue"

describe("RisInfoModal", () => {
  test("renders title and description", () => {
    const title = "Test Title"
    const description = "Test Description"

    render(RisInfoModal, {
      props: { title, description },
    })

    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(description)).toBeInTheDocument()
  })

  test("renders a button with the correct label", () => {
    const iconText = "Icon Text"

    render(RisInfoModal, {
      props: { iconText },
    })

    const button = screen.getByText(iconText)
    expect(button).toBeInTheDocument()
  })
})
