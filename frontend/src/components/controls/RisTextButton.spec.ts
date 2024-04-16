import { render } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisTextButton from "./RisTextButton.vue"
import IcOutlineModeEdit from "~icons/ic/outline-mode-edit"
import { markRaw } from "vue"
import { createRouter, createWebHistory } from "vue-router"

describe("RisTextButton", () => {
  test("renders the button with a label", () => {
    const { getByText } = render(RisTextButton, {
      props: { label: "Test" },
    })

    expect(getByText("Test")).toBeInTheDocument()
  })

  test("renders the button with an icon", () => {
    const { container } = render(RisTextButton, {
      props: { icon: markRaw(IcOutlineModeEdit), label: "Test" },
    })

    expect(container.querySelector("svg")).toBeInTheDocument()
  })

  test("renders the button with an icon and appropriate aria-label when iconOnly is true", () => {
    const { container } = render(RisTextButton, {
      props: {
        icon: markRaw(IcOutlineModeEdit),
        label: "Test",
        iconOnly: true,
      },
    })

    expect(container.querySelector("svg")).toBeInTheDocument()

    const button = container.querySelector('button[aria-label="Test"]')
    expect(button).toBeInTheDocument()
  })

  test("does not render the label when the button is rendered with only an icon", () => {
    const { queryByText, container } = render(RisTextButton, {
      props: {
        icon: markRaw(IcOutlineModeEdit),
        label: "Test",
        iconOnly: true,
      },
    })

    expect(container.querySelector("svg")).toBeInTheDocument()

    const labelText = queryByText("Test")
    expect(labelText).not.toBeInTheDocument()
  })

  test("shows the primary variant", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", variant: "primary" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-secondary")
    expect(button).not.toHaveClass("ds-button-tertiary")
    expect(button).not.toHaveClass("ds-button-ghost")
  })

  test("shows the secondary variant", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", variant: "secondary" },
    })

    const button = getByRole("button")
    expect(button).toHaveClass("ds-button-secondary")
    expect(button).not.toHaveClass("ds-button-tertiary")
    expect(button).not.toHaveClass("ds-button-ghost")
  })

  test("shows the tertiary variant", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", variant: "tertiary" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-secondary")
    expect(button).toHaveClass("ds-button-tertiary")
    expect(button).not.toHaveClass("ds-button-ghost")
  })

  test("shows the ghost variant", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", variant: "ghost" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-secondary")
    expect(button).not.toHaveClass("ds-button-tertiary")
    expect(button).toHaveClass("ds-button-ghost")
  })

  test("shows the primary variant by default", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-secondary")
    expect(button).not.toHaveClass("ds-button-tertiary")
    expect(button).not.toHaveClass("ds-button-ghost")
  })

  test("uses the default size", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", size: "default" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-small")
    expect(button).not.toHaveClass("ds-button-large")
  })

  test("uses the small size", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", size: "small" },
    })

    const button = getByRole("button")
    expect(button).toHaveClass("ds-button-small")
    expect(button).not.toHaveClass("ds-button-large")
  })

  test("uses the large size", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", size: "large" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-small")
    expect(button).toHaveClass("ds-button-large")
  })

  test("uses the default size by default", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test" },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-small")
    expect(button).not.toHaveClass("ds-button-large")
  })

  test("renders an enabled button if disabled is not set", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", disabled: false },
    })

    expect(getByRole("button")).not.toBeDisabled()
  })

  test("renders a disabled button if disabled is set", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", disabled: true },
    })

    expect(getByRole("button")).toBeDisabled()
  })

  test("renders a disabled link button if disabled is set", () => {
    const { container } = render(RisTextButton, {
      props: {
        label: "Test",
        disabled: true,
        to: { href: "https://example.com" },
      },
    })

    // Can't get by accessible role because links without a href are not
    // considered to be links. This is specifically why we remove the
    // href when disabling (links can't technically be disabled).
    expect(container.querySelector("a")).not.toHaveAttribute("href")
  })

  test("renders a flexible button if full width is not set", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", fullWidth: false },
    })

    const button = getByRole("button")
    expect(button).not.toHaveClass("ds-button-full-width")
  })

  test("renders a full width button if full width is set", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", fullWidth: true },
    })

    const button = getByRole("button")
    expect(button).toHaveClass("ds-button-full-width")
  })

  test("renders the button as a router link if a route location is provided", () => {
    const { getByRole } = render(RisTextButton, {
      props: { label: "Test", to: { name: "Example" } },
      global: {
        plugins: [
          createRouter({
            history: createWebHistory(),
            routes: [{ path: "/", name: "Example", component: {} }],
          }),
        ],
      },
    })

    const link = getByRole("link")
    expect(link).toBeInTheDocument()
  })

  test("renders the button as a regular link if a href is provided", () => {
    const { getByRole, queryByRole } = render(RisTextButton, {
      props: {
        label: "Test",
        to: { href: "https://example.com", target: "_blank" },
      },
    })

    expect(getByRole("link")).toBeInTheDocument()
    expect(queryByRole("button")).not.toBeInTheDocument()
  })

  test("assigns the correct href for link buttons", () => {
    const { getByRole } = render(RisTextButton, {
      props: {
        label: "Test",
        to: { href: "https://example.com", target: "_blank" },
      },
    })

    expect(getByRole("link")).toHaveAttribute("href", "https://example.com")
  })

  test("assigns the correct target for link buttons", () => {
    const { getByRole } = render(RisTextButton, {
      props: {
        label: "Test",
        to: { href: "https://example.com", target: "_blank" },
      },
    })

    expect(getByRole("link")).toHaveAttribute("target", "_blank")
  })
})
