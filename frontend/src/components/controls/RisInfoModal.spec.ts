import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisInfoModal from "./RisInfoModal.vue"

describe("RisInfoModal", () => {
  test("renders title and description", () => {
    const title = "Test Title"
    const description = "Test Description"

    render(RisInfoModal, {
      props: { title, description },
      global: { stubs: { RouterLink: true }, renderStubDefaultSlot: true },
    })

    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(description)).toBeInTheDocument()
  })

  test("renders a button with the correct label", () => {
    const iconText = "Icon Text"
    const title = "Dummy Title"

    render(RisInfoModal, {
      props: { iconText, title },
      global: { stubs: { RouterLink: true }, renderStubDefaultSlot: true },
    })

    const button = screen.getByText(iconText)
    expect(button).toBeInTheDocument()
  })
})
