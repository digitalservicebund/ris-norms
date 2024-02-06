import { markRaw } from "vue"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisTextButton from "./RisTextButton.vue"

describe("RisTextButton", () => {
  test("renders button with label", () => {
    const { getByText } = render(RisTextButton, {
      props: { label: "Test Button" },
    })
    expect(getByText("Test Button")).toBeInTheDocument()
  })

  test("renders button with icon", () => {
    const FakeIcon = markRaw({
      template: "<span>Fake Icon</span>",
    })

    const { getByText } = render(RisTextButton, {
      props: { icon: FakeIcon },
    })
    expect(getByText("Fake Icon")).toBeInTheDocument()
  })

  test("renders as a link when href is provided", () => {
    const { getByRole } = render(RisTextButton, {
      props: { href: "https://example.com" },
    })
    const link = getByRole("link")
    expect(link).toHaveAttribute("href", "https://example.com")
  })

  test("does not render as a button when href is provided", () => {
    render(RisTextButton, {
      props: { href: "https://example.com", label: "Test Link" },
    })

    const button = screen.queryByRole("button")

    expect(button).not.toBeInTheDocument()
  })

  test("is disabled when disabled prop is true", () => {
    const { getByRole } = render(RisTextButton, {
      props: { disabled: true },
    })
    const button = getByRole("button")
    expect(button).toBeDisabled()
  })
})
