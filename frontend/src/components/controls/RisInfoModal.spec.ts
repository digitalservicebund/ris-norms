import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisInfoModal from "./RisInfoModal.vue"

describe("risInfoModal", () => {
  it("renders title and description", () => {
    const title = "Test Title"
    const description = "Test Description"

    render(RisInfoModal, {
      props: { title, description },
      global: { stubs: { RouterLink: true }, renderStubDefaultSlot: true },
    })

    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(description)).toBeInTheDocument()
  })

  it("renders a button with the correct label", () => {
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
